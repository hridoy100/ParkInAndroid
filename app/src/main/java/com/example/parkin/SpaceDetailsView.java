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
import android.widget.CheckBox;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import static com.example.parkin.DB.Constants.Large_Car;
import static com.example.parkin.DB.Constants.Large_Van;
import static com.example.parkin.DB.Constants.Medium_Car;
import static com.example.parkin.DB.Constants.Mini_Van;
import static com.example.parkin.DB.Constants.Motor_Bike;
import static com.example.parkin.DB.Constants.Small_Car;
import static com.example.parkin.DB.Constants.cctv_cost;
import static com.example.parkin.DB.Constants.cctv_index;
import static com.example.parkin.DB.Constants.covered_parking_cost;
import static com.example.parkin.DB.Constants.covered_parking_index;
import static com.example.parkin.DB.Constants.disabled_access_cost;
import static com.example.parkin.DB.Constants.disabled_access_index;
import static com.example.parkin.DB.Constants.electric_vehicle_charging_index;
import static com.example.parkin.DB.Constants.electric_vehicle_cost;
import static com.example.parkin.DB.Constants.lighting_cost;
import static com.example.parkin.DB.Constants.lighting_index;
import static com.example.parkin.DB.Constants.oil_buying_cost;
import static com.example.parkin.DB.Constants.oil_buying_index;
import static com.example.parkin.DB.Constants.per_hour_cost;
import static com.example.parkin.DB.Constants.securely_gated_cost;
import static com.example.parkin.DB.Constants.securely_gated_index;

