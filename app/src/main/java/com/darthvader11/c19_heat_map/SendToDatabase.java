package com.darthvader11.c19_heat_map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

import java.util.concurrent.CompletableFuture;

public class SendToDatabase extends AsyncTask {

    public boolean isRunning = true;

    @Override
    protected Object doInBackground(Object[] objects) {
        run();
        return null;
    }

    @SuppressLint("MissingPermission")
    public void update() {
        MapsActivity.instance.location = MapsActivity.instance.locationManager.getLastKnownLocation(MapsActivity.instance.locationManager.getBestProvider(MapsActivity.instance.criteria, false));
        Log.v("tag", MapsActivity.instance.location.toString());



    }

    public void run(){
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Polygon").child("3");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //MapsActivity.instance.zones[3].polygon = (Polygon) dataSnapshot.child("polygon").getValue();
                //MapsActivity.instance.mMap.addPolygon(MapsActivity.instance.zones[3].polygon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        while(isRunning){ // isRunning is a boolean variable
            SystemClock.sleep(2000);

            update();
        }
    }

}
