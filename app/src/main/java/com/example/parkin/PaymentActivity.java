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

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    TextView totalCostBig,totalDays,totalTime,rentNo,perdayCost,penalty,discount,totalCost;
    String rent_no;
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
        penalty.setText("$0");
        discount.setText("$0");
//        String startDate = rentArrayList.get(rentArrayList.indexOf(Integer.parseInt(rent_no))).getStart_time();
//        String endDate = rentArrayList.get(rentArrayList.indexOf(Integer.parseInt(rent_no))).getEnd_time();
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
