package com.example.moiz.indoorts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText regid, fname, pass, cpass, em;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2;
    private Button sup, already;
    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private Context mContext;
    private String regID;
    private String append = "";
    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.textsignup);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
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
                            checkIfRegisterationIdExists(regid.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    finish();
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
//        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkIfRegisterationIdExists(final String registerationID) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        if(radioButton2.isChecked()){
            Query query = reference
                    .child(getString(R.string.dbname_students))
                    .orderByChild(getString(R.string.field_registerationID))
                    .equalTo(registerationID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                        if(singleSnapshot.exists()){
                            progressBar.setVisibility(View.VISIBLE);
                            tvview.setVisibility(View.VISIBLE);
                            ExistsDialog existsDialog = new ExistsDialog();
                            existsDialog.show(getSupportFragmentManager(),"exists dialog");
                            mAuth.signOut();
                            progressBar.setVisibility(View.GONE);
                            tvview.setVisibility(View.GONE);
                        }
                    }
                    firebaseMethods.addNewStudent(em.getText().toString(),"", "","","",fname.getText().toString(),1,regid.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    VerificationDialog verificationDialog = new VerificationDialog();
                    verificationDialog.show(getSupportFragmentManager(),"verification dialog");
                    mAuth.signOut();
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(radioButton1.isChecked()){
            Query query = reference
                    .child(getString(R.string.dbname_faculty))
                    .orderByChild(getString(R.string.field_registerationID))
                    .equalTo(registerationID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "TESTING " +dataSnapshot.toString());

                    for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                        if(singleSnapshot.exists()){
                            progressBar.setVisibility(View.VISIBLE);
                            tvview.setVisibility(View.VISIBLE);
                            ExistsDialog existsDialog = new ExistsDialog();
                            existsDialog.show(getSupportFragmentManager(),"exists dialog");
                            mAuth.signOut();
                            progressBar.setVisibility(View.GONE);
                            tvview.setVisibility(View.GONE);
                        }
                    }

                    firebaseMethods.addNewFaculty(em.getText().toString(),"", "","","",fname.getText().toString(),1,regid.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    tvview.setVisibility(View.VISIBLE);
                    VerificationDialog verificationDialog = new VerificationDialog();
                    verificationDialog.show(getSupportFragmentManager(),"verification dialog");
                    mAuth.signOut();
                    progressBar.setVisibility(View.GONE);
                    tvview.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                openDialog();
                            }
                        }
                    });
        }
    }

    public void addListenerOnButton(){

        regid = (EditText) findViewById(R.id.reg_id);
        fname = (EditText) findViewById(R.id.f_name);
        pass = (EditText) findViewById(R.id.p_word);
        cpass = (EditText) findViewById(R.id.c_pass);
        em = (EditText) findViewById(R.id.e_mail);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        sup = (Button) findViewById(R.id.btn_signup);
        already = (Button) findViewById(R.id.btn_alreadymember);
        radioButton1 = (RadioButton) findViewById(R.id.radBtn1);
        radioButton2 = (RadioButton) findViewById(R.id.radBtn2);

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rid = regid.getText().toString();
                final String fullname = fname.getText().toString();
                final String pword = pass.getText().toString();
                final String cpword = cpass.getText().toString();
                final String email = em.getText().toString();
                int radioID = radioGroup.getCheckedRadioButtonId();

                if (rid.isEmpty()) {
                    regid.setError("Please enter your Registeration ID");
                    regid.requestFocus();
                } else if (fullname.isEmpty()) {
                    fname.setError("Please enter your name");
                    fname.requestFocus();
                } else if (TextUtils.isEmpty(pword)) {
                    pass.setError("Please enter your password");
                    pass.requestFocus();
                } else if(!pword.equals(cpword)){
                    cpass.setError("Passwords do not match");
                    cpass.requestFocus();
                } else if (!isValidEmail(email)) {
                    em.setError("Please enter your Email");
                    em.requestFocus();
                }
                else if(radioID == -1)
                {
                    radioButton1 = findViewById(radioID);
                    radioButton1.requestFocus();
                }
                else {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, pword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(!task.isSuccessful()){
                                            if(!haveNetwork()){
                                                Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                openDialog();
                                            }
                                        }
                                        else{
                                            sendVerificationEmail();
                                            if(radioButton1.isChecked()){
                                                firebaseMethods.addNewFaculty(em.getText().toString(),"", "","","", fname.getText().toString(),1, regid.getText().toString());
                                                firebaseMethods.addMyStatus(fname.getText().toString(), "**no status**");
                                                if(!haveNetwork()){
                                                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    tvview.setVisibility(View.VISIBLE);
                                                    VerificationDialog verificationDialog = new VerificationDialog();
                                                    verificationDialog.show(getSupportFragmentManager(),"verification dialog");
                                                    mAuth.signOut();
                                                    progressBar.setVisibility(View.GONE);
                                                    tvview.setVisibility(View.GONE);
                                                }
                                            }
                                            else if(radioButton2.isChecked()){
                                                firebaseMethods.addNewStudent(em.getText().toString(),"", "","","", fname.getText().toString(),1, regid.getText().toString());
                                                if(!haveNetwork()){
                                                    Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    progressBar.setVisibility(View.VISIBLE);
                                                    tvview.setVisibility(View.VISIBLE);
                                                    VerificationDialog verificationDialog = new VerificationDialog();
                                                    verificationDialog.show(getSupportFragmentManager(),"verification dialog");
                                                    mAuth.signOut();
                                                    progressBar.setVisibility(View.GONE);
                                                    tvview.setVisibility(View.GONE);
                                                }
                                            }
                                        }
                                    }
                                });
                        }
                    }
            }
        });

        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginDialog();
            }
        });
    }

    public void openLoginDialog() {
        if(!haveNetwork()){
            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            tvview.setVisibility(View.VISIBLE);
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.show(getSupportFragmentManager(),"login dialog");
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

    public void openDialog() {
        EmailExistsDialog exampleDialog = new EmailExistsDialog();
        exampleDialog.show(getSupportFragmentManager(),"email exits dialog");
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
    }

    private boolean isValidEmail(String Email){
        String Email_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(Email_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }

}
