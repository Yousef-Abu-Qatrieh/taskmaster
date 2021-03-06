package com.example.firsttry;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.analytics.pinpoint.AWSPinpointAnalyticsPlugin;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.predictions.aws.AWSPredictionsPlugin;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView changeTitle;
    private Button studyButton;
    private Button codeButton;
    private Button workoutButton;
    private Button initilize;
    private Button reward;
    private RewardedAd mRewardedAd;

    private ProgressBar progressBar;
    String teamId ="";
    List<TaskRoom> taskRoom =new ArrayList<>();
    List<Task> taskAws=new ArrayList<>();
    CustomRecyclerView customRecyclerView;
    SharedPreferences sharedPreferences;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    int countRewardCoin = 0;

    //lap 28
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseActivity();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        teamId=sharedPreferences.getString("teamId",null);
        getTeameName();
        recordAnalistic();
        initilize=findViewById(R.id.initialisAdd);
        reward=findViewById(R.id.RewardAdd);
        // get the recycler view object
        //lap29
initilize.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        InterstitialAds();
    }
});

reward.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        RewardedAds();
    }
});
        List<TaskRoom> addTaskRoom =AppDatabase.getInstance(this).taskDao().getAll();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        addGoogleInit();
        bannerAdInit();
    }

    private void recordAnalistic() {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("openMainActivity")

                .addProperty("IsOpen", true)

                .build();


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
    private void addGoogleInit(){
        MobileAds.initialize(this, initializationStatus -> {
        });
    }


    @SuppressLint("MissingPermission")
    private void bannerAdInit(){


        AdView  mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
    }
    private void InterstitialAds(){




        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(MainActivity.this);
                        } else {

                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


    }

    private void RewardedAds (){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;

                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });

                        /**
                         * show the ads
                         */

                        if (mRewardedAd != null) {
                            Activity activityContext = MainActivity.this;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    Log.d(TAG, "The user earned the reward.");
                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();
                                    countRewardCoin++;
                                    TextView rewardCount =findViewById(R.id.text_view_reward);
                                    rewardCount.setText("You Have Earned : " +rewardType + " " + countRewardCoin);
                                    System.out.println("reward"+rewardType + "" +rewardAmount);
                                }
                            });
                        } else {
                            Log.d(TAG, "The rewarded ad wasn't ready yet.");
                        }
                    }
                });
    }
}