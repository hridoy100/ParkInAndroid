package com.example.parkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.Rent;
import com.example.parkin.DB.SpaceDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static com.example.parkin.DB.Constants.DEBUG;
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
    RadioGroup paymentMethodGroup;
    RadioButton paymentMethod;
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

        paymentMethodGroup = (RadioGroup) findViewById(R.id.paymentMethodGroup);

        paymentMethodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                paymentMethod = (RadioButton) findViewById(checkedId);
            }
        });

        rentNo.setText(rent_no);
        fetchDataFromDatabase();

    }

    private void fetchDataFromDatabase() {
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<Rent> rentArrayList = communicateWithPhp.getRentInfo(rent_no);
        penalty.setText(Constants.taka+"0");
        discount.setText(Constants.taka+"0");
        String startDate = rentArrayList.get(0).getStart_time();
        String endDate = rentArrayList.get(0).getEnd_time();
        StringTokenizer tokenizer1=new StringTokenizer(startDate);
        String s_date=tokenizer1.nextToken(" ");
        String s_time=tokenizer1.nextToken(" ");
        StringTokenizer tokenizer2=new StringTokenizer(endDate);
        String e_date=tokenizer2.nextToken(" ");
        String e_time=tokenizer2.nextToken(" ");
        if(Constants.DEBUG) {
            System.out.println("s_date:" + s_date);
            System.out.println("e_date:" + e_date);
            System.out.println("s_time:" + s_time);
            System.out.println("e_time:" + e_time);
        }
        SpaceDetails spaceDetails = communicateWithPhp.getSingleSpace(rentArrayList.get(0).getSpaceId());
        facility=communicateWithPhp.getFacility(String.valueOf(spaceDetails.getGarageid()));
        CalculateInitCost(s_date,e_date,s_time,e_time);


        cost+=Long.parseLong(String.valueOf(spaceDetails.getSpacesize()));
        totalCostBig.setText(Constants.taka+String.valueOf(cost));
        totalCost.setText(Constants.taka+String.valueOf(cost));
        int hour,minute;
        minute = Integer.valueOf(String.valueOf(minutes));
        hour = minute/60;
        minute=minute%60;
        //totalTime.setText(String.valueOf(minutes)+"M");
        totalTime.setText(String.valueOf(hour)+"H "+String.valueOf(minute)+"M ");
        totalDays.setText(String.valueOf(days));
        per_day_cost=cost/days;
        perdayCost.setText(Constants.taka+String.valueOf(per_day_cost));

    }
    void CalculateInitCost(String s_date,String e_date,String s_time,String e_time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sdate = sdf.parse(s_date);
            Date edate=sdf.parse(e_date);
            long diff = edate.getTime() - sdate.getTime();
            long diff_day= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if(Constants.DEBUG)
                System.out.println("diff day:"+diff_day);

            days=diff_day+1;
            sdf=new SimpleDateFormat("HH:mm:ss");
            Date stime=sdf.parse(s_time);
            Date etime=sdf.parse(e_time);
            diff=etime.getTime()-stime.getTime();
            long diff_time=TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS);

            if(Constants.DEBUG)
                System.out.println("diff time:"+diff_time);

            minutes=diff_time*days;
            long tmp=60;
            long diff_hour=diff_time/tmp;

            if(Constants.DEBUG)
                System.out.println("diff hour:"+diff_hour);

            cost=(diff_day+1)*(diff_hour+1)*per_hour_cost;

            if(DEBUG)
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

        if(paymentMethod==null){
//            paymentMethod = findViewById(R.id.cash);
//            paymentMethod.setChecked(true);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Select Payment Method");
            builder1.setMessage("Please Select one payment method");
            builder1.setCancelable(true);
            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder1.show();
            return;
        }

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing..

                CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
                communicateWithPhp.updateRentStatus(rent_no, Constants.completed);
                communicateWithPhp.updatePayment(rent_no,String.valueOf(cost),paymentMethod.getText().toString());

                AlertDialog.Builder builder2 = new AlertDialog.Builder(getApplicationContext());

                builder2.setMessage("Payment Completed");
                builder2.setCancelable(true);

                builder2.setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(homeIntent);
                    }
                });

                //Intent reviewIntent = new Intent(getApplicationContext(),ReviewActivity.class);
                //reviewIntent.putExtra("rentNo",rentNo.getText().toString());
                //startActivity(reviewIntent);
//                finish();
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
        Intent singleNotificationIntent = new Intent(getApplicationContext(), SingleNotificationActivity.class);
        singleNotificationIntent.putExtra("rentNo",rent_no);
        startActivity(singleNotificationIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

}
