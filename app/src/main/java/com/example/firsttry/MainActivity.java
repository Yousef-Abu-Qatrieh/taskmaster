package com.example.firsttry;

import static android.content.ContentValues.TAG;

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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView changeTitle;
    private Button studyButton;
    private Button codeButton;
    private Button workoutButton;
    private ProgressBar progressBar;
    String teamId ="";
    List<TaskRoom> taskRoom =new ArrayList<>();
    List<Task> taskAws=new ArrayList<>();
    CustomRecyclerView customRecyclerView;
    SharedPreferences sharedPreferences;
    //lap 28
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseActivity();
        configureAmplify();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        teamId=sharedPreferences.getString("teamId",null);
        getTeameName();
        // get the recycler view object
        //lap29

        List<TaskRoom> addTaskRoom =AppDatabase.getInstance(this).taskDao().getAll();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress_main);


        // create an adapter
//        CustomRecyclerView customRecyclerView = new CustomRecyclerView(task,position -> {
//            Toast.makeText(
//                    MainActivity.this,
//                    "The item clicked => " + task.get(position).getBody(), Toast.LENGTH_SHORT).show();
//
//
//            startActivity(new Intent(getApplicationContext(), TaskDetails.class));

        customRecyclerView = new CustomRecyclerView(taskAws, new CustomRecyclerView.CustomClickListener() {
            @Override
            public void onWeatherItemClicked(int position) {
                Gson gson  = new Gson();

                Intent taskDetailActivity = new Intent(getApplicationContext() , TaskDetails.class);
                taskDetailActivity.putExtra("task" ,gson.toJson(taskAws.get(position)));
                startActivity(taskDetailActivity);

            }
        } );


        // set adapter on recycler view
        recyclerView.setAdapter(customRecyclerView);

        // set other important properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // lap 29
        Button addbtn=findViewById(R.id.addBtn);
        addbtn.setOnClickListener((v)->{
            Intent intent=new Intent(getApplicationContext(),AddTask.class);


            startActivity(intent);
        });





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

    private void getTeameName() {
        teamId= sharedPreferences.getString("teamId","teamName");
    }

    private void initialiseActivity() {
        taskRoom.add(new TaskRoom("Task 1", "Climbing", "new"));
        taskRoom.add(new TaskRoom("Task 2", "Diving", "assigned"));
        taskRoom.add(new TaskRoom("Task 3", "Cleaning", "in progress"));
        taskRoom.add(new TaskRoom("Task 4", "Cooking", "in progress"));
        taskRoom.add(new TaskRoom("Task 5", "Swimming", "complete"));
        taskRoom.add(new TaskRoom("Task 6", "Studying", "complete"));
        taskRoom.add(new TaskRoom("Task 7", "Dancing", "new"));
        taskRoom.add(new TaskRoom("Task 8", "Bowling", "new"));

    }


    @Override
    protected void onStart() {

        super.onStart();
        Log.i(TAG, "onStart: Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        fetchData();

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
    public void setUsername(){

        changeTitle.setText(sharedPreferences.getString("username" ,"My") + " TasksList");
    }
    public void navigateToSettings(){
        Intent goSettingIntent = new Intent(this ,SettingPage.class);
        startActivity(goSettingIntent);
    }
    private void configureAmplify() {
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e(TAG, "Could not initialize Amplify", e);
        }
    }
    public void fetchData(){
        taskAws.clear();
        Amplify.API.query(ModelQuery.list(Task.class,Task.TEAM_TASK_ID.eq(teamId)),res->{
            System.out.println(teamId+"sdaadsasdasd");
            if (res.hasData()){
                for (Task t:res.getData()){
                    taskAws.add(t);
                }

                runOnUiThread(()->{
                    customRecyclerView.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                });
            }
        },err->{

        });
    }
}