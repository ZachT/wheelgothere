package com.tridroid.wheelgothere;

import android.graphics.Canvas;

import com.google.android.maps.MapView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.ArrayList;

public class WGTItemizedOverlay extends ItemizedOverlay
{
    private ArrayList<OverlayItem> overlays;

    public WGTItemizedOverlay(Drawable defaultmarker){
        super(boundCenterBottom(defaultmarker));
        overlays = new ArrayList<OverlayItem>();
    }

    public void addOverlay(OverlayItem item){
        overlays.add(item);
        populate();
    }

    public void clear(){
        overlays.clear();
        populate();
    }

    protected OverlayItem createItem(int i){
        return overlays.get(i);
    }   

    public int size(){
        return overlays.size();
    }

    public void draw(Canvas canvas, MapView mview, boolean shadow){
        super.draw(canvas, mview, shadow);
        if (!shadow){
            for (int i = 0; i < overlays.size(); i++){
                Point coord = new Point();
                OverlayItem item = overlays.get(i);

                mview.getProjection().toPixels(item.getPoint(), coord);

                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setARGB(150, 0, 0, 0);
                paint.setTextSize(20);
                
                canvas.drawText(item.getTitle(), coord.x, coord.y + 20, paint);
            }
        }
    }
}
