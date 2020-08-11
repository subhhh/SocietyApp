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
import android.view.LayoutInflater;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.societyapp.Utils.ApiClient.BASE_URL;

public class SignupActivity extends AppCompatActivity {
    EditText nameEd, phoneEd, addressEd, passwordEd, confirmpasswordEd;
    Button signupBtn;
    String MobilePattern = "[0-9]{10}";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String userName, userPhone, userAddress,  userPassword, userConfirmPassword;
    TextView doctor, patient;
    AlertDialog dialog;
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //  DialogShow();
        nameEd = findViewById(R.id.nameEd);
        phoneEd = findViewById(R.id.phoneEd);
        addressEd = findViewById(R.id.addressEd);
        passwordEd = findViewById(R.id.passwordEd);
        confirmpasswordEd = findViewById(R.id.confirmpasswordEd);
        signupBtn = findViewById(R.id.signupBtn);
        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = nameEd.getText().toString().trim();
                userPhone = phoneEd.getText().toString().trim();
                userAddress = addressEd.getText().toString().trim();
                userPassword = passwordEd.getText().toString().trim();
                userConfirmPassword = confirmpasswordEd.getText().toString().trim();

                if (userName.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    // Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    nameEd.requestFocus();
                    nameEd.setError("Please enter your name", customErrorDrawable);
                } else if (userPhone.length() == 0) {
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
                } else if (userAddress.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    // Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    addressEd.requestFocus();
                    addressEd.setError("Please enter your address", customErrorDrawable);

                } else if (userPassword.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    passwordEd.requestFocus();
                    passwordEd.setError("Please enter your password", customErrorDrawable);
                }
                else if (userPassword.length()< 6){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    passwordEd.requestFocus();
                    passwordEd.setError("Password field must be at least 6 characters", customErrorDrawable);
                }
                else if (userConfirmPassword.length() < 1) {
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    confirmpasswordEd.requestFocus();
                    confirmpasswordEd.setError("Please enter your password again", customErrorDrawable);
                }
                else if (userConfirmPassword.length()<6){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    confirmpasswordEd.requestFocus();
                    confirmpasswordEd.setError("Password field must be at least 6 characters", customErrorDrawable);
                }
                else if (!userPassword.equals(userConfirmPassword) ) {
                    Toast.makeText(SignupActivity.this, "Passwords are not matched", Toast.LENGTH_SHORT).show();
                }  else{
                    if (isNetworkAvailable()){
                        signUp();
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

        public void signUp(){

            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);

            String url = BASE_URL+"Userlogin/Signup?mobile="+userPhone+"&name="+userName+"&password="+userPassword+
                    "&cpassword="+userConfirmPassword+"&address="+userAddress;
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");

                        if(status.equals("success")){
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            String message = jsonObject.getString("msg");
                            String name = jsonObject1.getString("name");
                            String phoneNumber = jsonObject1.getString("mobile");
                            String address = jsonObject1.getString("address");
                            Toast.makeText(SignupActivity.this, message ,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            finish();
                        }
                        else {
                            String errormessage =  jsonObject.getString("mobile");
                            Toast.makeText(SignupActivity.this,"oops!"+errormessage,Toast.LENGTH_SHORT).show();
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
                    params.put("name", userName);
                    params.put("password", userPassword);
                    params.put("cpassword", userConfirmPassword);
                    params.put("address", userAddress);
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

   /* private void DialogShow(){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.usertypedialog, null);
        doctor = alertLayout.findViewById(R.id.doctor);
        patient = alertLayout.findViewById(R.id.patient);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Verify");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        doctor.setOnClickListener(new View.OnClickListener() {
            String docStr = doctor.getText().toString();
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "You selected doctor", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
              //  RegPrefManager.getInstance(SignupActivity.this).setDoctor(docStr);

            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            String patStr = patient.getText().toString();
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "You selected patient", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
               // RegPrefManager.getInstance(SignupActivity.this).setPatient(patStr);
            }
        });


        dialog = alert.create();
        dialog.show();
    }
*/


