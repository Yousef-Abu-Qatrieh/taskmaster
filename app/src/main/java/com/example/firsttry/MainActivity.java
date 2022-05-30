package com.example.firsttry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView changeTitle;
    private Button studyButton;
    private Button codeButton;
    private Button workoutButton;
List<Task> task=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseActivity();
        // get the recycler view object
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // create an adapter
        CustomRecyclerView customRecyclerView = new CustomRecyclerView(task,position -> {
            Toast.makeText(
                    MainActivity.this,
                    "The item clicked => " + task.get(position).getBody(), Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), TaskDetails.class));
        });

        // set adapter on recycler view
        recyclerView.setAdapter(customRecyclerView);

        // set other important properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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
      //  lab27
//        changeTitle=findViewById(R.id.myTaskTitle);
//        studyButton=findViewById(R.id.buttonTask1);
//        codeButton=findViewById(R.id.buttonTask2);
//        workoutButton=findViewById(R.id.buttonTask3);
//        navigateToTaskDetailPage();

    }
    private void initialiseActivity() {
        task.add(new Task("Task 1", "Climbing", "new"));
        task.add(new Task("Task 2", "Diving", "assigned"));
        task.add(new Task("Task 3", "Cleaning", "in progress"));
        task.add(new Task("Task 4", "Cooking", "in progress"));
        task.add(new Task("Task 5", "Swimming", "complete"));
        task.add(new Task("Task 6", "Studying", "complete"));
        task.add(new Task("Task 7", "Dancing", "new"));
        task.add(new Task("Task 8", "Bowling", "new"));

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
//        setUsername();
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
// lab27
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_settings:
//                navigateToSettings();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//    public void navigateToTaskDetailPage(){
//        studyButton.setOnClickListener(view -> {
//            Intent studyTaskPage=new Intent(this, TaskDetails.class);
//            studyTaskPage.putExtra("nameOfPage" , studyButton.getText().toString());
//            startActivity(studyTaskPage);
//        });
//        codeButton.setOnClickListener(view -> {
//            Intent codeTaskPage=new Intent(this, TaskDetails.class);
//            codeTaskPage.putExtra("nameOfPage" , codeButton.getText().toString());
//            startActivity(codeTaskPage);
//        });
//        workoutButton.setOnClickListener(view -> {
//            Intent workoutTaskPage=new Intent(this, TaskDetails.class);
//            workoutTaskPage.putExtra("nameOfPage" , workoutButton.getText().toString());
//            startActivity(workoutTaskPage);
//        });
//
//    }
//    public void setUsername(){
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        changeTitle.setText(sharedPreferences.getString("username" ,"My") + " TasksList");
//    }
//    public void navigateToSettings(){
//        Intent goSettingIntent = new Intent(this ,SettingPage.class);
//        startActivity(goSettingIntent);
//    }
}