package com.example.firsttry;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import com.google.android.gms.cast.framework.media.ImagePicker;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AddTask extends AppCompatActivity {
    int counter = 0;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences ;
    String teamId= "";

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
        Button addImage =findViewById(R.id.btn_add_image);
        addImage.setOnClickListener(v->{
            imagePicker();


            progressBar = findViewById(R.id.progress_task);
        //lap 31
        Spinner state = findViewById(R.id.stateSpinner);
//        lap29
//        EditText editText3=findViewById(R.id.editTextStat);
        Button button = findViewById(R.id.submitButtonTask);
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



    private void AddDataStorageAws(com.amplifyframework.datastore.generated.model.Task task) {
        Amplify.API.query(ModelMutation.create(task), success -> {
            progress.stopLoading();
            finish();
        }, error -> {

        });
    }
    void getTaskForEdit(Spinner spinner, Button addTask, EditText taskTitleField, EditText taskDescriptionInput, Button saveButton){
        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
        /// get the task
        Amplify.API.query(ModelQuery.get(com.amplifyframework.datastore.generated.model.Task.class, taskId
                ), res -> {
                    if (res.hasData()) {
                        taskFromAws = res.getData();
                        // set on click for save


                        runOnUiThread(() -> {
                            /// set text dor input
                            taskTitleField.setText(res.getData().getTitle());
                            taskDescriptionInput.setText(res.getData().getBody());
                            /// set visibility for button
                            saveButton.setVisibility(View.VISIBLE);
                            addTask.setVisibility(View.INVISIBLE);
                            TextView title = findViewById(R.id.text_view_add_task);
                            title.setText("Edit Task");
                            setSupportActionBar("Edit Task");

                        });

                    }
                }
                , err -> {
                });

    }

    private void imagePicker(){
        ImagePicker.with(this)
                .compress(1024)			//Final image size will be less than 1 MB
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri =data.getData();
        ImageView imageView =findViewById(R.id.view_add_image);

        imageView.setImageURI(uri);
        imageForUpload=convertUri(uri);

    }

    private Bitmap convertUri(Uri uri){
        Bitmap bitmap = null;
        ContentResolver contentResolver = getContentResolver();
        try {
            if(Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            } else {
                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, uri);
                bitmap = ImageDecoder.decodeBitmap(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private File convertToFile() throws IOException {
        File file = new File(getApplicationContext().getFilesDir(), "image.jpg");
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        imageForUpload.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.close();
        return file;
    }

    private void uploadImageAws(com.amplifyframework.datastore.generated.model.Task task) throws IOException {
        if (imageForUpload!=null){
            Amplify.Storage.uploadFile(
                    "image"+task.getId(),
                    convertToFile(),
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }
    }
        private void getIntentInflate() throws IOException {
            Intent intent = getIntent();
            Uri data = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (intent.getType()!=null&&  intent.getType().contains("image/") && data != null) {
                configureAwsAmplify();
                Uri iamgeUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                imageView.setImageURI(iamgeUri);
                imageForUpload = convertUriToBimap(iamgeUri);
                convertToFile();


            }

        }


        /// sum stuff to configure amplify
        void configureAwsAmplify() {
            try {
                Amplify.addPlugin(new AWSS3StoragePlugin());

                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.addPlugin(new AWSApiPlugin());
                Amplify.addPlugin(new AWSDataStorePlugin());
                Amplify.configure(getApplicationContext());

            } catch (AmplifyException e) {
//            Log.e("TAG", "Could not initialize Amplify", e);
            }



        }
}