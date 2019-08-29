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

import java.util.ArrayList;

public class Garage extends AppCompatActivity implements RecyclerViewAdapterGarage.OnItemClickListener{

    ListView garageList;
    RecyclerView garageView;
    private MainActivity mainActivity;

    ProgressDialog progressDialog;

    ArrayList<String> garageId = new ArrayList<>();
    ArrayList<String> locationName = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_list_layout);
        progressDialog = new ProgressDialog(this);

        garageView = findViewById(R.id.recycleView_garage);
        progressDialog.setMessage("Loading Garage Details");
        progressDialog.show();

        initImageBitmaps();

        progressDialog.dismiss();

    }

    @Override
    public void onItemClick(int i) {
        Toast.makeText(getApplicationContext(), locationName.get(i),Toast.LENGTH_SHORT).show();
        Intent showPopUpIntent = new Intent(getApplicationContext(), PopGarageDetails.class);
        showPopUpIntent.putExtra("location", locationName.get(i));
        showPopUpIntent.putExtra("garageId", garageId.get(i));
        Log.d("garageID",garageId.get(i));
        startActivity(showPopUpIntent);
    }
    public void addGarageActivity(View view){
        Intent addGarageIntent = new Intent(getApplicationContext(), AddGarage.class);
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

        for (int i=0; i<garageDetailsArrayList.size(); i++){
            locationName.add(garageDetailsArrayList.get(i).getAddressName());
            garageId.add("GarageID: "+garageDetailsArrayList.get(i).getGarageId());
            mImageUrls.add("https://png.pngtree.com/element_our/png_detail/20181229/vector-garage-icon-png_302706.jpg");

        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_garage);
        RecyclerViewAdapterGarage adapter = new RecyclerViewAdapterGarage(this, locationName, garageId, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
