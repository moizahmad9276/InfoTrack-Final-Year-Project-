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
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {

    private static final String TAG = "StudentLoginActivity";
    private EditText rid, p1;
    private Button fpass, login, signup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private Context mContext;
    private String regID;
    ProgressBar progressBar;
    TextView tvview;
    Animation fromBottom;
    private LinearLayout layouthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        layouthome = (LinearLayout) findViewById(R.id.textstdlogin);
        layouthome.startAnimation(fromBottom);

        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        addListenerOnButton();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseMethods = new FirebaseMethods(getApplicationContext());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    regID = mAuth.getCurrentUser().getUid();
                    Log.d(TAG, "signed in" +firebaseAuth.getCurrentUser().getUid());

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Log.d(TAG, "Signed out");
                }
            }
        };
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

    public void addListenerOnButton(){
        rid = (EditText) findViewById(R.id.reg_id);
        p1 = (EditText) findViewById(R.id.p_word);
        fpass = (Button) findViewById(R.id.f_pass);
        login = (Button) findViewById(R.id.btn_login);
        signup = (Button) findViewById(R.id.s_up);

        fpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                openForgotPasswordActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String regid = rid.getText().toString();
                final String password = p1.getText().toString();

                if (regid.isEmpty()) {
                    rid.setError("Please enter your Email");
                    rid.requestFocus();
                } else if (password.isEmpty()) {
                    p1.setError("Please enter your Password");
                    p1.requestFocus();
                }
                else
                {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        mAuth.signInWithEmailAndPassword(regid, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if(!task.isSuccessful()){
                                            if(!haveNetwork()){
                                                Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                openDialog();
                                            }
                                        }
                                        else{
                                            try{
                                                if(user.isEmailVerified()){
                                                    openStudentDashboardActivity();
                                                }
                                                else{
                                                    if(!haveNetwork()){
                                                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                                    }
                                                    else{
                                                        EmailDialog emailDialog = new EmailDialog();
                                                        emailDialog.show(getSupportFragmentManager(),"email dialog");
                                                        mAuth.signOut();
                                                        progressBar.setVisibility(View.GONE);
                                                        tvview.setVisibility(View.GONE);
                                                    }

                                                }

                                            }
                                            catch (NullPointerException e){
                                            }
                                        }
                                    }
                                });
                    }
            }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupActivity();
            }
        });
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
    }

    public void openForgotPasswordActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
        }
    }

    public void openStudentDashboardActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.GONE);
            tvview.setVisibility(View.GONE);
            finish();
        }
    }

    public void openSignupActivity(){
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            Intent intent = new Intent(this, SignupActivity.class);
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
