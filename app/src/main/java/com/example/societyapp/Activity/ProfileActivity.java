package com.example.societyapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.societyapp.R;
import com.example.societyapp.Response.UploadImageResponse;
import com.example.societyapp.Utils.ApiClient;
import com.example.societyapp.Utils.ApiInterface;
import com.example.societyapp.Utils.MultipartRequest;
import com.example.societyapp.Utils.RegPrefManager;
import com.example.societyapp.Utils.VolleyMultipartRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.societyapp.Utils.ApiClient.BASE_URL;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView editprofCv, changepassCv, membersCv, staffCv, vehicleCv;
    TextView takepicTv,uploadpicTv;
    ImageView userimage;
    ApiInterface apiService;
    private AlertDialog.Builder alertDialog;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_PHOTO = 1958;
    Boolean userImage;
    String userid,token;
    File file;
    private String imagefilePath="";
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri imageUri,imageUri1;
    private static final int REQUEST_PICK_IMAGE = 1002;
    private Bitmap bmp;
    private String encodedImage;
    String UserFilePath;
    private Bitmap bitmap;
    private static final String ROOT_URL = "https://ivrics.com/societyAPI/index.php/Userlogin/ProfileImageUpload";
    TextView username,addressname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editprofCv=findViewById(R.id.editprofCv);
        changepassCv=findViewById(R.id.changepassCv);
        membersCv=findViewById(R.id.membersCv);
        staffCv=findViewById(R.id.staffCv);
        vehicleCv=findViewById(R.id.vehicleCv);
        userimage=findViewById(R.id.userimage);
        takepicTv=findViewById(R.id.takepicTv);
        uploadpicTv=findViewById(R.id.uploadpicTv);
        username=findViewById(R.id.username);
        addressname=findViewById(R.id.addressname);

        userid= RegPrefManager.getInstance(this).getUserId();
        token= RegPrefManager.getInstance(this).getTokenAuth();

        username.setText(RegPrefManager.getInstance(this).getUserName());
        addressname.setText(RegPrefManager.getInstance(this).getUserAddress());
      /*  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ivrics.com/societyAPI/index.php/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiService = retrofit.create(ApiInterface.class);
*/
        apiService =
                ApiClient.getClient().create(ApiInterface.class);

        alertDialog = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);


        editprofCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
            }
        });
        changepassCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
            }
        });
        changepassCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
            }
        });
        membersCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MembersActivity.class));
            }
        });
        staffCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MyStaffsActivity.class));
            }
        });
        vehicleCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MyVehicleActivity.class));
            }
        });

        takepicTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();
                userImage = true;
                final CharSequence[] options_array = { "Camera","Gallery"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Choose");
                builder.setItems(options_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options_array[item].equals("Camera")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        } else if (options_array[item].equals("Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_PHOTO);
                        }
                    }
                });
                builder.show();
            }
        });

        getProfile();
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


    /*Gson gson = new GsonBuilder() .setLenient() .create();*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO) {

            imageUri = data.getData();
            imagefilePath = getPath(imageUri);

            if(userImage==true){
                imageUri1=imageUri;
                UserFilePath=imagefilePath;
                userimage.setImageURI(imageUri);
                file = new File(UserFilePath);
                takepicTv.setVisibility(View.GONE);
                uploadpicTv.setVisibility(View.VISIBLE);
                uploadpicTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isNetworkAvailable()){
                            upload();
                        }
                        else {
                            noNetwrokErrorMessage();
                        }
                    }
                });
            }
        }
        else if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST)
        {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            userimage.setImageBitmap(bitmap);

            imageUri= getImageUri(this,bitmap);
            imagefilePath = getPath(imageUri);


            if (userImage == true) {
                imageUri1 = imageUri;
                UserFilePath = imagefilePath;
                userimage.setImageBitmap(bitmap);
                file = new File(UserFilePath);
                takepicTv.setVisibility(View.GONE);
                uploadpicTv.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };//,Video,Audio
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Video
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void requestStoragePermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            return ;

        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {

        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void getProfile(){

        progressDialog.show();
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        String url = BASE_URL+"Userlogin/profile?id="+userid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                  //  Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("msg");

                    if(status.equals("success") && jsonObject!=null) {
                        //   JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                String userId = o.getString("id");
                                String userNumber = o.getString("mobile");
                                String userName = o.getString("name");
                                String userAddress = o.getString("address");
                                String userImage = "https://ivrics.com/App/assets/backend_panel/images/profile/large/"+o.getString("image");
                                username.setText(userName);
                                addressname.setText(userAddress);
                                Picasso.with(ProfileActivity.this).load(userImage).into(userimage);
                                // = o.getString("address");
                                RegPrefManager.getInstance(ProfileActivity.this).setUserId(userId);
                                RegPrefManager.getInstance(ProfileActivity.this).setUserMobile(userNumber);
                                RegPrefManager.getInstance(ProfileActivity.this).setUserName(userName);

                               /* RegPrefManager.getInstance(LoginActivity.this).setUserId(user_id);
                                RegPrefManager.getInstance(LoginActivity.this).setUserMobile(user_mobile);
                                RegPrefManager.getInstance(LoginActivity.this).setUserName(user_name);
                                RegPrefManager.getInstance(LoginActivity.this).setUserAddress(user_address);*/

                            }
                        }  else {
                            // JSONObject json_Object = new JSONObject(response);
                            //  errormsg = json_Object.getString("msg");
                            Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
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



  public void upload (){
      progressDialog.show();

      progressDialog.setMessage("Loading...");

      progressDialog.setCancelable(false);

      RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);

      // Add request params excluding files below

      HashMap<String, String> params = new HashMap<>();

      params.put("id", userid);

      //Add files to a request

      final HashMap<String, File> fileParams = new HashMap<>();

      fileParams.put("profile_img", file);

      // Add header to a request, if any
      Map<String, String> header = new HashMap<>();

      header.put("Authorization",  token);

/**

 * Create a new Multipart request for sending data in form of Map<String,String > ,along with files

 */

      MultipartRequest mMultipartRequest = new MultipartRequest(ROOT_URL,

              new Response.ErrorListener() {

                  @Override

                  public void onErrorResponse(final VolleyError error) {

                      // error handling
                  }

              },

              new Response.Listener<String>() {

                  @Override

                  public void onResponse(String response) {

                      try {

                          progressDialog.dismiss();

                          JSONObject jsonObject = new JSONObject(response);
                          Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                          String status = jsonObject.getString("status");
                          String message = jsonObject.getString("msg");

                          if (jsonObject.getString("status").equals("success")) {
                              Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                          }
                          else {

                              Toast.makeText(ProfileActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                          }

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }

              }, fileParams, params

      );

      mMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000,

              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
/**
 * adding request to queue
 */
      requestQueue.add(mMultipartRequest);
  }
}


/* progressDialog.show();
      progressDialog.setMessage("Loading...");

      VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ROOT_URL,
              new Response.Listener<NetworkResponse>() {
                  @Override
                  public void onResponse(NetworkResponse response) {
                      progressDialog.dismiss();
                      try {
                          JSONObject jsonObject = new JSONObject(new String(response.data));
                          String status = jsonObject.getString("status");
                          String message = jsonObject.getString("msg");
                          if (status.equals("success")){
                              Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                              uploadpicTv.setVisibility(View.GONE);
                              takepicTv.setVisibility(View.VISIBLE);
                          }
                          else {
                              Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                          }

                      } catch (JSONException e) {
                          progressDialog.dismiss();
                          e.printStackTrace();
                      }
                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      progressDialog.dismiss();
                      Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                      Log.e("GotError",""+error.getMessage());
                  }
              }) {

          @Override
          protected Map<String, DataPart> getByteData() {
              Map<String, DataPart> params = new HashMap<>();
              long imagename = System.currentTimeMillis();
              params.put("profile_img", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
              return params;
          }

          @Override
          public Map<String, String> getHeaders() {
              Map<String, String> params = new HashMap<>();
              params.put("Authorization", token);
              return params;
          }
          @Override
          protected Map<String, String> getParams() {
              Map<String, String> params = new HashMap<>();
              params.put("id", userid);
              return params;
          }


      };

      //adding the request to volley
      Volley.newRequestQueue(this).add(volleyMultipartRequest);
  }*/


/*/*
    private void Upload(){

        File file=new File (getRealPathFromURI(imageUri1));

        RequestBody mFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("uploaded_file", file.getName(), mFile);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody authorisation = RequestBody.create(MediaType.parse("text/plain"), token);
        progressDialog.show();
        progressDialog.setMessage("Uploading ...");
        Call<UploadImageResponse> call=apiService.getUpdateImageResponse(userId,authorisation,fileToUpload);
        call.enqueue(new Callback<UploadImageResponse>() {
            @Override
            public void onResponse(Call<UploadImageResponse> call, Response<UploadImageResponse> response) {
                progressDialog.dismiss();
                String successId = response.body().getStatus();
                String message=response.body().getMsg();
                if(successId.equals("success")){
                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                    uploadpicTv.setVisibility(View.GONE);
                    takepicTv.setVisibility(View.VISIBLE);
                }
                else {
                    String errorMessage = response.body().getMsg();
                    Toast.makeText(getApplicationContext(),errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UploadImageResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "Some thing went wrong",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
*/