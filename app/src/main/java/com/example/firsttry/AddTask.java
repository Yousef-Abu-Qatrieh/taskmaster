package com.example.firsttry;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.IOException;

public class AddTask extends AppCompatActivity {
    int counter = 0;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences ;
    String teamId= "";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        EditText editText1 = findViewById(R.id.TaskTitle);
        EditText editText2 = findViewById(R.id.taskDescription);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        teamId=sharedPreferences.getString("teamId",null);

        progressBar = findViewById(R.id.progress_task);
        //lap 31
        Spinner state = findViewById(R.id.stateSpinner);
//        lap29
//        EditText editText3=findViewById(R.id.editTextStat);
        Button button = findViewById(R.id.submitButtonTask);
        imageView = findViewById(R.id.image);
//      TextView textView=findViewById(R.id.total_Tasks);
        button.setOnClickListener(view -> {
//            editText1.getText().toString();
//            editText2.getText().toString();
//            state.getSelectedItem().toString();
//            lap29/30
//            editText3.getText().toString();
//          textView.setText("Total Task :"+(++counter));
//            TaskRoom taskRoom = new TaskRoom(editText1.getText().toString(), editText2.getText().toString(), state.getSelectedItem().toString());
//            Long newTask = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(taskRoom);
            progressBar.setVisibility(View.VISIBLE);
            saveData(state.getSelectedItem().toString(), editText1.getText().toString(), editText2.getText().toString());

            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        });
        try {
            getIntentInflate();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveData(String status, String title, String description) {
        Task task = Task.builder()
                .title(title)
                .body(description)
                .status(status)
                .teamTaskId(teamId)
                .build();
        Amplify.API.query(ModelMutation.create(task), res -> {

runOnUiThread(new Runnable() {
    @Override
    public void run() {
        progressBar.setVisibility(View.INVISIBLE);

        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
});
        }, err -> {
            System.out.println(err.toString()+"faild to add task");
        });
    }

    private void getIntentInflate() throws IOException {
        Intent intent = getIntent();
        Uri data = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        System.out.println(data);
if (intent.getType()!=null&&  intent.getType().contains("image/") && data != null){
    System.out.println(data.getPath());
    Uri iamgeUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);

    imageView.setImageURI(iamgeUri);
}



        }

    }




