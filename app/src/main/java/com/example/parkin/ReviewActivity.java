package com.example.parkin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReviewActivity extends AppCompatActivity {

    String rentNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent myIntent = getIntent();
        rentNo = myIntent.getStringExtra("rentNo");
    }
}
