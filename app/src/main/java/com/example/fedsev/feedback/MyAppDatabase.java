package com.example.fedsev.feedback;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CallStatEntity.class ,  SyncData.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract MyDao myDao();
}
