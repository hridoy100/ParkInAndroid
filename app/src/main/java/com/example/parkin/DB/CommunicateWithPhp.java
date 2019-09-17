package com.example.parkin.DB;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Space;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.HomeActivity;
import com.example.parkin.LoginActivity;
import com.example.parkin.MainActivity;
import com.example.parkin.Notification;
import com.example.parkin.R;
import com.example.parkin.Vehicle;
import com.example.parkin.util.NotificationThread;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
    String TAG = "CommunicateWithPHP";

    public void setMain(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setContext(Context context) {
        this.context = context;
    }

   /* public void verifyUser(final String mobileNo, String password) {
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

    */


    public ArrayList<GarageDetails> getAllGarageDetailsDB() {
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
            JSONArray jsonArray = new JSONArray(response.toString());
            ArrayList<GarageDetails> garageDetailsArrayList = new ArrayList<>();
            ArrayList<String> garageNameArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                GarageDetails garageDetails = new GarageDetails();
                JSONObject garageData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) garageData.get("garageDetails");
                Log.i("dataobj", dataobj.toString());
                garageDetails.setAddressId((String) dataobj.getString("addressId"));
                garageDetails.setGarageId((String) dataobj.getString("garageId"));
                garageDetails.setAddressName((String) dataobj.getString("addressName"));
                garageDetails.setPostalId((String) dataobj.getString("postalId"));
                garageDetails.setLongitude((String) dataobj.getString("longitude"));
                garageDetails.setLatitude((String) dataobj.getString("latitude"));

                garageDetailsArrayList.add(garageDetails);
                garageNameArrayList.add(garageDetails.getAddressName());

            }
            return garageDetailsArrayList;
        } catch (Exception e) {
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


    public ArrayList<GarageDetails> getMyGaragesDB(Context context) {
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
            ps.print("&mobileNo=" + mobNo);
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
            ArrayList<GarageDetails> garageDetailsArrayList = new ArrayList<>();
            ArrayList<String> garageNameArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                GarageDetails garageDetails = new GarageDetails();
                JSONObject garageData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) garageData.get("myGarage");
                Log.i("dataobj", dataobj.toString());
                garageDetails.setAddressId((String) dataobj.getString("addressId"));
                garageDetails.setGarageId((String) dataobj.getString("garageId"));
                garageDetails.setAddressName((String) dataobj.getString("addressName"));
                garageDetails.setPostalId((String) dataobj.getString("postalId"));
                garageDetails.setLongitude((String) dataobj.getString("longitude"));
                garageDetails.setLatitude((String) dataobj.getString("latitude"));

                garageDetailsArrayList.add(garageDetails);
                garageNameArrayList.add(garageDetails.getAddressName());

            }
            return garageDetailsArrayList;
        } catch (Exception e) {
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
            ps.print("&mobileNo=" + mobNo);

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
            ps.print("&licenseNo=" + licenseNo);

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

    public ArrayList<SpaceDetails> getAvailableSpaces(int garageid, Calendar arrivaltime, Calendar departuretime) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        ArrayList<SpaceDetails> spaceDetailsArrayList = new ArrayList<>();
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
            Log.d("starttime", DateFormat.format(arrivaltime.getTime()));
            Log.d("endtime", DateFormat.format(departuretime.getTime()));
            String arrive = DateFormat.format(arrivaltime.getTime());
            String end = DateFormat.format(departuretime.getTime());
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&garageId=" + garageid);
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
            System.out.println("available space: "+response.toString());
            JSONArray jsonArray = new JSONArray(response.toString());
            System.out.println("jsonArrayLength:"+jsonArray.length());

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
                Calendar cal = Calendar.getInstance();
                cal.setTime(myDateFormat.parse(dataobj.getString("start_time")));
                spaceDetails.setStarttime1(cal);
                cal.setTime(myDateFormat.parse(dataobj.getString("end_time")));
                spaceDetails.setStarttime2(cal);
                spaceDetails.setAvailability(dataobj.getString("availability"));
                spaceDetails.setCctvIp(dataobj.getString("cctvIp"));

                spaceDetailsArrayList.add(spaceDetails);
                String cctv=dataobj.getString("cctvIp");
                System.out.println("Dhuke ekhane");
            }
            System.out.println("spacearraysize:"+spaceDetailsArrayList.size());
            return spaceDetailsArrayList;
        } catch (Exception e) {
            System.out.println("Exception hoitese:");
            e.printStackTrace();
        }

        return spaceDetailsArrayList;
    }

    public void bookGarageSpace(Context context, int garageid, int spaceid, Calendar arrivaltime, Calendar departuretime, int licenseid) {
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
            Log.i("SharedmobileNo: ", mobNo);
            ps.print("&customerMobNo=" + mobNo);
            ps.print("&garageId=" + garageid);
            ps.print("&spaceId=" + spaceid);
            ps.print("&licenseID=" + licenseid);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SpaceDetails> getGarageSpace(String garageid) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        ArrayList<SpaceDetails> spaceDetailsArrayList = new ArrayList<>();
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
            ps.print("&garageId=" + garageid);

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
                Calendar cal = Calendar.getInstance();
                cal.setTime(myDateFormat.parse(dataobj.getString("start_time")));
                spaceDetails.setStarttime1(cal);
                cal.setTime(myDateFormat.parse(dataobj.getString("end_time")));
                spaceDetails.setStarttime2(cal);
                spaceDetails.setAvailability(dataobj.getString("availability"));
                spaceDetails.setCctvIp(dataobj.getString("cctvIp"));
                spaceDetailsArrayList.add(spaceDetails);
            }
            return spaceDetailsArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spaceDetailsArrayList;
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
            Log.i("SharedmobileNo: ", mobNo);
            ps.print("&customerMobNo=" + mobNo);

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


    public ArrayList<Notification> getNotification(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETNOTIFICATION);
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
            Log.i("mobileNoForNotif: ",mobNo);
            ps.print("&mobileNo=" + mobNo);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONArray jsonArray = new JSONArray(response.toString());


            ArrayList<Notification> notificationArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Notification notificationDetails = new Notification();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("notification");
                //Log.i("dataobj", dataobj.toString());
                notificationDetails.setId((String) dataobj.getString("id"));
                notificationDetails.setRentno((String) dataobj.getString("rent_no"));
                notificationDetails.setStatus((String) dataobj.getString("seen"));
                String timeStamp = dataobj.getString("cur_timestamp");
                String time = timeStamp.substring(timeStamp.indexOf(" ") + 1);
                String date = timeStamp.substring(0, timeStamp.indexOf(" "));
                notificationDetails.setTime(time);
                notificationDetails.setDate(date);

                String renterMob = dataobj.getString("renter_mob_no");
                String customerMob = dataobj.getString("customer_mob_no");

                notificationDetails.setCustomerMobileNo(customerMob);
                notificationDetails.setRenterMobileNo(renterMob);

                String notifMsg = null;
                if (customerMob.equals(mobNo)) {
                    notifMsg = "You have rented a space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                    notificationDetails.setMobileNo("Renter Mobile No: <font color=#3e9c64>" + renterMob + "</font>");
                } else if (renterMob.equals(mobNo)) {
                    notifMsg = "A Customer have rented your space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                    notificationDetails.setMobileNo("Customer Mobile No: <font color=#3e9c64>" + customerMob + "</font>");
                }
                notificationDetails.setNotificationMessage(notifMsg);
                notificationArrayList.add(notificationDetails);

            }
            return notificationArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Notification> getNotification2(String mobNo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETNOTIFICATION);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            //String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
            //Log.i("SharedmobileNo: ",mobNo);
            ps.print("&mobileNo=" + mobNo);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONArray jsonArray = new JSONArray(response.toString());


            ArrayList<Notification> notificationArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Notification notificationDetails = new Notification();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("notification");
                //Log.i("dataobj", dataobj.toString());
                notificationDetails.setId((String) dataobj.getString("id"));
                notificationDetails.setRentno((String) dataobj.getString("rent_no"));
                notificationDetails.setStatus((String) dataobj.getString("seen"));
                String timeStamp = dataobj.getString("cur_timestamp");
                String time = timeStamp.substring(timeStamp.indexOf(" ") + 1);
                String date = timeStamp.substring(0, timeStamp.indexOf(" "));
                notificationDetails.setTime(time);
                notificationDetails.setDate(date);

                String renterMob = dataobj.getString("renter_mob_no");
                String customerMob = dataobj.getString("customer_mob_no");
                String notifMsg = null;
                notificationDetails.setCustomerNo(customerMob);
                notificationDetails.setRenterNo(renterMob);
                if (customerMob.equals(mobNo)) {
                    notifMsg = "You have rented a space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                    notificationDetails.setMobileNo("Renter Mobile No: <font color=#3e9c64>" + renterMob + "</font>");
                } else if (renterMob.equals(mobNo)) {
                    notifMsg = "A Customer have rented your space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                    notificationDetails.setMobileNo("Customer Mobile No: <font color=#3e9c64>" + customerMob + "</font>");
                }
                notificationDetails.setNotificationMessage(notifMsg);
                notificationArrayList.add(notificationDetails);

            }
            return notificationArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Notification> getOnGoingParking(Context context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETNOTIFICATION);
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
            ps.print("&mobileNo=" + mobNo);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            //System.out.println(response.toString());
            JSONArray jsonArray = new JSONArray(response.toString());


            ArrayList<Notification> notificationArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Notification notificationDetails = new Notification();
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("notification");
                //Log.i("dataobj", dataobj.toString());

                ArrayList<Rent> rentArrayList = getRentInfo(dataobj.getString("rent_no"));
                for (int j=0; j<rentArrayList.size(); j++) {
                    if(rentArrayList.get(j).getStatus().contains("ongoing")){
                        notificationDetails.setId((String) dataobj.getString("id"));
                        notificationDetails.setRentno((String) dataobj.getString("rent_no"));
                        notificationDetails.setStatus((String) dataobj.getString("seen"));
                        String timeStamp = dataobj.getString("cur_timestamp");
                        String time = timeStamp.substring(timeStamp.indexOf(" ") + 1);
                        String date = timeStamp.substring(0, timeStamp.indexOf(" "));
                        notificationDetails.setTime(time);
                        notificationDetails.setDate(date);

                        String renterMob = dataobj.getString("renter_mob_no");
                        String customerMob = dataobj.getString("customer_mob_no");
                        String notifMsg = null;
                        notificationDetails.setCustomerNo(customerMob);
                        notificationDetails.setRenterNo(renterMob);
                        if (customerMob.equals(mobNo)) {
                            notifMsg = "You have rented a space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                            notificationDetails.setMobileNo("Renter Mobile No: <font color=#3e9c64>" + renterMob + "</font>");
                        } else if (renterMob.equals(mobNo)) {
                            notifMsg = "A Customer have rented your space with Rent No: <font color=#3e9c64><b>" + dataobj.getString("rent_no") + "</b></font>";
                            notificationDetails.setMobileNo("Customer Mobile No: <font color=#3e9c64>" + customerMob + "</font>");
                        }
                        notificationDetails.setNotificationMessage(notifMsg);
                        notificationArrayList.add(notificationDetails);
                    }
                }
            }
            return notificationArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Rent> getRentInfo(String rentNo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETRENTINFO);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&rentNo=" + rentNo);

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
                JSONObject dataobj = (JSONObject) vehicleData.get("rent_details");
                Log.i("dataobj", dataobj.toString());
                rentDetails.setLicenseId((String) dataobj.getString("license_id"));
                rentDetails.setRentNo((String) dataobj.getString("rent_no"));
                rentDetails.setPaymentNo((String) dataobj.getString("payment_no"));
                rentDetails.setRenterMobNo((String) dataobj.getString("renter_mob_no"));
                rentDetails.setSpaceId((String) dataobj.getString("space_id"));
                rentDetails.setCustomerMobNo((String) dataobj.getString("customer_mob_no"));
                rentDetails.setLicenseId((String) dataobj.getString("license_id"));
                rentDetails.setSpaceSize((String) dataobj.getString("space_size"));
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


    public void updateNotification(String rentNo, String newStatus) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_UPDATENOTIF);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&rentNo=" + rentNo);
            ps.print("&seen=" + newStatus);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if (response.toString().contains("success")) {
                Log.d("updating notification", response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRentStatus(String rentNo, String newStatus) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_UPDATERENTSTATUS);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&rentNo=" + rentNo);
            ps.print("&status=" + newStatus);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if (response.toString().contains("success")) {
                Log.d("updating notification", response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updatePayment(String rentNo, String cost, String paymentMethod) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            try {
                URL website = new URL(Constants.URL_UPDATEPAYMENT);
                //URLConnection connection = website.openConnection();
                HttpURLConnection connection = (HttpURLConnection) website.openConnection();
    //            connection.setReadTimeout(15000);
    //            connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
    //            connection.setDoInput(true);
                connection.setDoOutput(true);

                PrintStream ps = new PrintStream(connection.getOutputStream());

                ps.print("&rentNo=" + rentNo);
                ps.print("&cost=" + cost);
                ps.print("&paymentMethod=" + paymentMethod);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                if (response.toString().contains("success")) {
                    Log.d("updating notification", response.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void deleteVehicle(String licenseNo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_DELVEHICLE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&licenseNo=" + licenseNo);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if (response.toString().contains("success")) {
                Log.d("updating notification", response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createAccount(String email, String mobileNo, String password, String name, String address, String birthdate) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_REGISTER);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&email=" + email);
            ps.print("&mobileNo=" + mobileNo);
            ps.print("&password=" + password);
            ps.print("&name=" + name);
            ps.print("&address=" + address);
            ps.print("&birthdate=" + birthdate);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if (response.toString().contains("success")) {
                Log.d("Registration", response.toString());
                return true;
            } else if (response.toString().contains("failed")) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean verifyUser(String mobileNo, String password) {
        String mobileNoStr = mobileNo;
        String passwordStr = password;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_LOGIN);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&mobileNo=" + mobileNo);
            ps.print("&password=" + password);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if (response.toString().contains("true")) {
                Log.d("Registration", response.toString());
                return true;
            } else if (response.toString().contains("false")) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public String getEmail(String mobileNo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETEMAIL);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&mobileNo=" + mobileNo);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            //Log.d("getEmail" ,response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());


            String email=null;

            JSONObject emailObj = (JSONObject) jsonArray.get(0);
            JSONObject dataobj = (JSONObject) emailObj.get("email_array");
            //Log.i("dataobj", dataobj.toString());
            email = dataobj.getString("email");


            return email;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public String getRenterMobNo(String garageId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETRENTERMOB);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&garageId=" + garageId);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            String rentermob=null;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                rentermob=inputLine;
                System.out.println("inputline:"+inputLine);
            }
            in.close();
            //Log.d("getEmail" ,response.toString());

            rentermob=rentermob.substring(1,rentermob.length()-1);
            return rentermob;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public String getFacility(String garageId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETFACILITY);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&garageId=" + garageId);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            String facility=null;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                facility=inputLine;
                System.out.println("inputline:"+inputLine);
            }
            in.close();
            //Log.d("getEmail" ,response.toString());


            return facility;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateUserPassAddress(String mobileNo, String oldPassword, String newPassword, String address, String name) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String email = this.getEmail(mobileNo);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldPassword);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Password updated: "+newPassword);
                                    } else {
                                        Log.d(TAG, "Error password not updated "+newPassword);
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });

        try {
            URL website = new URL(Constants.URL_UPDATEUSERPASSADDRESS);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&mobileNo=" + mobileNo);
            ps.print("&password=" + newPassword);
            ps.print("&address=" + address);
            ps.print("&name=" + name);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println(inputLine);
            }
            in.close();
            //Log.d("getEmail" ,response.toString());

            //JSONArray jsonArray = new JSONArray(response.toString());

            /*
            JSONObject emailObj = (JSONObject) jsonArray.get(0);
            JSONObject dataobj = (JSONObject) emailObj.get("updateUserPassAddress");
            //Log.i("dataobj", dataobj.toString());
            email = dataobj.getString("status");
            */
            if(response.toString().contains("true"))
                return true;
            else if(response.toString().contains("false"))
                return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public AccountDetails getAccountDetails(String MobileNo) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_GETCUSTOMERDETAILS);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&mobileNo=" + MobileNo);

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
            System.out.println("Length: "+jsonArray.length());
            AccountDetails accountDetails=new AccountDetails();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject accountData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) accountData.get("customerDetails");
                Log.i("dataobj", dataobj.toString());
                accountDetails.setMobileNo((String) dataobj.getString("mobileNo"));
                accountDetails.setName((String) dataobj.getString("name"));
                accountDetails.setNID((String) dataobj.getString("NID"));
                accountDetails.setEmail((String) dataobj.getString("email"));
                accountDetails.setAddress((String) dataobj.getString("address"));
                accountDetails.setBirthdate((String) dataobj.getString("birthdate"));
                accountDetails.setCreationTime((String) dataobj.getString("creationTime"));
            }
            return accountDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String addGarage(String address, String mobileNo, String facilityStr, String spaceCount, String longitude, String latitude, String postalId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_ADDGARAGE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&addressName=" + address);
            ps.print("&renterMobNo=" + mobileNo);
            ps.print("&facilityId=" + facilityStr);
            ps.print("&rentingSpace=" + spaceCount);
            ps.print("&longitude=" + longitude);
            ps.print("&latitude=" + latitude);
            ps.print("&postalId=" + postalId);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            if(TextUtils.isEmpty(response.toString())){
                return "";
            }
            else {
                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void addSpace(String garageId, String spaceSize, String startTime, String endTime, String position, String cctvIp) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_ADDSPACE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&garage_id=" + garageId);
            ps.print("&space_size=" + spaceSize);
            ps.print("&start_time=" + startTime);
            ps.print("&end_time=" + endTime);
            ps.print("&position=" + position);
            ps.print("&cctv_ip=" + cctvIp);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            Log.d("Add space: " ,response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggleSpaceAvailability(String spaceId, String availability) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Log.d("spaceid",spaceId);
        Log.d("availableid",availability);

        try {
            URL website = new URL(Constants.URL_TOGGLEAVAILABILITY);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&spaceId=" + spaceId);
            ps.print("&availability=" + availability);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            Log.d("toggled: " ,response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public SpaceDetails getSingleSpace(String spaceId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        SpaceDetails spaceDetails = new SpaceDetails();
        try {
            URL website = new URL(Constants.URL_GETSINGLESPACE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("&spaceId=" + spaceId);

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

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vehicleData = (JSONObject) jsonArray.get(i);
                JSONObject dataobj = (JSONObject) vehicleData.get("spaceInfo");
                Log.i("dataobj", dataobj.toString());
                spaceDetails.setSpaceid(dataobj.getInt("spaceId"));
                spaceDetails.setGarageid(dataobj.getInt("garageId"));
                spaceDetails.setSpacesize(dataobj.getInt("spaceSize"));
                spaceDetails.setPosition(dataobj.getString("position"));
                SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
                Calendar cal = Calendar.getInstance();
                cal.setTime(myDateFormat.parse(dataobj.getString("start_time")));
                spaceDetails.setStarttime1(cal);
                cal.setTime(myDateFormat.parse(dataobj.getString("end_time")));
                spaceDetails.setStarttime2(cal);
                spaceDetails.setAvailability(dataobj.getString("availability"));
                spaceDetails.setCctvIp(dataobj.getString("cctvIp"));
                break;
            }
            return spaceDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public void updateSpace(int spaceId, int garageId, int spaceSize, String startTime, String endTime, String position, String cctvIp) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_UPDATESPACE);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&spaceId=" + spaceId);
//            ps.print("&garage_id=" + garageId);
            ps.print("&spaceSize=" + spaceSize);
            ps.print("&start_time=" + startTime);
            ps.print("&end_time=" + endTime);
            ps.print("&position=" + position);
            ps.print("&cctv_ip=" + cctvIp);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            Log.d("Update space: " ,response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCctvIp(String spaceId, String cctvIp) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            URL website = new URL(Constants.URL_UPDATECCTVIP);
            //URLConnection connection = website.openConnection();
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
//            connection.setReadTimeout(15000);
//            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
//            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream ps = new PrintStream(connection.getOutputStream());

            ps.print("&spaceId=" + spaceId);
//            ps.print("&garage_id=" + garageId);
            ps.print("&cctv_ip=" + cctvIp);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }
            in.close();
            Log.d("Update space: " ,response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
