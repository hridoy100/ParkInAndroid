package com.example.parkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.DB.VehicleDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapter;

import org.checkerframework.checker.units.qual.C;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SpaceDetailsView extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {
    ListView vehicleList;
    ProgressDialog progressDialog;
    int selectedItem;
    ArrayList<String> spacenolist = new ArrayList<>();
    ArrayList<String> spacesizelist= new ArrayList<>();
    ArrayList<String> minimumcostlist = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    ArrayList<SpaceDetails>spaceDetails=new ArrayList<>();
    Calendar arrivaltime;
    Calendar departuretime;
    String garageaddress;
    int garageid;
    int licenseid;
    private String vehicle_type;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_list_layout);

        progressDialog = new ProgressDialog(this);

        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Available Space Details");
        progressDialog.show();
        Intent myintent=getIntent();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        long arrtime=myintent.getLongExtra("arrivaltime", Calendar.getInstance().getTimeInMillis());
        long dtime=myintent.getLongExtra("departuretime", Calendar.getInstance().getTimeInMillis());
        garageaddress=myintent.getStringExtra("garagelocation");
        garageid=myintent.getIntExtra("garageid",1);//Integer.parseInt(myintent.getExtras().get("garageid").toString());
        vehicle_type= myintent.getStringExtra("vehicleType");
        System.out.println("Garage id: "+garageid);
        arrivaltime=gettime(arrtime);
        departuretime=gettime(dtime);
        initImageBitmaps();
/*
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getVehicleDetailsDB(getApplicationContext());
        String title[] = new String[vehicleDetailsArrayList.size()];
        for (int i=0; i<vehicleDetailsArrayList.size(); i++){
            title[i] = vehicleDetailsArrayList.get(i).getLicenseId();
        }
        MyAdapter myAdapter = new MyAdapter(this, vehicleDetailsArrayList, title);
        vehicleList.setAdapter(myAdapter);
        */
        progressDialog.dismiss();
    }
    Calendar gettime(long val)
    {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis(val);

        Date date = myDate.getTime();
        myDate.setTime(date);
        return myDate;
    }
    @Override
    public void onItemClick(int i) {
//        String licenseNo = license.get(i);
//        licenseNo = licenseNo.substring(licenseNo.lastIndexOf(" ")+1);
//        Toast.makeText(getApplicationContext(), licenseNo, Toast.LENGTH_SHORT).show();
//        Log.d("onItemClick", licenseNo);
//        Intent editIntent = new Intent(getApplicationContext(), VehicleEditActivity.class);
//        editIntent.putExtra("licenseNo",licenseNo);
//        startActivity(editIntent);
        final int gid=garageid;
        final int sid=spaceDetails.get(i).getSpaceid();
        final String glocation=garageaddress;
        final Calendar arrive=arrivaltime;
        final Calendar depart=departuretime;
        selectedItem=0;
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = (this).getLayoutInflater();
        context=this;
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        View layout = inflater.inflate(R.layout.custom_dialogue,null);
        TextView start_time=(TextView)layout.findViewById(R.id.Start_time);
        TextView end_time=(TextView)layout.findViewById(R.id.End_time);
        TextView space_size=(TextView)layout.findViewById(R.id.Space_size);
        TextView space_location=(TextView)layout.findViewById(R.id.Space_location);
        start_time.setText("Arrival Time : "+myDateFormat.format(arrivaltime.getTime()));
        end_time.setText("Departure Time : "+myDateFormat.format(departuretime.getTime()));
        space_size.setText("Space Size : "+spaceDetails.get(i).getSpacesize());
        space_location.setText("Space No : "+spaceDetails.get(i).getPosition());
        Spinner spinner = (Spinner) layout.findViewById(R.id.vehicle_list);
        CommunicateWithPhp com=new CommunicateWithPhp();
        ArrayList<VehicleDetails>vehcilelist=com.getVehicleDetailsDB(this);
        ArrayList<VehicleDetails>spinnerlist=vehcilelist;
        System.out.println(vehcilelist.size());
        List<String>spinneritem=new ArrayList<String>();
        spinneritem.add("Select Vehicle");
        int idxj=0;
        while(true)
        {
            if(!spinnerlist.get(idxj).getType().equals(vehicle_type) && vehicle_type!=null)
            {
                spinnerlist.remove(idxj);
            }
            else
                idxj++;
            if(idxj==spinnerlist.size())
                break;
        }
        for(int j=0;j<vehcilelist.size();j++)
        {
            String s="License No:"+vehcilelist.get(j).getLicenseNo()+
                    "\n Vehicle code:"+vehcilelist.get(j).getVehicleCode()+
                    "\n Type:"+vehcilelist.get(j).getType()+
                    "\n Company:"+vehcilelist.get(j).getCompany();
            spinneritem.add(s);
        }
        spinneritem.add("Add a vehicle");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem=position;
                if(position==vehcilelist.size()+1)
                {
                    Intent vehicleintent=new Intent(getApplicationContext(),Vehicle.class);
                    startActivity(vehicleintent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinneritem){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v = null;
                v = super.getDropDownView(position, null, parent);
                // If this is the selected item position
                if (position == selectedItem) {
                    v.setBackgroundColor(Color.BLUE);
                }
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        builder.setTitle("Rent this Space?");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_bike_blue);
        builder.setView(layout);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        int flag=1;
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.custom_toast,
                                (ViewGroup) findViewById(R.id.custom_toast_container));
                        if(selectedItem==0)
                            flag=0;
                        TextView text = (TextView) layout.findViewById(R.id.text);
                        if(flag==0)
                            text.setText("Please select a vehicle");
                        else
                            text.setText("Space Allocatin Successful");
                        Toast toast=new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                        if(flag==1) {
                            Log.d("SelectedItem",Integer.toString(selectedItem));
                            Log.d("VehicleList Size",Integer.toString(vehcilelist.size()));
                            Log.d("License ID", spinnerlist.get(selectedItem-1).getLicenseId());
                            licenseid=Integer.parseInt(spinnerlist.get(selectedItem-1).getLicenseId());
                            //Toast.makeText(getApplicationContext(), "Space Booked", Toast.LENGTH_SHORT).show();
                            CommunicateWithPhp com = new CommunicateWithPhp();
                            com.bookGarageSpace(context, garageid, sid, arrivaltime, departuretime,licenseid);
                            Intent spaceintent = new Intent(getApplicationContext(), SpaceDetailsView.class);
                            spaceintent.putExtra("arrivaltime", arrivaltime.getTimeInMillis());
                            spaceintent.putExtra("departuretime", departuretime.getTimeInMillis());
                            spaceintent.putExtra("garagelocation", garageaddress);
                            spaceintent.putExtra("garageid", garageid);
                            startActivity(spaceintent);
                        }
                    }});
        builder.setNegativeButton(android.R.string.no, null);
        builder.create();
        builder.show();
