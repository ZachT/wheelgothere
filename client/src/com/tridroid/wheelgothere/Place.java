package com.tridroid.wheelgothere;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.GeoPoint;
import android.graphics.drawable.Drawable;

public class Place extends OverlayItem
{
    private String name;
    private String icon;
    private double lat;
    private double lng;
    private int accessible;

    public Place(String name, Drawable icon, GeoPoint geopoint, int accessible){
        super(geopoint, name, "");
        this.name = name;
        this.lat  = lat;
        this.lng  = lng;
        this.accessible = accessible;

        setMarker(icon);
    }
}
