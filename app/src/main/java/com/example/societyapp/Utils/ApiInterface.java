package com.example.societyapp.Utils;




import com.example.societyapp.Response.UploadImageResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by RatnaDev008 on 11/5/2018.
 */

public interface ApiInterface {

  @Multipart
  @POST("Userlogin/ProfileImageUpload")
  Call<UploadImageResponse>getUpdateImageResponse(@Part("id") RequestBody id, @Part("Authorization") RequestBody Authorization,
                                               @Part MultipartBody.Part uploaded_file);

   /* @POST("api/user/register")
    @FormUrlEncoded
    Call<RegisterResponse>postRegister(@Field("first_name") String first_name, @Field("phone") String phone,
                                       @Field("email") String email, @Field("password") String password);

   @POST("api/user/login")
   @FormUrlEncoded
   Call<LoginResponse>postLogin(@Field("phone") String phone, @Field("user_pwd") String user_pwd);

   @POST("api/user/forgot_password")
   @FormUrlEncoded
   Call<ForgotPasswordResponse>postForgotPassResponse(@Field("phone") String user_email);

   @POST("api/user/forgot_password_verify_code")
   @FormUrlEncoded
   Call<VerifyPasscodeResponse>postPasscodeResponse(@Field("user_email") String user_email, @Field("passcode") String passcode);*/
  }
