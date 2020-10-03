package com.example.moiz.indoorts;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyStatusActivity extends AppCompatActivity {

    private RadioGroup rg1;
    private RadioButton b1, b2, b3, b4, b5, b6, b7;
    private EditText t1;
    private Button btnsave;
    private MyStatusSettings mMyStatusSettings;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;

    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.textfacstatus);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseMethods = new FirebaseMethods(getApplicationContext());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setProfileWidgets(firebaseMethods.getStatusSettings(dataSnapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        b1 = (RadioButton) findViewById(R.id.radBtn1);
        b2 = (RadioButton) findViewById(R.id.radBtn2);
        b3 = (RadioButton) findViewById(R.id.radBtn3);
        b4 = (RadioButton) findViewById(R.id.radBtn4);
        b5 = (RadioButton) findViewById(R.id.radBtn5);
        b6 = (RadioButton) findViewById(R.id.radBtn6);
        b7 = (RadioButton) findViewById(R.id.radBtn7);
        t1 = (EditText) findViewById(R.id.et1);
        btnsave = (Button) findViewById(R.id.btn_save);
        t1.setVisibility(View.GONE);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.radBtn1:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn2:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn3:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn4:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn5:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn6:
                        t1.setVisibility(View.GONE);
                        t1.setEnabled(false);
                        t1.setInputType(InputType.TYPE_NULL);
                        t1.setFocusableInTouchMode(false);
                        break;

                    case R.id.radBtn7:
                        t1.setVisibility(View.VISIBLE);
                        t1.setEnabled(true);
                        t1.setInputType(InputType.TYPE_CLASS_TEXT);
                        t1.setFocusableInTouchMode(true);
                        break;
                }
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetwork()){
                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    saveStatusSettings();
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

    private void setProfileWidgets(MyStatusSettings statusSettings){
        mMyStatusSettings = statusSettings;
        MyStatus myStatus = statusSettings.getMyStatus();
        t1.setText(myStatus.getStatus());
    }

    private void saveStatusSettings() {
        final String status = t1.getText().toString();
        final String status1 = b1.getText().toString();
        final String status2 = b2.getText().toString();
        final String status3 = b3.getText().toString();
        final String status4 = b4.getText().toString();
        final String status5 = b5.getText().toString();
        final String status6 = b6.getText().toString();

        if(!mMyStatusSettings.getMyStatus().getStatus().equals(status)){
            firebaseMethods.updateMyStatus(status);
            b7.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b1.isChecked()){
            firebaseMethods.updateMyStatus(status1);
            b1.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b2.isChecked()){
            firebaseMethods.updateMyStatus(status2);
            b2.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b3.isChecked()){
            firebaseMethods.updateMyStatus(status3);
            b3.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b4.isChecked()){
            firebaseMethods.updateMyStatus(status4);
            b4.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b5.isChecked()){
            firebaseMethods.updateMyStatus(status5);
            b5.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(b6.isChecked()){
            firebaseMethods.updateMyStatus(status6);
            b6.setChecked(true);
            finish();
            Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
        }
        else if(!b1.isChecked() || !b2.isChecked() || !b3.isChecked() || !b4.isChecked() || !b5.isChecked()
                || !b6.isChecked()){
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            StatusDialog emailDialog = new StatusDialog();
            emailDialog.show(getSupportFragmentManager(),"status dialog");
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
        }
    }

}
