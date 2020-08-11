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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.societyapp.Utils.ApiClient.BASE_URL;

public class NewComplaintsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText subjectEd, complaindescEd;
    TextView textdialogTv;
    Button submitBtn;
    String subjectStr, complainStr;
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    String user_id;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaints);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        subjectEd = findViewById(R.id.subjectEd);
        complaindescEd = findViewById(R.id.complaindescEd);
        textdialogTv = findViewById(R.id.textdialogTv);
        submitBtn = findViewById(R.id.submitBtn);

        textdialogTv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowAlertDialogWithListview();
                    }
                }
        );

        user_id = RegPrefManager.getInstance(this).getUserId();
        token = RegPrefManager.getInstance(this).getTokenAuth();

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subjectStr = subjectEd.getText().toString().trim();
                complainStr = complaindescEd.getText().toString().trim();

                if (subjectStr.length()<1){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    subjectEd.requestFocus();
                    subjectEd.setError("Please enter your subject",customErrorDrawable);
                }
                else if (complainStr.length()<1){
                    Drawable customErrorDrawable = getResources().getDrawable(R.drawable.error_2);
                    customErrorDrawable.setBounds(5, 5,
                            customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());
                    complaindescEd.requestFocus();
                    complaindescEd.setError("Please enter your complain",customErrorDrawable);
                }
                else {
                    if (isNetworkAvailable()){
                        addComplain();
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

    public void ShowAlertDialogWithListview()
    {
        List<String> categories = new ArrayList<String>();
        categories.add("Plumber");
        categories.add("Electrician");
        categories.add("Civil");
        categories.add("Housekeeping");
        categories.add("Security");
        categories.add("Carpenter");
        categories.add("Others");
        //Create sequence of items
        final CharSequence[] Animals = categories.toArray(new String[categories.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Categories");
        dialogBuilder.setItems(Animals, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedText = Animals[item].toString();  //Selected item in listview
                textdialogTv.setText(selectedText);
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }

    public void addComplain(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");

        String url = BASE_URL+"Complaint/addComplaint?uid="+user_id+"&subject="+subjectStr+"complaint="+complainStr;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NewComplaintsActivity.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status.equals("success")){
                        Toast.makeText(NewComplaintsActivity.this, message ,Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(NewComplaintsActivity.this,MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(NewComplaintsActivity.this,"oops!"+message,Toast.LENGTH_SHORT).show();
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
               // params.put("mobile",userPhone );
              //  params.put("password", userPassword);
                return params;
            }
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}