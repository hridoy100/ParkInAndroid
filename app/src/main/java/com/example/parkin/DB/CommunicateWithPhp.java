package com.example.parkin.DB;

import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.HomeActivity;
import com.example.parkin.MainActivity;
import com.example.parkin.Vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicateWithPhp {
    private MainActivity mainActivity;

    public void setMain (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void verifyUser(final String mobileNo, String password) {
        final String mobileNoStr = mobileNo;
        final String passwordStr = password;

        mainActivity.progressDialog.setMessage("Logging into account...");
        mainActivity.progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mainActivity.progressDialog.dismiss();
                        try {
                            Log.i("response",response);
                            if(response.contains("true")) {
                                Log.i("verify", "true");
                                Toast.makeText(mainActivity.getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(mainActivity.getApplicationContext(), HomeActivity.class);
                                mainActivity.startActivity(homeIntent);
                            }
                            else {
                                Toast.makeText(mainActivity.getApplicationContext(), "Incorrect Username or Password" , Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainActivity.progressDialog.hide();
                        Toast.makeText(mainActivity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobileNo", mobileNoStr);
                params.put("password", passwordStr);
                return params;
            }
        };

        RequestHandler.getInstance(mainActivity).addToRequestQueue(stringRequest);
    }


    public ArrayList<GarageDetails> getAllGarageDetailsDB(){
//        progressDialog.setMessage("Fetching Garage Details...");
//        progressDialog.show();
        //new JSONAsyncTask().execute(Constants.URL_AllGarage);
        //System.out.println(garageDetailsArrayList.get(0).getAddressName());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        List<GarageDetails> res = new ArrayList<>();
        try {
            URL website = new URL(Constants.URL_AllGarage);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            JSONArray jsonArray= new JSONArray(response.toString());
            ArrayList<GarageDetails> garageDetailsArrayList = new ArrayList<>();
            ArrayList<String> garageNameArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                GarageDetails garageDetails = new GarageDetails();
                JSONObject garageData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) garageData.get("garageDetails");
                Log.i("dataobj", dataobj.toString());
                garageDetails.setAddressId((String) dataobj.getString("addressId"));
                garageDetails.setGarageId((String)dataobj.getString("garageId"));
                garageDetails.setAddressName((String)dataobj.getString("addressName"));
                garageDetails.setPostalId((String)dataobj.getString("postalId"));
                garageDetails.setLongitude((String)dataobj.getString("longitude"));
                garageDetails.setLatitude((String)dataobj.getString("latitude"));

                garageDetailsArrayList.add(garageDetails);
                garageNameArrayList.add(garageDetails.getAddressName());

            }
            return garageDetailsArrayList;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_AllGarage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressDialog.dismiss();
                        try {
                            //getting the whole json object from the response
                            Log.i("response", response);
                            JSONArray jsonArray= new JSONArray(response);
                            ArrayList<GarageDetails> garageDetailsArrayList = new ArrayList<>();
                            ArrayList<String> garageNameArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                GarageDetails garageDetails = new GarageDetails();
                                JSONObject garageData = (JSONObject) jsonArray.get(i);
                                JSONObject dataobj = (JSONObject) garageData.get("garageDetails");
                                Log.i("dataobj", dataobj.toString());
                                garageDetails.setAddressId((String) dataobj.getString("addressId"));
                                garageDetails.setGarageId((String)dataobj.getString("garageId"));
                                garageDetails.setAddressName((String)dataobj.getString("addressName"));
                                garageDetails.setPostalId((String)dataobj.getString("postalId"));
                                garageDetails.setLongitude((String)dataobj.getString("longitude"));
                                garageDetails.setLatitude((String)dataobj.getString("latitude"));

                                garageDetailsArrayList.add(garageDetails);
                                garageNameArrayList.add(garageDetails.getAddressName());

                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, garageNameArrayList);
                            garageList.setAdapter(arrayAdapter);

                            /*} else {
                                Toast.makeText(getBaseContext(), obj.optString("No response from server") + "", Toast.LENGTH_SHORT).show();
                            }*/
/*
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        */
    }


    public ArrayList<VehicleDetails> getAllVehicleDetailsDB() {
//        progressDialog.setMessage("Fetching Garage Details...");
//        progressDialog.show();
        //new JSONAsyncTask().execute(Constants.URL_AllGarage);
        //System.out.println(garageDetailsArrayList.get(0).getAddressName());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_AllVehicle);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            JSONArray jsonArray = new JSONArray(response.toString());
            ArrayList<VehicleDetails> vehicleDetailsArrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                VehicleDetails vehicleDetails = new VehicleDetails();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("vehicleDetails");
                Log.i("dataobj", dataobj.toString());
                vehicleDetails.setLicenseId((String) dataobj.getString("licenseId"));
                vehicleDetails.setLicenseNo((String) dataobj.getString("licenseNo"));
                vehicleDetails.setAreaCode((String) dataobj.getString("areaCode"));
                vehicleDetails.setVehicleCode((String) dataobj.getString("vehicleCode"));
                vehicleDetails.setRegistrationNo((String) dataobj.getString("registrationNo"));
                vehicleDetails.setCustomerMobNo((String) dataobj.getString("customerMobNo"));
                vehicleDetails.setType((String) dataobj.getString("type"));
                vehicleDetails.setCompany((String) dataobj.getString("company"));
                vehicleDetails.setFitnessCertificate((String) dataobj.getString("fitnessCertificate"));
                vehicleDetails.setTaxToken((String) dataobj.getString("taxToken"));


                vehicleDetailsArrayList.add(vehicleDetails);

            }
            return vehicleDetailsArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
