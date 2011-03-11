package com.tridroid.wheelgothere;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.GeoPoint;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonNode;

public class WheelGoThere extends MapActivity
{
    private Context context;
    private MapView mapView;
    private MapController mapController;
    private MyLocationOverlay myLocation;
    private int ZOOM_LEVEL = 19;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = getApplicationContext();
        mapView = (MapView)findViewById(R.id.mapView);
        mapController = mapView.getController();
        myLocation = new MyLocationOverlay(this, mapView);

        myLocation.enableMyLocation();
        mapView.getOverlays().add(myLocation);

//        myLocation.runOnFirstFix(new Runnable(){
//            public void run(){
//                GeoPoint myLoc = myLocation.getMyLocation();
//                mapController.setZoom(ZOOM_LEVEL);
//                mapController.setCenter(myLoc);
//
//                String api_url       = context.getString(R.string.api_url);
//                String client_id     = context.getString(R.string.client_id);
//                String client_secret = context.getString(R.string.client_secret);
//                String lat           = Integer.toString(myLoc.getLatitudeE6() / 1000000);
//                String lon           = Integer.toString(myLoc.getLongitudeE6() / 1000000);
//
//                HttpGet request = new HttpGet("http://10.0.2.2/getPlaces?lat=" + lat + "&lng=" + lon);         
//                DefaultHttpClient client = new DefaultHttpClient();
//
//                try {
//                        HttpResponse httpresp  = client.execute(request);
//                        int respcode           = httpresp.getStatusLine().getStatusCode();
//                        InputStreamReader inp  = new InputStreamReader(httpresp.getEntity().getContent());
//                        BufferedReader in      = new BufferedReader(inp);
//                        StringBuffer sb        = new StringBuffer();
//                        String line            = "";
//
//                        while ((line = in.readLine()) != null){
//                            sb.append(line);
//                        }
//                        in.close();
//                    
//                        String response     = sb.toString();
//                        ObjectMapper mapper = new ObjectMapper();
//                        JsonNode root       = mapper.readValue(response, JsonNode.class); 
//
//                } catch (IOException e) { }    
//            }
//        });
    }

    protected boolean isRouteDisplayed(){
        return false;
    }
}
