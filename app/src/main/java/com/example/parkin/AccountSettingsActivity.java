package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.AccountDetails;
import com.example.parkin.DB.CommunicateWithPhp;

public class AccountSettingsActivity extends AppCompatActivity {
    private Context context;
    ProgressDialog progressDialog;
    private EditText mobileNoField;
    private EditText userNameField;
    private EditText birthDateField;
    private EditText addressField;
    private EditText currentpasswordField;
    private EditText newpasswordField;
    private EditText retypepasswordField;
    private Button passwordchange;
    private Button backButton;
    private Button updateButton;
    private String address;
    private String name;
    private String password;
    private String prev_password;
    private String mobNo;
    public SharedPreferences.Editor myEditor;
    public SharedPreferences mySharedPreferences;
    static int update=0;
    static int save=1;
    private int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);

        progressDialog = new ProgressDialog(this);
        System.out.println("hello");
        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Account Details");
        progressDialog.show();
        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myEditor = mySharedPreferences.edit();
        mobileNoField=(EditText)findViewById(R.id.mobileNo);
        userNameField=(EditText)findViewById(R.id.username);
        birthDateField=(EditText)findViewById(R.id.birthdate);
        addressField=(EditText)findViewById(R.id.address);
        passwordchange=(Button)findViewById(R.id.passwordchange);
        backButton=(Button)findViewById(R.id.backButton);
        updateButton=(Button)findViewById(R.id.updateButton);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        password=sharedPreferences.getString("com.example.parkin.password","");
        prev_password=password;
        System.out.println("Mobno:"+mobNo);
        System.out.println("password:"+password);
        AccountDetails details=new CommunicateWithPhp().getAccountDetails(mobNo);
        mobileNoField.setText(details.getMobileNo());
        userNameField.setText(details.getName());
        birthDateField.setText(details.getBirthdate());
        addressField.setText(details.getAddress());
        address=details.getAddress();
        name=details.getName();
        progressDialog.dismiss();
    }
    public void onChangePasswordClicked(View view)
    {
        //backButton.setVisibility(View.INVISIBLE);
        //passwordchange.setVisibility(View.INVISIBLE);
        //updateButton.setVisibility(View.INVISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = (this).getLayoutInflater();
        context=this;
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        View layout = inflater.inflate(R.layout.password_change_dialogue,null);
        currentpasswordField=(EditText)layout.findViewById(R.id.currentpassword);
        newpasswordField=(EditText)layout.findViewById(R.id.newpassword);
        retypepasswordField=(EditText)layout.findViewById(R.id.retypepassword);

        //builder.setTitle("Rent this Space?");
        builder.setCancelable(false);
        //builder.setIcon(R.drawable.ic_dashboard_black_24dp);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                if(currentpasswordField.getText().length()==0 ||
                newpasswordField.getText().length()==0 || retypepasswordField.getText().length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Fields cannot be left empty",Toast.LENGTH_LONG).show();
                }
                else if(!currentpasswordField.getText().toString().equals(password))
                {
                    Toast.makeText(getApplicationContext(),"Invalid Current Password",Toast.LENGTH_LONG).show();
                }
                else if(!newpasswordField.getText().toString().equals(retypepasswordField.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"Passwords dont match",Toast.LENGTH_LONG).show();
                }
                else if(newpasswordField.getText().toString().length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Password cant be less than 6 characters",Toast.LENGTH_LONG)
                            .show();
                }
                else
                {
                    prev_password=password;
                    password=newpasswordField.getText().toString();
                    System.out.println("New Password:"+password);
                    new CommunicateWithPhp().updateUserPassAddress(mobNo,prev_password,password,address,name);
                    Toast.makeText(getApplicationContext(),"Password updated succesfully",Toast.LENGTH_LONG)
                            .show();
                    myEditor.putString(getString(R.string.password), password);
                    myEditor.commit();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int whichButton) {
                backButton.setVisibility(View.VISIBLE);
                passwordchange.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.VISIBLE);
            }
        });
        builder.setView(layout);
        builder.create();
        builder.show();

    }
    public void onBackButtonClicked(View view)
    {
        Intent HomeIntent=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(HomeIntent);
    }
    public void onUpdateClicked(View view)
    {
        if(state==update)
        {
            backButton.setVisibility(View.INVISIBLE);
            passwordchange.setVisibility(View.INVISIBLE);
            addressField.setEnabled(true);
            userNameField.setEnabled(true);
            addressField.setHighlightColor(Color.YELLOW);
            userNameField.setHighlightColor(Color.YELLOW);
            addressField.setBackgroundColor(Color.CYAN);
            userNameField.setBackgroundColor(Color.CYAN);
            state=save;
            updateButton.setText("Save");
        }
        else if(state==save)
        {
            if(addressField.getText().length()==0 || userNameField.getText().length()==0)
            {
                Toast.makeText(getApplicationContext(),"Fill In the Fields",Toast.LENGTH_SHORT).show();
            }
            else {
                addressField.setBackgroundColor(Color.WHITE);
                userNameField.setBackgroundColor(Color.WHITE);
                addressField.setHighlightColor(Color.TRANSPARENT);
                userNameField.setHighlightColor(Color.TRANSPARENT);
                addressField.setEnabled(false);
                userNameField.setEnabled(false);
                backButton.setVisibility(View.VISIBLE);
                passwordchange.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Profile details updated",Toast.LENGTH_SHORT).show();
                state = update;
                address=addressField.getText().toString();
                name=userNameField.getText().toString();
                new CommunicateWithPhp().updateUserPassAddress(mobNo,password,password,address,name);
                updateButton.setText("Update");

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
