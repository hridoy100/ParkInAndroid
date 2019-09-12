package com.example.parkin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneLoginActivity extends AppCompatActivity {

    Button verify;
    Button sendVerificationCode;
    AppCompatEditText countryCode;
    AppCompatEditText mobileNo;
    EditText verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        verificationCode = (EditText) findViewById(R.id.verificationCode);
        sendVerificationCode = (Button) findViewById(R.id.sendVerificationCode);
        verify = (Button) findViewById(R.id.verifyBtn);
        countryCode = (AppCompatEditText) findViewById(R.id.countryCode);
        mobileNo = (AppCompatEditText) findViewById(R.id.mobileNo);
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode.setVisibility(View.INVISIBLE);
                verify.setVisibility(View.VISIBLE);
                countryCode.setVisibility(View.INVISIBLE);
                mobileNo.setVisibility(View.INVISIBLE);
                verificationCode.setVisibility(View.VISIBLE);
            }
        });


    }
}
