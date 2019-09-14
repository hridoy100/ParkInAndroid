package com.example.parkin;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.parkin.DB.AccountDetails;
import com.example.parkin.DB.CommunicateWithPhp;

public class AccountSettingsActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private EditText mobileNoField;
    private EditText userNameField;
    private EditText birthDateField;
    private EditText addressField;
    private Button passwordchange;
    private Button backButton;
    private Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        progressDialog = new ProgressDialog(this);

        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Account Details");
        progressDialog.show();
        mobileNoField=(EditText)findViewById(R.id.mobileNo);
        userNameField=(EditText)findViewById(R.id.username);
        birthDateField=(EditText)findViewById(R.id.birthdate);
        addressField=(EditText)findViewById(R.id.address);
        passwordchange=(Button)findViewById(R.id.passwordchange);
        backButton=(Button)findViewById(R.id.backButton);
        updateButton=(Button)findViewById(R.id.updateButton);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        AccountDetails details=new CommunicateWithPhp().getAccountDetails(mobNo);
        mobileNoField.setText(details.getMobileNo());
        userNameField.setText(details.getName());
        birthDateField.setText(details.getBirthdate());
        addressField.setText(details.getAddress());
    }
    public void onChangePasswordClicked()
    {

    }
    public void onBackButtonClicked()
    {

    }
    public void onUpdateClicked()
    {

    }
}
