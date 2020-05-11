package com.darthvader11.c19_heat_map;

import android.content.Context;
import android.location.Location;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.util.Date;

class Common {
    public static final String KEY_REQUESTING_LOCATION_UPDATES = "LocationUpdateEnabled";

    public static String getLocationText(Location mLocation){
        return mLocation == null ? "Unkown Location" : new StringBuilder()
                .append(mLocation.getLatitude())
                .append("/")
                .append(mLocation.getLongitude())
                .toString();

    }

    public static CharSequence getLocationTitle(BackgroundService myBackgroundService) {
        return String.format("Location Updated %1$s", DateFormat.getDateInstance().format(new Date()));
    }

    public static void setRequestingLocationUpdates(Context context, boolean b) {
        PreferenceManager.
                getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, b)
                .apply();
    }

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }
}
