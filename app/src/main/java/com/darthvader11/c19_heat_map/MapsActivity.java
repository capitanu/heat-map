package com.darthvader11.c19_heat_map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback/* GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener*/ {

    public GoogleMap mMap;
    private DatabaseReference dbRef;
    public Location location;
    public static MapsActivity instance;
    public LocationManager locationManager;
    public Criteria criteria;
    public Zone[] zones;
    public long maxId = 0;
    boolean markerClicked;
    PolygonOptions polygonOptions;
    Polygon polygon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Toast.makeText(this,"Firebase connection success", Toast.LENGTH_LONG).show();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Polygons");
        //dbRef = fbdb.getReference("message");
        //dbRef.setValue("Hello World!");
        //dbRef.setValue("whatever");
        instance = this;

      //  mMap.setMyLocationEnabled(true);
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setOnMapClickListener(this);
  //      mMap.setOnMapLongClickListener(this);
       // mMap.setOnMarkerClickListener(this);
   //     markerClicked = false;

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng bucharest = new LatLng(44.426972, 26.102528);
        //mMap.addMarker(new MarkerOptions().position(bucharest).title("Marker in Bucharest"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bucharest));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        mMap.setMyLocationEnabled(true);

        mMap.setMyLocationEnabled(true);

        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(15)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }


        Log.v("tag", location.toString());
        SendToDatabase std = new SendToDatabase();
        std.execute();

        PolygonOptions rectOptions1 = new PolygonOptions()
                .add(new LatLng(44.429015,  26.103404),
                        new LatLng(44.429730, 26.099163),
                        new LatLng(44.430022, 26.096822),
                        new LatLng(44.431985, 26.097231),
                        new LatLng(44.434674, 26.098453),
                        new LatLng(44.435277, 26.102237),
                        new LatLng(44.433718, 26.102897),
                        new LatLng(44.431756, 26.103338),
                        new LatLng(44.429015,  26.103404))
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions2 = new PolygonOptions()
                .add(new LatLng(44.430218, 26.096541),
                        new LatLng(44.432796, 26.093682),
                        new LatLng(44.433431, 26.091972),
                        new LatLng(44.433607, 26.089729),
                        new LatLng(44.434515, 26.089893),
                        new LatLng(44.434691, 26.098100),
                        new LatLng(44.434105, 26.098141),
                        new LatLng(44.431790, 26.096910),
                        new LatLng(44.430218, 26.096541))
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions3 = new PolygonOptions()
                .add(new LatLng(44.429970, 26.095334),
                        new LatLng(44.430250, 26.091304),
                        new LatLng(44.432893, 26.091529),
                        new LatLng(44.432581, 26.092704),
                        new LatLng(44.430168, 26.095536),
                        new LatLng(44.429970, 26.095334))
                .fillColor(Color.argb(50, 255, 207, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions4 = new PolygonOptions()
                .add(new LatLng(44.427065, 26.100815),
                        new LatLng(44.427393, 26.092485),
                        new LatLng(44.428048, 26.092147),
                        new LatLng(44.428444, 26.091151),
                        new LatLng(44.430031, 26.091304),
                        new LatLng(44.429757, 26.095212),
                        new LatLng(44.429032, 26.099923),
                        new LatLng(44.428499, 26.100939),
                        new LatLng(44.427065, 26.100815))
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions5 = new PolygonOptions()
                .add(new LatLng(44.435626, 26.102114),
                        new LatLng(44.434943, 26.098288),
                        new LatLng(44.439812, 26.096807),
                        new LatLng(44.440517, 26.099584),
                        new LatLng(44.435626, 26.102114))
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions6 = new PolygonOptions()
                .add(new LatLng(44.434987, 26.097918),
                        new LatLng(44.434701, 26.089803),
                        new LatLng(44.439900, 26.086810),
                        new LatLng(44.439746, 26.089155),
                        new LatLng(44.440583, 26.095696),
                        new LatLng(44.439305, 26.096776),
                        new LatLng(44.434987, 26.097918))
                .fillColor(Color.argb(50, 0, 255, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions7 = new PolygonOptions()
                .add(new LatLng(44.440781, 26.099461),
                        new LatLng(44.440054, 26.096653),
                        new LatLng(44.445429, 26.091161),
                        new LatLng(44.446729, 26.096375),
                        new LatLng(44.440781, 26.099461))
                .fillColor(Color.argb(50, 255, 207 , 0))
                .strokeWidth(0);

        PolygonOptions rectOptions8 = new PolygonOptions()
                .add(new LatLng(44.440803, 26.095326),
                        new LatLng(44.440275, 26.093166),
                        new LatLng(44.439944, 26.089278),
                        new LatLng(44.440142, 26.081842),
                        new LatLng(44.442566, 26.081194),
                        new LatLng(44.443623, 26.086378),
                        new LatLng(44.445341, 26.090914),
                        new LatLng(44.440803, 26.095326))
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions9 = new PolygonOptions()
                .add(new LatLng(44.434680, 26.089621),
                        new LatLng(44.434575, 26.084475),
                        new LatLng(44.439962, 26.081911),
                        new LatLng(44.440067, 26.084696) ,
                        new LatLng(44.439948, 26.086559),
                        new LatLng(44.434680, 26.089621))
                .fillColor(Color.argb(50, 0, 255, 0))
                .strokeWidth(0);

        PolygonOptions rectOptions10 = new PolygonOptions()
                .add(new LatLng(44.433627, 26.089510),
                        new LatLng(44.433943, 26.084475),
                        new LatLng(44.434417, 26.084493),
                        new LatLng(44.434522, 26.089676) ,
                        new LatLng(44.433627, 26.089510))
                .fillColor(Color.argb(50, 255, 207, 0))
                .strokeWidth(0);

// Get back the mutable Polygon
        Polygon polygon1 = mMap.addPolygon(rectOptions1);
        Polygon polygon2 = mMap.addPolygon(rectOptions2);
        Polygon polygon3 = mMap.addPolygon(rectOptions3);
        Polygon polygon4 = mMap.addPolygon(rectOptions4);
        Polygon polygon5 = mMap.addPolygon(rectOptions5);
        Polygon polygon6 = mMap.addPolygon(rectOptions6);
        Polygon polygon7 = mMap.addPolygon(rectOptions7);
        Polygon polygon8 = mMap.addPolygon(rectOptions8);
        Polygon polygon9 = mMap.addPolygon(rectOptions9);
        Polygon polygon10 = mMap.addPolygon(rectOptions10);

        Zone zone1 = new Zone(polygon1, 0, 0);
        Zone zone2 = new Zone(polygon2, 0, 1);
        Zone zone3 = new Zone(polygon3, 0, 2);
        Zone zone4 = new Zone(polygon4, 0, 3);
        Zone zone5 = new Zone(polygon5, 0, 4);
        Zone zone6 = new Zone(polygon6, 0, 5);
        Zone zone7 = new Zone(polygon7, 0, 6);
        Zone zone8 = new Zone(polygon8, 0, 7);
        Zone zone9 = new Zone(polygon9, 0, 8);
        Zone zone10 = new Zone(polygon10, 0, 9);

        zones = new Zone[10];

        zones[0] = zone1;
        zones[1] = zone2;
        zones[2] = zone3;
        zones[3] = zone4;
        zones[4] = zone5;
        zones[5] = zone6;
        zones[6] = zone7;
        zones[7] = zone8;
        zones[8] = zone9;
        zones[9] = zone10;

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxId = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //  for(int i = 0; i < 10; i++){
        //    dbRef.child(String.valueOf(maxId + i)).setValue(zones[i]);
        //}

        zones[3].polygon.setFillColor(Color.argb(255,255,255,10));
        dbRef.child(String.valueOf(zones[3].id)).setValue(zones[3]);





    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


   /* @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getApplicationContext(),
                "Long Press to select locations", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onMapLongClick(LatLng point) {
        mMap.addMarker(new MarkerOptions()
                .position(point).title(point.toString()));

        markerClicked = false;
    }*/

    /*@Override
    public boolean onMarkerClick(Marker marker) {
        if(markerClicked)
        {
            if(polygon != null)
            {
                polygon.remove();
                polygon = null;
            }

            polygonOptions.add(marker.getPosition());
            polygonOptions.strokeColor(Color.BLACK);
            polygonOptions.strokeWidth(5);
            polygonOptions.fillColor(0x884d4d4d);

            polygon = mMap.addPolygon(polygonOptions);
            marker.remove();

        }
        else
        {
            if(polygon != null)
            {
                polygon.remove();
                polygon = null;
            }

            polygonOptions = new PolygonOptions().add(marker.getPosition());
            markerClicked = true;
            marker.remove();
        }

        return true;
    }*/

}
