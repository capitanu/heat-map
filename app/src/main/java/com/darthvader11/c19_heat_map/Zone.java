package com.darthvader11.c19_heat_map;

import com.google.android.gms.maps.model.Polygon;

import java.security.Policy;

public class Zone {
    public int id;
    public int color;
    public Polygon polygon;
    public int users;
    public Zone(Polygon _polygon, int _users, int _id, int _color){
        this.polygon = _polygon;
        this.users = _users;
        this.id = _id;
        this.color = _color;
    }
}
