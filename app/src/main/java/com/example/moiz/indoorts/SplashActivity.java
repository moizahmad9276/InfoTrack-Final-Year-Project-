package com.example.moiz.indoorts;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import hotchemi.android.rate.AppRate;

public class SplashActivity extends AppCompatActivity {

    private int SLEEP_TIMER = 2;
    private ImageView iv, bgimage;
    private TextView tv;
    private LinearLayout layouttext, layouthome;
    private static final String TAG = "SplashActivity";
    Animation fromBottom;
    private Button student, faculty;
    ProgressBar progressBar;
    TextView tvview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
      //  AppRate.with(this).clearAgreeShowDialog();

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        layouttext = (LinearLayout) findViewById(R.id.textsplash);
        layouthome = (LinearLayout) findViewById(R.id.texthome);
        faculty = (Button) findViewById(R.id.btnfaculty);
        student = (Button) findViewById(R.id.btnstudent);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);

        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        layouttext.animate().translationY(140).alpha(0).setDuration(500).setStartDelay(2000);
        layouthome.startAnimation(fromBottom);


        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetwork()){
                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), FacultyLoginActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);
                }


            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetwork()){
                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(getApplicationContext(), StudentLoginActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);
                }
            }
        });
    }
    public boolean haveNetwork(){
        boolean haveWifi = false;
        boolean haveData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info : networkInfos){
            if(info.getTypeName().equalsIgnoreCase("Wifi"))
                if(info.isConnected())
                    haveWifi = true;
            if(info.getTypeName().equalsIgnoreCase("Mobile"))
                if(info.isConnected())
                    haveData = true;
        }
        return haveData || haveWifi;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyApplication app = (MyApplication) getApplication();
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else if (!app.isBeaconNotificationsEnabled()) {
            Log.d(TAG, "Enabling beacon notifications");
            app.enableBeaconNotifications();

        }
    }
}
