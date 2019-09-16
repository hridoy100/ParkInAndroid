package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkin.Add.AddGarage;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterGarage;
import com.example.parkin.Stepper.MyStepperTest;

import java.util.ArrayList;

public class Garage extends AppCompatActivity implements RecyclerViewAdapterGarage.OnItemClickListener{

    ListView garageList;
    RecyclerView garageView;
    private MainActivity mainActivity;

    ProgressDialog progressDialog;

    ArrayList<String> garageId;
    ArrayList<String> locationName;
    ArrayList<String> mImageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_list_layout);
        progressDialog = new ProgressDialog(this);
        garageId = new ArrayList<>();
        locationName = new ArrayList<>();
        mImageUrls = new ArrayList<>();

        garageView = findViewById(R.id.recycleView_garage);
        progressDialog.setMessage("Loading Garage Details..");
        progressDialog.show();

        initImageBitmaps();
    }

    @Override
    public void onItemClick(int i) {
        Toast.makeText(getApplicationContext(), locationName.get(i),Toast.LENGTH_SHORT).show();
//        Intent showPopUpIntent = new Intent(getApplicationContext(), PopGarageDetails.class);
//        showPopUpIntent.putExtra("location", locationName.get(i));
//        showPopUpIntent.putExtra("garageId", garageId.get(i));
        Log.d("garageID",garageId.get(i));
        Intent singleGarageIntent = new Intent(getApplicationContext(), SingleGarageActivity.class);
        String garId = garageId.get(i).substring(garageId.get(i).indexOf(" ")+1);
        Log.d("garageID",garId);
        singleGarageIntent.putExtra("garageId",garId);
        singleGarageIntent.putExtra("location",locationName.get(i));
        startActivity(singleGarageIntent);
//        startActivity(showPopUpIntent);
    }
    public void addGarageActivity(View view){
        //Intent addGarageIntent = new Intent(getApplicationContext(), AddGarage.class);
        Intent addGarageIntent = new Intent(getApplicationContext(), MyStepperTest.class);
        startActivity(addGarageIntent);
    }

    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<GarageDetails> garageDetailsArrayList = communicateWithPhp.getMyGaragesDB(this);
        progressDialog.dismiss();
        if(garageDetailsArrayList==null){
            return;
        }
        for (int i=0; i<garageDetailsArrayList.size(); i++){
            locationName.add(garageDetailsArrayList.get(i).getAddressName());
            garageId.add("GarageID: "+garageDetailsArrayList.get(i).getGarageId());
            mImageUrls.add("http://www.regencygarages.com/images/garage-img/picture_3.jpg");
            //mImageUrls.add("http://www.regencygarages.com/images/garage-img/picture_3.jpg");
        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_garage);
        RecyclerViewAdapterGarage adapter = new RecyclerViewAdapterGarage(this, locationName, garageId, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationName = new ArrayList<>();
        garageId = new ArrayList<>();
        mImageUrls = new ArrayList<>();
        initImageBitmaps();

    }
}
