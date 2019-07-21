package com.example.parkin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.SpaceDetails;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PopGarageDetails extends Activity implements RecyclerViewAdapterPopSpace.OnItemClickListener{

    TextView locationName;
    TextView garageID;

    ArrayList<String> spaceID=new ArrayList<>();
    ArrayList<String> spaceSize=new ArrayList<>();
    ArrayList<String> position=new ArrayList<>();
    ArrayList<String> start_time=new ArrayList<>();
    ArrayList<String> end_time=new ArrayList<>();
    ArrayList<String> availability=new ArrayList<>();

    RecyclerView popRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_garage_details);

        locationName = (TextView) findViewById(R.id.popLocation);
        garageID = (TextView) findViewById(R.id.popGarageID);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9), (int)(height*.7));
        Intent myIntent = getIntent();
        locationName.setText(myIntent.getStringExtra("location"));
        garageID.setText(myIntent.getStringExtra("garageId"));

        popRecycleView = (RecyclerView) findViewById(R.id.recycleView_pop_space);
        initImageBitmaps();
    }

    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        Log.d("GarageID",garageID.getText().toString());
        String garID = garageID.getText().toString();
        garID = garID.substring(garID.lastIndexOf(" ")+1);
        ArrayList<SpaceDetails> spaceDetailsArrayList = communicateWithPhp.getGarageSpace(garID);

        for (int i=0; i<spaceDetailsArrayList.size(); i++){
            spaceID.add(Integer.toString(spaceDetailsArrayList.get(i).getSpaceid()));
            spaceSize.add(Integer.toString(spaceDetailsArrayList.get(i).getSpacesize()));
            position.add(spaceDetailsArrayList.get(i).getPosition());

            SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
            String start=DateFormat.format(spaceDetailsArrayList.get(i).getStarttime1().getTime());
            String end=DateFormat.format(spaceDetailsArrayList.get(i).getGetStarttime2().getTime());
            start_time.add(start);
            end_time.add(end);
            availability.add(spaceDetailsArrayList.get(i).getAvailability());

        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_pop_space);
        RecyclerViewAdapterPopSpace adapter = new RecyclerViewAdapterPopSpace(this, spaceID, spaceSize, position, start_time, end_time, availability, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemClick(int i) {

    }
}
