package com.example.fedsev.feedback;

public class DataFromServer {
    int id;
    String fname;
    String lname;
    String vno;
    String phone;
    int service_id;
    String D_Service;
    String N_Service;
    int kms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getVno() {
        return vno;
    }

    public void setVno(String vno) {
        this.vno = vno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getD_Service() {
        return D_Service;
    }

    public void setD_Service(String d_Service) {
        D_Service = d_Service;
    }

    public String getN_Service() {
        return N_Service;
    }

    public void setN_Service(String n_Service) {
        N_Service = n_Service;
    }

    public int getKms() {
        return kms;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }
}