public class SpaceDetailsView extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener, RecyclerViewAdapter.OnItemLongClickListener {
    ListView vehicleList;
    ProgressDialog progressDialog;
    int selectedItem;
    int selected_space_size;
    private String facility;
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
    long cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_list_layout);

        progressDialog = new ProgressDialog(this);

        //vehicleList = findViewById(R.id.vehicleList);
        progressDialog.setMessage("Loading Available Space Details");
        progressDialog.show();
        cost=0;
        Intent myintent=getIntent();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        long arrtime=myintent.getLongExtra("arrivaltime", Calendar.getInstance().getTimeInMillis());
        long dtime=myintent.getLongExtra("departuretime", Calendar.getInstance().getTimeInMillis());
        garageaddress=myintent.getStringExtra("garagelocation");
        garageid=Integer.parseInt(myintent.getExtras().get("garageid").toString());
        vehicle_type= myintent.getStringExtra("vehicleType");
        facility=myintent.getStringExtra("facility");
        System.out.println("Garage id: "+garageid);
        arrivaltime=gettime(arrtime);
        departuretime=gettime(dtime);
        String start=myDateFormat.format(arrivaltime.getTime());
        String end=myDateFormat.format(departuretime.getTime());
        StringTokenizer tokenizer1=new StringTokenizer(start);
        String s_date=tokenizer1.nextToken(" ");
        String s_time=tokenizer1.nextToken(" ");
        StringTokenizer tokenizer2=new StringTokenizer(end);
        String e_date=tokenizer2.nextToken(" ");
        String e_time=tokenizer2.nextToken(" ");
        System.out.println("s_date:"+s_date);
        System.out.println("e_date:"+e_date);
        System.out.println("s_time:"+s_time);
        System.out.println("e_time:"+e_time);
        CalculateInitCost(s_date,e_date,s_time,e_time);

        System.out.println("Again init cost: "+cost);
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
    void CalculateInitCost(String s_date,String e_date,String s_time,String e_time)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date sdate = sdf.parse(s_date);
            Date edate=sdf.parse(e_date);
            long diff = edate.getTime() - sdate.getTime();
            long diff_day=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.out.println("diff day:"+diff_day);
            sdf=new SimpleDateFormat("HH:mm:ss");
            Date stime=sdf.parse(s_time);
            Date etime=sdf.parse(e_time);
            diff=etime.getTime()-stime.getTime();
            long diff_time=TimeUnit.MINUTES.convert(diff,TimeUnit.MILLISECONDS);
            System.out.println("diff time:"+diff_time);
            long tmp=60;
            long diff_hour=diff_time/tmp;
            System.out.println("diff hour:"+diff_hour);
            cost=(diff_day+1)*(diff_hour+1)*per_hour_cost;
            System.out.println("init cost:"+cost);
            //facility cost adding
            if(facility.length()>=7) {
                if (facility.charAt(covered_parking_index) == '1')
                    cost += covered_parking_cost;
                if (facility.charAt(cctv_index) == '1')
                    cost += cctv_cost;
                if (facility.charAt(securely_gated_index) == '1')
                    cost += securely_gated_cost;
                if (facility.charAt(disabled_access_index) == '1')
                    cost += disabled_access_cost;
                if (facility.charAt(electric_vehicle_charging_index) == '1')
                    cost += electric_vehicle_cost;
                if (facility.charAt(lighting_index) == '1')
                    cost += lighting_cost;
                if (facility.charAt(oil_buying_index) == '1')
                    cost += oil_buying_cost;
            }
        } catch (ParseException e) {
            System.out.println("Cost exception");
            e.printStackTrace();
        }

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
        selected_space_size=spaceDetails.get(i).getSpacesize();
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
        CheckBox covered_parking=(CheckBox)layout.findViewById(R.id.covered_parking);
        CheckBox cctv=(CheckBox)layout.findViewById(R.id.cctv);
        CheckBox securely_gated=(CheckBox)layout.findViewById(R.id.securely_gated);
        CheckBox disabled_access=(CheckBox)layout.findViewById(R.id.disabled_access);
        CheckBox electric_vehicle_charging=(CheckBox)layout.findViewById(R.id.electric_vehicle);
        CheckBox lighting=(CheckBox)layout.findViewById(R.id.lighting);
        CheckBox oil_buying=(CheckBox)layout.findViewById(R.id.oil_buying);
        ////set the checkbox facilitites//////////////
        if(facility.length()>=7) {
            if (facility.charAt(covered_parking_index) == '1')
                covered_parking.setChecked(true);
            if (facility.charAt(cctv_index) == '1')
                cctv.setChecked(true);
            if (facility.charAt(securely_gated_index) == '1')
                securely_gated.setChecked(true);
            if (facility.charAt(disabled_access_index) == '1')
                disabled_access.setChecked(true);
            if (facility.charAt(electric_vehicle_charging_index) == '1')
                electric_vehicle_charging.setChecked(true);
            if (facility.charAt(lighting_index) == '1')
                lighting.setChecked(true);
            if (facility.charAt(oil_buying_index) == '1')
                oil_buying.setChecked(true);
        }
        start_time.setText("Arrival Time : "+myDateFormat.format(arrivaltime.getTime()));
        end_time.setText("Departure Time : "+myDateFormat.format(departuretime.getTime()));
        int tmp_space_size=spaceDetails.get(i).getSpacesize();
        String tmp_string=null;
        if(tmp_space_size==Large_Van)
        {
            tmp_string="Large_Van";
        }
        else if(tmp_space_size==Mini_Van)
        {
            tmp_string="Mini_Van";
        }
        else if(tmp_space_size==Large_Car)
        {
            tmp_string="Large_Car";
        }
        else if(tmp_space_size==Medium_Car)
        {
            tmp_string="Medium_Car";
        }
        else if(tmp_space_size==Small_Car)
        {
            tmp_string="Small_Car";
        }
        else if(tmp_space_size==Motor_Bike)
            tmp_string="Motor_Bike";
        space_size.setText("Space Size : "+tmp_string);
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
            if(idxj==spinnerlist.size())
                break;
            if(!spinnerlist.get(idxj).getType().equals(vehicle_type) && vehicle_type!=null)
            {
                spinnerlist.remove(idxj);
            }
            else
                idxj++;

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
                        else if(selected_space_size<spinnerlist.get(selectedItem-1).getSpaceSize()) {
                            flag = 0;
                            text.setText("This space is smaller than your selected vehicle");
                        }
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
                            spaceintent.putExtra("facility",facility);
                            finish();
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
        super.onBackPressed();
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        String tmp_string=null;
        for (int i=0; i<spaceDetails.size(); i++){
            int tmp_space_size=spaceDetails.get(i).getSpacesize();
            if(tmp_space_size==Large_Van)
            {
                tmp_string="Large_Van";
            }
            else if(tmp_space_size==Mini_Van)
            {
                tmp_string="Mini_Van";
            }
            else if(tmp_space_size==Large_Car)
            {
                tmp_string="Large_Car";
            }
            else if(tmp_space_size==Medium_Car)
            {
                tmp_string="Medium_Car";
            }
            else if(tmp_space_size==Small_Car)
            {
                tmp_string="Small_Car";
            }
            else if(tmp_space_size==Motor_Bike)
                tmp_string="Motor_Bike";
            long show_cost=cost+tmp_space_size;
            spacenolist.add("Space No: "+ i);
            spacesizelist.add("Space Size: "+tmp_string);
            minimumcostlist.add("Minimum Cost: "+String.valueOf(show_cost));
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

