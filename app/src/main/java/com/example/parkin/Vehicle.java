package com.example.parkin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.DB.VehicleDetails;
import com.example.parkin.R;

import java.util.ArrayList;

public class Vehicle extends AppCompatActivity {
    ListView vehicleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list_layout);

        vehicleList = findViewById(R.id.vehicleList);

        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getAllVehicleDetailsDB();
        String title[] = new String[vehicleDetailsArrayList.size()];
        for (int i=0; i<vehicleDetailsArrayList.size(); i++){
            title[i] = vehicleDetailsArrayList.get(i).getLicenseId();
        }
        MyAdapter myAdapter = new MyAdapter(this, vehicleDetailsArrayList, title);
        vehicleList.setAdapter(myAdapter);
    }

    class MyAdapter extends ArrayAdapter<String> {
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
            ImageView imageView = row.findViewById(R.id.vehicleImage);

            licenseNo.setText("License: "+ vehicleDetailsArrayList.get(position).getLicenseNo());
            company.setText("Company: "+vehicleDetailsArrayList.get(position).getCompany());
            registration.setText("Reg No: "+vehicleDetailsArrayList.get(position).getAreaCode()+" " +vehicleDetailsArrayList.get(position).getVehicleCode()+" " +vehicleDetailsArrayList.get(position).getRegistrationNo());
            if(vehicleDetailsArrayList.get(position).getType().equals("car")){
                imageView.setImageResource(R.drawable.ic_directions_car_blue);
            }
            else if(vehicleDetailsArrayList.get(position).getType().equals("bike")){
                imageView.setImageResource(R.drawable.ic_motorcycle_blue);
            }else if(vehicleDetailsArrayList.get(position).getType().equals("cycle")){
                imageView.setImageResource(R.drawable.ic_bike_blue);
            }
            return row;
        }
    }
    public void addVehicleActivity(View view){
        Intent addVehicleIntent = new Intent(getApplicationContext(),AddVehicle.class);
        startActivity(addVehicleIntent);
    }
}
