package com.example.societyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.example.societyapp.Adapters.DoctorListAdapter;
import com.example.societyapp.R;

public class PaymentsActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView doctorslistRv;
    DoctorListAdapter doctorListAdapter;
    String[] orderId = {"1", "2", "3", "4"};
    String[] foodName = {"1", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        doctorslistRv = (RecyclerView) findViewById(R.id.doctorslistRv);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(PaymentsActivity.this);
        doctorslistRv.setLayoutManager(mLayoutManager);
        doctorListAdapter = new DoctorListAdapter(PaymentsActivity.this, orderId, foodName);
        doctorslistRv.setAdapter(doctorListAdapter);
    }
}