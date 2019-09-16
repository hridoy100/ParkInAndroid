package com.example.parkin.RecyclerViewAdapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.R;
import com.example.parkin.SingleGarageActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterSingleGarage extends RecyclerView.Adapter<RecyclerViewAdapterSingleGarage.ViewHolder>{

    static final String TAG = "RecyclerViewAdapterSingleGarage";
    ArrayList<String> spaceID = new ArrayList<>();
    ArrayList<String> spaceSize = new ArrayList<>();
    ArrayList<String> position = new ArrayList<>();
    ArrayList<String> startTime = new ArrayList<>();
    ArrayList<String> endTime = new ArrayList<>();
    ArrayList<String> availability = new ArrayList<>();
    ArrayList<String> cctvIp = new ArrayList<>();
    ArrayList<String> mImageUrls = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;
    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;

    public RecyclerViewAdapterSingleGarage(Context context, ArrayList<String> spaceId, ArrayList<String> spaceSize,
                                           ArrayList<String> position, ArrayList<String> startTime, ArrayList<String> endTime,
                                           ArrayList<String> availability, ArrayList<String> cctvIp, ArrayList<String> mImageUrls, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.spaceID = spaceId;
        this.spaceSize = spaceSize;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
        this.cctvIp = cctvIp;
        this.mImageUrls = mImageUrls;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_space_availability,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.spaceId.setText("Space ID: "+spaceID.get(i));
        viewHolder.position.setText("Space Name: "+position.get(i));
        if(availability.get(i).equals("yes")){
            viewHolder.available.setChecked(true);
        }
        else {
            viewHolder.available.setChecked(false);
        }
        Glide.with(context)
                .asBitmap()
                .load(mImageUrls.get(i))
                .into(viewHolder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return spaceID.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView position,spaceId;
        Switch available;
        CircleImageView circleImageView;
        public ViewHolder(View itemView, RecyclerViewAdapterSingleGarage.OnItemClickListener onItemClickListener) {
            super(itemView);
            position = (TextView) itemView.findViewById(R.id.spaceNo);
            spaceId = (TextView) itemView.findViewById(R.id.spaceId);
            available = (Switch) itemView.findViewById(R.id.available);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.listIconSpace);
            available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        String spId = spaceId.getText().toString().substring(spaceId.getText().toString().lastIndexOf(" ")+1);
                        Log.d("garageID",spId);
                        int index = spaceID.indexOf(spId);
                        availability.set(index,"yes");

                        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
                        communicateWithPhp.toggleSpaceAvailability(spId,"yes");
                        Toast.makeText(context, "Checked toggle enabled",Toast.LENGTH_SHORT).show();
                    } else {
                        // The toggle is disabled

                        String spId = spaceId.getText().toString().substring(spaceId.getText().toString().lastIndexOf(" ")+1);
                        Log.d("garageID",spId);
                        int index = spaceID.indexOf(spId);
                        availability.set(index,"no");

                        CommunicateWithPhp communicateWithPhp = new CommunicateWithPhp();
                        communicateWithPhp.toggleSpaceAvailability(spId,"no");
                        Toast.makeText(context, "Checked toggle disabled",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
