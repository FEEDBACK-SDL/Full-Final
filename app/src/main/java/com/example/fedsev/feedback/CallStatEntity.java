package com.example.fedsev.feedback;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CallStatEntity {

    private String name;

    @PrimaryKey
    private int serviceid;

    private int number; //change to long if error occurs

    private String time;

    private String date1;

    public String getName() {
        return name;
    }

    public int getServiceid() {
        return serviceid;
    }

    public int getNumber() {
        return number;
    }

    public String getTime() {
        return time;
    }

    public String getDate1() {
        return date1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}
