package com.hrushikesh.Bosshomes;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Location extends AppCompatActivity {
    ImageView light,storecon,storecon2,storecon3;
    Button buttonstop,buttonnav;
    TextView scanning;
    Animation scan , store,store2,store3,buttonanime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        light =(ImageView) findViewById(R.id.light);
        ///
        storecon = (ImageView) findViewById(R.id.storecon);
        storecon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:15.8497,74.4977?q=Furniture Bosshome");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
            }
        });
        ////





        storecon2 = (ImageView) findViewById(R.id.storecon2);
        storecon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:15.8497,74.4977?q=Furniture Bosshome");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
            }
        });

        //
        storecon3= (ImageView) findViewById(R.id.storecon3);
       storecon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:15.8497,74.4977?q=Furniture Bosshome");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
            }
        });

        buttonstop = (Button) findViewById(R.id.buttonstop);
        //


        buttonnav = (Button) findViewById(R.id.buttonnav);
        buttonnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=15.8497,74.4977");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                    }
                }, 1000);
            }
        });

        scanning = (TextView) findViewById(R.id.scanning);

        scan = AnimationUtils.loadAnimation(this,R.anim.scan);
        store = AnimationUtils.loadAnimation(this,R.anim.store);
        store2 = AnimationUtils.loadAnimation(this,R.anim.store2);
        store3  = AnimationUtils.loadAnimation(this,R.anim.store3);
        buttonanime  = AnimationUtils.loadAnimation(this,R.anim.buttonanime);
        storecon.startAnimation(store);
        storecon2.startAnimation(store2);
        storecon3.startAnimation(store3);

        light.startAnimation(scan);
        buttonstop.startAnimation(buttonanime);
        buttonstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.clearAnimation();
            }
        });


    }

}
