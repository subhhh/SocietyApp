package com.example.societyapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.societyapp.Adapters.DoctorListAdapter;
import com.example.societyapp.Adapters.ServicesAdapter;
import com.example.societyapp.Adapters.ServicesListAdapter;
import com.example.societyapp.Model.ServiceListModel;
import com.example.societyapp.Model.ServiceModel;
import com.example.societyapp.R;
import com.example.societyapp.Utils.ApiClient;
import com.example.societyapp.Utils.ApiInterface;
import com.example.societyapp.Utils.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView servicelistRv;
    TextView servicenameTv;
    ServicesListAdapter servicesListAdapter;
    String[] orderId = {"1", "2", "3", "4"};
    String[] foodName = {"1", "2"};
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    int i=0;
    private List<ServiceListModel> serviceListModels;
    String service_name, service_id;
    String user_id, user_name, user_phone, token;
    String servId;
    TextView nodataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        servicelistRv = (RecyclerView) findViewById(R.id.servicelistRv);
        servicenameTv = (TextView) findViewById(R.id.servicenameTv);
        nodataTv = (TextView) findViewById(R.id.nodataTv);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        user_id = RegPrefManager.getInstance(ServiceListActivity.this).getUserId();
        user_name = RegPrefManager.getInstance(ServiceListActivity.this).getUserName();
        user_phone = RegPrefManager.getInstance(ServiceListActivity.this).getUserMobile();
        token = RegPrefManager.getInstance(ServiceListActivity.this).getTokenAuth();
        servId = RegPrefManager.getInstance(ServiceListActivity.this).getServiceId();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            service_id = bundle.getString("ID");
            service_name = bundle.getString("NAME");
            Log.d("url", service_id);
        }

        servicenameTv.setText(service_name);

        serviceListModels = new ArrayList<>();
        if (isNetworkAvailable()){
            getServiceList();
        }
        else {
            noNetwrokErrorMessage();
        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        servicelistRv.setLayoutManager(mLayoutManager);

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

    public void getServiceList(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = "https://ivrics.com/societyAPI/index.php/Services/SingleServices?id="+service_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                 //   Toast.makeText(ServiceListActivity.this, response, Toast.LENGTH_SHORT).show();
                    String status_str = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status_str.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                String id = "", name = "", serviceType = "", mobile="", image="", status="";

                                JSONObject o = array.getJSONObject(i);
                                id = o.getString("id");
                                name = o.getString("name");
                                serviceType = o.getString("serviceType");
                                mobile = o.getString("mobile");
                                image = o.getString("image");
                                status = o.getString("status");

                                ServiceListModel item = new ServiceListModel(
                                        id, name, serviceType, mobile, image, status);

                                serviceListModels.add(item);

                            }
                        }  else {
                            Toast.makeText(ServiceListActivity.this, "No Data Found",
                                    Toast.LENGTH_LONG).show();
                            servicelistRv.setVisibility(View.GONE);
                            nodataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    servicesListAdapter = new ServicesListAdapter(serviceListModels, ServiceListActivity.this);
                    servicelistRv.setAdapter(servicesListAdapter);

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization",token );
                return header;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(ServiceListActivity.this);
        requestQueue.add(stringRequest);
    }

}
