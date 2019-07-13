package com.example.parkin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.VehicleDetails;

import java.util.ArrayList;

public class VehicleEditActivity extends AppCompatActivity {
    String manufacturerString[] = {
            "Ford",
            "Honda",
            "Hyundai",
            "Maruti Suzuki",
            "Mitsubishi",
            "Nissan",
            "Tata",
            "Toyota"
    };

    Spinner manufacturer;
    Spinner vehicleType;
    Spinner localCode;
    EditText vehicleCode;
    EditText vehicleCode1;
    EditText vehicleCode2;
    EditText vehicleCode3;
    EditText vehicleCode4;
    EditText vehicleCode5;
    EditText license;
    EditText fitness;
    EditText tax;

    private ProgressDialog progressDialog;

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_edit_layout);


        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        VehicleDetails vehicleDetails = communicateWithPhp.getOneVehicleDetails("");


        manufacturer = (Spinner) findViewById(R.id.manufacturer);
        setSpinText(manufacturer, vehicleDetails.getCompany());

        localCode = (Spinner) findViewById(R.id.areaCode);
        setSpinText(localCode, vehicleDetails.getAreaCode());

        vehicleType = (Spinner) findViewById(R.id.type);
        setSpinText(vehicleType, vehicleDetails.getType());

        license = (EditText) findViewById(R.id.vehicleLicenseNo);
        license.setText(vehicleDetails.getLicenseNo());

        vehicleCode = (EditText) findViewById(R.id.vehicleCode);
        vehicleCode1 = (EditText) findViewById(R.id.vehicleCode1);
        vehicleCode2 = (EditText) findViewById(R.id.vehicleCode2);
        vehicleCode3 = (EditText) findViewById(R.id.vehicleCode3);
        vehicleCode4 = (EditText) findViewById(R.id.vehicleCode4);
        vehicleCode5 = (EditText) findViewById(R.id.vehicleCode5);


        vehicleCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(vehicleCode1.getText().toString().length()>0){
                    vehicleCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //vehicleCode2.requestFocus();
            }
        });

        vehicleCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(vehicleCode2.getText().toString().length()>0){
                    vehicleCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //vehicleCode3.requestFocus();
            }
        });

        vehicleCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(vehicleCode3.getText().toString().length()>0){
                    vehicleCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //vehicleCode4.requestFocus();
            }
        });
        vehicleCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(vehicleCode4.getText().toString().length()>0){
                    vehicleCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fitness = (EditText) findViewById(R.id.fitness);
        tax = (EditText) findViewById(R.id.tax);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.manufaturerList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturer.setAdapter(arrayAdapter);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.areaCodeList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localCode.setAdapter(adapter);

        ArrayAdapter vehicleTypeAd = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vehicleType));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(vehicleTypeAd);

        progressDialog = new ProgressDialog(this);


    }
}
