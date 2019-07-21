package com.example.parkin;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;


public class RecyclerViewAdapterSpace extends RecyclerView.Adapter<RecyclerViewAdapterSpace.ViewHolder> {
    static final String TAG = "RecyclerViewAdapterSpace";
    ArrayList<String> spaceNo = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterSpace(ArrayList<String> spaceNo, Context context, OnItemClickListener onItemClickListener) {
        this.spaceNo = spaceNo;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.space_layout_listitem_recycle_view,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        viewHolder.spaceNoTxt.setText(spaceNo.get(i));
    }

    @Override
    public int getItemCount() {
        return spaceNo.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView spaceNoTxt;
        Spinner position;
        OnItemClickListener onItemClickListener;
        Button openTime;
        Button closeTime;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            spaceNoTxt = (TextView) itemView.findViewById(R.id.spaceNo);
            position = (Spinner) itemView.findViewById(R.id.position);
            openTime = (Button) itemView.findViewById(R.id.openTime);
            closeTime = (Button) itemView.findViewById(R.id.closeTime);

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

            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, context.getResources().getStringArray(R.array.position));
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            position.setAdapter(arrayAdapter);

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
