package com.example.parkin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.DB.Constants;
import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.RecyclerViewAdapters.RecyclerViewAdapterSingleSpace;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class AddNewSpaceActivity extends AppCompatActivity {

    TextView spaceNoTxt;
    AppCompatEditText position;
    RecyclerViewAdapterSingleSpace.OnItemClickListener onItemClickListener;
    Button openTime;
    Button closeTime;
    AppCompatEditText cctvIp;
    RadioGroup vehicleType;
    RadioButton vehicleTypeSelected;
    private Button current;
    Calendar arrivaltime;
    Calendar departuretime;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private SwitchDateTimeDialogFragment dateTimeFragment2;
    SpaceDetails spaceDetails;

    Button submit;

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    String garageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_space);

        Intent intent = getIntent();
        garageId = intent.getStringExtra("garageId");

        View includedLayout = findViewById(R.id.included_layout);

        spaceNoTxt = (TextView) includedLayout.findViewById(R.id.spaceNo);
        position = (AppCompatEditText) includedLayout.findViewById(R.id.aboutSpace);
        openTime = (Button) includedLayout.findViewById(R.id.start_time);
        closeTime = (Button) includedLayout.findViewById(R.id.end_time);
        cctvIp = (AppCompatEditText) includedLayout.findViewById(R.id.cctv_ip_address);
        vehicleType = (RadioGroup) includedLayout.findViewById(R.id.vehicleType);

        submit = (Button) findViewById(R.id.submitBtn);

        submit.setText("Submit");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAddSpace();
            }
        });

        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    "DateTime",
                    "OK",
                    "Cancel"
                    // Optional
            );
        }
        if (dateTimeFragment2 == null) {
            dateTimeFragment2 = SwitchDateTimeDialogFragment.newInstance(
                    "DateTime",
                    "OK",
                    "Cancel"
                    // Optional
            );
        }
        // Optionally define a timezone
        dateTimeFragment.setTimeZone(TimeZone.getDefault());
        dateTimeFragment2.setTimeZone(TimeZone.getDefault());
        // Init format
        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        // Assign unmodifiable values
        //arrivaltime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        //departuretime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        arrivaltime=Calendar.getInstance();
        departuretime=Calendar.getInstance();
        departuretime.add(Calendar.MINUTE,30);
        Calendar calendar = Calendar.getInstance();
        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(false);
        dateTimeFragment.setMinimumDateTime(calendar.getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment2.set24HoursMode(false);
        dateTimeFragment2.setHighlightAMPMSelection(false);
        dateTimeFragment2.setMinimumDateTime(departuretime.getTime());
        dateTimeFragment2.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                arrivaltime.setTime(date);
                current.setText(myDateFormat.format(date));
                //dateTimeFragment2.setMinimumDateTime(cal.getTime());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
            }
        });
        dateTimeFragment2.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                departuretime.setTime(date);
                current.setText(myDateFormat.format(date));

            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Do nothing
            }

            @Override
            public void onNeutralButtonClick(Date date) {
                // Optional if neutral button does'nt exists
            }
        });



        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current=openTime;
                Calendar calendar=Calendar.getInstance();
                dateTimeFragment.startAtCalendarView();
                dateTimeFragment.setDefaultDateTime(arrivaltime.getTime());
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);


            }
        });

        closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current=closeTime;
                Calendar calendar=Calendar.getInstance();
                dateTimeFragment2.startAtCalendarView();
                dateTimeFragment2.setDefaultDateTime(departuretime.getTime());
                dateTimeFragment2.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);


            }
        });

        vehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vehicleTypeSelected = (RadioButton) findViewById(checkedId);
                //Toast.makeText(context, "Selected v: "+ vehicleTypeSelected.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void submitAddSpace(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Space Edit Confirmation");
        builder.setMessage("Are you sure you want to submit changes you've made?");
        builder.setCancelable(true);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
                int spaceSize = Constants.Motor_Bike;
                if(vehicleTypeSelected.getText().equals("Small Car")){
                    spaceSize = Constants.Small_Car;
                }
                else if(vehicleTypeSelected.getText().equals("Medium Car")){
                    spaceSize = Constants.Medium_Car;
                }
                if(vehicleTypeSelected.getText().equals("4x4 or Large Car")){
                    spaceSize = Constants.Large_Car;
                }
                if(vehicleTypeSelected.getText().equals("Mini Van")){
                    spaceSize = Constants.Mini_Van;
                }
                if(vehicleTypeSelected.getText().equals("Large Van or Minibus")){
                    spaceSize = Constants.Large_Van;
                }

                communicateWithPhp.addSpace(garageId, String.valueOf(spaceSize),openTime.getText().toString(),
                        closeTime.getText().toString(),position.getText().toString(),cctvIp.getText().toString());
                Toast.makeText(getApplicationContext(), "Space edited successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing..
            }
        });
        builder.show();

    }
}
