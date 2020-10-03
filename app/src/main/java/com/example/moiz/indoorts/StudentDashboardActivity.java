package com.example.moiz.indoorts;

import android.bluetooth.BluetoothAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hotchemi.android.rate.AppRate;

public class StudentDashboardActivity extends AppCompatActivity {

    BluetoothAdapter myBluetoothAdapter;
    private ImageView service, myprofile, logout, status, indoormap, about, help, contact, services, team, rate, tour;
    Intent btEnableIntent;
    int requestForEnable;
    private static final String TAG = "StudentDashbordActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    ProgressBar progressBar;
    TextView tvview;
    Animation fromBottom;
    private LinearLayout layouthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        layouthome = (LinearLayout) findViewById(R.id.textstddash);
        layouthome.startAnimation(fromBottom);

        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        service = (ImageView) findViewById(R.id.bt);
        logout = (ImageView) findViewById(R.id.lo);
        status = (ImageView) findViewById(R.id.st);
        myprofile = (ImageView) findViewById(R.id.mp);
        indoormap = (ImageView) findViewById(R.id.um);
        about = (ImageView) findViewById(R.id.au);
        help = (ImageView) findViewById(R.id.hc);
        contact = (ImageView) findViewById(R.id.cu);
        services = (ImageView) findViewById(R.id.tc);
        team = (ImageView) findViewById(R.id.te);
        rate = (ImageView) findViewById(R.id.rt);
        tour = (ImageView) findViewById(R.id.ta);

        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btEnableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        requestForEnable = 1;

        btconnect();
        openProfile();
        openTheirStatus();
        openFindLocation();
        openAbout();
        openHelpCenter();
        openContactUs();
        openService();
        openOurTeam();
        openRateOurApp();
        openTakeATour();
        loggingout();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){

                    Log.d(TAG, "signed in" +firebaseAuth.getCurrentUser().getUid());
                }
                else{
                    Log.d(TAG, "Signed out");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==requestForEnable)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(getApplicationContext(),"Bluetooth Enabled", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(), "Cannot Enable Bluetooth", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btconnect(){
       service.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(myBluetoothAdapter==null)
               {
                   Toast.makeText(getApplicationContext(), "Bluetooth does not support on this device.",Toast.LENGTH_LONG).show();
               }
                else if(myBluetoothAdapter.isEnabled())
               {
                   myBluetoothAdapter.disable();
               }
               else
               {
                   if(!myBluetoothAdapter.isEnabled())
                   {
                        startActivityForResult(btEnableIntent, requestForEnable);
                   }
               }
           }
       });
    }

    public void openTheirStatus(){
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTheirStatusActivity();
            }
        });
    }

    private void openTheirStatusActivity() {
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, TheirStatusActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openProfile(){
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfileActivity();
            }
        });
    }

    public void openMyProfileActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, StudentProfileActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void loggingout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogoutActivity();
            }
        });
    }

    public void openLogoutActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            StudentLogoutDialog logoutDialog = new StudentLogoutDialog();
            logoutDialog.show(getSupportFragmentManager(),"student dialog");
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }
    }

    public void openFindLocation(){
        indoormap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFindLocationActivity();
            }
        });
    }

    private void openFindLocationActivity() {
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, FindLocationActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openAbout(){
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutActivity();
            }
        });
    }

    public void openAboutActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openHelpCenter(){
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHelpCenterActivity();
            }
        });
    }

    public void openHelpCenterActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, HelpCenterActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openContactUs(){
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactUsActivity();
            }
        });
    }

    public void openContactUsActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, ContactUsActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openService(){
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity();
            }
        });
    }

    public void openServiceActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, ServicesActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }

    }

    public void openOurTeam(){
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOurTeamActivity();
            }
        });
    }

    public void openOurTeamActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, OurTeamActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }
    }

    public void openRateOurApp(){
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRateOurAppActivity();
            }
        });
    }

    public void openRateOurAppActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            AppRate.with(this).showRateDialog(this);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }
    }

    public void openTakeATour(){
        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTakeATourActivity();
            }
        });
    }

    public void openTakeATourActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, TakeATourActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }
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
}
