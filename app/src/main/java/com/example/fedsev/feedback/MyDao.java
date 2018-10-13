package com.example.fedsev.feedback;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao
{

    @Insert
    public void addrecord(CallStatEntity c1);

    @Insert
    public void addSyncData(SyncData syncData);

    @Insert
    public void addCalls(CallsRoom call);

    @Query("select * from CallsRoom")
    public List<CallsRoom>  getCalls();

    @Query("select * from SyncData")
    public List<SyncData> syncData();

    @Query("Select * from CallStatEntity")
    public List<CallStatEntity> getrecords();


}
