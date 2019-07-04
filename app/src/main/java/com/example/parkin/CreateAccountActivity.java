package com.example.parkin;

import android.app.ProgressDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    EditText mobileNo;
    EditText password;
    EditText name;
    EditText email;
    EditText address;
    DatePickerDialog picker;
    EditText birthdate;
    private ProgressDialog progressDialog;
    RadioButton customer;
    RadioButton renter;
    Calendar cldr;

    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        mobileNo = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        birthdate = (EditText) findViewById(R.id.birthdate);

        customer = (RadioButton) findViewById(R.id.customer);
        renter = (RadioButton) findViewById(R.id.renter);

        customer.setActivated(true);
        renter.setActivated(false);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        birthdate.setInputType(InputType.TYPE_NULL);
        progressDialog = new ProgressDialog(this);
        cldr = Calendar.getInstance();
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
        System.out.println("mobileNo: "+mobileNo.getText());
        System.out.println("name: "+name.getText());
        System.out.println("password: "+password.getText());
        System.out.println("email: "+email.getText());
        System.out.println("address: "+address.getText());
        System.out.println("birthdate: "+birthdate.getText());

        registerUser();

    }
    private void registerUser() {
        final String emailStr = email.getText().toString().trim();
        final String mobileNoStr = mobileNo.getText().toString().trim();
        final String passwordStr = password.getText().toString().trim();
        final String addressStr = address.getText().toString().trim();
        final String nameStr = name.getText().toString().trim();
        final String birthdateStr = birthdate.getText().toString().trim();
        final String type;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        //type = "customer";

        type = radioButton.getText().toString();
//        if(renter.isPressed())
//            type = "renter";
//        else if(customer.isPressed())
//            type = "customer";
//        else type="";


        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(response.contains("success")){
                                Toast.makeText(getApplicationContext(),"Success" , Toast.LENGTH_SHORT).show();
                                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(homeIntent);
                            }else {
                                Toast.makeText(getApplicationContext(),"Failed" , Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobileNo", mobileNoStr);
                params.put("email", emailStr);
                params.put("password", passwordStr);
                params.put("name", nameStr);
                params.put("address", addressStr);
                params.put("birthdate", birthdateStr);
                params.put("type", type);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        this.finish();
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}