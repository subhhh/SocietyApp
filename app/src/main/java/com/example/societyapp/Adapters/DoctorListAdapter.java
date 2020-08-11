package com.example.societyapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.societyapp.R;


public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {
    private String[] orderId;
    private String[] foodName;

    private int mSelectedPosition = 0;
    Context context;


    public DoctorListAdapter(Context context, String[] orderId, String[] foodName) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.customdoctorlist, parent, false);
        MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

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
        return orderId.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView menu_name, add,dish_name;
        public ImageView menu_icon;
        public CardView cardview;
        public LinearLayout grid_lay, counter_lay;



        public MyViewHolder(View view1) {
            super(view1);
            menu_name = (TextView) view1.findViewById(R.id.dish_name);
            cardview = (CardView) view1.findViewById(R.id.cardview);


        }
    }
}