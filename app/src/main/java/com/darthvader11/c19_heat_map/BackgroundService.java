package com.darthvader11.c19_heat_map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class BackgroundService  extends Service {

        private static final int NOTIF_ID = 1223;
        private static final String NOTIF_CHANNEL_ID = "Channel_Id";
        private static final int UPDATE_INTERVAL = 5000;
        private static final int FASTEST_UPDATE_INTERVAL = 2500;
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = "com.darthvader11.c19_heat_map" + ".started_from_notification";
    private boolean mChangingConfiguration = false;
        private NotificationManager mNotificationManager;

        private LocationRequest locationRequest;
        private FusedLocationProviderClient fusedLocationProviderClient;
        private LocationCallback locationCallback;
        private Handler mServiceHandler;
        private Location mLocation;

        private final IBinder mBinder = new LocalBinder();

    public void requestLocationUpdates() {
        Log.i("test", "got here");
        Common.setRequestingLocationUpdates(this,true);
        startService(new Intent(getApplicationContext(), BackgroundService.class));
        try{
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }catch(SecurityException ex){
            Log.e("CCDev", "Lost location permission" + ex);
        }

    }



    public class LocalBinder extends Binder {
            BackgroundService getService(){return BackgroundService.this;}
        }

        public BackgroundService(){}

    @Override
    public void onCreate() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };


        createLocationRequest();
        getLastLocation();

        HandlerThread handlerThread = new HandlerThread("CCDev");
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(NOTIF_CHANNEL_ID,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        Runnable std = new SendToDatabase();
        Thread thread = new Thread(std);
        thread.start();


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            boolean startFromNotification = intent.getBooleanExtra(EXTRA_STARTED_FROM_NOTIFICATION, false);
            if(startFromNotification){
                removeLocationUpdates();
                stopSelf();
            }


            return START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    public void removeLocationUpdates() {
            try{
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                Common.setRequestingLocationUpdates(this,false);
                stopSelf();
            }
            catch(SecurityException ex){
                Common.setRequestingLocationUpdates(this,true);
                Log.e("CCDev", "Lost location permission. Could not remove updates" + ex);
            }
    }

    private void getLastLocation() {
            try{
                fusedLocationProviderClient.getLastLocation()
                        .addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if(task.isSuccessful() && task.getResult() != null)
                                    mLocation = task.getResult();
                                else
                                    Log.v("CCDev", "Failed to get location");
                            }
                        });
            }
            catch(SecurityException ex){
                Log.v("CCDev", "Lost location permission" + ex);
            }
    }

    private void createLocationRequest() {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(UPDATE_INTERVAL);
            locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void onNewLocation(Location lastLocation) {
            mLocation = lastLocation;
            Log.i("location", mLocation.toString());
            EventBus.getDefault().postSticky(new SendLocationToActivity(mLocation));


            //Update notification content if running in foreground
            MapsActivity.instance.location = mLocation;
            //if(serviceIsRunningInForeground(this))
            //    mNotificationManager.notify(NOTIF_ID, getNotification());
    }

    private Notification getNotification() {
            Intent intent = new Intent(this, BackgroundService.class);
            String text = Common.getLocationText(mLocation);

            intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);
            PendingIntent servicePendingIntent = PendingIntent.getService(this, 0 , intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, MapsActivity.class), 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .addAction(R.drawable.ic_stat_name, "Launch", activityPendingIntent)
                    .addAction(R.drawable.ic_stat_name, "Exit", servicePendingIntent)
                    .setContentText("Process running in background")
                    .setContentTitle("Pass by")
                    .setOngoing(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setTicker(text)
                    .setWhen(System.currentTimeMillis());

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                builder.setChannelId(NOTIF_CHANNEL_ID);
            }
            return builder.build();
    }

    private boolean serviceIsRunningInForeground(Context context) {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
                if(getClass().getName().equals(service.service.getClassName()))
                    if(service.foreground)
                        return true;
            return false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
            stopForeground(true);
            mChangingConfiguration = false;
            return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
            stopForeground(true);
            mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if(!mChangingConfiguration && Common.requestingLocationUpdates(this))
            startForeground(NOTIF_ID,getNotification());
        return true;
    }

    public DatabaseReference reff;
    @Override
    public void onDestroy() {
        reff = FirebaseDatabase.getInstance().getReference().child("Polygons");
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (MapsActivity.instance.i != -1) {
                    Log.v("previous", String.valueOf(MapsActivity.instance.i));
                    int users2 = dataSnapshot.child(String.valueOf(MapsActivity.instance.i)).child("users").getValue(Integer.class);
                    users2--;
                    reff.child(String.valueOf(MapsActivity.instance.i)).child("users").setValue(users2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        mServiceHandler.removeCallbacks(null);
        super.onDestroy();
    }
}
