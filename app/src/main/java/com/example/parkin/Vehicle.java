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

import com.example.parkin.Add.AddVehicle;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.VehicleDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapter;

import java.util.ArrayList;

public class Vehicle extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {
    ListView vehicleList;
    ProgressDialog progressDialog;

    ArrayList<String> license = new ArrayList<>();
    ArrayList<String> company = new ArrayList<>();
    ArrayList<String> reg = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list_layout);

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

    /*class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<VehicleDetails> vehicleDetailsArrayList;

        public MyAdapter(Context context, ArrayList<VehicleDetails> vehicleDetails, String title[]) {
            super(context, R.layout.garage_list_row, title);
            this.context = context;
            this.vehicleDetailsArrayList = vehicleDetails;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.vehicle_list_row, parent, false);
            TextView licenseNo = row.findViewById(R.id.licenseNo);
            TextView company = row.findViewById(R.id.company);
            TextView registration = row.findViewById(R.id.regCode);
            CircleImageView imageView = row.findViewById(R.id.vehicleImage);

            licenseNo.setText("License: "+ vehicleDetailsArrayList.get(position).getLicenseNo());
            company.setText("Company: "+vehicleDetailsArrayList.get(position).getCompany());
            registration.setText("Reg No: "+vehicleDetailsArrayList.get(position).getAreaCode()+" " +vehicleDetailsArrayList.get(position).getVehicleCode()+" " +vehicleDetailsArrayList.get(position).getRegistrationNo());
            if(vehicleDetailsArrayList.get(position).getType().equals("Car")){
                imageView.setImageResource(R.drawable.ic_directions_car_blue);
            }
            else if(vehicleDetailsArrayList.get(position).getType().equals("Bike")){
                imageView.setImageResource(R.drawable.ic_motorcycle_blue);
            }else if(vehicleDetailsArrayList.get(position).getType().equals("Cycle")){
                imageView.setImageResource(R.drawable.ic_bike_blue);
            }
            else if(vehicleDetailsArrayList.get(position).getType().equals("Bus")){
                imageView.setImageResource(R.drawable.ic_directions_bus_black_24dp);
            }
            return row;
        }
    }*/
    public void addVehicleActivity(View view){
        Intent addVehicleIntent = new Intent(getApplicationContext(), AddVehicle.class);
        startActivity(addVehicleIntent);
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
             if(vehicleDetailsArrayList.get(i).getType().equals("Car")){
                 mImageUrls.add("https://banner2.kisspng.com/20180211/kgq/kisspng-car-icon-driving-car-5a804313d86b14.6905057915183552198865.jpg");
             }
             if(vehicleDetailsArrayList.get(i).getType().equals("Bike")){
//                 mImageUrls.add("https://blackswanmoto.com/wp-content/uploads/2018/06/MotoCross-512.png");
                 mImageUrls.add("https://images.vexels.com/media/users/3/152654/isolated/preview/e5694fb12916c00661195c0a833d1ba9-sports-bike-icon-by-vexels.png");
             }
             if(vehicleDetailsArrayList.get(i).getType().equals("Cycle")){
//                 mImageUrls.add("https://apprecs.org/gp/images/app-icons/300/3c/com.littlefluffytoys.cyclehire.jpg");
                 mImageUrls.add("https://cdn4.iconfinder.com/data/icons/cycling/100/cycling-mountain-bike-color-2-512.png");
             }
            if(vehicleDetailsArrayList.get(i).getType().equals("Bus")){
                mImageUrls.add("http://aux4.iconspalace.com/uploads/8012276391018748620.png");
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
        String licenseNo = license.get(i);
        licenseNo = licenseNo.substring(licenseNo.lastIndexOf(" ")+1);
        Toast.makeText(getApplicationContext(), licenseNo, Toast.LENGTH_SHORT).show();
        Log.d("onItemClick", licenseNo);
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        communicateWithPhp.deleteVehicle(licenseNo);
        Toast.makeText(getApplicationContext(),licenseNo+"del", Toast.LENGTH_SHORT).show();
    }
}
