package com.example.societyapp.Model;

public class ServiceListModel {

    public ServiceListModel(String id, String name, String serviceType, String mobile, String image, String status) {
        this.id = id;
        this.name = name;
        this.serviceType = serviceType;
        this.mobile = mobile;
        this.image = image;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String id;
    String name;
    String serviceType;
    String mobile;
    String image;
    String status;
}
