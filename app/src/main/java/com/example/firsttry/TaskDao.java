package com.example.firsttry;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT*FROM TaskRoom")
    List<TaskRoom> getAll();

    @Query("SELECT*FROM TaskRoom WHERE id=:id")
    TaskRoom getTaskById(long id );

    @Insert
    Long insertTask(TaskRoom taskRoom);
}
