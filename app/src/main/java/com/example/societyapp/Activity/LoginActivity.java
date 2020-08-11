package com.example.societyapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {
    EditText phoneEd, passwordEd;
    Button loginBtn;
    String MobilePattern = "[0-9]{10}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String userName, userPhone, userPassword,token;
    TextView createaccTv, forgotpassTv;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    String errormsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneEd = findViewById(R.id.phoneEd);
        passwordEd = findViewById(R.id.passwordEd);
        loginBtn = findViewById(R.id.loginBtn);

        createaccTv = findViewById(R.id.createaccTv);
        forgotpassTv = findViewById(R.id.forgotpassTv);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userPhone = phoneEd.getText().toString().trim();
                userPassword = passwordEd.getText().toString().trim();

                if (userPhone.length() == 0 ) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    phoneEd.requestFocus();
                    phoneEd.setError("Please enter your valid phone number",customErrorDrawable);
                }

                else if (!phoneEd.getText().toString().matches(MobilePattern)){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    phoneEd.requestFocus();
                    phoneEd.setError("Please enter a valid phone number",customErrorDrawable);
                }
                else if (userPassword.length()<1 ) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    passwordEd.requestFocus();
                    passwordEd.setError("Please enter your password",customErrorDrawable);
                }
                else if (userPassword.length()< 6){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    passwordEd.requestFocus();
                    passwordEd.setError("Password field must be at least 6 characters", customErrorDrawable);
                }
                else {
                    if (isNetworkAvailable()){
                        logIn();
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
                }
            }
        });

        createaccTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        forgotpassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
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

    public void logIn(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = BASE_URL+"Userlogin/login?mobile="+userPhone+"&password="+userPassword;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    token =  jsonObject.getString("token");
                    RegPrefManager.getInstance(LoginActivity.this).setTokenAuth(token);

                    if(status.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String user_id = o.getString("id");
                                String user_mobile = o.getString("mobile");
                                String user_name = o.getString("name");
                                String user_address = o.getString("address");
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();

                                RegPrefManager.getInstance(LoginActivity.this).setUserId(user_id);
                                RegPrefManager.getInstance(LoginActivity.this).setUserMobile(user_mobile);
                                RegPrefManager.getInstance(LoginActivity.this).setUserName(user_name);
                                RegPrefManager.getInstance(LoginActivity.this).setUserAddress(user_address);

                            }
                        }  else {
                            JSONObject json_Object = new JSONObject(response);
                            errormsg = json_Object.getString("msg");
                            String status_failure = json_Object.getString("status");
                            if (status_failure.equals("failure")){
                                Toast.makeText(LoginActivity.this, errormsg, Toast.LENGTH_SHORT).show();
                            }

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
                params.put("mobile",userPhone );
                params.put("password", userPassword);

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
