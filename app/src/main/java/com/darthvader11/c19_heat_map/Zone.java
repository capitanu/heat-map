package com.darthvader11.c19_heat_map;

import com.google.android.gms.maps.model.Polygon;

import java.security.Policy;

public class Zone {
    public Polygon polygon;
    public int users;
    public Zone(Polygon _polygon, int _users){
        this.polygon = _polygon;
        this.users = _users;
    }
}
