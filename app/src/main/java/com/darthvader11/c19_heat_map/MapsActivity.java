package com.darthvader11.c19_heat_map;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.Circle;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.maps.android.PolyUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;

import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener {

    public GoogleMap mMap;
    public ArrayList<Zone> polyList = new ArrayList<>();
    private DatabaseReference dbRef;
    public Location location;
    public static MapsActivity instance;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public long maxId = 0;
    public String CHANNEL_ID = "test";
    public int i;
    public int previousZone = -1;
    public Thread main;
    public int semaphore = 0;
    BackgroundService mService = null;
    boolean mBound = false;
    boolean once = true;
    TextInputEditText mSearchText;
    Marker mk;
    Circle ck;
    public boolean isHome = false;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String HOME_LAT = "hLat";
    public static final String HOME_LNG = "hLng";
    Criteria criteria;
    LocationManager locationManager;
    public SharedPreferences.Editor editor;


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("CCDev", "onserviceconnectd");
            BackgroundService.LocalBinder binder = (BackgroundService.LocalBinder)service;
            mService = binder.getService();
            mBound = true;
            mService.requestLocationUpdates();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);
        Button btnSetHome = findViewById(R.id.btnSet);
        Button btnSetCircle = findViewById(R.id.btnSetCurrent);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        btnSetCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ck != null)
                        ck.remove();
                    ck = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(location.getLatitude(), location.getLongitude()))
                            .radius(100)
                            .fillColor(Color.argb(50, 30, 30, 150))
                            .strokeWidth(0)
                    );
                    //editor.putFloat(HOME_LAT, (float) location.getLatitude());
                    //editor.putFloat(HOME_LONG, (float) location.getLongitude());

                    mk.remove();

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                editor.putFloat(HOME_LAT, (float) location.getLatitude());
                editor.putFloat(HOME_LNG, (float) location.getLongitude());
                editor.apply();
                Log.v("CCDev", "Added to the shared preferences");
            }
        });
        
        btnSetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ck != null)
                    ck.remove();
                if(mk != null) {
                    ck = mMap.addCircle(new CircleOptions()
                            .center(mk.getPosition())
                            .radius(100)
                            .fillColor(Color.argb(50, 30, 30, 150))
                            .strokeWidth(0)
                    );
                    //editor.putFloat(HOME_LAT, (float) location.getLatitude());
                   // editor.putFloat(HOME_LONG, (float) location.getLongitude());

                    mk.remove();
                }
                editor.putFloat(HOME_LAT, (float)  mk.getPosition().latitude);
                editor.putFloat(HOME_LNG, (float)  mk.getPosition().longitude);
                editor.apply();
                Log.v("CCDev", "Added to the shared preferences");

            }
        });

        if(checkAndRequestPermissions()) {
                // carry on the normal flow, as the case of  permissions  granted.
            mapFragment.getMapAsync(this);
            bindService(new Intent(MapsActivity.this, BackgroundService.class),
                    mServiceConnection,
                    Context.BIND_AUTO_CREATE);
        }

        dbRef = FirebaseDatabase.getInstance().getReference().child("Polygons");
        instance = this;
        main = Thread.currentThread();
        criteria = new Criteria();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng bucharest = new LatLng(44.426972, 26.102528);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bucharest));

        mMap.setMyLocationEnabled(true);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(mk != null)
                    mk.remove();
                mk = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Home Address"));
            }
        });



        //double lat = sharedPreferences.getFloat(HOME_LAT, -1);
        //double log = sharedPreferences.getFloat(HOME_LONG, -1);
/*
        if(ck != null)
            ck.remove();
        ck = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat,log))
                    .radius(100)
                    .fillColor(Color.argb(50, 30, 30, 150))
                    .strokeWidth(0)
            );

        /*
        Set all to zero
         */




        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        float lng = sharedPreferences.getFloat(HOME_LNG, -1);
        float lat = sharedPreferences.getFloat(HOME_LAT, -1);
        Log.v("CCDev",String.valueOf(lng));
        Log.v("CCDev",String.valueOf(lat));
        if(lat != -1 && lng != -1) {
            if (ck != null)
                ck.remove();
            ck = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat, lng))
                .radius(100)
                .fillColor(Color.argb(50, 30, 30, 150))
                .strokeWidth(0));

        }




        Poly.instantiate();
        for (int i = 0; i < Poly.listOfpoints.size(); i++)
            polyList.add(new Zone(Poly.listOfPolygons.get(i), 0, 0));


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxId = (dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




            for(int i = 0; i < 70; i++){
                dbRef.child(String.valueOf(maxId + i)).setValue(polyList.get(i));
            }





        if(mService != null){
            Log.i("ATLEASTHERE", "pls");
        }
        Log.v("TEST","TESSSTING");


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        ProcessPhoenix.triggerRebirth(this);

                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void sendYellowNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MapsActivity.instance, MapsActivity.instance.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        createNotificationChannel();
        builder.setContentTitle("Moderate risk zone!");
        builder.setContentText("Avoid contact with crowded areas");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MapsActivity.instance);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    public void sendRedNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MapsActivity.instance, MapsActivity.instance.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        createNotificationChannel();
        builder.setContentTitle("Extremely high risk zone!");
        builder.setContentText("It is highly advised you keep distance");
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MapsActivity.instance);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    public void createNotificationChannel(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test1" ;
                    //getString(R.string.channel_name);
            String description = "another test1";
                    //getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(Common.KEY_REQUESTING_LOCATION_UPDATES)){
            setButtonState(sharedPreferences.getBoolean(Common.KEY_REQUESTING_LOCATION_UPDATES, false));
        }
    }

    private void setButtonState(boolean aBoolean) {
        if(aBoolean){
            //requestLocation.setEnabled(false);
            //
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onListenLocation(SendLocationToActivity event){
        if(event != null){
            String data = new StringBuilder()
                    .append(event.getLocation().getLatitude())
                    .append("/")
                    .append(event.getLocation().getLongitude())
                    .toString();
            //Toast.makeText(mService, data, Toast.LENGTH_SHORT).show();
            location = event.getLocation();
            if(once == true) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                once = false;
            }
        }
    }

}



