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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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
import com.example.societyapp.Adapters.ServicesListAdapter;
import com.example.societyapp.Adapters.VisitorListAdapter;
import com.example.societyapp.Model.ServiceListModel;
import com.example.societyapp.Model.VisitorsListModel;
import com.example.societyapp.R;
import com.example.societyapp.Utils.ApiClient;
import com.example.societyapp.Utils.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitorListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView visitorlistRv;
    VisitorListAdapter visitorListAdapter;
    String[] orderId = {"1", "2", "3", "4", "5", "6"};
    String[] foodName = {"1", "2"};
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    int i=0;
    private List<VisitorsListModel> visitorsListModels;
    TextView nodataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        visitorlistRv = (RecyclerView) findViewById(R.id.visitorlistRv);
        nodataTv = (TextView) findViewById(R.id.nodataTv);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        visitorsListModels = new ArrayList<>();
        if (isNetworkAvailable()){
            getVisitorList();
        }
        else {
            noNetwrokErrorMessage();
        }

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        visitorlistRv.setLayoutManager(mLayoutManager);
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

    public void getVisitorList(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = "https://ivrics.com/societyAPI/index.php/Visitor/VisitorList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                //    Toast.makeText(ServiceListActivity.this, response, Toast.LENGTH_SHORT).show();
                    String status_str = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status_str.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                String id = "", flatno = "", visitorName = "", visitorNumber="", visitorProfession="", visitorAddress="",
                                        vehicalType="", vehicalName ="", vechicalNumber="", visitorReason="", visitorIntime="",visitorOuttime="",
                                        created="", createdBy="";

                                JSONObject o = array.getJSONObject(i);
                                id = o.getString("id");
                                flatno = o.getString("flatno");
                                visitorName = o.getString("visitorName");
                                visitorNumber = o.getString("visitorNumber");
                                visitorProfession = o.getString("visitorProfession");
                                visitorAddress = o.getString("visitorAddress");
                                vehicalType = o.getString("vehicalType");
                                vehicalName = o.getString("vehicalName");
                                vechicalNumber = o.getString("vechicalNumber");
                                visitorReason = o.getString("visitorReason");
                                visitorIntime = o.getString("visitorIntime");
                                visitorOuttime = o.getString("visitorOuttime");
                                created = o.getString("created");
                                createdBy = o.getString("createdBy");

                                VisitorsListModel item = new VisitorsListModel(
                                        id, flatno, visitorName, visitorNumber, visitorProfession, visitorAddress,vehicalType,vehicalName, vechicalNumber,
                                        visitorReason,visitorIntime,visitorOuttime,created,createdBy);

                                visitorsListModels.add(item);

                            }
                        }  else {
                            Toast.makeText(VisitorListActivity.this, "No Data Found",
                                    Toast.LENGTH_LONG).show();
                            visitorlistRv.setVisibility(View.GONE);
                            nodataTv.setVisibility(View.VISIBLE);
                        }
                    }

                    visitorListAdapter = new VisitorListAdapter(visitorsListModels, VisitorListActivity.this);
                    visitorlistRv.setAdapter(visitorListAdapter);

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
       //         header.put("Authorization",token );
                return header;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        RequestQueue requestQueue = Volley.newRequestQueue(VisitorListActivity.this);
        requestQueue.add(stringRequest);
    }

}
