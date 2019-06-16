package com.example.parkin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    String usernameFromLocal;
    String passwordFromLocal;
    SharedPreferences loginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

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

        if(username.getText().toString().equals("123") && password.getText().toString().equals("admin")){
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Incorrect Username & Password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.finish();
    }
}
