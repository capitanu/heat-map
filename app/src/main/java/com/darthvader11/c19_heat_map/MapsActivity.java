package com.darthvader11.c19_heat_map;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback/* GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener*/ {

    private GoogleMap mMap;
    List<Polygon> polyList = new ArrayList<>();


    private FirebaseDatabase fbdb;
    private DatabaseReference dbRef;
    public Location location;
    public static MapsActivity instance;
    public LocationManager locationManager;
    public Criteria criteria;

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


        Toast.makeText(this, "Firebase connection success", Toast.LENGTH_LONG).show();
        //fbdb = FirebaseDatabase.getInstance();
        // dbRef = fbdb.getReference("message");
        //dbRef.setValue("Hello World!");
      //   dbRef.setValue("whatever");
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
        mMap.setMyLocationEnabled(true);

        mMap.setMyLocationEnabled(true);

        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null) {
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

        /*PolygonOptions rectOptions1 = new PolygonOptions()
                .add(new LatLng(44.429015, 26.103404),
                        new LatLng(44.429730, 26.099163),
                        new LatLng(44.430022, 26.096822),
                        new LatLng(44.431985, 26.097231),
                        new LatLng(44.434674, 26.098453),
                        new LatLng(44.435277, 26.102237),
                        new LatLng(44.433718, 26.102897),
                        new LatLng(44.431756, 26.103338),
                        new LatLng(44.429015, 26.103404))
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
                        new LatLng(44.440067, 26.084696),
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
                .strokeWidth(0)
        ;

//  Get back the mutable Polygon
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

*/      List<LatLng> newPolygon1 = new ArrayList<>();
        newPolygon1.add(new LatLng(44.429015, 26.103404));
        newPolygon1.add (new LatLng(44.429730, 26.099163));
        newPolygon1.add  (new LatLng(44.430022, 26.096822));
        newPolygon1.add (new LatLng(44.431985, 26.097231));
        newPolygon1.add(new LatLng(44.434674, 26.098453));
        newPolygon1.add (new LatLng(44.435277, 26.102237));
        newPolygon1.add (new LatLng(44.433718, 26.102897));
        newPolygon1.add (new LatLng(44.431756, 26.103338));
        newPolygon1.add (new LatLng(44.429015, 26.103404));


        List<LatLng> newPolygon2 = new ArrayList<>();
        newPolygon2.add(new LatLng(44.430218, 26.096541));
        newPolygon2.add (new LatLng(44.432796, 26.093682));
        newPolygon2.add  (new LatLng(44.433431, 26.091972));
        newPolygon2.add (new LatLng(44.433607, 26.089729));
        newPolygon2.add(new LatLng(44.434515, 26.089893));
        newPolygon2.add (new LatLng(44.434691, 26.098100));
        newPolygon2.add (new LatLng(44.434105, 26.098141));
        newPolygon2.add (new LatLng(44.431790, 26.096910));
        newPolygon2.add (new LatLng(44.430218, 26.096541));

        List<LatLng> newPolygon3 = new ArrayList<>();
        newPolygon3.add(new LatLng(44.429970, 26.095334));
        newPolygon3.add (new LatLng(44.430250, 26.091304));
        newPolygon3.add  (new LatLng(44.432893, 26.091529));
        newPolygon3.add (new LatLng(44.432581, 26.092704));
        newPolygon3.add(new LatLng(44.430168, 26.095536));
        newPolygon3.add (new LatLng(44.429970, 26.095334));

        List<LatLng> newPolygon4 = new ArrayList<>();
        newPolygon4.add(new LatLng(44.427065, 26.100815));
        newPolygon4.add (new LatLng(44.427393, 26.092485));
        newPolygon4.add  (new LatLng(44.428048, 26.092147));
        newPolygon4.add (new LatLng(44.428444, 26.091151));
        newPolygon4.add( new LatLng(44.430031, 26.091304));
        newPolygon4.add (new LatLng(44.429757, 26.095212));
        newPolygon4.add (new LatLng(44.429032, 26.099923));
        newPolygon4.add (new LatLng(44.428499, 26.100939));
        newPolygon4.add (new LatLng(44.427065, 26.100815));

        List<LatLng> newPolygon5 = new ArrayList<>();
        newPolygon5.add(new LatLng(44.435626, 26.102114));
        newPolygon5.add (new LatLng(44.434943, 26.098288));
        newPolygon5.add  (new LatLng(44.439812, 26.096807));
        newPolygon5.add (new LatLng(44.440517, 26.099584));
        newPolygon5.add(new LatLng(44.435626, 26.102114));

        List<LatLng> newPolygon6 = new ArrayList<>();
        newPolygon6.add(new LatLng(44.434987, 26.097918));
        newPolygon6.add (new LatLng(44.434701, 26.089803));
        newPolygon6.add  (new LatLng(44.439900, 26.086810));
        newPolygon6.add (new LatLng(44.439746, 26.089155));
        newPolygon6.add( new LatLng(44.440583, 26.095696));
        newPolygon6.add (new LatLng(44.439305, 26.096776));
        newPolygon6.add (new LatLng(44.434987, 26.097918));

        List<LatLng> newPolygon7 = new ArrayList<>();
        newPolygon7.add(new LatLng(44.440781, 26.099461));
        newPolygon7.add(new LatLng(44.440054, 26.096653));
        newPolygon7.add(new LatLng(44.445429, 26.091161));
        newPolygon7.add(new LatLng(44.446729, 26.096375));

        List<LatLng> newPolygon8 = new ArrayList<>();
        newPolygon8.add(new LatLng(44.440803, 26.095326));
        newPolygon8.add (new LatLng(44.440275, 26.093166));
        newPolygon8.add  (new LatLng(44.439944, 26.089278));
        newPolygon8.add (new LatLng(44.440142, 26.081842));
        newPolygon8.add( new LatLng(44.442566, 26.081194));
        newPolygon8.add (new LatLng(44.443623, 26.086378));
        newPolygon8.add (new LatLng(44.445341, 26.090914));
        newPolygon8.add (new LatLng(44.440803, 26.095326));

        List<LatLng> newPolygon9 = new ArrayList<>();
        newPolygon9.add(new LatLng(44.434680, 26.089621));
        newPolygon9.add (new LatLng(44.434575, 26.084475));
        newPolygon9.add  (new LatLng(44.439962, 26.081911));
        newPolygon9.add (new LatLng(44.440067, 26.084696));
        newPolygon9.add( new LatLng(44.439948, 26.086559));
        newPolygon9.add (new LatLng(44.434680, 26.089621));

        List<LatLng> newPolygon10 = new ArrayList<>();
        newPolygon10.add(new LatLng(44.433627, 26.089510));
        newPolygon10.add(new LatLng(44.433943, 26.084475));
        newPolygon10.add(new LatLng(44.434417, 26.084493));
        newPolygon10.add(new LatLng(44.434522, 26.089676));
        newPolygon10.add(new LatLng(44.433627, 26.089510));
        

        Polygon p1 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon1)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 0, 250, 0)));

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
                .fillColor(Color.argb(50, 0, 250, 0)));

        Polygon p6 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon6)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 0, 250, 0)));

        Polygon p7= mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon7)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p8= mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon8)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p9= mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon9)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));

        Polygon p10 = mMap.addPolygon(new PolygonOptions()
                .addAll(newPolygon10)
                .strokeWidth(0)
                .fillColor(Color.argb(50, 255, 207, 0)));


       /* polyList.add(polygon1);
        polyList.add(polygon2);
        polyList.add(polygon3);
        polyList.add(polygon4);
        polyList.add(polygon5);
        polyList.add(polygon6);
        polyList.add(polygon7);
        polyList.add(polygon8);
        polyList.add(polygon9);
        polyList.add(polygon10);*/

        polyList.add(p1);
        polyList.add(p2);
        polyList.add(p3);
        polyList.add(p4);
        polyList.add(p5);
        polyList.add(p6);
        polyList.add(p7);
        polyList.add(p8);
        polyList.add(p9);
        polyList.add(p10);

        for (Polygon pObj : polyList) {
            //find Polygon user tapped inside of
            if (PolyUtil.containsLocation(new LatLng(location.getLatitude(), location.getLongitude()), pObj.getPoints(), true)) {
                //first case, no Marker
                Toast.makeText(this,
                        "you are not inside", Toast.LENGTH_LONG).show();
            }


        }
    }


        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
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

