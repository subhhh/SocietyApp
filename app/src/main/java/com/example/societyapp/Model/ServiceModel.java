package com.example.societyapp.Model;

public class ServiceModel {

    public ServiceModel(String id, String serviceName, String logo, String status) {
        this.id = id;
        this.serviceName = serviceName;
        this.logo = logo;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String id;
    String serviceName;
    String logo;
    String status;
}
