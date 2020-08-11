package com.example.societyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.societyapp.Activity.ServiceListActivity;
import com.example.societyapp.Model.ServiceModel;
import com.example.societyapp.R;
import com.example.societyapp.Utils.RegPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.MyViewHolder> {
    private List<ServiceModel> serviceModels;
    private Context context;

    private int mSelectedPosition = 0;


    public ServicesAdapter(List<ServiceModel> serviceModels, Context context) {
        this.serviceModels = serviceModels;
        this.context = context;

    }

    @Override
    public ServicesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customservice,parent,false);
        return new ServicesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ServiceModel list = serviceModels.get(position);
        holder.serviceid.setText(list.getId());
        holder.servicestatus.setText(list.getStatus());
        holder.servicename.setText(list.getServiceName());
        final String id = list.getId();
        RegPrefManager.getInstance(context).setServiceId(id);
        final String name = list.getServiceName();
        Picasso.with(context).load(list.getLogo()).into(holder.serviceimage);

        holder.serviceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context, ServiceListActivity.class);
                myIntent.putExtra("ID",id);
                myIntent.putExtra("NAME",name);
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
        return serviceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView servicename, serviceid, servicestatus;
        public ImageView serviceimage;
        public CardView serviceCardView;

        public MyViewHolder(View view1) {
            super(view1);
            servicename = (TextView) view1.findViewById(R.id.servicename);
            serviceid = (TextView) view1.findViewById(R.id.serviceid);
            servicestatus = (TextView) view1.findViewById(R.id.servicestatus);
            serviceimage = (ImageView) view1.findViewById(R.id.serviceimage);
            serviceCardView = (CardView) view1.findViewById(R.id.serviceCardView);
        }
    }
}