package com.example.parkin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.parkin.R;

public class Garage extends AppCompatActivity {
    ListView garageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_list_layout);

        garageList = findViewById(R.id.garageList);
    }
}
