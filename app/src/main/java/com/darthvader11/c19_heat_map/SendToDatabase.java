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
    public static int i;
    public boolean flag;
    public static int previousZone = 100;


    @SuppressLint("MissingPermission")
    public void update() {
        MapsActivity.instance.location = MapsActivity.instance.locationManager.getLastKnownLocation(MapsActivity.instance.locationManager.getBestProvider(MapsActivity.instance.criteria, false));
        //MapsActivity.instance.test();
    }

    public void run(){
        Log.v("threadmain", "did this");
        while(isRunning){ // isRunning is a boolean variable
            SystemClock.sleep(2000);
            checkAndUpdate();
            update();
        }
    }
    public void checkAndUpdate(){
        for (i = 0; i < MapsActivity.instance.polyList.size() - 1; i++) {

            MapsActivity.instance.main.run();

            MapsActivity.instance.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    flag = MapsActivity.instance.checkIfTrue(i);
                    Log.i("flag", String.valueOf(flag));
                }
            });
            Log.v("gothere", "gothere");
            if(flag){
                Log.v("message", "did it");
                reff = FirebaseDatabase.getInstance().getReference().child("Polygons");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(i != previousZone){
                            int users = dataSnapshot.child(String.valueOf(i)).child("users").getValue(Integer.class);
                            users++;
                            reff.child(String.valueOf(i)).child("users").setValue(users);
                            previousZone = i;
                    }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

}
