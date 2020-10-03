package com.example.moiz.indoorts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentChangeEmailActivity extends AppCompatActivity implements ConfirmPasswordDialog.onConfirmPasswordListener{

    @Override
    public void onConfirmPassword(String password) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.fetchProvidersForEmail(em.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                                            if(task.isSuccessful()){
                                                try{
                                                    if(task.getResult().getProviders().size() == 1){
                                                        Toast.makeText(getApplicationContext(), "The e-mail is already in use.", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{

                                                        mAuth.getCurrentUser().updateEmail(em.getText().toString())
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {

                                                                            Toast.makeText(getApplicationContext(), "E-mail is updated successfully.", Toast.LENGTH_SHORT).show();
                                                                            firebaseMethods.updateStudentEmail(em.getText().toString());

                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                                catch (NullPointerException e){
                                                }
                                            }
                                        }
                                    });
                        }
                        else{
                        }
                    }
                });
    }

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private Context mContext;
    private String regID;
    private StudentSettings mStudentSettings;
    private EditText em;
    private ImageView checkmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change_email);

        em = (EditText) findViewById(R.id.email);
        checkmark = (ImageView) findViewById(R.id.savechanges);

        ImageView backArrow = (ImageView) findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEmailSettings();

            }
        });

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseMethods = new FirebaseMethods(getApplicationContext());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    regID = firebaseAuth.getCurrentUser().getUid();
                }
                else{
                }
            }
        };
    }

    private void saveEmailSettings(){

        final String email = em.getText().toString();

        if(!mStudentSettings.getStudent().getEmail().equals(email)){
            StudentEditProfileFragment fragment = new StudentEditProfileFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            Toast.makeText(StudentChangeEmailActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
        }
        else{
            StudentEditProfileFragment fragment = new StudentEditProfileFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            Toast.makeText(StudentChangeEmailActivity.this, "Email is already is use", Toast.LENGTH_SHORT).show();
        }
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
}
