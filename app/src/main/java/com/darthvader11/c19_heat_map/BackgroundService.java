package com.darthvader11.c19_heat_map;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class BackgroundService  extends Service {

        private static final int NOTIF_ID = 2;
        private static final String NOTIF_CHANNEL_ID = "Channel_Id";

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){

            Runnable std = new SendToDatabase();
            Thread thread = new Thread(std);
            thread.start();

            startForeground();

            return super.onStartCommand(intent, flags, startId);
        }

        private void startForeground() {



            Intent intent = new Intent("close_app");
            PendingIntent pIntent = PendingIntent.getBroadcast(this, (int)
                    System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            createNotificationChannel();
            startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                    NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                    .setOngoing(true)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Service is running background")
                    .addAction(R.drawable.notification_icon,  "exit", pIntent)
                    .setContentIntent(pIntent)
                    .build());

            IntentFilter filter = new IntentFilter();
            filter.addAction("close_app");
            registerReceiver(mReceiver, filter);

        }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG123" ,"onReceive ");
            finish();
        }
    };
        public int i;
        public DatabaseReference reff;
        public void finish(){
            reff = FirebaseDatabase.getInstance().getReference().child("Polygons");
            for (i = 0; i < MapsActivity.instance.polyList.size(); i++) {
                try {
                    if (PolyUtil.containsLocation(new LatLng(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude()), MapsActivity.instance.polyList.get(i).polygon.getPoints(), true)) {
                            MapsActivity.instance.i = i;
                            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    int users2 = 0;
                                    Log.v("THISSHOULDHAPPEN", "This happened");
                                    users2 = dataSnapshot.child(String.valueOf(MapsActivity.instance.i)).child("users").getValue(Integer.class);
                                    users2--;
                                    reff.child(String.valueOf(MapsActivity.instance.i)).child("users").setValue(users2);
                                    MapsActivity.instance.previousZone = MapsActivity.instance.i;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                    catch(Exception e){ e.printStackTrace(); }
                }
            this.stopSelf();
            }




    private void createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test" ;
            //getString(R.string.channel_name);
            String description = "another test";
            //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
