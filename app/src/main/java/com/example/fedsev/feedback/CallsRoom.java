package com.example.fedsev.feedback;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//entity represents a table in room database. By default class name is table name.
@Entity
public class CallsRoom
{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name="PhoneNo") //explicitly mention column name in table
    private String PhoneNo;

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNo()
    {
        return PhoneNo;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.PhoneNo = phoneNo;
    }
}
