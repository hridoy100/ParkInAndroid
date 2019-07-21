package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.DB.VehicleDetails;
import com.example.parkin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpaceDetailsView extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {
    ListView vehicleList;
    ProgressDialog progressDialog;

    ArrayList<String> spacenolist = new ArrayList<>();
    ArrayList<String> spacesizelist= new ArrayList<>();
    ArrayList<String> minimumcostlist = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<SpaceDetails>spaceDetails=new ArrayList<>();
    Calendar arrivaltime;
    Calendar departuretime;
    String garageaddress;
    int garageid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_list_layout);

        progressDialog = new ProgressDialog(this);

        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Available Space Details");
        progressDialog.show();
        Intent myintent=getIntent();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        long arrtime=myintent.getLongExtra("arrivaltime", Calendar.getInstance().getTimeInMillis());
        long dtime=myintent.getLongExtra("departuretime", Calendar.getInstance().getTimeInMillis());
        garageaddress=myintent.getStringExtra("garagelocation");
        garageid=myintent.getIntExtra("garageid",1);//Integer.parseInt(myintent.getExtras().get("garageid").toString());
        arrivaltime=gettime(arrtime);
        departuretime=gettime(dtime);
        initImageBitmaps();
/*
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getVehicleDetailsDB(getApplicationContext());
        String title[] = new String[vehicleDetailsArrayList.size()];
        for (int i=0; i<vehicleDetailsArrayList.size(); i++){
            title[i] = vehicleDetailsArrayList.get(i).getLicenseId();
        }
        MyAdapter myAdapter = new MyAdapter(this, vehicleDetailsArrayList, title);
        vehicleList.setAdapter(myAdapter);
        */
        progressDialog.dismiss();
    }
    Calendar gettime(long val)
    {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis(val);

        Date date = myDate.getTime();
        myDate.setTime(date);
        return myDate;
    }
    @Override
    public void onItemClick(int i) {
//        String licenseNo = license.get(i);
//        licenseNo = licenseNo.substring(licenseNo.lastIndexOf(" ")+1);
//        Toast.makeText(getApplicationContext(), licenseNo, Toast.LENGTH_SHORT).show();
//        Log.d("onItemClick", licenseNo);
//        Intent editIntent = new Intent(getApplicationContext(), VehicleEditActivity.class);
//        editIntent.putExtra("licenseNo",licenseNo);
//        startActivity(editIntent);
    }

    public void goBackActivity(View view){
        Intent mapIntent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(mapIntent);
    }


    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        Log.d("GarageID",Integer.toString(garageid));
        Log.d("arrivalTime",myDateFormat.format(arrivaltime.getTime()));
        Log.d("DepartureTime",myDateFormat.format(departuretime.getTime()));
        spaceDetails = communicateWithPhp.getAvailableSpaces(garageid, arrivaltime, departuretime);
        //ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getVehicleDetailsDB(getApplicationContext());

        for (int i=0; i<spaceDetails.size(); i++){
            spacenolist.add("Space No: "+ i);
            spacesizelist.add("Space Size: "+spaceDetails.get(i).getSpacesize());
            minimumcostlist.add("Minimum Cost: "+"10 taka");
            mImageUrls.add("https://banner2.kisspng.com/20180211/kgq/kisspng-car-icon-driving-car-5a804313d86b14.6905057915183552198865.jpg");
        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_space);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,spacenolist,spacesizelist, minimumcostlist, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}

