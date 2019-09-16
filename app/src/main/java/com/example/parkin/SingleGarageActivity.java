package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSingleGarage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Queue;

public class SingleGarageActivity extends AppCompatActivity implements RecyclerViewAdapterSingleGarage.OnItemClickListener{

    RecyclerView singleGarageView;

    ProgressDialog progressDialog;

    ArrayList<String> spaceId = new ArrayList<>();
    ArrayList<String> spaceSize = new ArrayList<>();
    ArrayList<String> position = new ArrayList<>();
    ArrayList<String> startTime = new ArrayList<>();
    ArrayList<String> endTime = new ArrayList<>();
    ArrayList<String> availability = new ArrayList<>();
    ArrayList<String> cctvIp = new ArrayList<>();

    String addressName, garageIdStr;
    ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_garage);
        progressDialog = new ProgressDialog(this);

        singleGarageView = findViewById(R.id.recycleView_single_garage);
        progressDialog.setMessage("Loading Garage Details..");
        progressDialog.show();

        Intent myIntent = getIntent();
        addressName = myIntent.getStringExtra("location");
        garageIdStr = myIntent.getStringExtra("garageId");

        initImageBitmaps();
    }


    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<SpaceDetails> spaceDetailsArrayList = communicateWithPhp.getGarageSpace(garageIdStr);
        progressDialog.dismiss();
        if(spaceDetailsArrayList==null){
            return;
        }
        for (int i=0; i<spaceDetailsArrayList.size(); i++){
            spaceId.add(Integer.toString(spaceDetailsArrayList.get(i).getSpaceid()));
            spaceSize.add(Integer.toString(spaceDetailsArrayList.get(i).getSpacesize()));
            position.add(spaceDetailsArrayList.get(i).getPosition());

            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            String start=DateFormat.format(spaceDetailsArrayList.get(i).getStarttime1().getTime());
            String end=DateFormat.format(spaceDetailsArrayList.get(i).getStarttime2().getTime());
            startTime.add(start);
            endTime.add(end);
            availability.add(spaceDetailsArrayList.get(i).getAvailability());
            cctvIp.add(spaceDetailsArrayList.get(i).getCctvIp());

            mImageUrls.add("http://www.regencygarages.com/images/garage-img/picture_3.jpg");

        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_single_garage);
        RecyclerViewAdapterSingleGarage adapter = new RecyclerViewAdapterSingleGarage(this, spaceId, spaceSize, position,
                startTime, endTime, availability, cctvIp, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemClick(int i) {
        Toast.makeText(getApplicationContext(), spaceId.get(i),Toast.LENGTH_SHORT).show();
    }
}
