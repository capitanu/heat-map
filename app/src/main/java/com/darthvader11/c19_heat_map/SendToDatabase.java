package com.darthvader11.c19_heat_map;

import  android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SendToDatabase implements Runnable {

    public String CHANNEL_ID = "Warning";

    public boolean isRunning = true;
    public DatabaseReference reff;
    public static int i;
    public static int previousZoneCheck = 100;
    public static int previousZone = -1;
    public static int nrOfUsers;
    public ArrayList<Zone> zoneList;

    @SuppressLint("MissingPermission")
    public void update() {
        //MapsActivity.instance.test();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int i = 0; i < MapsActivity.instance.polyList.size(); i++) {
                    int pl = dataSnapshot.child(String.valueOf(i)).child("polygon").child("fillColor").getValue(Integer.class);
                    MapsActivity.instance.polyList.get(i).polygon.setFillColor(pl);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void run(){
        reff = FirebaseDatabase.getInstance().getReference().child("Polygons");
        while(isRunning){ // isRunning is a boolean variable
            SystemClock.sleep(2000);
            checkAndUpdate();
            update();
        }
    }
    public void checkAndUpdate(){
            MapsActivity.instance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    float []distance = new float[2];
                    try {
                        Location.distanceBetween(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude(), MapsActivity.instance.ck.getCenter().latitude, MapsActivity.instance.ck.getCenter().longitude, distance);
                    }
                    catch(Exception e){
                        MapsActivity.instance.ck = MapsActivity.instance.mMap.addCircle(new CircleOptions()
                                 .radius(1)
                                 .center(new LatLng(MapsActivity.instance.location.getLatitude() + 20, MapsActivity.instance.location.getLongitude()))
                        );
                    }
                    if(distance[0] > MapsActivity.instance.ck.getRadius())
                    for (i = 0; i < MapsActivity.instance.polyList.size(); i++) {
                        MapsActivity.instance.isHome = false;
                        try {
                            if (PolyUtil.containsLocation(new LatLng(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude()), MapsActivity.instance.polyList.get(i).polygon.getPoints(), true)) {
                                MapsActivity.instance.i = i;
                                if (previousZoneCheck != i) {
                                    previousZoneCheck = i;
                                    //MapsActivity.instance.previousZone = previousZone;
                                    reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int users = dataSnapshot.child(String.valueOf(MapsActivity.instance.i)).child("users").getValue(Integer.class);
                                            users++;
                                            SendToDatabase.nrOfUsers = users;
                                            reff.child(String.valueOf(MapsActivity.instance.i)).child("users").setValue(users);
                                            if(users >= 2){
                                                //MapsActivity.instance.polyList.get(MapsActivity.instance.i).polygon.setFillColor(Color.argb(70, 255, 0, 0));
                                                reff.child(String.valueOf(MapsActivity.instance.i)).child("polygon").child("fillColor").setValue(Color.argb(70, 255, 0, 0));
                                                MapsActivity.instance.sendRedNotification();
                                            }
                                            else if(users >= 1){
                                                //MapsActivity.instance.polyList.get(MapsActivity.instance.i).polygon.setFillColor(Color.argb(50, 255, 207, 0));
                                                reff.child(String.valueOf(MapsActivity.instance.i)).child("polygon").child("fillColor").setValue(Color.argb(50, 255, 207, 0));
                                                MapsActivity.instance.sendYellowNotification();
                                            }
                                            if(MapsActivity.instance.previousZone != -1) {
                                                Log.v("previous", String.valueOf(MapsActivity.instance.previousZone));
                                                int users2 = dataSnapshot.child(String.valueOf(MapsActivity.instance.previousZone)).child("users").getValue(Integer.class);
                                                users2--;
                                                reff.child(String.valueOf(MapsActivity.instance.previousZone)).child("users").setValue(users2);
                                            }
                                            MapsActivity.instance.previousZone = MapsActivity.instance.i;

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        if(MapsActivity.instance.isHome == false)
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(MapsActivity.instance.previousZone != -1) {
                                    Log.v("previous", String.valueOf(MapsActivity.instance.previousZone));
                                    int users2 = dataSnapshot.child(String.valueOf(MapsActivity.instance.previousZone)).child("users").getValue(Integer.class);
                                    users2--;
                                    reff.child(String.valueOf(MapsActivity.instance.previousZone)).child("users").setValue(users2);
                                }
                                MapsActivity.instance.previousZone = MapsActivity.instance.i;

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        MapsActivity.instance.isHome = true;
                    }
                }
            });
    }

}


