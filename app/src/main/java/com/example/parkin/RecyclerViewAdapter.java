package com.example.parkin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    static final String TAG = "RecyclerViewAdapter";
    ArrayList<String> licenseNumber = new ArrayList<>();
    ArrayList<String> companyName = new ArrayList<>();
    ArrayList<String> regNo = new ArrayList<>();
    ArrayList<String> mImages = new ArrayList<>();
    Context context;
    OnItemClickListener onItemClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<String> licenseNumber, ArrayList<String> companyName,
                               ArrayList<String> regNo, ArrayList<String> mImages, OnItemClickListener onItemClickListener) {
        this.licenseNumber = licenseNumber;
        this.companyName = companyName;
        this.regNo = regNo;
        this.mImages = mImages;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_recycle_view_vehicle,viewGroup, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,final int i) {
        Glide.with(context)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.circleImageView);
        viewHolder.licenseNo.setText(licenseNumber.get(i));
        viewHolder.compName.setText(companyName.get(i));
        viewHolder.regCode.setText(regNo.get(i));

        /*viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mImagesNames.get(i).equals("Bangladesh Power Division")){
//                    Intent pdbIntent = new Intent(context, PDBJobList.class);
//                    pdbIntent.putExtra("image_url", mImages.get(i));
//                    pdbIntent.putExtra("image_name", mImagesNames.get(i));
//                    context.startActivity(pdbIntent);
//                }
//                else if(mImagesNames.get(i).equals("Bank")){
//                    Intent bankIntent = new Intent(context, BankJobList.class);
//                    bankIntent.putExtra("image_url", mImages.get(i));
//                    bankIntent.putExtra("image_name", mImagesNames.get(i));
//                    context.startActivity(bankIntent);
//                }
//                else
                    Toast.makeText(context, licenseNumber.get(i), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return licenseNumber.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageView;
        TextView licenseNo;
        TextView compName;
        TextView regCode;
        LinearLayout parentLayout;
        OnItemClickListener onItemClickListener;
        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.listImage);
            licenseNo = itemView.findViewById(R.id.licenseNo);
            compName = itemView.findViewById(R.id.company);
            regCode = itemView.findViewById(R.id.regCode);

            parentLayout = itemView.findViewById(R.id.linearRecycleView_category);
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
