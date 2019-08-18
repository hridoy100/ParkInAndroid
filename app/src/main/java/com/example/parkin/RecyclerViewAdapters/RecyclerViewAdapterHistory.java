package com.example.parkin.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parkin.DB.Rent;
import com.example.parkin.R;

import java.util.ArrayList;

public class RecyclerViewAdapterHistory extends RecyclerView.Adapter<RecyclerViewAdapterHistory.ViewHolder> {
    static final String TAG = "RecycleViewAdapterHistory";
    ArrayList<Rent> rentArrayList = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterHistory(Context context, ArrayList<Rent> rentArrayList, OnItemClickListener onItemClickListener) {
        this.rentArrayList = rentArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_history_row,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        viewHolder.renterMobNo.setText(rentArrayList.get(i).getRenterMobNo());
        viewHolder.customerMobNo.setText(rentArrayList.get(i).getCustomerMobNo());
        viewHolder.spaceSize.setText(rentArrayList.get(i).getSpaceSize());
        viewHolder.start_time.setText(rentArrayList.get(i).getStart_time());
        viewHolder.end_time.setText(rentArrayList.get(i).getEnd_time());
        viewHolder.status.setText(rentArrayList.get(i).getStatus());
        viewHolder.cost.setText(rentArrayList.get(i).getCost());
    }

    @Override
    public int getItemCount() {
        return rentArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rentNo;
        TextView paymentNo;
        TextView customerMobNo;
        TextView renterMobNo;
        TextView spaceId;
        TextView licenseId;
        TextView spaceSize;
        TextView start_time;
        TextView end_time;
        TextView status;
        TextView cost;
        TextView review;
        OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            customerMobNo = (TextView) itemView.findViewById(R.id.customerMob);
            renterMobNo = (TextView) itemView.findViewById(R.id.renterMob);
            start_time = (TextView) itemView.findViewById(R.id.start_time);
            end_time = (TextView) itemView.findViewById(R.id.end_time);
            spaceSize = (TextView) itemView.findViewById(R.id.spaceSize);
            cost = (TextView) itemView.findViewById(R.id.cost);
            status = (TextView) itemView.findViewById(R.id.status);

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