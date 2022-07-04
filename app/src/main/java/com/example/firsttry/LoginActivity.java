package com.example.firsttry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_activtity);
        configureAwsAmplify();
        functionalityForm();

    }

    private void functionalityForm() {
        EditText email = findViewById(R.id.edit_text_email);
        EditText password =findViewById(R.id.edit_text_password);
        SwitchCompat rememberMe =findViewById(R.id.remember_me);
        ProgressBar progressBar =findViewById(R.id.progress_bar_Login);
        Button login = findViewById(R.id.btn_login);
        TextView haveAccount = findViewById(R.id.text_view_dont_account);

        login.setOnClickListener(v->{
            System.out.println(email.getText().toString());
            System.out.println(password.getText().toString());




            if (validation(email,password)){
                progressBar.setVisibility(View.VISIBLE);
                Amplify.Auth.signIn(
                        email.getText().toString(),
                        password.getText().toString(),
                        result -> {
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        },
                        error -> {
                            Snackbar snackbar =  Snackbar.
                                    make(findViewById(R.id.Login_layout),"Incorrect Email or password",Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                );

            }
        });

        haveAccount.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(),SignUpPage.class));
            finish();
        });

    }

    private boolean validation(EditText email, EditText password) {
        if (email.length()<6){
            email.setError("Please Enter valid email ");
            return false;
        }else if (password.length()<6){
            password.setError("Password must have at least 6 character");
            return false;
        }
        return true;
    }

    /// sum stuff to configure amplify
    void configureAwsAmplify() {
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());


            Amplify.addPlugin(new AWSPredictionsPlugin());

            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(getApplication()));
            Amplify.configure(getApplicationContext());

        } catch (AmplifyException e) {
//            Log.e("TAG", "Could not initialize Amplify", e);
        }

    }

}