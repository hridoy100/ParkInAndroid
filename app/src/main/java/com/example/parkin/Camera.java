package com.example.parkin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.SpaceDetails;

import java.util.ArrayList;

public class Camera extends AppCompatActivity {
    WebView cameraView;
    Button submit;
    EditText ipAddr;
    String url;
    String spaceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);


        Intent myIntent = getIntent();
        spaceId = myIntent.getStringExtra("spaceId");
        String user = myIntent.getStringExtra("user");
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        SpaceDetails spaceDetails = communicateWithPhp.getSingleSpace(spaceId);

        ipAddr = (EditText) findViewById(R.id.ipaddress);
        submit = (Button) findViewById(R.id.ipaddressSubmit);

        if(user.equals("customer")){
            ipAddr.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);
        }

        ipAddr.setText("http://"+spaceDetails.getCctvIp()+":8080/jsfs.html");

        url = ipAddr.getText().toString();
//        url = "http://192.168.0.104:8080/jsfs.html";
        cameraView = (WebView) findViewById(R.id.webviewCamera);

        cameraView.getSettings().setJavaScriptEnabled(true);
        cameraView.loadUrl(url);
    }

    public void submit(View view){
        url = ipAddr.getText().toString();
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        String ip = ipAddr.getText().toString();
        ip = ip.substring(ip.indexOf("/")+2);
        ip = ip.substring(0,ip.lastIndexOf("/"));
        communicateWithPhp.updateCctvIp(spaceId,ip);

        cameraView.loadUrl(url);
    }
}