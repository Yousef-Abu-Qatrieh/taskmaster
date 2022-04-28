package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_page);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        textView=findViewById(R.id.titleTaskDetail);
        Intent intent=getIntent();
        textView.setText(intent.getStringExtra("nameOfPage"));

    }
}