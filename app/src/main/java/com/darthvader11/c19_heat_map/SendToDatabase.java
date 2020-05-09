package com.darthvader11.c19_heat_map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.ActivityCompat;

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
        while(isRunning){ // isRunning is a boolean variable
            SystemClock.sleep(2000);
            update();
        }
    }

}
