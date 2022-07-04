package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TaskDetails extends AppCompatActivity {
    TextView textView;
    TaskRoom taskRoom;
    TextView taskDescription;
    TextView taskStatus;
    Task task;
    Button transelate;
    Button voice;
    boolean translated = false;
    private final MediaPlayer mp = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        transelate = findViewById(R.id.transelate);
        textView = findViewById(R.id.titleTaskDetail);
        taskDescription = findViewById(R.id.descTaskDetails);
        taskStatus = findViewById(R.id.taskSts);
        voice = findViewById(R.id.voice);
        Intent intent = getIntent();
        Gson gson = new Gson();
        task = gson.fromJson(intent.getStringExtra("task"), Task.class
        );

        transelate.setOnClickListener(v -> {

            Amplify.Predictions.translateText(taskDescription.getText().toString(),
                    result -> {
                        translated = true;
                        runOnUiThread(() -> {
                            taskDescription.setText(result.getTranslatedText());
                        });
                    },
                    error -> Log.e("MyAmplifyApp", "Translation failed", error)
            );

        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Predictions.convertTextToSpeech(
                        taskDescription.getText().toString(),
                        result -> playAudio(result.getAudioData()),
                        error -> Log.e("MyAmplifyApp", "Conversion failed", error)
                );
            }
        });


        textView.setText(intent.getStringExtra("nameOfPage"));
//        taskRoom = AppDatabase.getInstance(getApplicationContext()).taskDao().getTaskById(Integer.valueOf(intent.getStringExtra("id")));
        textView.setText(task.getTitle());
        taskDescription.setText(task.getBody());
        taskStatus.setText(task.getStatus());


    }

    private void playAudio(InputStream data) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = data.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }
}