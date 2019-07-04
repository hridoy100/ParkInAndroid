package com.example.parkin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Timer;

public class AddGarage extends AppCompatActivity {
    TimePicker timePicker;

    Spinner hrCost;
    Spinner totalSlot;
    Spinner city;
    Spinner locality;

    Button openTime;
    Button closeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_add_layout);

        hrCost = (Spinner) findViewById(R.id.hourlyCost);
        totalSlot = (Spinner) findViewById(R.id.totalSlot);
        city = (Spinner) findViewById(R.id.cityCode);
        locality = (Spinner) findViewById(R.id.localityCode);
        openTime = (Button) findViewById(R.id.openTime);
        closeTime = (Button) findViewById(R.id.closeTime);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.hourCost));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hrCost.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.totalSlot));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        totalSlot.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.city));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(arrayAdapter);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.localityDhaka));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locality.setAdapter(arrayAdapter);


    }

    public void setOpenTime(View view){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.i("hr", String.valueOf(selectedHour));
                Log.i("min", String.valueOf(selectedMinute));
                if(selectedMinute<10)
                    openTime.setText( selectedHour + ":0" + selectedMinute);
                else
                    openTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void setCloseTime(View view){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.i("hr", String.valueOf(selectedHour));
                Log.i("min", String.valueOf(selectedMinute));
                if(selectedMinute<10)
                    closeTime.setText( selectedHour + ":0" + selectedMinute);
                else
                    closeTime.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}
