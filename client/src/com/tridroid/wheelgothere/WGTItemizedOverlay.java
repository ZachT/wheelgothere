package com.tridroid.wheelgothere;

import com.tridroid.wheelgothere.PlaceActivity;

import android.content.Intent;
import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Bitmap;

import com.google.android.maps.MapView;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.ArrayList;

public class WGTItemizedOverlay extends ItemizedOverlay
{
    private ArrayList<OverlayItem> overlays;
    private Context context;

    public WGTItemizedOverlay(Context context, Drawable defaultmarker){
        super(boundCenterBottom(defaultmarker));
        overlays = new ArrayList<OverlayItem>();
        this.context = context;
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
                Place place = (Place)overlays.get(i);

                mview.getProjection().toPixels(place.getPoint(), coord);

                Paint paint = new Paint();
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setARGB(150, 0, 0, 0);
                paint.setTextSize(20);

                canvas.drawText(place.getTitle(), coord.x, coord.y + 20, paint);

                Drawable overlay = place.getAccessibilityOverlay();
                Bitmap bitmap = ((BitmapDrawable)overlay).getBitmap();

                int w = overlay.getIntrinsicWidth();
                int h = overlay.getIntrinsicHeight();

                canvas.drawBitmap(bitmap, 
                                  coord.x + (-w / 2), 
                                  coord.y - h, 
                                  paint);
            }
        }
    }

    protected boolean onTap(int i){
        Place place = (Place)overlays.get(i);

        Intent intent = new Intent(context, PlaceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXTRA_PLACE_NAME", place.getName());

        context.startActivity(intent);
        return true;         
    }
}
