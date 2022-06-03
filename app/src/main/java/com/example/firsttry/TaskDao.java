package com.example.firsttry;


import android.util.Log;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT*FROM Task")
    List<Task> getAll();

    @Query("SELECT*FROM TASK WHERE id=:id")
    Task getTaskById(long id );

    @Insert
    Long insertTask(Task task);
}
