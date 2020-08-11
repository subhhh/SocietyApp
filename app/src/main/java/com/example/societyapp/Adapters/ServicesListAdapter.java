package com.example.societyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.societyapp.Model.ServiceListModel;
import com.example.societyapp.Model.ServiceModel;
import com.example.societyapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServicesListAdapter extends RecyclerView.Adapter<ServicesListAdapter.MyViewHolder> {
    private List<ServiceListModel> serviceListModels;
    private Context context;

    private int mSelectedPosition = 0;


    public ServicesListAdapter(List<ServiceListModel> serviceListModels, Context context) {
        this.serviceListModels = serviceListModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.customservicelist, parent, false);
        MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ServiceListModel list = serviceListModels.get(position);
        holder.servicelistid.setText(list.getId());
        holder.serviceliststatus.setText(list.getStatus());
        holder.servicelisttype.setText(list.getServiceType());
        holder.user_name.setText(list.getName());
        holder.number.setText("Phone"+"-"+list.getMobile());

        final String id = list.getId();
        final String name = list.getName();
        final String usernumber = list.getMobile();
        Picasso.with(context).load(list.getImage()).into(holder.userimage);

        holder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + usernumber));
                context.startActivity(intent);
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
        return serviceListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, number, servicelistid, servicelisttype, serviceliststatus;
        public ImageView userimage;
        public CardView cardview;
        public Button bookbtn;

        public MyViewHolder(View view1) {
            super(view1);
            user_name = (TextView) view1.findViewById(R.id.user_name);
            servicelistid = (TextView) view1.findViewById(R.id.servicelistid);
            servicelisttype = (TextView) view1.findViewById(R.id.servicelisttype);
            serviceliststatus = (TextView) view1.findViewById(R.id.serviceliststatus);
            number = (TextView) view1.findViewById(R.id.number);
            cardview = (CardView) view1.findViewById(R.id.cardview);
            userimage = (ImageView) view1.findViewById(R.id.userimage);
        }
    }
}