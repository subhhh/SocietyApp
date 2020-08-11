package com.example.societyapp.Model;

public class PendingListModel {

    public PendingListModel(String cid, String uid, String subject, String complaint, String createdat, String name, String flatno) {
        this.cid = cid;
        this.uid = uid;
        this.subject = subject;
        this.complaint = complaint;
        this.createdat = createdat;
        this.name = name;
        this.flatno = flatno;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    String cid;
    String uid;
    String subject;
    String complaint;
    String createdat;
    String name;
    String flatno;
}
