package com.example.parkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Camera extends AppCompatActivity {
    WebView cameraView;
    Button submit;
    EditText ipAddr;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        ipAddr = (EditText) findViewById(R.id.ipaddress);
        submit = (Button) findViewById(R.id.ipaddressSubmit);
        url = "http://192.168.0.104:8080/jsfs.html";
        cameraView = (WebView) findViewById(R.id.webviewCamera);

        cameraView.getSettings().setJavaScriptEnabled(true);
        cameraView.loadUrl(url);
    }

    public void submit(View view){
        url = ipAddr.getText().toString();
        cameraView.loadUrl(url);
    }
}