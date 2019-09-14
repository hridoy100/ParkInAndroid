package com.example.parkin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.Stepper.MyStepperTest;
import com.example.parkin.util.NotificationThread;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    NotificationThread thread;
    public PendingIntent pintent;
    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;
    ImageView bgapp, clover, parkingAppIcon;
    Animation bganim, cloveranim, homeTextShow;
    LinearLayout textTopLayout, textParkIn, menus, garageLayout, nearbyLayout;
    TextView textTop;
    Context mContext;
    Toolbar toolbar;
    NavigationView navigationView;
    public ProgressDialog progressDialog;
    int notifcount=0;
    private FloatingActionButton fab;
    boolean loggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loggedIn = false;
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textParkIn = (LinearLayout) findViewById(R.id.textParkIn);
        textTopLayout = (LinearLayout) findViewById(R.id.textHome);

        garageLayout = (LinearLayout) findViewById(R.id.garageLayout);
        //nearbyLayout = (LinearLayout) findViewById(R.id.nearbyLayout);

        menus = (LinearLayout) findViewById(R.id.menus);
        textTop = (TextView) findViewById(R.id.textTop);
        parkingAppIcon = (ImageView) findViewById(R.id.parkingAppIcon);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        mToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mToggle.syncState();

        mContext=this;
        progressDialog = new ProgressDialog(this);
        /*runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                int prev_notif_count=0;
                //while(true) {
                    int notif_count=0;
                    CommunicateWithPhp com=new CommunicateWithPhp();
                    ArrayList<com.example.parkin.Notification> notif_list=com.getNotification(mContext);
                    System.out.println(notif_list.size());
                    for(int i=0;i<notif_list.size();i++)
                    {
                        if(notif_list.get(i).getStatus()=="no")
                        {
                            notif_count++;
                        }
                    }
                    if(notif_count>0 && prev_notif_count!=notif_count) {
                        int notifyID = 1;
                        String CHANNEL_ID = "my_channel_01";// The id of the channel.
                        CharSequence name = "eito chole";// The user-visible name of the channel.
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
// Create a notification and set the notification channel.
                        Notification notification = new Notification.Builder(mContext)
                                .setContentTitle("New Rent")
                                .setContentText("You've " + notif_count + "Notifications")
                                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                                .setChannelId(CHANNEL_ID)
                                .build();
                        NotificationManager mNotificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        mNotificationManager.createNotificationChannel(mChannel);
// Issue the notification.
                        //mNotificationManager.cancel(notifyID);
                        mNotificationManager.notify(notifyID, notification);
                    }
                    prev_notif_count=notif_count;
                }
        //    }
        });*/

        showHomePage();
        final Handler handler=new Handler(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what==1 && msg.arg1>notifcount)
                {
                    System.out.println("From handler");
                    int notifyID = 1;
                    String CHANNEL_ID = "my_channel_01";// The id of the channel.
                    CharSequence name = "eito chole";// The user-visible name of the channel.
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
// Create a notification and set the notification channel.
                    Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pintent=PendingIntent.getActivity(mContext,0,intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new Notification.Builder(mContext)
                            .setContentTitle("New Rent")
                            .setContentText("You've " + msg.arg1 + "Notifications")
                            .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pintent)
                            .build();
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.createNotificationChannel(mChannel);
// Issue the notification.
                    //mNotificationManager.cancel(notifyID);
                    mNotificationManager.notify(notifyID, notification);
                    notifcount=msg.arg1;
                    thread.start(notifcount);

                }
                super.handleMessage(msg);
            }
        };
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        thread=new NotificationThread(mobNo,handler);
        thread.start(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    void showHomePage(){
        bgapp.animate().translationY(-2500).alpha((float) 0.8).setDuration(800).setStartDelay(700);
        clover.animate().translationX(-400).alpha(0).setDuration(800).setStartDelay(900);
        parkingAppIcon.animate().translationY(-300).alpha(0).setDuration(800).setStartDelay(700);
        textParkIn.animate().alpha(0).setDuration(800).setStartDelay(900);
        //textTop.setAlpha(0);
        menus.setAlpha(0);

        homeTextShow= AnimationUtils.loadAnimation(this, R.anim.hometextshow);


        //textTop.setText("Home");
        menus.setAlpha(1);
        menus.startAnimation(homeTextShow);
//        nearbyLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Nearby Button Under Implementation", Toast.LENGTH_LONG).show();
//            }
//        });

//        garageLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Garage Button Under Implementation", Toast.LENGTH_LONG).show();
//            }
//        });
        //textTop.startAnimation(homeTextShow);
        //menus.startAnimation(homeTextShow);

        //textHome.animate().alpha(1).setDuration(800).setStartDelay(1300);

    }


    public void garageActivity(View view){
        //Intent garageIntent = new Intent(getApplicationContext(), Garage.class);
        Intent garageIntent = new Intent(getApplicationContext(), MyStepperTest.class);
        startActivity(garageIntent);
    }

    public void vehicleActivity(View view){
        Intent vehicleIntent = new Intent(getApplicationContext(), Vehicle.class);
        startActivity(vehicleIntent);
    }

    public void mapActivity(View view){
        Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(mapIntent);
    }

    public void cameraActivity(View view){
        Intent cameraIntent = new Intent(getApplicationContext(), Camera.class);
        startActivity(cameraIntent);
    }

    /*public void historyActivity(View view){
        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(historyIntent);
    }*/
    public void historyActivity(View view){
        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(historyIntent);
    }
    public void notificationActivity(View view){
        Intent notificationIntent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(notificationIntent);
    }
    public void settingsActivity(View view){
        Intent settingsIntent = new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(settingsIntent);
    }
    public void logOutActivity(View view){
        Intent logOutIntent = new Intent(getApplicationContext(),LoginActivity.class);
        logOutIntent.putExtra("from","home");
        startActivity(logOutIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            if(drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }else{
                drawerLayout.openDrawer(Gravity.LEFT);
            }

        }else if (item.getItemId()== R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //    void showLoginWindow(){
//        Intent showLoginScreen = new Intent(getApplicationContext(), CreateAccountActivity.class);
//        startActivity(showLoginScreen);
//        //System.out.println(showLoginScreen.getExtras());
//    }

}
