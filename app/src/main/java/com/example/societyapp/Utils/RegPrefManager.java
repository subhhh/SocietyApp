package com.example.societyapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class RegPrefManager  {

    private SharedPreferences mSharedPreferences;
    private static RegPrefManager mPrefManager;
    public Context mContext;

    private RegPrefManager(Context context) {
        this.mContext=context;
        mSharedPreferences = context.
                getSharedPreferences("ivricsapp", Context.MODE_PRIVATE);
    }

    public static RegPrefManager getInstance(Context context) {
        if (mPrefManager == null) {
            mPrefManager = new RegPrefManager(context);
        }
        return mPrefManager;
    }

    public void setUserId(String userId){
        mSharedPreferences.edit().putString("userId",userId).apply();
    }
    public String getUserId(){
        return mSharedPreferences.getString("userId",null);
    }

    public void setUserMobile(String usermobile){
        mSharedPreferences.edit().putString("userMobile",usermobile).apply();
    }
    public String getUserMobile(){
        return mSharedPreferences.getString("userMobile",null);
    }

    public void setUserName(String username){
        mSharedPreferences.edit().putString("username",username).apply();
    }
    public String getUserName(){
        return mSharedPreferences.getString("username",null);
    }

    public void setUserAddress(String userAddress){
        mSharedPreferences.edit().putString("userAddress",userAddress).apply();
    }
    public String getUserAddress(){
        return mSharedPreferences.getString("userAddress",null);
    }

    public void setTokenAuth(String token){
        mSharedPreferences.edit().putString("token",token).apply();
    }
    public String getTokenAuth(){
        return mSharedPreferences.getString("token",null);
    }

    public void setServiceId(String service_id){
        mSharedPreferences.edit().putString("service_id",service_id).apply();
    }
    public String getServiceId(){
        return mSharedPreferences.getString("service_id",null);
    }
}
