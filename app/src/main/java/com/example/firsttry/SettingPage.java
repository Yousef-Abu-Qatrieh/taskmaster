package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

public class SettingPage extends AppCompatActivity {
    private static final String USERNAME="username";
private EditText mUsername;
   private Button mSaveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        mUsername=findViewById(R.id.username);
        mSaveButton=findViewById(R.id.btn_save);
        mSaveButton.setOnClickListener(view -> {
            saveUserName();
        });
    }
    public void saveUserName() {
        String username = mUsername.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor  preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString(USERNAME,username);
        preferencesEditor.apply();


    }
}