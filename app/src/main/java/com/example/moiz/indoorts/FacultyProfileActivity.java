package com.example.moiz.indoorts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyProfileActivity extends AppCompatActivity {

    private static final String TAG = "FacultyProfileActivity";
    public ImageView profileMenuBar;
    public CircleImageView profilePhoto;
    public TextView fullname, email, regid, phoneno, designation, ad, name;
    private static final int ACTIVITY_NUM = 4;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private Context mContext;
    private String regID;
    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.textfacpro);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
        mContext = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseMethods = new FirebaseMethods(FacultyProfileActivity.this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    regID = firebaseAuth.getCurrentUser().getUid();
                    Log.d(TAG, "signed in 2 " +firebaseAuth.getCurrentUser().getUid());

                }
                else{
                    Log.d(TAG, "Signed out");

                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setProfileWidgets(firebaseMethods.getFacultySettings(dataSnapshot));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initImageLoader();
        setupActivityWidgets();
        setupToolbar();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(FacultyProfileActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    public void setupToolbar(){
        profileMenuBar = (ImageView) findViewById(R.id.profilemenubar);
        profileMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccountSettingActivity();
            }
        });
    }

    private void setupActivityWidgets(){
        profilePhoto = (CircleImageView) findViewById(R.id.profilepic);
        fullname = (TextView) findViewById(R.id.fn);
        name = (TextView) findViewById(R.id.etname);
        email = (TextView) findViewById(R.id.em);
        regid = (TextView) findViewById(R.id.rid);
        phoneno = (TextView) findViewById(R.id.pn);
        designation = (TextView) findViewById(R.id.dsg);
        ad = (TextView) findViewById(R.id.add);
    }

    private void setProfileWidgets(FacultySettings facultySettings){
        Faculty faculty = facultySettings.getFaculty();
        FacultyAccountSettings settings = facultySettings.getSettings();

        UniversalImageLoader.setImage(faculty.getProfile_photo(), profilePhoto, null,"");
        UniversalImageLoader.setImage(settings.getProfile_photo(), profilePhoto, null,"");

        fullname.setText(settings.getFull_name());
        name.setText(settings.getFull_name());
        email.setText(settings.getEmail());
        regid.setText(faculty.getRegisteration_id());
        phoneno.setText(String.valueOf(settings.getPhone_number()));
        designation.setText(settings.getDesignation());
        ad.setText(settings.getAddress());

    }

    public void openAccountSettingActivity(){

        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, FacultyAccountSettingActivity.class);
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
