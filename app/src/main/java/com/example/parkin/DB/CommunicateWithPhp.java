package com.example.parkin.DB;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.HomeActivity;
import com.example.parkin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
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


}
