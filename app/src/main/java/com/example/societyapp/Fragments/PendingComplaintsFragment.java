package com.example.societyapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.societyapp.Activity.ServiceListActivity;
import com.example.societyapp.Adapters.PendingComplaintAdapter;
import com.example.societyapp.Adapters.ServicesListAdapter;
import com.example.societyapp.Model.PendingListModel;
import com.example.societyapp.Model.ServiceListModel;
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

public class PendingComplaintsFragment extends Fragment {
    RecyclerView pendingcomplainRv;
    PendingComplaintAdapter pendingComplaintAdapter;
    String[] orderId = {"1", "2", "3", "4"};
    String[] foodName = {"1", "2"};
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    int i=0;
    private List<PendingListModel> pendingListModels;
    String user_id, user_name, user_phone, token;
    TextView nodataTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pending_complaint, container, false);
        pendingcomplainRv=rootView.findViewById(R.id.pendingcomplainRv);
        nodataTv=rootView.findViewById(R.id.nodataTv);

        alertDialog = new AlertDialog.Builder(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        user_id = RegPrefManager.getInstance(getActivity()).getUserId();
        token = RegPrefManager.getInstance(getActivity()).getTokenAuth();

        pendingListModels = new ArrayList<>();

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        pendingcomplainRv.setLayoutManager(mLayoutManager);

        if (isNetworkAvailable()){
            getPendingList();
        }
        else {
            noNetwrokErrorMessage();
        }

        return rootView;
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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


    public void getPendingList(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = "https://ivrics.com/societyAPI/index.php/Complaint/PendingComplaint?id="+user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    String status_str = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status_str.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                String cid = "", uid = "", subject = "", complaint="", createdat="", name="", flatno="";

                                JSONObject o = array.getJSONObject(i);
                                cid = o.getString("cid");
                                uid = o.getString("uid");
                                subject = o.getString("subject");
                                complaint = o.getString("complaint");
                                createdat = o.getString("createdat");
                                name = o.getString("name");
                                flatno = o.getString("flatno");

                                PendingListModel item = new PendingListModel(
                                        cid, uid, subject, complaint, createdat, name,flatno);

                                pendingListModels.add(item);

                            }
                        }  else {
                            Toast.makeText(getActivity(), "No Data Found",
                                    Toast.LENGTH_LONG).show();
                            pendingcomplainRv.setVisibility(View.GONE);
                            nodataTv.setVisibility(View.VISIBLE);
                            // no_orders_text.setVisibility(View.VISIBLE);
                        }
                    }

                    pendingComplaintAdapter = new PendingComplaintAdapter(pendingListModels, getActivity());
                    pendingcomplainRv.setAdapter(pendingComplaintAdapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
