package com.example.parkin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterHistory;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSimpleCardView;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements RecyclerViewAdapterSimpleCardView.OnItemClickListener {
    RecyclerView notificationList;
    ArrayList<Notification> notificationArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notificationList = (RecyclerView) findViewById(R.id.notificationList);

        init();

    }

    void init() {
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        notificationArrayList = communicateWithPhp.getNotification(this);
        Log.d("notification list size", Integer.toString(notificationArrayList.size()));

        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.notificationList);
        RecyclerViewAdapterSimpleCardView adapter = new RecyclerViewAdapterSimpleCardView(this, notificationArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemClick(int i) {
        Intent singleNotification = new Intent(getApplicationContext(),SingleNotificationActivity.class);
        singleNotification.putExtra("rentNo",notificationArrayList.get(i).getRentno());
        startActivity(singleNotification);
    }
}
