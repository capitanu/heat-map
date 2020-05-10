package com.darthvader11.c19_heat_map;

import  android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SendToDatabase implements Runnable {

    public boolean isRunning = true;
    public int users;
    public DatabaseReference reff;
    public DatabaseReference reff2;
    public static int i;
    public boolean flag;
    public static int previousZoneCheck = 100;
    public static int previousZone = -1;


    @SuppressLint("MissingPermission")
    public void update() {
        MapsActivity.instance.location = MapsActivity.instance.locationManager.getLastKnownLocation(MapsActivity.instance.locationManager.getBestProvider(MapsActivity.instance.criteria, false));
        //MapsActivity.instance.test();
    }

    public void run(){
        while(isRunning){ // isRunning is a boolean variable
            SystemClock.sleep(2000);
            checkAndUpdate();
            update();
        }
    }
    public void checkAndUpdate(){

            MapsActivity.instance.semaphore = 0;
            MapsActivity.instance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (i = 0; i < MapsActivity.instance.polyList.size(); i++) {
                        //flag = MapsActivity.instance.checkIfTrue(i);
                        MapsActivity.instance.semaphore = 1;
                        if (PolyUtil.containsLocation(new LatLng(MapsActivity.instance.location.getLatitude(), MapsActivity.instance.location.getLongitude()), MapsActivity.instance.polyList.get(i).polygon.getPoints(), true)) {
                            reff = FirebaseDatabase.getInstance().getReference().child("Polygons").child(String.valueOf(SendToDatabase.i));
                            //System.out.println("i is " + i);
                            //System.out.println("previous thing " + previousZoneCheck);
                            if(previousZoneCheck != i){
                                //System.out.println("this thing happened");
                                previousZoneCheck = i;
                                reff2 = FirebaseDatabase.getInstance().getReference().child("Polygons").child(String.valueOf(SendToDatabase.previousZone));
                                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        int users = dataSnapshot.child("users").getValue(Integer.class);
                                        users++;
                                        System.out.println(users);
                                        reff.child("users").setValue(users);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                if(previousZone != -1)
                                reff2.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       int users = dataSnapshot.child("users").getValue(Integer.class);
                                       System.out.println(users);
                                        users--;
                                        reff2.child("users").setValue(users);
                                 }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                                previousZone = i;
                            }
                        }
                    }
                }
            });


    }
}


