package com.example.parkin;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;


public class RecyclerViewAdapterPopSpace extends RecyclerView.Adapter<RecyclerViewAdapterPopSpace.ViewHolder> {
    static final String TAG = "RecyclerViewAdapterSpace";
    ArrayList<String> spaceID = new ArrayList<>();
    ArrayList<String> spaceSize = new ArrayList<>();
    ArrayList<String> position = new ArrayList<>();
    ArrayList<String> start_time = new ArrayList<>();
    ArrayList<String> end_time = new ArrayList<>();
    ArrayList<String> availability = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterPopSpace(Context context, ArrayList<String> spaceID, ArrayList<String> spaceSize, ArrayList<String> position,
                                       ArrayList<String> start_time, ArrayList<String> end_time, ArrayList<String> availability, OnItemClickListener onItemClickListener) {
        this.spaceID = spaceID;
        this.spaceSize = spaceSize;
        this.position = position;
        this.start_time = start_time;
        this.end_time = end_time;
        this.context = context;
        this.availability = availability;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.space_recycle_view_pop,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        viewHolder.spaceIdTxt.setText(spaceID.get(i));
        viewHolder.positionTxt.setText(position.get(i));
        viewHolder.openTime.setText(start_time.get(i));
        viewHolder.closeTime.setText(end_time.get(i));
        if(availability.get(i).equals("yes")){
            viewHolder.availabilityTxt.setText("Available");
            //viewHolder.availabilityTxt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_play_circle_filled_black_24dp,0);
            viewHolder.availabilityImg.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
        }
        else {
            viewHolder.availabilityTxt.setText("Unavailable");
            //viewHolder.availabilityTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_pause_circle_filled_black_24dp, 0);
            viewHolder.availabilityImg.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return spaceID.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView spaceIdTxt;
        TextView positionTxt;
        TextView availabilityTxt;
        ImageView availabilityImg;
        RadioGroup spaceTypeTxt;

        OnItemClickListener onItemClickListener;
        Button openTime;
        Button closeTime;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            spaceIdTxt = (TextView) itemView.findViewById(R.id.spaceId);
            positionTxt = (TextView) itemView.findViewById(R.id.spacePosition);
            availabilityTxt = (TextView) itemView.findViewById(R.id.availability);
            availabilityImg = (ImageView) itemView.findViewById(R.id.availabilityImg);
            spaceTypeTxt = (RadioGroup) itemView.findViewById(R.id.spaceType);

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
