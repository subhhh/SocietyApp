package com.example.societyapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.societyapp.Model.PendingListModel;
import com.example.societyapp.Model.VisitorsListModel;
import com.example.societyapp.R;

import java.util.List;


public class VisitorListAdapter extends RecyclerView.Adapter<VisitorListAdapter.MyViewHolder> {
    private List<VisitorsListModel> visitorsListModels;
    private Context context;

    private int mSelectedPosition = 0;


    public VisitorListAdapter(List<VisitorsListModel> visitorsListModels, Context context) {
        this.visitorsListModels = visitorsListModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.customvisitorlist, parent, false);
         MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VisitorsListModel list = visitorsListModels.get(position);
        holder.vistorid.setText(list.getId());
        holder.vistornameTv.setText(list.getVisitorName());
        holder.flatnoTv.setText(list.getFlatno());
        holder.visitreasonTv.setText("Purpose of visit"+" : "+list.getVisitorReason());
        holder.visitornoTv.setText(list.getVisitorNumber());
        holder.addressTv.setText("From"+" : "+list.getVisitorAddress());
        holder.checkinTv.setText(list.getVisitorIntime());
        holder.checkoutTv.setText(list.getVisitorOuttime());
        holder.vehiclenameTv.setText(list.getVehicalName());
        holder.vehicletypeTv.setText(list.getVehicalType());
        holder.vehiclenumberTv.setText(list.getVechicalNumber());

        final String visitornumber = list.getVisitorNumber();
        holder.visitornoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + visitornumber));
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
        return visitorsListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView vistorid, vistornameTv, flatnoTv, visitreasonTv, visitornoTv, addressTv, checkinTv, checkoutTv, vehiclenameTv, vehicletypeTv,
                         vehiclenumberTv;



        public MyViewHolder(View view1) {
            super(view1);
            vistorid = (TextView) view1.findViewById(R.id.vistorid);
            vistornameTv = (TextView) view1.findViewById(R.id.vistornameTv);
            flatnoTv = (TextView) view1.findViewById(R.id.flatnoTv);
            visitreasonTv = (TextView) view1.findViewById(R.id.visitreasonTv);
            visitornoTv = (TextView) view1.findViewById(R.id.visitornoTv);
            addressTv = (TextView) view1.findViewById(R.id.addressTv);
            checkinTv = (TextView) view1.findViewById(R.id.checkinTv);
            checkoutTv = (TextView) view1.findViewById(R.id.checkoutTv);
            vehiclenameTv = (TextView) view1.findViewById(R.id.vehiclenameTv);
            vehicletypeTv = (TextView) view1.findViewById(R.id.vehicletypeTv);
            vehiclenumberTv = (TextView) view1.findViewById(R.id.vehiclenumberTv);



        }
    }
}