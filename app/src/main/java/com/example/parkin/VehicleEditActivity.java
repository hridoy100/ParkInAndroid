package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.RequestHandler;
import com.example.parkin.DB.VehicleDetails;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


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
    EditText licenseNo;
    TextView licenseId;
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

        Intent editIntent = getIntent();
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        VehicleDetails vehicleDetails = communicateWithPhp.getOneVehicleDetails(editIntent.getStringExtra("licenseNo"));


        manufacturer = (Spinner) findViewById(R.id.manufacturer);

        localCode = (Spinner) findViewById(R.id.areaCode);

        vehicleType = (Spinner) findViewById(R.id.type);

        licenseNo = (EditText) findViewById(R.id.vehicleLicenseNo);
        licenseNo.setText(vehicleDetails.getLicenseNo());
        licenseId = (TextView) findViewById(R.id.licenseId);
        licenseId.setText(licenseId.getText()+vehicleDetails.getLicenseId());

        int vCode = Integer.parseInt(vehicleDetails.getRegistrationNo());
        vehicleCode = (EditText) findViewById(R.id.vehicleCode);
        vehicleCode.setText(vehicleDetails.getVehicleCode());

        vehicleCode1 = (EditText) findViewById(R.id.vehicleCode1);
        vehicleCode2 = (EditText) findViewById(R.id.vehicleCode2);
        vehicleCode3 = (EditText) findViewById(R.id.vehicleCode3);
        vehicleCode4 = (EditText) findViewById(R.id.vehicleCode4);
        vehicleCode5 = (EditText) findViewById(R.id.vehicleCode5);
        Log.d("vCode", String.valueOf(vCode));
        vehicleCode5.setText(String.valueOf(vCode%10));
        vCode = vCode/10;
        vehicleCode4.setText(String.valueOf(vCode%10));
        vCode = vCode/10;
        vehicleCode3.setText(String.valueOf(vCode%10));
        vCode = vCode/10;
        vehicleCode2.setText(String.valueOf(vCode%10));
        vCode = vCode/10;
        vehicleCode1.setText(String.valueOf(vCode));

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
        fitness.setText(vehicleDetails.getFitnessCertificate());
        tax = (EditText) findViewById(R.id.tax);
        tax.setText(vehicleDetails.getTaxToken());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.manufaturerList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturer.setAdapter(arrayAdapter);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.areaCodeList));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localCode.setAdapter(adapter);

        ArrayAdapter vehicleTypeAd = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.vehicleType));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleType.setAdapter(vehicleTypeAd);

        setSpinText(manufacturer, vehicleDetails.getCompany());
        setSpinText(localCode, vehicleDetails.getAreaCode());
        setSpinText(vehicleType, vehicleDetails.getType());

        progressDialog = new ProgressDialog(this);
    }



    public void submitEditedVehicle(View view) {
        final String manufacturerStr = manufacturer.getSelectedItem().toString();
        final String licenseStr = licenseNo.getText().toString();
        String licenseIdStrTmp = licenseId.getText().toString();
        licenseIdStrTmp = licenseIdStrTmp.substring(licenseIdStrTmp.lastIndexOf(" ")+1);
        Log.d("licenseId", licenseIdStrTmp);

        final String licenseIdStr = licenseIdStrTmp;
        final String localCodeStr = localCode.getSelectedItem().toString();
        final String vehicleCodeStr = vehicleCode.getText().toString();
        String vehicleCode1Str = vehicleCode1.getText().toString();
        String vehicleCode2Str = vehicleCode2.getText().toString();
        String vehicleCode3Str = vehicleCode3.getText().toString();
        String vehicleCode4Str = vehicleCode4.getText().toString();
        String vehicleCode5Str = vehicleCode5.getText().toString();
        final String fitnessStr = fitness.getText().toString();
        final String taxStr = tax.getText().toString();

        final String type = vehicleType.getSelectedItem().toString();

        final String registrationNo = vehicleCode1Str+vehicleCode2Str+vehicleCode3Str+vehicleCode4Str+vehicleCode5Str;

        progressDialog.setMessage("Submitting Edit Details...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_EditVehicle,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(response.contains("success")){
                                Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_SHORT).show();
                                finishActivity(1);

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
                params.put("licenseId", licenseIdStr);
                params.put("areaCode", localCodeStr);
                params.put("vehicleCode", vehicleCodeStr);
                params.put("registrationNo", registrationNo);
                params.put("fitnessCertificate", fitnessStr);
                params.put("taxToken", taxStr);
                params.put("type", type);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
