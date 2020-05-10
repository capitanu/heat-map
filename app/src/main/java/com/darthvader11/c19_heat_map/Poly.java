package com.darthvader11.c19_heat_map;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class Poly {
    public static ArrayList<ArrayList<LatLng>> listOfpoints = new ArrayList<>(10);
    public static List<Polygon> listOfPolygons = new ArrayList<>(listOfpoints.size());
   // public List<LatLng> polygon1 = new ArrayList<>();
  //  public List<LatLng> polygon2 = new ArrayList<>();
    public Poly(){

        for ( int i= 0 ; i < 10; i++ ){
            listOfpoints.add(new ArrayList<LatLng>());
        }

        listOfpoints.get(0).add(new LatLng(44.429015, 26.103404));
        listOfpoints.get(0).add(new LatLng(44.429730, 26.099163));
        listOfpoints.get(0).add(new LatLng(44.430022, 26.096822));
        listOfpoints.get(0).add(new LatLng(44.431985, 26.097231));
        listOfpoints.get(0).add(new LatLng(44.434674, 26.098453));
        listOfpoints.get(0).add(new LatLng(44.435277, 26.102237));
        listOfpoints.get(0).add(new LatLng(44.433718, 26.102897));
        listOfpoints.get(0).add(new LatLng(44.431756, 26.103338));
        listOfpoints.get(0).add(new LatLng(44.429015, 26.103404));


        listOfpoints.get(1).add(new LatLng(44.430218, 26.096541));
        listOfpoints.get(1).add(new LatLng(44.432796, 26.093682));
        listOfpoints.get(1).add(new LatLng(44.433431, 26.091972));
        listOfpoints.get(1).add(new LatLng(44.433607, 26.089729));
        listOfpoints.get(1).add(new LatLng(44.434515, 26.089893));
        listOfpoints.get(1).add(new LatLng(44.434691, 26.098100));
        listOfpoints.get(1).add(new LatLng(44.434105, 26.098141));
        listOfpoints.get(1).add(new LatLng(44.431790, 26.096910));
        listOfpoints.get(1).add(new LatLng(44.430218, 26.096541));


        listOfpoints.get(2).add(new LatLng(44.429970, 26.095334));
        listOfpoints.get(2).add (new LatLng(44.430250, 26.091304));
        listOfpoints.get(2).add  (new LatLng(44.432893, 26.091529));
        listOfpoints.get(2).add (new LatLng(44.432581, 26.092704));
        listOfpoints.get(2).add(new LatLng(44.430168, 26.095536));
        listOfpoints.get(2).add (new LatLng(44.429970, 26.095334));


        listOfpoints.get(3).add(new LatLng(44.427065, 26.100815));
        listOfpoints.get(3).add (new LatLng(44.427393, 26.092485));
        listOfpoints.get(3).add  (new LatLng(44.428048, 26.092147));
        listOfpoints.get(3).add (new LatLng(44.428444, 26.091151));
        listOfpoints.get(3).add( new LatLng(44.430031, 26.091304));
        listOfpoints.get(3).add (new LatLng(44.429757, 26.095212));
        listOfpoints.get(3).add (new LatLng(44.429032, 26.099923));
        listOfpoints.get(3).add (new LatLng(44.428499, 26.100939));
        listOfpoints.get(3).add (new LatLng(44.427065, 26.100815));


        listOfpoints.get(4).add(new LatLng(44.435626, 26.102114));
        listOfpoints.get(4).add (new LatLng(44.434943, 26.098288));
        listOfpoints.get(4).add  (new LatLng(44.439812, 26.096807));
        listOfpoints.get(4).add (new LatLng(44.440517, 26.099584));
        listOfpoints.get(4).add(new LatLng(44.435626, 26.102114));


        listOfpoints.get(5).add(new LatLng(44.434987, 26.097918));
        listOfpoints.get(5).add (new LatLng(44.434701, 26.089803));
        listOfpoints.get(5).add  (new LatLng(44.439900, 26.086810));
        listOfpoints.get(5).add (new LatLng(44.439746, 26.089155));
        listOfpoints.get(5).add( new LatLng(44.440583, 26.095696));
        listOfpoints.get(5).add (new LatLng(44.439305, 26.096776));
        listOfpoints.get(5).add (new LatLng(44.434987, 26.097918));


        listOfpoints.get(6).add(new LatLng(44.440781, 26.099461));
        listOfpoints.get(6).add(new LatLng(44.440054, 26.096653));
        listOfpoints.get(6).add(new LatLng(44.445429, 26.091161));
        listOfpoints.get(6).add(new LatLng(44.446729, 26.096375));


        listOfpoints.get(7).add(new LatLng(44.440803, 26.095326));
        listOfpoints.get(7).add (new LatLng(44.440275, 26.093166));
        listOfpoints.get(7).add  (new LatLng(44.439944, 26.089278));
        listOfpoints.get(7).add (new LatLng(44.440142, 26.081842));
        listOfpoints.get(7).add( new LatLng(44.442566, 26.081194));
        listOfpoints.get(7).add (new LatLng(44.443623, 26.086378));
        listOfpoints.get(7).add (new LatLng(44.445341, 26.090914));
        listOfpoints.get(7).add (new LatLng(44.440803, 26.095326));


        listOfpoints.get(8).add(new LatLng(44.434680, 26.089621));
        listOfpoints.get(8).add (new LatLng(44.434575, 26.084475));
        listOfpoints.get(8).add  (new LatLng(44.439962, 26.081911));
        listOfpoints.get(8).add (new LatLng(44.440067, 26.084696));
        listOfpoints.get(8).add( new LatLng(44.439948, 26.086559));
        listOfpoints.get(8).add (new LatLng(44.434680, 26.089621));


        listOfpoints.get(9).add(new LatLng(44.433627, 26.089510));
        listOfpoints.get(9).add(new LatLng(44.433943, 26.084475));
        listOfpoints.get(9).add(new LatLng(44.434417, 26.084493));
        listOfpoints.get(9).add(new LatLng(44.434522, 26.089676));
        listOfpoints.get(9).add(new LatLng(44.433627, 26.089510));


       for(int i = 0 ; i < listOfpoints.size(); i++){
           listOfPolygons.add( MapsActivity.instance.mMap.addPolygon(new PolygonOptions()
                   .addAll(listOfpoints.get(i))
                   .strokeWidth(0)
                   .fillColor(Color.argb(50, 0, 250, 0))) );
        }

    }

}
