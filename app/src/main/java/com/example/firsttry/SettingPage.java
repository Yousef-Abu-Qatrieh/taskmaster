package com.example.firsttry;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

public class SettingPage extends AppCompatActivity {
    private static final String USERNAME="username";
    private EditText mUsername;
    private Button mSaveButton;
    Spinner spinner;
    String teamId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("TaskMaster");
        spinner=findViewById(R.id.team_sp)
        ;        mUsername=findViewById(R.id.username);
        mSaveButton=findViewById(R.id.btn_save);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                saveUserName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSaveButton.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor  preferencesEditor = sharedPreferences.edit();
            preferencesEditor.putString("teamId",teamId);
            preferencesEditor.apply();
            new AlertDialog.Builder(this).
                    setMessage("Success")
                    .setTitle("Change Team Success")
                    .setPositiveButton("Ok", (dialog, witch) -> {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }).show();

            System.out.println(teamId+"test++++++++++");
        });
    }
    public void saveUserName() {
        String username = mUsername.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor  preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString(USERNAME,username);
        String teamname = spinner.getSelectedItem().toString();
        preferencesEditor.putString("teamName",teamname);
        preferencesEditor.apply();
        saveTeamId();



    }

    private void saveTeamId() {


        Amplify.API.query(ModelQuery.list(Team.class,Team.NAME.eq(spinner.getSelectedItem().toString())),res->{


            for(Team t :res.getData()){
                if (res.hasData()) {
                    teamId=t.getId();
                }
            }


        },err->{

        });


    }
}