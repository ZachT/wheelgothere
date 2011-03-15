package com.tridroid.wheelgothere;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Button;

public class PlaceActivity extends Activity
{
    private TextView placetext;
    private RadioGroup radiogrp;
    private Button submitbn;

    private int rating;
    private String id;
    private String placename;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place);

        Bundle bundle = getIntent().getExtras();
        placename     = bundle.getString("EXTRA_PLACE_NAME");
        id            = bundle.getString("EXTRA_PLACE_ID");

        placetext = (TextView)findViewById(R.id.placename);
        placetext.setText(placename);

        radiogrp = (RadioGroup)findViewById(R.id.accessibleradiogroup);
        radiogrp.setOnCheckedChangeListener(mCheckedChangeListener);

        submitbn = (Button)findViewById(R.id.submit);
        submitbn.setOnClickListener(mSubmitListener);
    }

    private OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener(){
        public void onCheckedChanged(RadioGroup group, int checkedId){
            
        }
    };

    private OnClickListener mSubmitListener = new OnClickListener(){
        public void onClick(View v){
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(R.string.api_url + "ratePlace");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("id", id));
            nvps.add(new BasicNameValuePair("rating", Integer.toString(rating)));
            
            try {
                post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                client.execute(post);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
