package com.example.societyapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class EditProfileActivity extends AppCompatActivity {
  Toolbar toolbar;
  EditText nameEd, phoneEd, addressEd;
  String userid, username, usermobile,token, address;
  ProgressBar simpleProgressBar;
  Button updateBtn;
  private AlertDialog.Builder alertDialog;
  ProgressDialog progressDialog;
  ApiInterface apiService;
  String MobilePattern = "[0-9]{10}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nameEd = findViewById(R.id.nameEd);
        phoneEd = findViewById(R.id.phoneEd);
        addressEd = findViewById(R.id.addressEd);
        updateBtn = findViewById(R.id.updateBtn);
     //   simpleProgressBar = findViewById(R.id.simpleProgressBar);
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        username = RegPrefManager.getInstance(this).getUserName();
        usermobile = RegPrefManager.getInstance(this).getUserMobile();
        token = RegPrefManager.getInstance(this).getTokenAuth();
        userid = RegPrefManager.getInstance(this).getUserId();
        address = RegPrefManager.getInstance(this).getUserAddress();

        nameEd.setText(RegPrefManager.getInstance(this).getUserName());
        phoneEd.setText(RegPrefManager.getInstance(this).getUserMobile());
        addressEd.setText(RegPrefManager.getInstance(this).getUserAddress());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = nameEd.getText().toString().trim();
                usermobile = phoneEd.getText().toString().trim();
                address = addressEd.getText().toString().trim();

                if (username.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    // Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    nameEd.requestFocus();
                    nameEd.setError("Please enter your name", customErrorDrawable);
                } else if (usermobile.length() == 0) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    phoneEd.requestFocus();
                    phoneEd.setError("Please enter your phone number", customErrorDrawable);

                } else if (!phoneEd.getText().toString().matches(MobilePattern)) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    phoneEd.requestFocus();
                    phoneEd.setError("Please enter a valid phone number", customErrorDrawable);

                } else if (address.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    // Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    addressEd.requestFocus();
                    addressEd.setError("Please enter your address", customErrorDrawable);
                }
                else {
                  if (isNetworkAvailable()){
                    update();
                }
                else {
                    noNetwrokErrorMessage();
                }
                }
            }
        });
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

    public void update(){
        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = BASE_URL+"Userlogin/ChangePhoneNumber?id="+userid+"name="+username+"mobile="+usermobile;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status.equals("success")) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                       if (status.equals("success")){
                           Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
                           finish();
                        }  else {
                           Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
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
           //     params.put("id",userid );
           //     params.put("name", username);
            //    params.put("mobile", usermobile);
                return params;
            }
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization",token );
                return header;
            }*/
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