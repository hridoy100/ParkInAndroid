package com.example.parkin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        System.out.println(notificationArrayList.size());

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

    }
}
