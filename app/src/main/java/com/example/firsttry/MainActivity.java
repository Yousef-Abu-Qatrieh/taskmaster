package com.example.firsttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView changeTitle;
    private Button studyButton;
    private Button codeButton;
    private Button workoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // lab 26
//        Button addAllTask= findViewById(R.id.allTasksButton);
//        addAllTask.setOnClickListener(view -> {
//            Intent allTaskPage = new Intent(this, AllTasks.class);
//            startActivity(allTaskPage);
//        });
//        Button addTask= findViewById(R.id.addSingleTask);
//        addTask.setOnClickListener(view -> {
//            Intent allTaskPage = new Intent(this, AddTask.class);
//            startActivity(allTaskPage);
//        });
        changeTitle=findViewById(R.id.myTaskTitle);
        studyButton=findViewById(R.id.buttonTask1);
        codeButton=findViewById(R.id.buttonTask2);
        workoutButton=findViewById(R.id.buttonTask3);
        navigateToTaskDetailPage();

    }


    @Override
    protected void onStart() {

        super.onStart();
        Log.i(TAG, "onStart: Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Called-The App Is Visible");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: Called");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void navigateToTaskDetailPage(){
        studyButton.setOnClickListener(view -> {
            Intent goToTaskPage=new Intent(this,TaskDetailsPage.class);
            startActivity(goToTaskPage);
        });
        codeButton.setOnClickListener(view -> {
            Intent goToTaskPage=new Intent(this,TaskDetailsPage.class);
            startActivity(goToTaskPage);
        });
        workoutButton.setOnClickListener(view -> {
            Intent goToTaskPage=new Intent(this,TaskDetailsPage.class);
            startActivity(goToTaskPage);
        });
    }
    public void navigateToSettings(){
        Intent goSettingIntent = new Intent(this ,SettingPage.class);
        startActivity(goSettingIntent);
    }
}