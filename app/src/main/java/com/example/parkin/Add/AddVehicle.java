package com.example.parkin.Add;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.RequestHandler;
import com.example.parkin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddVehicle extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_add_layout);
        manufacturer = (Spinner) findViewById(R.id.manufacturer);
        localCode = (Spinner) findViewById(R.id.areaCode);
        vehicleType = (Spinner) findViewById(R.id.type);

        license = (EditText) findViewById(R.id.vehicleLicenseNo);

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

    public void addVehicle(View view) {
        final String manufacturerStr = manufacturer.getSelectedItem().toString();
        final String licenseStr = license.getText().toString();
        final String localCodeStr = localCode.getSelectedItem().toString();
        final String vehicleCodeStr = vehicleCode.getText().toString();
        final String vehicleCode1Str = vehicleCode1.getText().toString();
        String vehicleCode2Str = vehicleCode2.getText().toString();
        String vehicleCode3Str = vehicleCode3.getText().toString();
        String vehicleCode4Str = vehicleCode4.getText().toString();
        String vehicleCode5Str = vehicleCode5.getText().toString();
        final String fitnessStr = fitness.getText().toString();
        final String taxStr = tax.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");

        final String type = vehicleType.getSelectedItem().toString();

        final String registrationNo = vehicleCode1Str+vehicleCode2Str+vehicleCode3Str+vehicleCode4Str+vehicleCode5Str;

        progressDialog.setMessage("Adding Vehicle...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_AddVehicle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(response.contains("success")){
                                Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_SHORT).show();
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(),"Failed" , Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("company", manufacturerStr);
                params.put("licenseNo", licenseStr);
                params.put("areaCode", localCodeStr);
                params.put("vehicleCode", vehicleCodeStr);
                params.put("registrationNo", registrationNo);
                params.put("fitnessCertificate", fitnessStr);
                params.put("taxToken", taxStr);
                params.put("type", type);
                params.put("customerMobNo", mobNo);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
