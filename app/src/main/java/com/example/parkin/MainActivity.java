package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    String usernameFromLocal;
    String passwordFromLocal;
    SharedPreferences loginDetails;

    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        username.setText("123");
        password.setText("admin");

        progressDialog = new ProgressDialog(this);

//        loginDetails = this.getSharedPreferences("com.example.parkin", Context.MODE_PRIVATE);
//
//        usernameFromLocal= loginDetails.getString("username", "");
//        passwordFromLocal = loginDetails.getString("password", "");

//        System.out.println("username from local: " +usernameFromLocal+ "hu");
//        if(!usernameFromLocal.equals("")){
//            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//            startActivity(homeIntent);
//        }

    }


    public void onLoginButton(View view){

        System.out.println(username.getText());
        System.out.println(password.getText());
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        communicateWithPhp.setMain(this);
        //if(username.getText().toString().equals("123") && password.getText().toString().equals("admin")){
        String mobileNo = username.getText().toString();
        if(mobileNo.startsWith("0"))
            mobileNo = mobileNo.substring(mobileNo.indexOf("0")+1);

        System.out.println("mobileNo: "+mobileNo);
        System.out.println("password: "+password.getText().toString()+"end");

        if(mobileNo.length()!=0 && password.getText().length()!=0) {
            //Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
            //startActivity(mapIntent);
            communicateWithPhp.verifyUser(mobileNo, password.getText().toString());
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
        }
//        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//        startActivity(homeIntent);

        //if(communicateWithPhp.verifyUser(mobileNo, password.getText().toString())){
//            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
//            startActivity(homeIntent);
        //}
//        else {
//            Toast.makeText(getApplicationContext(), "Incorrect Username & Password", Toast.LENGTH_LONG).show();
//        }
    }

    public void onCreateAccountButton(View view){
        Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        this.finish();
//    }

}
