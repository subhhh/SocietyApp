package com.example.societyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.societyapp.Activity.ComplaintDetailsActivity;
import com.example.societyapp.Activity.ServiceListActivity;
import com.example.societyapp.Model.PendingListModel;
import com.example.societyapp.Model.ServiceListModel;
import com.example.societyapp.R;

import java.util.List;

public class PendingComplaintAdapter extends RecyclerView.Adapter<PendingComplaintAdapter.MyViewHolder> {
    private List<PendingListModel> pendingListModels;
    private Context context;

    private int mSelectedPosition = 0;


    public PendingComplaintAdapter(List<PendingListModel> pendingListModels, Context context) {
        this.pendingListModels = pendingListModels;
        this.context = context;
    }

    @Override
    public PendingComplaintAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_pending_adapter, parent, false);
        PendingComplaintAdapter.MyViewHolder holder = new PendingComplaintAdapter.MyViewHolder(view1);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final PendingComplaintAdapter.MyViewHolder holder, final int position) {
        final PendingListModel list = pendingListModels.get(position);
        final String cId = list.getCid();
        holder.complainTv.setText(list.getCid());
        holder.nameTv.setText(list.getName());
        holder.flatnoTv.setText(list.getFlatno());
        holder.subjectTv.setText(list.getSubject());
        holder.createddtTv.setText(list.getCreatedat());

        holder.viewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, ComplaintDetailsActivity.class);
                myIntent.putExtra("CID",cId);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
            }
        });
        //  holder.menu_name.setText(orderId.length);
        /*holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DoctorProfileActivity.class));
            }
        });*/
        
    }

    @Override
    public int getItemCount() {
        return pendingListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView complainTv, nameTv, flatnoTv, subjectTv, createddtTv, viewTv;

        public MyViewHolder(View view1) {
            super(view1);
            complainTv = view1.findViewById(R.id.complainTv);
            nameTv = view1.findViewById(R.id.nameTv);
            flatnoTv = view1.findViewById(R.id.flatnoTv);
            subjectTv = view1.findViewById(R.id.subjectTv);
            createddtTv = view1.findViewById(R.id.createddtTv);
            viewTv = view1.findViewById(R.id.viewTv);

        }
    }
}