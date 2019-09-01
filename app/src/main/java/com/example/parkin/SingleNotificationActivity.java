package com.example.parkin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parkin.DB.CommunicateWithPhp;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_notification);


        init();
        Intent callerIntent = getIntent();
        String rentNumber = callerIntent.getStringExtra("rentNo");
        fetchFromDB(rentNumber);

    }

    void init(){
        rentNo = (TextView) findViewById(R.id.rentNo);
        renterMob = (TextView) findViewById(R.id.renterMobNo);
        customerMob = (TextView) findViewById(R.id.customerMobNo);
        start = (TextView) findViewById(R.id.startTime);
        end = (TextView) findViewById(R.id.endTime);
        cost = (TextView) findViewById(R.id.cost);
        status = (TextView) findViewById(R.id.currentStatus);
        statusImage = (ImageView) findViewById(R.id.statusImage);
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
            if(status.getText().toString().contains("booked") ){
                Glide.with(this)
                        .load(R.drawable.booked)
                        .into(statusImage);
                //statusImage.setImageResource(R.drawable.car_on_going);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
                Log.i("Notif mobileNo: ",mobNo);
                if(mobNo.equals(rentArrayList.get(i).getRenterMobNo()))
                    status.setText("Your Space is booked and customer's vehicle is on the way");
                else if(mobNo.equals(rentArrayList.get(i).getCustomerMobNo()))
                    status.setText("You have booked the space. Please arrive on time");
            }
            if(status.getText().toString().contains("parked")){
                Glide.with(this)
                        .load(R.drawable.car_parked)
                        .into(statusImage);
            }

            if(status.getText().toString().contains("left")){
                Glide.with(this)
                        .load(R.drawable.car_left)
                        .into(statusImage);
            }

        }
    }

    public void onCCTVClicked(View view) {
        Intent cameraIntent = new Intent(getApplicationContext(), Camera.class);
        startActivity(cameraIntent);
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
