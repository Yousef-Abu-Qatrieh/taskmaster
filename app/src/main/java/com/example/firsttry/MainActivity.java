package com.example.firsttry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addAllTask= findViewById(R.id.allTasksButton);
        addAllTask.setOnClickListener(view -> {
            Intent allTaskPage = new Intent(this, AllTasks.class);
            startActivity(allTaskPage);
        });
        Button addTask= findViewById(R.id.addSingleTask);
        addTask.setOnClickListener(view -> {
            Intent allTaskPage = new Intent(this, AddTask.class);
            startActivity(allTaskPage);
        });

    }



    @Override
    protected  void onStart() {

        super.onStart();
        Log.i(TAG,"onStart: Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume: Called-The App Is Visible");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop: Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy: Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart: Called");
    }
}