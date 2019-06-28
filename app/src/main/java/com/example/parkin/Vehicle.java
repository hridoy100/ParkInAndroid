package com.example.parkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.parkin.R;

public class Vehicle extends AppCompatActivity {
    ListView vehicleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_list_layout);

        vehicleList = findViewById(R.id.vehicleList);
    }
}
