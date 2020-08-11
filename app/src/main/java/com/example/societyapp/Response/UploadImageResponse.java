package com.example.societyapp.Response;

import com.google.gson.annotations.SerializedName;

public class UploadImageResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;
}
