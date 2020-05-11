package com.darthvader11.c19_heat_map;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class Poly {
    public static ArrayList<ArrayList<LatLng>> listOfpoints = new ArrayList<>(3);
    public static List<Polygon> listOfPolygons = new ArrayList<>(listOfpoints.size());
   // public List<LatLng> polygon1 = new ArrayList<>();
  //  public List<LatLng> polygon2 = new ArrayList<>();
    public Poly(){

        for ( int i= 0 ; i < 3; i++ ){
            listOfpoints.add(new ArrayList<LatLng>());
        }

        listOfpoints.get(0).add(new LatLng(44.4293595,26.1035863));
        listOfpoints.get(0).add(new LatLng(44.4295434,26.1035434));
        listOfpoints.get(0).add(new LatLng(44.4297579,26.103479));
        listOfpoints.get(0).add(new LatLng(44.431566,26.1033503));
        listOfpoints.get(0).add(new LatLng(44.4327305,26.1031572));
        listOfpoints.get(0).add(new LatLng(44.4337724,26.1029211));
        listOfpoints.get(0).add(new LatLng(44.4342474,26.1028138));
        listOfpoints.get(0).add(new LatLng( 44.4348756,26.1025563));
        listOfpoints.get(0).add(new LatLng(44.4353199,26.1023203));
        listOfpoints.get(0).add(new LatLng( 44.4353046,26.101977));
        listOfpoints.get(0).add(new LatLng( 44.4352893,26.1015478));
        listOfpoints.get(0).add(new LatLng(44.4351974,26.1010758));
        listOfpoints.get(0).add(new LatLng(44.4350595,26.100432));
        listOfpoints.get(0).add(new LatLng(44.4348756,26.0995523));
        listOfpoints.get(0).add(new LatLng(44.4346458,26.0983292));
        listOfpoints.get(0).add(new LatLng(44.4343547,26.098415));
        listOfpoints.get(0).add(new LatLng( 44.4340176,26.0983506));
        listOfpoints.get(0).add(new LatLng( 44.4327918,26.0975996));
        listOfpoints.get(0).add(new LatLng(44.4318878,26.0972134));
        listOfpoints.get(0).add(new LatLng( 44.4307692,26.0968701));
        listOfpoints.get(0).add(new LatLng( 44.4300644,26.0968271));
        listOfpoints.get(0).add(new LatLng(  44.4298498,26.0972992));
        listOfpoints.get(0).add(new LatLng(44.4297732,26.0977713));
        listOfpoints.get(0).add(new LatLng(44.4296966,26.0985867));
        listOfpoints.get(0).add(new LatLng (44.4296506,26.0994235));
        listOfpoints.get(0).add(new LatLng (44.4295587,26.1002389));
        listOfpoints.get(0).add(new LatLng (44.4294361,26.1007754));
        listOfpoints.get(0).add(new LatLng (44.4292369,26.1016766));
        listOfpoints.get(0).add(new LatLng (44.429099,26.1025778));
        listOfpoints.get(0).add(new LatLng (44.4289917,26.1033717));


        listOfpoints.get(1).add(new LatLng (44.4356099,26.1021262));
        listOfpoints.get(1).add(new LatLng (44.43630606,26.1017829));
        listOfpoints.get(1).add(new LatLng (44.4370654,26.101461));
        listOfpoints.get(1).add(new LatLng (44.4382451,26.1007958));
        listOfpoints.get(1).add(new LatLng (44.4393176,26.1002379));
        listOfpoints.get(1).add(new LatLng (44.4406045,26.0996371));
        listOfpoints.get(1).add(new LatLng (44.4418301,26.0990792));
        listOfpoints.get(1).add(new LatLng (44.4415084,26.0983067));
        listOfpoints.get(1).add(new LatLng (44.4412173,26.0977059));
        listOfpoints.get(1).add(new LatLng (44.4409875,26.097148));
        listOfpoints.get(1).add(new LatLng (44.4406811,26.0965472));
        listOfpoints.get(1).add(new LatLng (44.4404207,26.0959893));
        listOfpoints.get(1).add(new LatLng (44.4400989,26.0963326));
        listOfpoints.get(1).add(new LatLng (44.4393636,26.0968691));
        listOfpoints.get(1).add(new LatLng (44.4390725,26.0969549));
        listOfpoints.get(1).add(new LatLng (44.4381532,26.0971051));
        listOfpoints.get(1).add(new LatLng (44.4372952,26.0975557));
        listOfpoints.get(1).add(new LatLng (44.4365598,26.0977703));
        listOfpoints.get(1).add(new LatLng (44.4358244,26.0977918));
        listOfpoints.get(1).add(new LatLng (44.435043,26.0980063));
        listOfpoints.get(1).add(new LatLng (44.4348132,26.0979205));
        listOfpoints.get(1).add(new LatLng (44.4348591,26.0983497));
        listOfpoints.get(1).add(new LatLng (44.4352115,26.1003452));
        listOfpoints.get(1).add(new LatLng (44.435472,26.1017185));
        listOfpoints.get(1).add(new LatLng (44.4356099,26.1021262));


        listOfpoints.get(2).add(new LatLng (44.4358071,26.1034753));
        listOfpoints.get(2).add(new LatLng (44.435899,26.1039688));
        listOfpoints.get(2).add(new LatLng (44.4360216,26.1044623));
        listOfpoints.get(2).add(new LatLng (44.4360982,26.1049988));
        listOfpoints.get(2).add(new LatLng (44.4362361,26.1055781));
        listOfpoints.get(2).add(new LatLng (44.4363127,26.1059));
        listOfpoints.get(2).add(new LatLng (44.4364506,26.1063721));
        listOfpoints.get(2).add(new LatLng (44.4365425,26.1067583));
        listOfpoints.get(2).add(new LatLng (44.4367264,26.1068441));
        listOfpoints.get(2).add(new LatLng (44.4369715,26.1071016));
        listOfpoints.get(2).add(new LatLng (44.4374312,26.1073591));
        listOfpoints.get(2).add(new LatLng (44.4380593,26.1076381));
        listOfpoints.get(2).add(new LatLng (44.4386722,26.1078741));
        listOfpoints.get(2).add(new LatLng (44.439239,26.1080672));
        listOfpoints.get(2).add(new LatLng (44.4399744,26.1083033));
        listOfpoints.get(2).add(new LatLng (44.4406485,26.1084106));
        listOfpoints.get(2).add(new LatLng (44.4407557,26.1080458));
        listOfpoints.get(2).add(new LatLng (44.440817,26.1075737));
        listOfpoints.get(2).add(new LatLng (44.440863,26.1070373));
        listOfpoints.get(2).add(new LatLng (44.4410928,26.1070587));
        listOfpoints.get(2).add(new LatLng (44.4419814,26.1071016));
        listOfpoints.get(2).add(new LatLng (44.4422877,26.1071875));
        listOfpoints.get(2).add(new LatLng (44.4432069,26.107445));
        listOfpoints.get(2).add(new LatLng (44.443498,26.107402));
        listOfpoints.get(2).add(new LatLng (44.4433754,26.10693));
        listOfpoints.get(2).add(new LatLng (44.4432988,26.1065008));
        listOfpoints.get(2).add(new LatLng (44.4431763,26.1059858));
        listOfpoints.get(2).add(new LatLng (44.4429465,26.1052992));
        listOfpoints.get(2).add(new LatLng (44.4429312,26.1044838));
        listOfpoints.get(2).add(new LatLng (44.4429312,26.1036469));
        listOfpoints.get(2).add(new LatLng (44.4429618,26.1029818));
        listOfpoints.get(2).add(new LatLng (44.4432069,26.1027457));
        listOfpoints.get(2).add(new LatLng (44.4431303,26.1025311));
        listOfpoints.get(2).add(new LatLng (44.4429618,26.1022737));
        listOfpoints.get(2).add(new LatLng (44.4427627,26.101866));
        listOfpoints.get(2).add(new LatLng (44.4426707,26.1015656));
        listOfpoints.get(2).add(new LatLng (44.4426554,26.101072));
        listOfpoints.get(2).add(new LatLng (44.4427014,26.1002781));
        listOfpoints.get(2).add(new LatLng (44.4427933,26.0989692));
        listOfpoints.get(2).add(new LatLng (44.4420426,26.0993125));
        listOfpoints.get(2).add(new LatLng (44.4412,26.0997202));
        listOfpoints.get(2).add(new LatLng (44.4400663,26.100321));
        listOfpoints.get(2).add(new LatLng (44.4390399,26.100836));
        listOfpoints.get(2).add(new LatLng (44.4382279,26.1012651));
        listOfpoints.get(2).add(new LatLng (44.4374924,26.1016514));
        listOfpoints.get(2).add(new LatLng (44.4366038,26.1021449));
        listOfpoints.get(2).add(new LatLng (44.4358071,26.1026384));
        listOfpoints.get(2).add(new LatLng (44.4357305,26.1027886));
        listOfpoints.get(2).add(new LatLng (44.4358071,26.1034753));


       // listOfpoints.get(3).add(new LatLng(44.427065, 26.100815));



        /*listOfpoints.get(4).add(new LatLng(44.435626, 26.102114));
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

        listOfpoints.get(10).add(new LatLng(44.426157, 26.100917));
        listOfpoints.get(10).add(new LatLng (44.428611, 26.101160));
        listOfpoints.get(10).add(new LatLng (44.429131, 26.101701));
        listOfpoints.get(10).add(new LatLng (44.428864, 26.103457));
        listOfpoints.get(10).add(new LatLng (44.425917, 26.103905));

        listOfpoints.get(11).add(new LatLng (44.428782, 26.103823));
        listOfpoints.get(11).add(new LatLng (44.432227, 26.111530));
        listOfpoints.get(11).add(new LatLng (44.429748, 26.111301));
        listOfpoints.get(11).add(new LatLng (44.427998, 26.111990));
        listOfpoints.get(11).add(new LatLng ( 44.426449, 26.111862));
        listOfpoints.get(11).add(new LatLng (44.426814, 26.104078));

        listOfpoints.get(12).add(new LatLng (44.428782, 26.103823));
        listOfpoints.get(12).add(new LatLng (44.432227, 26.111530));
        listOfpoints.get(12).add(new LatLng (44.432864, 26.112449));
        listOfpoints.get(12).add(new LatLng (44.433338, 26.115818));
        listOfpoints.get(12).add(new LatLng (44.436783, 26.116865));
        listOfpoints.get(12).add(new LatLng (44.438095, 26.116431));
        listOfpoints.get(12).add(new LatLng (44.435507, 26.102572));


        listOfpoints.get(13).add(new LatLng (44.432227, 26.111530));
        listOfpoints.get(13).add(new LatLng (44.432864, 26.112449));
        listOfpoints.get(13).add(new LatLng (44.433338, 26.115818));
        listOfpoints.get(13).add(new LatLng (44.433029, 26.120718));
        listOfpoints.get(13).add(new LatLng (44.430404, 26.120310));
        listOfpoints.get(13).add(new LatLng (44.428673, 26.119213));
        listOfpoints.get(13).add(new LatLng (44.426103, 26.118957));
        listOfpoints.get(13).add(new LatLng (44.426413, 26.112015));
        listOfpoints.get(13).add(new LatLng (44.427962, 26.112041));
        listOfpoints.get(13).add(new LatLng (44.429748, 26.111403));

        listOfpoints.get(14).add(new LatLng (44.433338, 26.115818));
        listOfpoints.get(14).add(new LatLng (44.436783, 26.116865));
        listOfpoints.get(14).add(new LatLng (44.438095, 26.116431));
        listOfpoints.get(14).add(new LatLng (44.441294, 26.133272));
        listOfpoints.get(14).add(new LatLng (44.437487, 26.136300));
        listOfpoints.get(14).add(new LatLng (44.432042, 26.138395));
        listOfpoints.get(14).add(new LatLng (44.431487, 26.132546));
        listOfpoints.get(14).add(new LatLng ( 44.433090, 26.120889));
*/

       for(int i = 0 ; i < listOfpoints.size(); i++){
           listOfPolygons.add( MapsActivity.instance.mMap.addPolygon(new PolygonOptions()
                   .addAll(listOfpoints.get(i))
                   .strokeWidth(0)
                   .fillColor(Color.argb(50, 0, 250, 0))) );
        }

    }

}
