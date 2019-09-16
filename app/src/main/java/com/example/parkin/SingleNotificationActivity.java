package com.example.parkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.Rent;

import java.util.ArrayList;

public class SingleNotificationActivity extends AppCompatActivity {

    TextView rentNo;
    TextView renterMob;
    TextView customerMob;
    TextView start;
    TextView end;
    TextView cost;
    TextView status;
    ImageView statusImage;

    SharedPreferences sharedPreferences;

    boolean cameraAccessible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_notification);
        cameraAccessible = false;

        init();
        Intent callerIntent = getIntent();
        String rentNumber = callerIntent.getStringExtra("rentNo");
        fetchFromDB(rentNumber);

    }

    void init(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        rentNo = (TextView) findViewById(R.id.rentNo);
        renterMob = (TextView) findViewById(R.id.renterMobNo);
        customerMob = (TextView) findViewById(R.id.customerMobNo);
        start = (TextView) findViewById(R.id.startTime);
        end = (TextView) findViewById(R.id.endTime);
        cost = (TextView) findViewById(R.id.cost);
        status = (TextView) findViewById(R.id.currentStatus);
        statusImage = (ImageView) findViewById(R.id.statusImage);
        cameraAccessible = false;
    }

    void fetchFromDB(String rentNumber) {
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<Rent> rentArrayList = communicateWithPhp.getRentInfo(rentNumber);
        for (int i=0; i<rentArrayList.size(); i++){
            rentNo.setText(rentArrayList.get(i).getRentNo());
            renterMob.setText(rentArrayList.get(i).getRenterMobNo());
            customerMob.setText(rentArrayList.get(i).getCustomerMobNo());
            start.setText(rentArrayList.get(i).getStart_time());
            end.setText(rentArrayList.get(i).getEnd_time());
            cost.setText(rentArrayList.get(i).getCost());
            status.setText(rentArrayList.get(i).getStatus());
            if(status.getText().toString().contains(Constants.booked) ){
                Glide.with(this)
                        .load(R.drawable.booked_1)
                        .into(statusImage);
                //statusImage.setImageResource(R.drawable.car_on_going);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                Log.i("Notif mobileNo: ",mobNo);
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Your Space is booked and customer's vehicle is on the way.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    status.setText("You have booked the space. Please arrive on time.");
                    cameraAccessible = false;
                }

            }
            else if(status.getText().toString().contains(Constants.starting_maybe) ){
                Glide.with(this)
                        .load(R.drawable.starting)
                        .into(statusImage);
                //statusImage.setImageResource(R.drawable.car_on_going);

                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                Log.i("Notif mobileNo: ",mobNo);
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Time has started but customer hasn't arrived yet.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = true;
                    status.setText("Your time counting has started. Please park your vehicle in the garage and confirm for arrival.");
                }
            }
            else if(status.getText().toString().contains(Constants.started)){
                Glide.with(this)
                        .load(R.drawable.started)
                        .into(statusImage);

                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Customer's vehicle is parked in the garage.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = true;
                    status.setText("Your vehicle is parked in the garage.");
                }
            }
            else if(status.getText().toString().contains(Constants.stopping_maybe)){
                Glide.with(this)
                        .load(R.drawable.stopping_1)
                        .into(statusImage);
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Time ended but customer hasn't left with his vehicle.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = true;
                    status.setText("Your time has ended. Please leave or additional fine will be applied.");
                }
            }
            else if(status.getText().toString().contains(Constants.stopped)){
                Glide.with(this)
                        .load(R.drawable.stopped)
                        .into(statusImage);
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Customer left the garage. Will arrive the next day.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = false;
                    status.setText("You have left the garage. Please arrive in time on the next day.");
                }
            }
            else if(status.getText().toString().contains(Constants.finishing)){
                Glide.with(this)
                        .load(R.drawable.finishing)
                        .into(statusImage);
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Rent for this garage has completed. Customer hasn't left yet.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = false;
                    status.setText("Rent for this garage has completed. Please leave before additional charge is applied and complete the payment.");
                }
            }
            else if(status.getText().toString().contains(Constants.completed)){
                Glide.with(this)
                        .load(R.drawable.completed)
                        .into(statusImage);
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo())) {
                    cameraAccessible = true;
                    status.setText("Customer left the garage. Rent for this garage has completed.");
                }
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo())) {
                    cameraAccessible = false;
                    status.setText("You have left the garage. Your rent for this garage has completed.");
                }
            }

        }
    }

    public void onCCTVClicked(View view) {
        if(cameraAccessible) {
            Intent cameraIntent = new Intent(getApplicationContext(), Camera.class);
            startActivity(cameraIntent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("CCTV CAMERA");
            builder.setMessage("You can access camera only between your parking time period");
            builder.setCancelable(true);
            builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing..
                }
            });
            builder.show();
        }
    }


    public void onBackClicked(View view){
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
