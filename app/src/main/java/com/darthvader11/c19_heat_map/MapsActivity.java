package com.darthvader11.c19_heat_map;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.google.maps.android.PolyUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.ValueEventListener;


import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public ArrayList<Zone> polyList = new ArrayList<>();
    private DatabaseReference dbRef;
    public Location location;
    public static MapsActivity instance;
    public LocationManager locationManager;
    public Criteria criteria;
    public long maxId = 0;
    public String CHANNEL_ID = "test";
    public int i;
    public int previousZone = -1;
    public Thread main;
    public int semaphore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        dbRef = FirebaseDatabase.getInstance().getReference().child("Polygons");
        instance = this;
        main = Thread.currentThread();



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng bucharest = new LatLng(44.426972, 26.102528);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bucharest));


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
        mMap.setMyLocationEnabled(true);


        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


      //  Log.v("tag", location.toString());
        startService(new Intent(this, BackgroundService.class));



        List<LatLng> newPolygon1 = new ArrayList<>();
        newPolygon1.add(new LatLng(44.429015, 26.103404));
        newPolygon1.add(new LatLng(44.429730, 26.099163));
        newPolygon1.add(new LatLng(44.430022, 26.096822));
        newPolygon1.add(new LatLng(44.431985, 26.097231));
        newPolygon1.add(new LatLng(44.434674, 26.098453));
        newPolygon1.add(new LatLng(44.435277, 26.102237));
        newPolygon1.add(new LatLng(44.433718, 26.102897));
        newPolygon1.add(new LatLng(44.431756, 26.103338));
        newPolygon1.add(new LatLng(44.429015, 26.103404));


        List<LatLng> newPolygon2 = new ArrayList<>();
        newPolygon2.add(new LatLng(44.430218, 26.096541));
        newPolygon2.add(new LatLng(44.432796, 26.093682));
        newPolygon2.add(new LatLng(44.433431, 26.091972));
        newPolygon2.add(new LatLng(44.433607, 26.089729));
        newPolygon2.add(new LatLng(44.434515, 26.089893));
        newPolygon2.add(new LatLng(44.434691, 26.098100));
        newPolygon2.add(new LatLng(44.434105, 26.098141));
        newPolygon2.add(new LatLng(44.431790, 26.096910));
        newPolygon2.add(new LatLng(44.430218, 26.096541));

        List<LatLng> newPolygon3 = new ArrayList<>();
        newPolygon3.add(new LatLng(44.429970, 26.095334));
        newPolygon3.add(new LatLng(44.430250, 26.091304));
        newPolygon3.add(new LatLng(44.432893, 26.091529));
        newPolygon3.add(new LatLng(44.432581, 26.092704));
        newPolygon3.add(new LatLng(44.430168, 26.095536));
        newPolygon3.add(new LatLng(44.429970, 26.095334));

        List<LatLng> newPolygon4 = new ArrayList<>();
        newPolygon4.add(new LatLng(44.427065, 26.100815));
        newPolygon4.add(new LatLng(44.427393, 26.092485));
        newPolygon4.add(new LatLng(44.428048, 26.092147));
        newPolygon4.add(new LatLng(44.428444, 26.091151));
        newPolygon4.add(new LatLng(44.430031, 26.091304));
        newPolygon4.add(new LatLng(44.429757, 26.095212));
        newPolygon4.add(new LatLng(44.429032, 26.099923));
        newPolygon4.add(new LatLng(44.428499, 26.100939));
        newPolygon4.add(new LatLng(44.427065, 26.100815));

        List<LatLng> newPolygon5 = new ArrayList<>();
        newPolygon5.add(new LatLng(44.435626, 26.102114));
        newPolygon5.add(new LatLng(44.434943, 26.098288));
        newPolygon5.add(new LatLng(44.439812, 26.096807));
        newPolygon5.add(new LatLng(44.440517, 26.099584));
        newPolygon5.add(new LatLng(44.435626, 26.102114));

        List<LatLng> newPolygon6 = new ArrayList<>();
        newPolygon6.add(new LatLng(44.434987, 26.097918));
        newPolygon6.add(new LatLng(44.434701, 26.089803));
        newPolygon6.add(new LatLng(44.439900, 26.086810));
        newPolygon6.add(new LatLng(44.439746, 26.089155));
        newPolygon6.add(new LatLng(44.440583, 26.095696));
        newPolygon6.add(new LatLng(44.439305, 26.096776));
        newPolygon6.add(new LatLng(44.434987, 26.097918));

        List<LatLng> newPolygon7 = new ArrayList<>();
        newPolygon7.add(new LatLng(44.440781, 26.099461));
        newPolygon7.add(new LatLng(44.440054, 26.096653));
        newPolygon7.add(new LatLng(44.445429, 26.091161));
        newPolygon7.add(new LatLng(44.446729, 26.096375));

        List<LatLng> newPolygon8 = new ArrayList<>();
        newPolygon8.add(new LatLng(44.440803, 26.095326));
        newPolygon8.add(new LatLng(44.440275, 26.093166));
        newPolygon8.add(new LatLng(44.439944, 26.089278));
        newPolygon8.add(new LatLng(44.440142, 26.081842));
        newPolygon8.add(new LatLng(44.442566, 26.081194));
        newPolygon8.add(new LatLng(44.443623, 26.086378));
        newPolygon8.add(new LatLng(44.445341, 26.090914));
        newPolygon8.add(new LatLng(44.440803, 26.095326));

        List<LatLng> newPolygon9 = new ArrayList<>();
        newPolygon9.add(new LatLng(44.434680, 26.089621));
        newPolygon9.add(new LatLng(44.434575, 26.084475));
        newPolygon9.add(new LatLng(44.439962, 26.081911));
        newPolygon9.add(new LatLng(44.440067, 26.084696));
        newPolygon9.add(new LatLng(44.439948, 26.086559));
        newPolygon9.add(new LatLng(44.434680, 26.089621));

        List<LatLng> newPolygon10 = new ArrayList<>();
        newPolygon10.add(new LatLng(44.433627, 26.089510));
        newPolygon10.add(new LatLng(44.433943, 26.084475));
        newPolygon10.add(new LatLng(44.434417, 26.084493));
        newPolygon10.add(new LatLng(44.434522, 26.089676));
        newPolygon10.add(new LatLng(44.433627, 26.089510));


        Polygon p1 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon1)
                .strokeWidth(0)
                .fillColor(Color.argb(70, 255, 0, 0)));

        Polygon p2 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon2)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 0, 250, 0)));

        Polygon p3 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon3)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 0, 250, 0)));

        Polygon p4 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon4)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 0, 250, 0)));

        Polygon p5 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon5)
                .strokeWidth(0)
                .fillColor(Color.argb(80, 255, 75, 0)));

        Polygon p6 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon6)
                .strokeWidth(0)
                .fillColor(Color.argb(80, 255, 75, 0)));

        Polygon p7 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon7)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p8 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon8)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p9 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon9)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p10 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon10)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));


        polyList.add(new Zone(p1, 0));
        polyList.add(new Zone(p2, 0));
        polyList.add(new Zone(p3, 0));
        polyList.add(new Zone(p4, 0));
        polyList.add(new Zone(p5, 0));
        polyList.add(new Zone(p6, 0));
        polyList.add(new Zone(p7, 0));
        polyList.add(new Zone(p8, 0));
        polyList.add(new Zone(p9, 0));
        polyList.add(new Zone(p10, 0));


        /*String textTitle = "Red zone";
        String textContent = "fuck this";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        createNotificationChannel();
        for (Zone pObj : MapsActivity.instance.polyList) {
            if (PolyUtil.containsLocation(new LatLng(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude()), pObj.polygon.getPoints(), true)) {

                if(pObj.polygon.getFillColor() == Color.argb(70, 255, 0, 0)){
                    builder.setContentTitle("Extremely high risk zone!");
                    builder.setContentText("We would recommend you move out of this area, as it is crowded currently.");
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());
                }
                if(pObj.polygon.getFillColor() == Color.argb(80, 255, 75, 0)){
                    builder.setContentTitle("High risk zone!");
                    builder.setContentText("We would recommend you move out of this area, as it is crowded currently.");
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());
                }

                if(pObj.polygon.getFillColor() == Color.argb(50, 0, 250, 0)){
                    pObj.polygon.setFillColor(Color.argb(50, 255, 207, 0));
                }


            }
        }
*/



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

        //dbRef.setValue(polyList);
        for(int i = 0; i < 10; i++){
            dbRef.child(String.valueOf(maxId + i)).setValue(polyList.get(i));
        }



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
                .setSmallIcon(R.drawable.notification_icon)
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
                .setSmallIcon(R.drawable.notification_icon)
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


    public boolean checkIfTrue(int i){
        Log.v("checkiftrue", "yyeye");
        return PolyUtil.containsLocation(new LatLng(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude()), MapsActivity.instance.polyList.get(i).polygon.getPoints(), true);
    }




}



