package com.example.parkin.RecyclerViewAdapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.parkin.DB.SpaceDetails;
import com.example.parkin.R;

import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerViewAdapterSingleSpace extends RecyclerView.Adapter<RecyclerViewAdapterSingleSpace.ViewHolder> {

    static final String TAG = "RecyclerViewAdapterSingleSpace";
    ArrayList<String> spaceArrayList = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterSingleSpace(ArrayList<String> spaceArrayList, Context context, OnItemClickListener onItemClickListener) {
        this.spaceArrayList = spaceArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_space_layout,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.spaceNoTxt.setText(spaceArrayList.get(i));
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
        TextView cctvIp;
        public ViewHolder(View itemView, RecyclerViewAdapterSingleSpace.OnItemClickListener onItemClickListener) {
            super(itemView);
            spaceNoTxt = (TextView) itemView.findViewById(R.id.spaceNo);
            position = (AppCompatEditText) itemView.findViewById(R.id.aboutSpace);
            openTime = (Button) itemView.findViewById(R.id.start_time);
            closeTime = (Button) itemView.findViewById(R.id.end_time);
            cctvIp = (TextView) itemView.findViewById(R.id.cctv_ip_address);

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
                        }
                    }, hour, minute, false);//No 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
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
