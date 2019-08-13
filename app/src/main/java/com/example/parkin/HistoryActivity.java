package com.example.parkin;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.DB.Rent;
import com.example.parkin.DB.SpaceDetails;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements  RecyclerViewAdapterHistory.OnItemClickListener{
    RecyclerView historyView;
    ProgressDialog progressDialog;
    ArrayList<Rent>rentArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        progressDialog = new ProgressDialog(this);

        historyView = findViewById(R.id.recycleView_history);
        progressDialog.setMessage("Loading Garage Details");
        progressDialog.show();

        initImageBitmaps();

        progressDialog.dismiss();
    }



    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        rentArrayList = communicateWithPhp.getHistory(this);
        System.out.println(rentArrayList.size());

        /*for (int i=0; i<rentArrayList.size(); i++){
            locationName.add(rentArrayList.get(i).getAddressName());
            garageId.add("GarageID: "+garageDetailsArrayList.get(i).getGarageId());
            mImageUrls.add("https://png.pngtree.com/element_our/png_detail/20181229/vector-garage-icon-png_302706.jpg");

        }*/


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_history);
        RecyclerViewAdapterHistory adapter = new RecyclerViewAdapterHistory(this, rentArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemClick(int i) {

    }
}
