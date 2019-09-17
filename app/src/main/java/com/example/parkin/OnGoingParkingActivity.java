package com.example.parkin;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSimpleCardView;

import java.util.ArrayList;
import java.util.List;

public class OnGoingParkingActivity extends AppCompatActivity implements RecyclerViewAdapterSimpleCardView.OnItemClickListener{


    RecyclerView notificationList;
    ArrayList<Notification> notificationArrayList;
    RecyclerView recyclerView;
    CommunicateWithPhp communicateWithPhp;
    SharedPreferences sharedPreferences;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        recyclerView = findViewById(R.id.notificationList);
        notificationList = (RecyclerView) findViewById(R.id.notificationList);
        communicateWithPhp = new CommunicateWithPhp();
        progressDialog = new ProgressDialog(this);
        init();

    }

    void init() {
        notificationArrayList = new ArrayList<>();
        progressDialog.setMessage("Fetching data..");
        progressDialog.show();
        notificationArrayList = communicateWithPhp.getOnGoingParking(this);
        Log.d("notification list size", Integer.toString(notificationArrayList.size()));
        progressDialog.dismiss();
        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerViewAdapterSimpleCardView adapter = new RecyclerViewAdapterSimpleCardView(this, notificationArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    protected Boolean isActivityRunning(Class activityClass)
    {
        ActivityManager activityManager = (ActivityManager) getBaseContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    @Override
    public void onItemClick(int i) {

        String myMobNo = sharedPreferences.getString("com.example.parkin.mobileNo", "");
        String oldStatus = notificationArrayList.get(i).getStatus();
        String renterStatus = oldStatus.substring(0,oldStatus.indexOf(","));
        String customerStatus = oldStatus.substring(oldStatus.indexOf(",")+1);

        Log.d("renter status",renterStatus);
        Log.d("customer status",customerStatus);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        if(myMobNo.equals(notificationArrayList.get(i).getCustomerMobileNo()) && customerStatus.equals("no"))
        {
            String newStatus = renterStatus+","+"yes";
            communicateWithPhp.updateNotification(notificationArrayList.get(i).getRentno(), newStatus);
        }
        else if(myMobNo.equals(notificationArrayList.get(i).getRenterMobileNo()) && renterStatus.equals("no"))
        {
            String newStatus = "yes"+","+customerStatus;
            communicateWithPhp.updateNotification(notificationArrayList.get(i).getRentno(), newStatus);
        }
        //Toast.makeText(getApplicationContext(),"rentNo: "+notificationArrayList.get(i).getRentno(),Toast.LENGTH_SHORT).show();

        Intent singleNotification = new Intent(getApplicationContext(),SingleNotificationActivity.class);
        singleNotification.putExtra("rentNo",notificationArrayList.get(i).getRentno());
        startActivity(singleNotification);
        if(isActivityRunning(SingleNotificationActivity.class)){
            progressDialog.dismiss();
        }
    }

    public void onBackClicked(View view){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        progressDialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
