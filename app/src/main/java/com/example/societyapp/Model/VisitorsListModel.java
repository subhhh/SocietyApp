package com.example.societyapp.Model;

public class VisitorsListModel {

    public VisitorsListModel(String id, String flatno, String visitorName, String visitorNumber, String visitorProfession, String visitorAddress, String vehicalType, String vehicalName, String vechicalNumber, String visitorReason, String visitorIntime, String visitorOuttime, String created, String createdBy) {
        this.id = id;
        this.flatno = flatno;
        this.visitorName = visitorName;
        this.visitorNumber = visitorNumber;
        this.visitorProfession = visitorProfession;
        this.visitorAddress = visitorAddress;
        this.vehicalType = vehicalType;
        this.vehicalName = vehicalName;
        this.vechicalNumber = vechicalNumber;
        this.visitorReason = visitorReason;
        this.visitorIntime = visitorIntime;
        this.visitorOuttime = visitorOuttime;
        this.created = created;
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorNumber() {
        return visitorNumber;
    }

    public void setVisitorNumber(String visitorNumber) {
        this.visitorNumber = visitorNumber;
    }

    public String getVisitorProfession() {
        return visitorProfession;
    }

    public void setVisitorProfession(String visitorProfession) {
        this.visitorProfession = visitorProfession;
    }

    public String getVisitorAddress() {
        return visitorAddress;
    }

    public void setVisitorAddress(String visitorAddress) {
        this.visitorAddress = visitorAddress;
    }

    public String getVehicalType() {
        return vehicalType;
    }

    public void setVehicalType(String vehicalType) {
        this.vehicalType = vehicalType;
    }

    public String getVehicalName() {
        return vehicalName;
    }

    public void setVehicalName(String vehicalName) {
        this.vehicalName = vehicalName;
    }

    public String getVechicalNumber() {
        return vechicalNumber;
    }

    public void setVechicalNumber(String vechicalNumber) {
        this.vechicalNumber = vechicalNumber;
    }

    public String getVisitorReason() {
        return visitorReason;
    }

    public void setVisitorReason(String visitorReason) {
        this.visitorReason = visitorReason;
    }

    public String getVisitorIntime() {
        return visitorIntime;
    }

    public void setVisitorIntime(String visitorIntime) {
        this.visitorIntime = visitorIntime;
    }

    public String getVisitorOuttime() {
        return visitorOuttime;
    }

    public void setVisitorOuttime(String visitorOuttime) {
        this.visitorOuttime = visitorOuttime;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    String id;
    String flatno;
    String visitorName;
    String visitorNumber;
    String visitorProfession;
    String visitorAddress;
    String vehicalType;
    String vehicalName;
    String vechicalNumber;
    String visitorReason;
    String visitorIntime;
    String visitorOuttime;
    String created;
    String createdBy;

}
