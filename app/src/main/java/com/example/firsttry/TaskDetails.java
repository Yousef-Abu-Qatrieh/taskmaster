package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Task;
import com.google.gson.Gson;

public class TaskDetails extends AppCompatActivity {
    TextView textView;
    TaskRoom taskRoom;
    TextView taskDescription;
    TextView taskStatus;
    Task task ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.titleTaskDetail);
        taskDescription = findViewById(R.id.descTaskDetails);
        taskStatus = findViewById(R.id.taskSts);
        Intent intent = getIntent();
Gson gson = new Gson();
task=gson.fromJson(intent.getStringExtra("task"),Task.class
);


        textView.setText(intent.getStringExtra("nameOfPage"));
//        taskRoom = AppDatabase.getInstance(getApplicationContext()).taskDao().getTaskById(Integer.valueOf(intent.getStringExtra("id")));
        textView.setText(task.getTitle());
        taskDescription.setText(task.getBody());
        taskStatus.setText(task.getStatus());


    }
}