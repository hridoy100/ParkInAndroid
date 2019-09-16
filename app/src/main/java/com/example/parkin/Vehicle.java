package com.example.parkin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.parkin.Add.AddVehicle;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.VehicleDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class Vehicle extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {
    ListView vehicleList;
    ProgressDialog progressDialog;

    ArrayList<String> license ;
    ArrayList<String> company ;
    ArrayList<String> reg ;
    ArrayList<String> mImageUrls ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list_layout);
        license = new ArrayList<>();
        company = new ArrayList<>();
        reg = new ArrayList<>();
        mImageUrls = new ArrayList<>();

        progressDialog = new ProgressDialog(this);

        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Vehicle Details");
        progressDialog.show();
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

    @Override
    public void onItemClick(int i) {
        String licenseNo = license.get(i);
        licenseNo = licenseNo.substring(licenseNo.lastIndexOf(" ")+1);
        Toast.makeText(getApplicationContext(), licenseNo, Toast.LENGTH_SHORT).show();
        Log.d("onItemClick", licenseNo);
        Intent editIntent = new Intent(getApplicationContext(), VehicleEditActivity.class);
        editIntent.putExtra("licenseNo",licenseNo);
        startActivity(editIntent);
    }
    public void addVehicleActivity(View view){
        Intent addVehicleIntent = new Intent(getApplicationContext(), AddVehicle.class);
        startActivity(addVehicleIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getVehicleDetailsDB(getApplicationContext());

        for (int i=0; i<vehicleDetailsArrayList.size(); i++){
             license.add("License No: "+vehicleDetailsArrayList.get(i).getLicenseNo());
             company.add("Company: "+vehicleDetailsArrayList.get(i).getCompany());
             reg.add("Registration No: "+vehicleDetailsArrayList.get(i).getRegistrationNo());
             if(vehicleDetailsArrayList.get(i).getType().equals("Small Car")){
//                 mImageUrls.add(String.valueOf(getResources().getIdentifier("small.jpg","drawable",this.getPackageName())));
                 //mImageUrls.add("https://banner2.kisspng.com/20180211/kgq/kisspng-car-icon-driving-car-5a804313d86b14.6905057915183552198865.jpg");
                 mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7pl5qcWceXBTYFU4n3_xVyMfSedhWHTDP4eSE3hZyzzLynK5K");
             }
             else if(vehicleDetailsArrayList.get(i).getType().equals("Medium Car")){
                 //mImageUrls.add(String.valueOf(getResources().getIdentifier("medium_1", "drawable",this.getPackageName())));
                 mImageUrls.add("https://banner2.kisspng.com/20180418/edq/kisspng-car-opel-insignia-b-opel-cascada-common-rail-view-clipart-5ad7d5a282f9f6.6507513715240943705365.jpg");
                 //mImageUrls.add("https://banner2.kisspng.com/20180211/kgq/kisspng-car-icon-driving-car-5a804313d86b14.6905057915183552198865.jpg");
             }
             else if(vehicleDetailsArrayList.get(i).getType().equals("Motor Bike")){
//                 mImageUrls.add("https://blackswanmoto.com/wp-content/uploads/2018/06/MotoCross-512.png");
                 //mImageUrls.add(String.valueOf(getResources().getIdentifier("bike_small_32.png", "drawable",this.getPackageName())));
                 mImageUrls.add("https://ic.maxabout.us//bikes/hyosung/gt250r//New-Hyosung-GT250R-Red-Titanium.jpg");
                 //mImageUrls.add("https://images.vexels.com/media/users/3/152654/isolated/preview/e5694fb12916c00661195c0a833d1ba9-sports-bike-icon-by-vexels.png");
             }
//             if(vehicleDetailsArrayList.get(i).getType().contains("Cycle")){
////                 mImageUrls.add("https://apprecs.org/gp/images/app-icons/300/3c/com.littlefluffytoys.cyclehire.jpg");
//                 mImageUrls.add("https://cdn4.iconfinder.com/data/icons/cycling/100/cycling-mountain-bike-color-2-512.png");
//             }
            else if(vehicleDetailsArrayList.get(i).getType().equals("4x4 or Large Car")){
//                mImageUrls.add(String.valueOf(getResources().getIdentifier("pajero.jpg", "drawable",this.getPackageName())));
//                mImageUrls.add("http://aux4.iconspalace.com/uploads/8012276391018748620.png");
                mImageUrls.add("https://giatotmitsu.com/wp-content/uploads/2019/04/Xam-U17.png");
            }
            else if(vehicleDetailsArrayList.get(i).getType().equals("Mini Van")){
//                mImageUrls.add(String.valueOf(getResources().getIdentifier("minivan_32.jpg", "drawable",this.getPackageName())));
                mImageUrls.add("https://5.imimg.com/data5/QB/KJ/LI/SELLER-7746960/intra-v20-250x250.jpg");
            }
            else  if(vehicleDetailsArrayList.get(i).getType().equals("Large Van or Minibus")){
//                mImageUrls.add(String.valueOf(getResources().getIdentifier("mini_bus.jpg", "drawable",this.getPackageName())));
                mImageUrls.add("https://i.ytimg.com/vi/z-lQPPoOzYM/maxresdefault.jpg");
            }
            else {
                mImageUrls.add("https://cdn3.iconfinder.com/data/icons/transport-03-set-of-trucks-tractors-concrete-mixer/110/Freight_transport_24-512.png");
            }

        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_vehicle);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,license,company, reg, mImageUrls, this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemLongClick(int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vehicle Deletion");
        builder.setMessage("Are you sure you want to delete vehicle with license number "+license.get(i)+" ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String licenseNo = license.get(i);
                licenseNo = licenseNo.substring(licenseNo.lastIndexOf(" ")+1);
                Toast.makeText(getApplicationContext(), licenseNo, Toast.LENGTH_SHORT).show();
                Log.d("onItemClick", licenseNo);
                CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
                communicateWithPhp.deleteVehicle(licenseNo);
                Toast.makeText(getApplicationContext(),licenseNo+"del", Toast.LENGTH_SHORT).show();

                license = new ArrayList<>();
                company = new ArrayList<>();
                reg = new ArrayList<>();
                mImageUrls = new ArrayList<>();
                initImageBitmaps();
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

    @Override
    protected void onResume() {
        super.onResume();

        license = new ArrayList<>();
        company = new ArrayList<>();
        reg = new ArrayList<>();
        mImageUrls = new ArrayList<>();
        initImageBitmaps();
    }
}
