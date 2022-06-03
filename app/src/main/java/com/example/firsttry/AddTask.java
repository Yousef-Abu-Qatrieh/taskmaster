package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {
int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        EditText editText1=findViewById(R.id.TaskTitle);
        EditText editText2=findViewById(R.id.taskDescription);
        EditText editText3=findViewById(R.id.editTextStat);
        Button button=findViewById(R.id.submitButtonTask);
//      TextView textView=findViewById(R.id.total_Tasks);
        button.setOnClickListener(view -> {
            editText1.getText().toString();
            editText2.getText().toString();
            editText3.getText().toString();
//          textView.setText("Total Task :"+(++counter));
            Task task=new Task(editText1.getText().toString(),editText2.getText().toString(),editText3.getText().toString());
            Long newTask = AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
            startActivity(new Intent(getApplicationContext() , MainActivity.class));



        });
    }
}