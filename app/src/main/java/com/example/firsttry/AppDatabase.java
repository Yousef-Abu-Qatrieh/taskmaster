package com.example.firsttry;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskRoom.class},version = 1)
public abstract class  AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public static AppDatabase appDatabase;

    public AppDatabase() {
    }
    public static synchronized AppDatabase getInstance(Context context){
        if (appDatabase==null){
            appDatabase= Room.databaseBuilder(context,AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
