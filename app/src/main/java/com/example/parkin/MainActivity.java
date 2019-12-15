package com.example.parkin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences mySharedPreferences; // for storing data into local storage.
    public SharedPreferences.Editor myEditor; // for storing data into local storage.
    Context mainContext; // for later use..


    EditText username;
    EditText password;

    String usernameFromLocal;
    String passwordFromLocal;
    SharedPreferences loginDetails;

    public ProgressDialog progressDialog;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);
        mainContext = this;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = mySharedPreferences.edit();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        checkSharedPreferences();
        /*tryToLogin(mySharedPreferences.getString(getString(R.string.mobileNo),""),
                mySharedPreferences.getString(getString(R.string.password), ""));
                */


//        username.setText("123");
//        password.setText("admin");

    }

    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(loginIntent);
    }


    public void onLoginButton(View view){
        if(Constants.DEBUG) {
            System.out.println(username.getText());
            System.out.println(password.getText());
        }
//        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
//        communicateWithPhp.setMain(this);
//        communicateWithPhp.setContext(this);
//        String mobileNo = username.getText().toString();
//        if(mobileNo.startsWith("0"))
//            mobileNo = mobileNo.substring(mobileNo.indexOf("0")+1);
//
//        System.out.println("mobileNo: "+mobileNo);
//        System.out.println("password: "+password.getText().toString()+"end");

        tryToLogin(username.getText().toString(), password.getText().toString());
//
//        if(mobileNo.length()>0 && password.getText().length()>0) {
//            //Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
//            //startActivity(mapIntent);
//            progressDialog.setMessage("Logging into account...");
//            progressDialog.show();
//            communicateWithPhp.verifyUser(mobileNo, password.getText().toString());
//            setSharedPreferences(mobileNo, password.getText().toString());
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
//            vibratePhone(250);
//        }
    }

    public void tryToLogin(String username, String password){
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        communicateWithPhp.setMain(this);
        communicateWithPhp.setContext(this);
        String mobileNo = username;
        if(mobileNo.startsWith("0"))
            mobileNo = mobileNo.substring(mobileNo.indexOf("0")+1);

        if(Constants.DEBUG) {
            System.out.println("mobileNo: " + mobileNo);
            System.out.println("password: " + password);
        }
        if(mobileNo.length()>0 && password.length()>0) {
            //Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
            //startActivity(mapIntent);
            progressDialog.setMessage("Logging into account...");
            progressDialog.show();
            communicateWithPhp.verifyUser(mobileNo, password);
            setSharedPreferences(mobileNo, password);
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter correct details", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
        }
    }


    public void onCreateAccountButton(View view){
        Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }
    public void checkSharedPreferences() {
        String userMob = mySharedPreferences.getString(getString(R.string.mobileNo), "");
        String userpassword = mySharedPreferences.getString(getString(R.string.password), "");
        if(userMob.length()>0){
            username.setText(userMob);
        }
        if(userpassword.length()>0){
            password.setText(userpassword);
        }
    }



    public void setSharedPreferences(String userMob, String userPass) {
        myEditor.putString(getString(R.string.mobileNo), userMob);
        myEditor.commit();
        myEditor.putString(getString(R.string.password), userPass);
        myEditor.commit();
    }

    public void vibratePhone(int seconds){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for x milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(seconds, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(seconds);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        sendUserToLoginActivity();

        /*if(currentUser==null){
            sendUserToLoginActivity();
        }
        else {
            sendUserToHomeActivity();
        }*/
    }

    private void sendUserToHomeActivity() {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeIntent);
    }
}
