package com.example.parkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    ImageView bgapp, clover, parkingAppIcon;
    Animation bganim, cloveranim, homeTextShow;
    LinearLayout textTopLayout, textParkIn, menus, garageLayout, nearbyLayout;
    TextView textTop;
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggedIn = false;
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textParkIn = (LinearLayout) findViewById(R.id.textParkIn);
        textTopLayout = (LinearLayout) findViewById(R.id.textHome);

        garageLayout = (LinearLayout) findViewById(R.id.garageLayout);
        nearbyLayout = (LinearLayout) findViewById(R.id.nearbyLayout);

        menus = (LinearLayout) findViewById(R.id.menus);
        textTop = (TextView) findViewById(R.id.textTop);
        parkingAppIcon = (ImageView) findViewById(R.id.parkingAppIcon);

        showHomePage();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void showHomePage(){
        bgapp.animate().translationY(-2100).alpha((float) 0.8).setDuration(800).setStartDelay(700);
        clover.animate().translationX(-400).alpha(0).setDuration(800).setStartDelay(900);
        parkingAppIcon.animate().translationY(-300).alpha(0).setDuration(800).setStartDelay(700);
        textParkIn.animate().alpha(0).setDuration(800).setStartDelay(900);
        //textTop.setAlpha(0);
        menus.setAlpha(0);

        homeTextShow= AnimationUtils.loadAnimation(this, R.anim.hometextshow);


        textTop.setText("Home");
        menus.setAlpha(1);
        menus.startAnimation(homeTextShow);
        nearbyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Nearby Button Under Implementation", Toast.LENGTH_LONG).show();
            }
        });

        garageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Garage Button Under Implementation", Toast.LENGTH_LONG).show();
            }
        });
        textTop.startAnimation(homeTextShow);
        //menus.startAnimation(homeTextShow);

        //textHome.animate().alpha(1).setDuration(800).setStartDelay(1300);

    }


//    void showLoginWindow(){
//        Intent showLoginScreen = new Intent(getApplicationContext(), CreateAccountActivity.class);
//        startActivity(showLoginScreen);
//        //System.out.println(showLoginScreen.getExtras());
//    }

}
