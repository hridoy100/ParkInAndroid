package com.example.parkin.RecyclerViewAdapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.R;

import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerViewAdapterSingleSpace extends RecyclerView.Adapter<RecyclerViewAdapterSingleSpace.ViewHolder> {

    static final String TAG = "RecyclerViewAdapterSingleSpace";
    ArrayList<String> spaceArrayList = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    public RecyclerViewAdapterSingleSpace(ArrayList<String> spaceArrayList, Context context, OnItemClickListener onItemClickListener) {
        this.spaceArrayList = spaceArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        myEditor = mySharedPreferences.edit();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_space_layout,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);
//        holder.checkSharedPreferences();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.spaceNoTxt.setText(spaceArrayList.get(i));
        myEditor.putString("com.example.parkin."+viewHolder.spaceNoTxt.getText()+"space_no", viewHolder.spaceNoTxt.getText().toString());
        myEditor.commit();
        viewHolder.checkSharedPreferences();
    }

    @Override
    public int getItemCount() {
        return spaceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView spaceNoTxt;
        AppCompatEditText position;
        RecyclerViewAdapterSingleSpace.OnItemClickListener onItemClickListener;
        Button openTime;
        Button closeTime;
        AppCompatEditText cctvIp;
        RadioGroup vehicleType;
        RadioButton vehicleTypeSelected;
        public ViewHolder(View itemView, RecyclerViewAdapterSingleSpace.OnItemClickListener onItemClickListener) {
            super(itemView);
            spaceNoTxt = (TextView) itemView.findViewById(R.id.spaceNo);
            position = (AppCompatEditText) itemView.findViewById(R.id.aboutSpace);
            openTime = (Button) itemView.findViewById(R.id.start_time);
            closeTime = (Button) itemView.findViewById(R.id.end_time);
            cctvIp = (AppCompatEditText) itemView.findViewById(R.id.cctv_ip_address);
            vehicleType = (RadioGroup) itemView.findViewById(R.id.vehicleType);


            openTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Log.i("hr", String.valueOf(selectedHour));
                            Log.i("min", String.valueOf(selectedMinute));
                            if(selectedMinute<10)
                                openTime.setText( selectedHour + ":0" + selectedMinute);
                            else
                                openTime.setText( selectedHour + ":" + selectedMinute);

                            myEditor.putString("com.example.parkin."+spaceNoTxt.getText()+"open", openTime.getText().toString());
                            myEditor.commit();
                        }
                    }, hour, minute, false);//No 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            closeTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Log.i("hr", String.valueOf(selectedHour));
                            Log.i("min", String.valueOf(selectedMinute));
                            if(selectedMinute<10)
                                closeTime.setText( selectedHour + ":0" + selectedMinute);
                            else
                                closeTime.setText( selectedHour + ":" + selectedMinute);
                            myEditor.putString("com.example.parkin."+spaceNoTxt.getText()+"close", closeTime.getText().toString());
                            myEditor.commit();
                        }
                    }, hour, minute, false);//No 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

//            cctvIp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //String cctv_ip = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt+cctvIp.getId(), "");
//                    myEditor.putString("com.example.parkin."+spaceNoTxt+cctvIp.getId(), cctvIp.getText().toString());
//                }
//            });

            /*cctvIp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    myEditor.putString("com.example.parkin."+spaceNoTxt+cctvIp.getId(), cctvIp.getText().toString());
                    myEditor.commit();
                    Toast.makeText(context, cctvIp.getText().toString(), Toast.LENGTH_SHORT).show();
                    Log.d("cctvIP recy",cctvIp.getText().toString());
                }
            });*/

            cctvIp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    myEditor.putString("com.example.parkin."+spaceNoTxt.getText()+"cctvIP", cctvIp.getText().toString());
                    myEditor.commit();
                    //Toast.makeText(context, cctvIp.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Log.d("cctvIP recy","com.example.parkin."+spaceNoTxt.getText().toString()+cctvIp.getId() + " Text:  "+ cctvIp.getText().toString());
                }
            });

            position.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    myEditor.putString("com.example.parkin."+spaceNoTxt.getText()+"position", position.getText().toString());
                    myEditor.commit();
                    //Toast.makeText(context, position.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Log.d("cctvIP recy","com.example.parkin."+spaceNoTxt.getText().toString()+position.getId() + " Text:  "+ position.getText().toString());
                }
            });

            vehicleType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    vehicleTypeSelected = (RadioButton) itemView.findViewById(checkedId);
                    //Toast.makeText(context, "Selected v: "+ vehicleTypeSelected.getText().toString(), Toast.LENGTH_SHORT).show();
                    myEditor.putString("com.example.parkin."+spaceNoTxt.getText()+"vehicleType", vehicleTypeSelected.getText().toString());
                    myEditor.commit();
                    //Log.d("vehicleType recy","com.example.parkin."+spaceNoTxt.getText().toString()+vehicleType.getId() + "  " + String.valueOf(vehicleTypeSelected.getId()));
                }
            });
            //checkSharedPreferences();

        }
        int count=0;
        public void checkSharedPreferences() {
            Log.d("check Shared Pref", "called"+spaceNoTxt.getText());
            String space_no = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"space_no", "");
            String position_txt = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"position", "");
            String open_time = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"open", "");
            String close_time = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"close", "");
            String cctv_ip = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"cctvIP", "");
            String vehicle_type = mySharedPreferences.getString("com.example.parkin."+spaceNoTxt.getText()+"vehicleType", "");
            if(space_no.length()>0){
                spaceNoTxt.setText(space_no);
                Log.d("check Shared Pref", "spaceno "+space_no);
            }
            if(position_txt.length()>0){
                position.setText(position_txt);

                Log.d("check Shared Pref", "pos "+position_txt);
            }
            if(open_time.length()>0){
                openTime.setText(open_time);

                Log.d("check Shared Pref", "open "+open_time);
            }
            if(close_time.length()>0){
                closeTime.setText(close_time);

                Log.d("check Shared Pref", "close "+close_time);
            }
            if(cctv_ip.length()>0){
                cctvIp.setText(cctv_ip);

                Log.d("check Shared Pref", "cctvIp"+cctv_ip);
            }
            if(vehicle_type.length()>0){
                if(vehicle_type.equals("Small Car")) {
                    vehicleType.check(R.id.smallCar);
                }
                else if(vehicle_type.equals("Medium Car")) {
                    vehicleType.check(R.id.mediumCar);
                }
                else if(vehicle_type.equals("4x4 or Large Car")) {
                    vehicleType.check(R.id.largeCar);
                }
                else if(vehicle_type.equals("Mini Van")) {
                    vehicleType.check(R.id.miniVan);
                }
                else if(vehicle_type.equals("Large Van or Minibus")) {
                    vehicleType.check(R.id.largeVan);
                }
                else if(vehicle_type.equals("Motor Bike")) {
                    vehicleType.check(R.id.bike);
                }
                Log.d("check Shared Pref", "radio "+vehicle_type);
            }
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }





}
