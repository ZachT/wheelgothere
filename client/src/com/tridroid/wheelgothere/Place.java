package com.tridroid.wheelgothere;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.GeoPoint;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;

public class Place extends OverlayItem
{
    private String name;
    private String icon;
    private double lat;
    private double lng;
    private int accessible;
    private Drawable overlay;

    public Place(String name, Drawable icon, GeoPoint geopoint, int accessible, Resources res){
        super(geopoint, name, "");
        this.name = name;
        this.lat  = lat;
        this.lng  = lng;
        this.accessible = accessible;
        
        overlay = res.getDrawable(R.drawable.inaccessible);

        setMarker(icon);
    }

    public Drawable getAccessibilityOverlay(){
        return overlay;
    }
}
