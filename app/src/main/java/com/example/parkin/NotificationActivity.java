package com.example.parkin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterHistory;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSimpleCardView;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements RecyclerViewAdapterSimpleCardView.OnItemClickListener {
    RecyclerView notificationList;
    ArrayList<Notification> notificationArrayList;
    RecyclerView recyclerView;
    CommunicateWithPhp communicateWithPhp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.notificationList);
        notificationList = (RecyclerView) findViewById(R.id.notificationList);
        communicateWithPhp = new CommunicateWithPhp();
        init();

    }

    void init() {
        notificationArrayList = communicateWithPhp.getNotification(this);
        Log.d("notification list size", Integer.toString(notificationArrayList.size()));

        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerViewAdapterSimpleCardView adapter = new RecyclerViewAdapterSimpleCardView(this, notificationArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int i) {
        if(notificationArrayList.get(i).getStatus().equals("no"))
            communicateWithPhp.updateNotification(notificationArrayList.get(i).getRentno());
        Intent singleNotification = new Intent(this,SingleNotificationActivity.class);
        singleNotification.putExtra("rentNo",notificationArrayList.get(i).getRentno());
        startActivity(singleNotification);
    }

    public void onBackClicked(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
