package com.example.parkin.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.Rent;
import com.example.parkin.Notification;
import com.example.parkin.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapterSimpleCardView extends RecyclerView.Adapter<RecyclerViewAdapterSimpleCardView.ViewHolder> {
    static final String TAG = "RecyclerViewAdapterSimpleCardView";
    ArrayList<Notification> notificationArrayList = new ArrayList<>();
    Context context;
    RecyclerViewAdapterSimpleCardView.OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterSimpleCardView(Context context, ArrayList<Notification> notifications, RecyclerViewAdapterSimpleCardView.OnItemClickListener onItemClickListener) {
        this.notificationArrayList = notifications;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerViewAdapterSimpleCardView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_card_view_list,viewGroup, false);
        RecyclerViewAdapterSimpleCardView.ViewHolder holder = new RecyclerViewAdapterSimpleCardView.ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterSimpleCardView.ViewHolder viewHolder, final int i) {
        viewHolder.notifMsg.setText(Html.fromHtml(notificationArrayList.get(i).getNotificationMessage()));
        viewHolder.date.setText(notificationArrayList.get(i).getDate());
        viewHolder.time.setText(notificationArrayList.get(i).getTime());
        viewHolder.mobileNo.setText(Html.fromHtml(notificationArrayList.get(i).getMobileNo()));
        if(notificationArrayList.get(i).getStatus().equals("no"))
            viewHolder.simpleCardView.setCardBackgroundColor(Color.parseColor("#69ffcd"));
        else viewHolder.simpleCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView notifMsg;
        TextView date, time;
        TextView mobileNo;
        CardView simpleCardView;
        RecyclerViewAdapterSimpleCardView.OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView, RecyclerViewAdapterSimpleCardView.OnItemClickListener onItemClickListener) {
            super(itemView);
            notifMsg = (TextView) itemView.findViewById(R.id.notificationText);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            mobileNo = (TextView) itemView.findViewById(R.id.mobileNo);

            simpleCardView = (CardView) itemView.findViewById(R.id.simpleCardView);
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
