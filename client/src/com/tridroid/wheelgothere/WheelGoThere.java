package com.tridroid.wheelgothere;

import com.tridroid.wheelgothere.WGTItemizedOverlay;
import com.tridroid.wheelgothere.PlacesManager;
import com.tridroid.wheelgothere.Place;

import android.content.Context;
import android.content.res.Resources;

import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;

public class WheelGoThere extends MapActivity implements LocationListener
{
    private Context context;
    private Resources res;
    private MapView mapView;
    private MapController mapController;
    private WGTItemizedOverlay wgtoverlay;
    private LocationManager locationManager;
    private PlacesManager placesManager;
    private int ZOOM_LEVEL = 18;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = getApplicationContext();
        res     = context.getResources();

        mapView       = (MapView)findViewById(R.id.mapView);
        mapController = mapView.getController();

        mapController.setZoom(ZOOM_LEVEL);
        mapView.setSatellite(false);
        mapView.setTraffic(true);

        placesManager = new PlacesManager(context);

        wgtoverlay = new WGTItemizedOverlay(res.getDrawable(R.drawable.defaultmarker));
        mapView.getOverlays().add(wgtoverlay);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    
    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
    }

    public void onLocationChanged(Location location){
        mapView.invalidate();

        GeoPoint geopoint = new GeoPoint((int)location.getLatitude() * 1000000,
                                         (int)location.getLongitude() * 1000000);

        List<Place> places = placesManager.getNearbyLocations(geopoint);

        wgtoverlay.clear();
        for (Place place : places){
            wgtoverlay.addOverlay((OverlayItem)place);    
        }

        mapController.animateTo(geopoint);
    } 

    public void onProviderEnabled(String provider){}
    public void onProviderDisabled(String provider){}
    public void onStatusChanged(String provider, int status, Bundle extras){}

    protected boolean isRouteDisplayed(){
        return false;
    }
}
