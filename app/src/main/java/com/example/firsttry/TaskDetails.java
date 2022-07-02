package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Task;
import com.google.gson.Gson;

import java.io.File;

public class TaskDetails extends AppCompatActivity {
    TextView textView;
    TaskRoom taskRoom;
    TextView taskDescription;
    TextView taskStatus;
    Task task ;
File file;

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
    private void Fetch() {
        loadingProgress.startLoading();

        if (!file.exists()){
            Amplify.Storage.downloadFile(
                    "image"+taskId,
                    file,
                    result -> {
                        convertFileToBitMab(file);
                    },
                    error -> {
                        Toast.makeText(this, "No image for this task", Toast.LENGTH_SHORT).show();
                    }
            );
        }

        Amplify.API.query(
                ModelQuery.get(Task.class, taskId),
                response -> {
                    t = response.getData();
                    runOnUiThread(() -> {
                        taskDetails.setText(t.getBody());
                        taskDescription.setText("Stats : " + t.getState());
                        setSupportActionBar(t.getTitle());
                        if (file.exists()){
                            convertFileToBitMab(file);
                        }
                        loadingProgress.stopLoading();
                    });
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
        );





    }


    private void convertFileToBitMab(File file){
        if (file!=null){
            ImageView imageView =findViewById(R.id.image_view_task_details);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            imageView.setImageBitmap(bitmap);
        }


    }
}