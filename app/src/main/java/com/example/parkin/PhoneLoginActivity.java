package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {

    String TAG ="PhoneLoginActivity";
    Button verify;
    Button sendVerificationCode;
    AppCompatEditText countryCode;
    AppCompatEditText mobileNo;
    EditText verificationCode;
    ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        verificationCode = (EditText) findViewById(R.id.verificationCode);
        sendVerificationCode = (Button) findViewById(R.id.sendVerificationCode);
        verify = (Button) findViewById(R.id.verifyBtn);
        countryCode = (AppCompatEditText) findViewById(R.id.countryCode);
        mobileNo = (AppCompatEditText) findViewById(R.id.mobileNo);
        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mobileNo.getText().toString())) {
                    mobileNo.requestFocus();
                    mobileNo.setError("Please enter a phone number");
                    mobileNo.setBackgroundResource(R.drawable.edit_text_error);
                } else {

                    loadingBar.setTitle("Phone Verification");
                    loadingBar.setMessage("Please wait, while we are authenticating your phone!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    String phoneNumber = countryCode.getText().toString()+mobileNo.getText().toString();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNumber,        // Phone number to verify
                            60,                 // Timeout duration
                            TimeUnit.SECONDS,   // Unit of timeout
                            PhoneLoginActivity.this,               // Activity (for callback binding)
                            mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java

                }
            }
        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNo.setBackgroundResource(R.drawable.bg_rounded_edit_text);
                sendVerificationCode.setVisibility(View.INVISIBLE);
                mobileNo.setVisibility(View.INVISIBLE);
                countryCode.setVisibility(View.INVISIBLE);

                if(TextUtils.isEmpty(verificationCode.getText().toString())){
                    Toast.makeText(getApplicationContext(), " Please enter correct detials..", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.setTitle("Code Verification");
                    loadingBar.setMessage("Please wait, while we are verifying your code!");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                // Show a message and update the UI
                // ...
                loadingBar.dismiss();
                Toast.makeText(getApplicationContext(),"Invalid Phone Number, Please Enter Correct Phone Number with country code..", Toast.LENGTH_SHORT).show();
                mobileNo.setBackgroundResource(R.drawable.bg_rounded_edit_text);
                sendVerificationCode.setVisibility(View.VISIBLE);
                verify.setVisibility(View.INVISIBLE);
                countryCode.setVisibility(View.VISIBLE);
                mobileNo.setVisibility(View.VISIBLE);
                verificationCode.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                loadingBar.dismiss();

                Toast.makeText(getApplicationContext(), "Code have been sent", Toast.LENGTH_SHORT).show();
                // ...
                mobileNo.setBackgroundResource(R.drawable.bg_rounded_edit_text);
                sendVerificationCode.setVisibility(View.INVISIBLE);
                verify.setVisibility(View.VISIBLE);
                countryCode.setVisibility(View.INVISIBLE);
                mobileNo.setVisibility(View.INVISIBLE);
                verificationCode.setVisibility(View.VISIBLE);
            }
        };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // ...
                            loadingBar.dismiss();
                            Toast.makeText(getApplicationContext(), "Congratulations, you're logged in successfully.", Toast.LENGTH_LONG).show();
                            sendToHomeActivity();
                        } else {
                            // Sign in failed, display a message and update the UI
                            loadingBar.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(),task.getException().toString(), Toast.LENGTH_SHORT).show();
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
                        }
                    }
                });
    }

    void sendToHomeActivity() {
        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

}
