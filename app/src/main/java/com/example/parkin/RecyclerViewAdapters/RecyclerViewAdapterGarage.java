package com.example.parkin.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parkin.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterGarage extends RecyclerView.Adapter<RecyclerViewAdapterGarage.ViewHolder> {
    static final String TAG = "RecyclerViewAdapter";
    ArrayList<String> locationName = new ArrayList<>();
    ArrayList<String> garageId = new ArrayList<>();
    ArrayList<String> mImages = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterGarage(Context context, ArrayList<String> locationName, ArrayList<String> garageId, ArrayList<String> mImages,
                                      OnItemClickListener onItemClickListener) {
        this.locationName = locationName;
        this.garageId = garageId;
        this.mImages = mImages;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_recycle_view_garage,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        Glide.with(context)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.circleImageView);
        viewHolder.locationNameRow.setText(locationName.get(i));
        viewHolder.garageIDRow.setText(garageId.get(i));
    }

    @Override
    public int getItemCount() {
        return garageId.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageView;
        TextView locationNameRow;
        TextView garageIDRow;
        LinearLayout parentLayout;
        OnItemClickListener onItemClickListener;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.listIconGarage);
            locationNameRow = itemView.findViewById(R.id.locationName);
            garageIDRow = itemView.findViewById(R.id.garageID);

            parentLayout = itemView.findViewById(R.id.linearRecycleView_layout);
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
