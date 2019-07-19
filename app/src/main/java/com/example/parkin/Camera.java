package com.example.parkin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Camera extends AppCompatActivity {
    WebView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);

        cameraView = (WebView) findViewById(R.id.webviewCamera);
        String url = "http://192.168.0.102:8080/jsfs.html";
        cameraView.getSettings().setJavaScriptEnabled(true);
        cameraView.loadUrl(url);
    }
}