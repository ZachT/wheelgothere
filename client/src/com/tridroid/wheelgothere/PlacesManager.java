package com.tridroid.wheelgothere;
import com.tridroid.wheelgothere.Place;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.IllegalStateException;
import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import com.google.android.maps.GeoPoint;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.JsonNode;

public class PlacesManager
{
    private String api_url;
    private Resources res;

    public PlacesManager(Context context){
        api_url  = context.getString(R.string.api_url);
        res      = context.getResources();
    }

    public List<Place> getNearbyLocations(GeoPoint geopoint){
        double lat = geopoint.getLatitudeE6() / 1000000; 
        double lng = geopoint.getLongitudeE6() / 1000000;
        List<Place> places = new ArrayList<Place>();

        String response     = requestServerUpdate(lat, lng);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readValue(response, JsonNode.class); 
    
            for (JsonNode place : root){
                places.add(new Place(place.get("name").toString().replaceAll("\"", ""), 
                                     requestImageFromServer(place.get("icon").toString()),
                                     new GeoPoint( 
                                     (int)(Double.parseDouble(place.get("lat").toString()) * 1000000), 
                                     (int)(Double.parseDouble(place.get("lng").toString()) * 1000000)
                                     ), 0, res));
            }
        } catch (IOException e) { System.out.println("WGT:" + e); }
        return places;
    } 

    private String requestServerUpdate(double lat, double lng){
        HttpGet request = new HttpGet(api_url + "getPlaces?lat=" + Double.toString(lat) + "&lng=" + Double.toString(lng));         
        DefaultHttpClient client = new DefaultHttpClient();
        String response = "";

        try {
                HttpResponse httpresp  = client.execute(request);
                int respcode           = httpresp.getStatusLine().getStatusCode();
                InputStreamReader inp  = new InputStreamReader(httpresp.getEntity().getContent());
                BufferedReader in      = new BufferedReader(inp);
                StringBuffer sb        = new StringBuffer();
                String line            = "";

                while ((line = in.readLine()) != null){
                    sb.append(line);
                }
                in.close();
            
                response = sb.toString();
        } catch (IOException e) { }
        return response;
    }

    private Drawable requestImageFromServer(String url){
        try {
            HttpGet request = new HttpGet(url.replaceAll("\"", ""));
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpresp = client.execute(request);
            HttpEntity entity     = httpresp.getEntity();

            Drawable icon = Drawable.createFromStream(entity.getContent(), "src");
      
            int w = icon.getIntrinsicWidth();
            int h = icon.getIntrinsicHeight();
            icon.setBounds(-w / 2, -h, w / 2, 0);

            return icon;

        } catch (IOException e) { return null; }
          catch (IllegalStateException e) { return null; }
    }
}
