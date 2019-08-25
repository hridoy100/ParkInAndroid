package com.example.parkin.DB;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Space;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.HomeActivity;
import com.example.parkin.MainActivity;
import com.example.parkin.Notification;
import com.example.parkin.R;
import com.example.parkin.Vehicle;
import com.example.parkin.util.NotificationThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicateWithPhp {
    private MainActivity mainActivity;
    private Context context;
    public void setMain (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void setContext(Context context) {this.context = context;}

    public void verifyUser(final String mobileNo, String password) {
        final String mobileNoStr = mobileNo;
        final String passwordStr = password;

//        mainActivity.progressDialog.setMessage("Logging into account...");
//        mainActivity.progressDialog.show();

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
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(context, HomeActivity.class);
                                context.startActivity(homeIntent);
                            }
                            else {
                                Toast.makeText(context, "Incorrect Username or Password" , Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Connection Error" , Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainActivity.progressDialog.hide();
                        Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
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


    public ArrayList<GarageDetails> getMyGaragesDB(Context context){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        List<GarageDetails> res = new ArrayList<>();
        try {
            URL website = new URL(Constants.URL_GETMYGARAGES);
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            //Log.i("SharedmobileNo: ",mobNo);
            Log.i("Mob No", mobNo);
            ps.print("&mobileNo="+mobNo);
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
                JSONObject dataobj = (JSONObject) garageData.get("myGarage");
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
    }


    public ArrayList<VehicleDetails> getVehicleDetailsDB(Context context) {
//        progressDialog.setMessage("Fetching Garage Details...");
//        progressDialog.show();
        //new JSONAsyncTask().execute(Constants.URL_AllGarage);
        //System.out.println(garageDetailsArrayList.get(0).getAddressName());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_AllVehicle);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            //Log.i("SharedmobileNo: ",mobNo);
            ps.print("&mobileNo="+mobNo);

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

    public VehicleDetails getOneVehicleDetails(String licenseNo) {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_OneVehicle);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&licenseNo="+licenseNo);

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

            VehicleDetails vehicleDetails = new VehicleDetails();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("vehicleDescription");
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
            }
            return vehicleDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<SpaceDetails> getAvailableSpaces(int garageid, Calendar arrivaltime,Calendar departuretime) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_AVAILABLESPACES);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            Log.d("starttime",DateFormat.format(arrivaltime.getTime()));
            Log.d("endtime",DateFormat.format(departuretime.getTime()));
            String arrive=DateFormat.format(arrivaltime.getTime());
            String end=DateFormat.format(departuretime.getTime());
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&garageId="+garageid);
            //ps.print("&start_time="+arrivaltime);
            //ps.print("&end_time="+departuretime);
            ps.print("&start_time="+arrive);
            ps.print("&end_time="+end);

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

            ArrayList<SpaceDetails> spaceDetailsArrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                SpaceDetails spaceDetails = new SpaceDetails();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("availableSpace");
                Log.i("dataobj", dataobj.toString());
                spaceDetails.setSpaceid(dataobj.getInt("spaceId"));
                spaceDetails.setGarageid(dataobj.getInt("garageId"));
                spaceDetails.setSpacesize(dataobj.getInt("spaceSize"));
                spaceDetails.setPosition(dataobj.getString("position"));
                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                Calendar cal=Calendar.getInstance();
                cal.setTime(myDateFormat.parse(dataobj.getString("start_time")));
                spaceDetails.setStarttime1(cal);
                cal.setTime(myDateFormat.parse(dataobj.getString("end_time")));
                spaceDetails.setGetStarttime2(cal);
                spaceDetails.setAvailability(dataobj.getString("availability"));
                spaceDetailsArrayList.add(spaceDetails);
            }
            return spaceDetailsArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void bookGarageSpace(Context context,int garageid,int spaceid, Calendar arrivaltime,Calendar departuretime,int licenseid) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_BOOKSPACE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);
            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            Log.d("starttime", DateFormat.format(arrivaltime.getTime()));
            Log.d("endtime", DateFormat.format(departuretime.getTime()));
            String arrive = DateFormat.format(arrivaltime.getTime());
            String end = DateFormat.format(departuretime.getTime());
            PrintStream ps = new PrintStream(connection.getOutputStream());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            Log.i("SharedmobileNo: ",mobNo);
            ps.print("&customerMobNo="+mobNo);
            ps.print("&garageId=" + garageid);
            ps.print("&spaceId=" + spaceid);
            ps.print("licenseID=" +licenseid);
            //ps.print("&start_time="+arrivaltime);
            //ps.print("&end_time="+departuretime);
            ps.print("&start_time=" + arrive);
            ps.print("&end_time=" + end);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<SpaceDetails> getGarageSpace(String garageid) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GARAGESPACE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&garageId="+garageid);

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

            ArrayList<SpaceDetails> spaceDetailsArrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                SpaceDetails spaceDetails = new SpaceDetails();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("garageTotalSpace");
                Log.i("dataobj", dataobj.toString());
                spaceDetails.setSpaceid(dataobj.getInt("spaceId"));
                spaceDetails.setGarageid(dataobj.getInt("garageId"));
                spaceDetails.setSpacesize(dataobj.getInt("spaceSize"));
                spaceDetails.setPosition(dataobj.getString("position"));
                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                Calendar cal=Calendar.getInstance();
                cal.setTime(myDateFormat.parse(dataobj.getString("start_time")));
                spaceDetails.setStarttime1(cal);
                cal.setTime(myDateFormat.parse(dataobj.getString("end_time")));
                spaceDetails.setGetStarttime2(cal);
                spaceDetails.setAvailability(dataobj.getString("availability"));
                spaceDetailsArrayList.add(spaceDetails);
            }
            return spaceDetailsArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Rent> getHistory(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETHISTORY);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            Log.i("SharedmobileNo: ",mobNo);
            ps.print("&customerMobNo="+mobNo);

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


            ArrayList<Rent> rentArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Rent rentDetails = new Rent();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("customerHistory");
                Log.i("dataobj", dataobj.toString());
                rentDetails.setLicenseId((String) dataobj.getString("licenseId"));
                rentDetails.setRentNo((String) dataobj.getString("rentNo"));
                rentDetails.setPaymentNo((String) dataobj.getString("paymentNo"));
                rentDetails.setRenterMobNo((String) dataobj.getString("renterMobNo"));
                rentDetails.setSpaceId((String) dataobj.getString("spaceId"));
                rentDetails.setCustomerMobNo((String) dataobj.getString("customerMobNo"));
                rentDetails.setLicenseId((String) dataobj.getString("licenseId"));
                rentDetails.setSpaceSize((String) dataobj.getString("spaceSize"));
                rentDetails.setStart_time((String) dataobj.getString("start_time"));
                rentDetails.setEnd_time((String) dataobj.getString("end_time"));
                rentDetails.setStatus((String) dataobj.getString("status"));
                rentDetails.setCost((String) dataobj.getString("cost"));
                rentDetails.setReview((String) dataobj.getString("review"));
                rentArrayList.add(rentDetails);

            }
            return rentArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Notification> getNotifications(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETHISTORY);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            Log.i("SharedmobileNo: ",mobNo);
            ps.print("&customerMobNo="+mobNo);

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


            ArrayList<Notification> notificationArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Notification notificationDetails = new Notification();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("customerHistory");
                Log.i("dataobj", dataobj.toString());
                /*rentDetails.setLicenseId((String) dataobj.getString("licenseId"));
                rentDetails.setRentNo((String) dataobj.getString("rentNo"));
                rentDetails.setPaymentNo((String) dataobj.getString("paymentNo"));
                rentDetails.setRenterMobNo((String) dataobj.getString("renterMobNo"));
                rentDetails.setSpaceId((String) dataobj.getString("spaceId"));
                rentDetails.setCustomerMobNo((String) dataobj.getString("customerMobNo"));
                rentDetails.setLicenseId((String) dataobj.getString("licenseId"));
                rentDetails.setSpaceSize((String) dataobj.getString("spaceSize"));
                rentDetails.setStart_time((String) dataobj.getString("start_time"));
                rentDetails.setEnd_time((String) dataobj.getString("end_time"));
                rentDetails.setStatus((String) dataobj.getString("status"));
                rentDetails.setCost((String) dataobj.getString("cost"));
                rentDetails.setReview((String) dataobj.getString("review"));
                */
                notificationArrayList.add(notificationDetails);

            }
            return notificationArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
