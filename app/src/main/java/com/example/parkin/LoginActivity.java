package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.googlecode.javacpp.annotation.Const;


public class LoginActivity extends AppCompatActivity {


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
    String whereFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);
        //sendUserToHomeActivity();

        mainContext = this;

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = mySharedPreferences.edit();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging into account...");
        progressDialog.show();
        checkSharedPreferences();

        whereFrom = getIntent().getStringExtra("from");
        //Toast.makeText(getApplicationContext(), "Where From: "+whereFrom,Toast.LENGTH_SHORT).show();

        if(currentUser != null && tryToLogin(username.getText().toString(),password.getText().toString())){ //tryToLogin checks corner case..
            //setSharedPreferences(username.getText().toString(),password.getText().toString()));
            progressDialog.dismiss();
            if(whereFrom!=null && whereFrom.equals("home")){
                //do nothing..
                Log.d("LoginActivity","in side if");
//                username.setText("");
//                password.setText("");
            }
            else{
                Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),"Login Successful\n"+username.getText().toString()+
//                        "\n"+password.getText().toString(), Toast.LENGTH_LONG).show();
                sendUserToHomeActivity();
            }

        }
//        else if(username.getText().toString().length()<0,password.getText().toString())) {
//            progressDialog.hide();
//            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
//        }
        else {
            progressDialog.hide();

            if(whereFrom!=null && whereFrom.equals("home"))
            {
                Toast.makeText(getApplicationContext(), "Successfully logged out from home", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
            }
        }
        /*tryToLogin(mySharedPreferences.getString(getString(R.string.mobileNo),""),
                mySharedPreferences.getString(getString(R.string.password), ""));

        */
//        username.setText("123");
//        password.setText("admin");

    }


    private void sendUserToHomeActivity() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public boolean tryToLogin(String username, String password){
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        String mobileNo = username;
//        if(mobileNo.startsWith("0"))
//            mobileNo = mobileNo.substring(mobileNo.indexOf("0")+1);

        if(Constants.DEBUG) {
            System.out.println("mobileNo: " + mobileNo);
            System.out.println("password: " + password);
        }

        if(mobileNo.length()<11 && mobileNo.length()>0){
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),"Incorrect Mobile Number..", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length()<6 && password.length()>0){
            progressDialog.hide();
            Toast.makeText(getApplicationContext(),"Incorrect Password..", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mobileNo.length()>0 && password.length()>0) {

            //progressDialog.setMessage("Logging into account...");
            //progressDialog.show();
            boolean verified = communicateWithPhp.verifyUser(mobileNo, password);
            if(verified) {
                progressDialog.dismiss();
                setSharedPreferences(mobileNo, password);
            }
            else {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "Incorrect Mobile No or Password", Toast.LENGTH_SHORT).show();
                vibratePhone(250);
            }
            return verified;
        }
        else if(mobileNo.length()<1) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), "Mobile number field cannot be empty", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
        }
        else if(password.length()<1) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
        }
        return false;
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

    public void onCreateAccountButton(View view){
        Intent createAccountIntent = new Intent(getApplicationContext(), CreateAccountActivity.class);
        startActivity(createAccountIntent);
    }

    public void onLoginButton(View view){
        progressDialog.setTitle("Log In");
        progressDialog.setMessage("Logging into account...");
        progressDialog.show();


        if(username.getText().toString().length()<11 && username.getText().toString().length()>0){
            progressDialog.hide();
            //Toast.makeText(getApplicationContext(),"Please enter 11 digits mobile number..", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
//            username.setError("Please enter 11 digits mobile number");
            username.setError("Incorrect Mobile Number");
            username.setBackgroundResource(R.drawable.edit_text_error);
            password.setBackgroundResource(R.color.transparentColor);
            return;
        }
        else if(password.getText().toString().length()<6 && password.getText().toString().length()>0){
            progressDialog.hide();
            //Toast.makeText(getApplicationContext(),"Password should be at least 6 digits..", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
            password.setError("Invalid Password");
//            password.setError("Password should be at least 6 digits");
            password.setBackgroundResource(R.drawable.edit_text_error);
            username.setBackgroundResource(R.color.transparentColor);
            return;
        }
        else if(username.getText().toString().length()<1) {
            progressDialog.hide();
            //Toast.makeText(getApplicationContext(), "Mobile number field cannot be empty", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
            username.requestFocus();
            username.setError("Mobile number field cannot be empty");
            username.setBackgroundResource(R.drawable.edit_text_error);
            password.setBackgroundResource(R.color.transparentColor);
            return;
        }
        else if(password.getText().toString().length()<1) {
            progressDialog.hide();
            //Toast.makeText(getApplicationContext(), "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            vibratePhone(250);
            password.requestFocus();
            password.setError("Password field cannot be empty");
            password.setBackgroundResource(R.drawable.edit_text_error);
            username.setBackgroundResource(R.color.transparentColor);
            return;
        }
        else {
            password.setBackgroundResource(R.color.transparentColor);
        }

        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        String email = communicateWithPhp.getEmail(username.getText().toString());
        if(TextUtils.isEmpty(email)){
            progressDialog.hide();
            vibratePhone(250);
            username.requestFocus();
            username.setError("Incorrect Mobile Number");
            username.setBackgroundResource(R.drawable.edit_text_error);
            password.setBackgroundResource(R.color.transparentColor);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w("TAG", "signInWithEmail:failed", task.getException());
                            progressDialog.hide();
                            vibratePhone(250);
                            if(task.getException().toString().contains("password is invalid")) {
                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                password.requestFocus();

                                password.setError("Invalid Password");
                                password.setBackgroundResource(R.drawable.edit_text_error);
                                username.setBackgroundResource(R.color.transparentColor);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            checkIfEmailVerified();
                        }
                        // ...
                    }
                });
    }


    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            progressDialog.dismiss();
            setSharedPreferences(username.getText().toString(), password.getText().toString());
            ////finish();
            Toast.makeText(LoginActivity.this, "Successfully logged in...", Toast.LENGTH_SHORT).show();
            /*CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();

            communicateWithPhp.updateUserPassAddress(username.getText().toString(),password.getText().toString(),"adminami",
                    "hello dhaka","hridoy");
            */
            sendUserToHomeActivity();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), "Please Verify your email address", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            //restart this activity

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkSharedPreferences();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        checkSharedPreferences();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null && tryToLogin(username.getText().toString(),password.getText().toString())){
            sendUserToHomeActivity();
        }
    }
    */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}
