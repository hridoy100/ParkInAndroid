package com.example.parkin;

import android.app.ProgressDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.RequestHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateAccountActivity extends AppCompatActivity {

    EditText mobileNo;
    EditText password;
    EditText name;
    EditText email;
    EditText address;
    DatePickerDialog picker;
    EditText birthdate;
    private ProgressDialog progressDialog;
    Calendar cldr;

    FirebaseAuth registrationAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences mySharedPreferences;
    public SharedPreferences.Editor myEditor; // for storing data into local storage.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        registrationAuth = FirebaseAuth.getInstance();

        mobileNo = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        birthdate = (EditText) findViewById(R.id.birthdate);

        birthdate.setInputType(InputType.TYPE_NULL);
        progressDialog = new ProgressDialog(this);
        cldr = Calendar.getInstance();

        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = mySharedPreferences.edit();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    // NOTE: this Activity should get onpen only when the user is not signed in, otherwise
                    // the user will receive another verification email.
                    sendVerificationEmail();
                } else {
                    // User is signed out

                }
                // ...
            }
        };


    }


    public void setDate(View view){
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(CreateAccountActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }

    public void onSubmitButton(View view){
//        System.out.println("mobileNo: "+mobileNo.getText());
//        System.out.println("name: "+name.getText());
//        System.out.println("password: "+password.getText());
//        System.out.println("email: "+email.getText());
//        System.out.println("address: "+address.getText());
//        System.out.println("birthdate: "+birthdate.getText());

        if(TextUtils.isEmpty(mobileNo.getText().toString())){
            Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
        }
        else if(mobileNo.getText().toString().length()<11){
            Toast.makeText(this, "Please enter 11 digit number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().length()<6){
            Toast.makeText(this, "Please enter at least 6 digits of password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else if(!isEmailValid(email.getText().toString())){
            Toast.makeText(this, "Please enter correct email address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(birthdate.getText().toString())){
            Toast.makeText(this, "Please enter birthdate", Toast.LENGTH_SHORT).show();
        }

        else {
            progressDialog.setTitle("Creating New Account");
            progressDialog.setMessage("Please Wait while we are creating new account for you...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            registrationAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful() && registerUser()){
                                /*
                                sendUserToLoginActivity(view);
                                */
                                //sendVerificationEmail();
                                Toast.makeText(CreateAccountActivity.this, "Account Created Successfully. Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {
                                String message = task.getException().toString();
                                if(message.contains("email address is already in use")){
                                    Toast.makeText(CreateAccountActivity.this, "Error: Email address already in use", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(CreateAccountActivity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }
                        }
                    });
        }

        //registerUser();

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent

                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    private boolean registerUser() {
        final String emailStr = email.getText().toString().trim();
        final String mobileNoStr = mobileNo.getText().toString().trim();
        final String passwordStr = password.getText().toString().trim();
        final String addressStr = address.getText().toString().trim();
        final String nameStr = name.getText().toString().trim();
        final String birthdateStr = birthdate.getText().toString().trim();

        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        setSharedPreferences(mobileNoStr,passwordStr);
        return communicateWithPhp.createAccount(emailStr, mobileNoStr, passwordStr, nameStr, addressStr, birthdateStr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public void sendUserToLoginActivity(View view){
        Intent loginIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registrationAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            registrationAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void setSharedPreferences(String userMob, String userPass) {
        myEditor.putString(getString(R.string.mobileNo), userMob);
        myEditor.commit();
        myEditor.putString(getString(R.string.password), userPass);
        myEditor.commit();
    }

}