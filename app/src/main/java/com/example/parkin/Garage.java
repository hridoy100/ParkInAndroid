package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.GarageDetails;
import com.example.parkin.DB.RequestHandler;
import com.example.parkin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Garage extends AppCompatActivity {

    ListView garageList;
    private MainActivity mainActivity;

    ProgressDialog progressDialog;

    public void setMain (MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garage_list_layout);
        progressDialog = new ProgressDialog(this);

        garageList = findViewById(R.id.garageList);
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        progressDialog.setMessage("Loading Garage Details");
        progressDialog.show();
        ArrayList<GarageDetails> garageDetailsArrayList = communicateWithPhp.getAllGarageDetailsDB();
        String title[] = new String[garageDetailsArrayList.size()];
        for (int i=0; i<garageDetailsArrayList.size(); i++){
            title[i] = garageDetailsArrayList.get(i).getAddressName();
        }
        MyAdapter myAdapter = new MyAdapter(this, garageDetailsArrayList, title);
        garageList.setAdapter(myAdapter);
        progressDialog.dismiss();
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<GarageDetails> garageDetailsArrayList;

        public MyAdapter(Context context, ArrayList<GarageDetails> garageDetails, String title[]) {
            super(context, R.layout.garage_list_row, title);
            this.context = context;
            this.garageDetailsArrayList = garageDetails;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.garage_list_row, parent, false);
            TextView locationName = row.findViewById(R.id.locationName);
            TextView postalId = row.findViewById(R.id.postalId);

            locationName.setText(garageDetailsArrayList.get(position).getAddressName());
            postalId.setText("Postal ID: "+garageDetailsArrayList.get(position).getPostalId());


            return row;
        }
    }

    public void addGarageActivity(View view){
        Intent addGarageIntent = new Intent(getApplicationContext(),AddGarage.class);
        startActivity(addGarageIntent);
    }
}
