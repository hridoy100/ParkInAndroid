package com.example.parkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Rent;
import com.example.parkin.DB.SpaceDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static com.example.parkin.DB.Constants.cctv_cost;
import static com.example.parkin.DB.Constants.cctv_index;
import static com.example.parkin.DB.Constants.covered_parking_cost;
import static com.example.parkin.DB.Constants.covered_parking_index;
import static com.example.parkin.DB.Constants.disabled_access_cost;
import static com.example.parkin.DB.Constants.disabled_access_index;
import static com.example.parkin.DB.Constants.electric_vehicle_charging_index;
import static com.example.parkin.DB.Constants.electric_vehicle_cost;
import static com.example.parkin.DB.Constants.lighting_cost;
import static com.example.parkin.DB.Constants.lighting_index;
import static com.example.parkin.DB.Constants.oil_buying_cost;
import static com.example.parkin.DB.Constants.oil_buying_index;
import static com.example.parkin.DB.Constants.per_hour_cost;
import static com.example.parkin.DB.Constants.securely_gated_cost;
import static com.example.parkin.DB.Constants.securely_gated_index;

public class PaymentActivity extends AppCompatActivity {

    TextView totalCostBig,totalDays,totalTime,rentNo,perdayCost,penalty,discount,totalCost;
    String rent_no;
    String facility;
    long days;
    long minutes;
    long cost;
    long per_day_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent myIntent = getIntent();
        rent_no = myIntent.getStringExtra("rentNo");

        totalCost= (TextView) findViewById(R.id.totalCost);
        totalCostBig= (TextView) findViewById(R.id.totalCostBig);
        totalDays= (TextView) findViewById(R.id.totalDays);
        totalTime= (TextView) findViewById(R.id.totalTime);
        rentNo= (TextView) findViewById(R.id.rentNo);
        perdayCost= (TextView) findViewById(R.id.perDayCost);
        penalty= (TextView) findViewById(R.id.penalty);
        discount= (TextView) findViewById(R.id.discount);

        rentNo.setText(rent_no);
        fetchDataFromDatabase();

    }

    private void fetchDataFromDatabase() {
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<Rent> rentArrayList = communicateWithPhp.getRentInfo(rent_no);
        penalty.setText("\u09F3"+"0");
        discount.setText("\u09F3"+"0");
        String startDate = rentArrayList.get(0).getStart_time();
        String endDate = rentArrayList.get(0).getEnd_time();
        StringTokenizer tokenizer1=new StringTokenizer(startDate);
        String s_date=tokenizer1.nextToken(" ");
        String s_time=tokenizer1.nextToken(" ");
        StringTokenizer tokenizer2=new StringTokenizer(endDate);
        String e_date=tokenizer2.nextToken(" ");
        String e_time=tokenizer2.nextToken(" ");
        System.out.println("s_date:"+s_date);
        System.out.println("e_date:"+e_date);
        System.out.println("s_time:"+s_time);
        System.out.println("e_time:"+e_time);
        SpaceDetails spaceDetails = communicateWithPhp.getSingleSpace(rentArrayList.get(0).getSpaceId());
        facility=communicateWithPhp.getFacility(String.valueOf(spaceDetails.getGarageid()));
        CalculateInitCost(s_date,e_date,s_time,e_time);


        cost+=Long.parseLong(String.valueOf(spaceDetails.getSpacesize()));
        totalCostBig.setText(String.valueOf(cost));
        totalCost.setText(String.valueOf(cost));
        totalTime.setText(String.valueOf(minutes)+"M");
        totalDays.setText(String.valueOf(days));
        per_day_cost=cost/days;
        perdayCost.setText(String.valueOf(per_day_cost));

    }
    void CalculateInitCost(String s_date,String e_date,String s_time,String e_time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sdate = sdf.parse(s_date);
            Date edate=sdf.parse(e_date);
            long diff = edate.getTime() - sdate.getTime();
            long diff_day= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println("diff day:"+diff_day);
            days=diff_day+1;
            sdf=new SimpleDateFormat("HH:mm:ss");
            Date stime=sdf.parse(s_time);
            Date etime=sdf.parse(e_time);
            diff=etime.getTime()-stime.getTime();
            long diff_time=TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS);
            System.out.println("diff time:"+diff_time);
            minutes=diff_time*days;
            long tmp=60;
            long diff_hour=diff_time/tmp;
            System.out.println("diff hour:"+diff_hour);
            cost=(diff_day+1)*(diff_hour+1)*per_hour_cost;
            System.out.println("init cost:"+cost);
            //facility cost adding
            if(facility.length()>=7) {
                if (facility.charAt(covered_parking_index) == '1')
                    cost += covered_parking_cost;
                if (facility.charAt(cctv_index) == '1')
                    cost += cctv_cost;
                if (facility.charAt(securely_gated_index) == '1')
                    cost += securely_gated_cost;
                if (facility.charAt(disabled_access_index) == '1')
                    cost += disabled_access_cost;
                if (facility.charAt(electric_vehicle_charging_index) == '1')
                    cost += electric_vehicle_cost;
                if (facility.charAt(lighting_index) == '1')
                    cost += lighting_cost;
                if (facility.charAt(oil_buying_index) == '1')
                    cost += oil_buying_cost;
            }
        } catch (ParseException e) {
            System.out.println("Cost exception");
            e.printStackTrace();
        }

    }
    public void confirmPayment(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Payment");
        builder.setMessage("By pressing confirm you state that you have paid the fee.");
        builder.setCancelable(true);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing..
                Intent reviewIntent = new Intent(getApplicationContext(),ReviewActivity.class);
                reviewIntent.putExtra("rentNo",rentNo.getText().toString());
                startActivity(reviewIntent);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing..
            }
        });
        builder.show();
    }

    public void onBackClicked(View view){
        finish();
    }
}
