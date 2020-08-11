package com.example.societyapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.societyapp.R;
import com.example.societyapp.Utils.ApiClient;
import com.example.societyapp.Utils.ApiInterface;
import com.example.societyapp.Utils.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.societyapp.Utils.ApiClient.BASE_URL;

public class ComplaintDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    TextView nameTv, subjectTv, descriptionTv, createddtTv, flatnoTv;
    String user_id;
    String complain_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);
        toolbar= findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        nameTv = findViewById(R.id.nameTv);
        subjectTv = findViewById(R.id.subjectTv);
        descriptionTv = findViewById(R.id.descriptionTv);
        createddtTv = findViewById(R.id.createddtTv);
        flatnoTv = findViewById(R.id.flatnoTv);
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        user_id = RegPrefManager.getInstance(this).getUserId();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            complain_id = bundle.getString("CID");
            Log.d("url", complain_id);
        }

        if (isNetworkAvailable()){
            getComplainDetails();
        }
        else {
            noNetwrokErrorMessage();
        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void noNetwrokErrorMessage(){
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }


    public void getComplainDetails(){ 

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = "https://ivrics.com/societyAPI/index.php/Complaint/SingleComplaint?id="+complain_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                  //  Toast.makeText(ComplaintDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");


                    if(status.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String complain_id = o.getString("cid");
                                String user_id = o.getString("uid");
                                String subject = o.getString("subject");
                                String descripton = o.getString("complaint");
                                String createddatetime = o.getString("createdat");
                                String user_name = o.getString("name");
                                String user_flatno = o.getString("flatno");

                                nameTv.setText("Name"+" : "+user_name);
                                subjectTv.setText("Subject"+" : "+subject);
                                flatnoTv.setText("Flat No"+" : "+user_flatno);
                                createddtTv.setText(createddatetime);
                                descriptionTv.setText(descripton);

                            }
                        }  else if (array.length()==0){
                            Toast.makeText(ComplaintDetailsActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}