package com.example.societyapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.societyapp.Utils.ApiClient.BASE_URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNav;
    Toolbar toolbar;
    MenuItem menu_home;
    FrameLayout container;
    FragmentManager myFragmentManager;
    CardView visitorlist, complaintslist, maintenancelist, facilitieslist, serviceslist, parkinglist,
            noticelist, paymentlist, cctvfootage;
    String userId, userName, userNumber, token;
    ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    TextView username;
    ImageView image;
    ProgressBar my_progress_bar;
    String imagepath ="https://ivrics.com/App/assets/backend_panel/images/profile/large/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        container = (FrameLayout) findViewById(R.id.maincontainer);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        userId = RegPrefManager.getInstance(this).getUserId();
        token = RegPrefManager.getInstance(this).getTokenAuth();

        my_progress_bar= findViewById(R.id.my_progress_bar);
        visitorlist=findViewById(R.id.visitorlist);
        complaintslist=findViewById(R.id.complaintslist);
        facilitieslist=findViewById(R.id.facilitieslist);
        serviceslist=findViewById(R.id.serviceslist);
        parkinglist=findViewById(R.id.parkinglist);
        noticelist=findViewById(R.id.noticelist);
        paymentlist=findViewById(R.id.paymentlist);
        cctvfootage=findViewById(R.id.cctvfootage);
        getProfile();

        visitorlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,VisitorListActivity.class));
            }
        });

        complaintslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ComplaintsActivity.class));
            }
        });

        facilitieslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EventsActivity.class));
            }
        });

        serviceslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ServicesActivity.class));
            }
        });

        parkinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ParkingActivity.class));
            }
        });

        noticelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NoticeActivity.class));
            }
        });

        paymentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PaymentsActivity.class));
            }
        });

        cctvfootage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraRecordActvity.class));
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(drawer.isDrawerOpen(GravityCompat.START))) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        /*userName = RegPrefManager.getInstance(this).getUserName();
        userNumber = RegPrefManager.getInstance(this).getUserMobile();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.username);
        image = headerView.findViewById(R.id.image);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setCheckable(false);
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }


    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.menuHome:
             /*   Intent intent_ordr = new Intent(HomeActivity.this,GroceryProductActivity.class);
                startActivity(intent_ordr);*/
                break;

            case R.id.menuProfile:
                Intent intent_ordr = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent_ordr);
                break;

            case R.id.bookings:
                Intent booking = new Intent(MainActivity.this,BookingsActivity.class);
                startActivity(booking);
                break;

            case R.id.menuLogout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Log Out");

                alertDialogBuilder
                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                              //  RegPrefManager.getInstance(MainActivity.this).logout();
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
        }
        
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.maincontainer, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
        this.finish();
    }


    public void getProfile(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = BASE_URL+"Userlogin/profile?id="+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                userId = o.getString("id");
                                userNumber = o.getString("mobile");
                                userName = o.getString("name");
                                String userAddress = o.getString("address");
                                String userImage = "https://ivrics.com/App/assets/backend_panel/images/profile/large/"+o.getString("image");
                                username.setText(userName);
                                Picasso.with(MainActivity.this).load(userImage).into(image);
                                // = o.getString("address");
                                RegPrefManager.getInstance(MainActivity.this).setUserId(userId);
                                RegPrefManager.getInstance(MainActivity.this).setUserMobile(userNumber);
                                RegPrefManager.getInstance(MainActivity.this).setUserName(userName);



                               /* RegPrefManager.getInstance(LoginActivity.this).setUserId(user_id);
                                RegPrefManager.getInstance(LoginActivity.this).setUserMobile(user_mobile);
                                RegPrefManager.getInstance(LoginActivity.this).setUserName(user_name);
                                RegPrefManager.getInstance(LoginActivity.this).setUserAddress(user_address);*/

                            }
                        }  else {
                           // JSONObject json_Object = new JSONObject(response);
                          //  errormsg = json_Object.getString("msg");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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
           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile",userPhone );
                params.put("password", userPassword);

                return params;
            }*/

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