//        new AlertDialog.Builder(this)
//                .setTitle("Space Allocation Confirmation")
//                .setMessage("Do you Want to Book this space?\n"+"Arrival Time: "+myDateFormat.format(arrivaltime.getTime())
//                +"\n"+"Departure Time: "+myDateFormat.format(departuretime.getTime())
//                        +"\n"+"Space Size: "+spaceDetails.get(i).getSpacesize()+"\n"+
//                        "Space Position: "+spaceDetails.get(i).getPosition())
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.custom_toast,
//                                (ViewGroup) findViewById(R.id.custom_toast_container));
//                        TextView text = (TextView) layout.findViewById(R.id.text);
//                        text.setText("Space Allocatin Successful");
//                        Toast toast=new Toast(getApplicationContext());
//                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
//                        //Toast.makeText(getApplicationContext(), "Space Booked", Toast.LENGTH_SHORT).show();
//                        CommunicateWithPhp com=new CommunicateWithPhp();
//                        com.bookGarageSpace(garageid,sid,arrivaltime,departuretime);
//                        Intent spaceintent=new Intent(getApplicationContext(),SpaceDetailsView.class);
//                        spaceintent.putExtra("arrivaltime",arrivaltime.getTimeInMillis());
//                        spaceintent.putExtra("departuretime",departuretime.getTimeInMillis());
//                        spaceintent.putExtra("garagelocation",garageaddress);
//                        spaceintent.putExtra("garageid",garageid);
//                        startActivity(spaceintent);
//                    }})
//                .setNegativeButton(android.R.string.no, null).show();

    }

    public void goBackActivity(View view){
        Intent mapIntent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(mapIntent);
    }


    void initImageBitmaps() {
        //mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLCINLcwcG0xWtc73CfeEjnOM0oi_yRG9BTmMjQf60DljywHYD");
        /*mImageUrls.add("https://img7.androidappsapk.co/300/4/6/4/namaa.com.jobsbank.png");
        mNames.add("Bank");
        mImageUrls.add("https://youthcarnival.org/bn/wp-content/uploads/2018/04/BPDB1-3-1.png");
        mNames.add("Bangladesh Power Division");
        */
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
        Log.d("GarageID",Integer.toString(garageid));
        Log.d("arrivalTime",myDateFormat.format(arrivaltime.getTime()));
        Log.d("DepartureTime",myDateFormat.format(departuretime.getTime()));
        spaceDetails = communicateWithPhp.getAvailableSpaces(garageid, arrivaltime, departuretime);
        //ArrayList<VehicleDetails> vehicleDetailsArrayList = communicateWithPhp.getVehicleDetailsDB(getApplicationContext());
        Log.d("size",String.valueOf(spaceDetails.size()));
        for (int i=0; i<spaceDetails.size(); i++){
            spacenolist.add("Space No: "+ i);
            spacesizelist.add("Space Size: "+spaceDetails.get(i).getSpacesize());
            minimumcostlist.add("Minimum Cost: "+"10 taka");
            mImageUrls.add("https://banner2.kisspng.com/20180211/kgq/kisspng-car-icon-driving-car-5a804313d86b14.6905057915183552198865.jpg");
        }


        initRecyclerView();
    }

    void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycleView_space);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,spacenolist,spacesizelist, minimumcostlist, mImageUrls, this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemLongClick(int i) {

    }
}

