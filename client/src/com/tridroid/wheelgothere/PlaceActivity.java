package com.tridroid.wheelgothere;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

import android.widget.TextView;

public class PlaceActivity extends Activity
{
    private TextView placetext;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place);

        Bundle bundle = getIntent().getExtras();
        String placename = bundle.getString("EXTRA_PLACE_NAME");

        placetext = (TextView)findViewById(R.id.placename);
        placetext.setText(placename);
    }
}
