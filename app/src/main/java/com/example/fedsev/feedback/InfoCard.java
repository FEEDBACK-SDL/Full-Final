package com.example.fedsev.feedback;

import java.time.LocalDate;

public class InfoCard {

    private String name;
    private int serviceid;
    private int number; //change to long if error occurs
    private String time;
    private String date1;
    private LocalDate d;

    public InfoCard(String name, int serviceid, int number, String time, String date1) {
        this.name = name;
        this.serviceid = serviceid;
        this.number = number;
        this.time = time;
        this.date1 = date1;
        d = LocalDate.parse(date1);
    }

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


}
