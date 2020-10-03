package com.example.moiz.indoorts;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextView tv;
    private EditText email;
    private Button sendpass;
    ProgressBar progressBar;
    TextView tvview;
    Animation fromBottom;
    private LinearLayout layouthome;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        layouthome = (LinearLayout) findViewById(R.id.textforgot);
        layouthome.startAnimation(fromBottom);

        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
        email = (EditText) findViewById(R.id.e_mail);
        sendpass = (Button) findViewById(R.id.btn_sendpass);

        mAuth = FirebaseAuth.getInstance();

        addListenerOnButton();
    }

    public void addListenerOnButton(){
        sendpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Email Address is invalid");
                    email.requestFocus();
                }
                else if(email.getText().toString().isEmpty()){
                    email.setError("Please enter your Email");
                    email.requestFocus();
                }
                else {
                    if (!haveNetwork()) {
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    } else {

                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);

                        mAuth.sendPasswordResetEmail(email.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (!task.isSuccessful()) {
                                            if (!haveNetwork()) {
                                                Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                openDialog();
                                            }

                                        } else {
                                            if (!haveNetwork()) {
                                                Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog();
                                                resetPasswordDialog.show(getSupportFragmentManager(), "reset dialog");
                                                mAuth.signOut();
                                                progressBar.setVisibility(View.GONE);
                                                tvview.setVisibility(View.GONE);
                                            }


                                        }
                                    }
                                });
                    }
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
    private boolean isValidEmail(String Email){
        String Email_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(Email_PATTERN);
        Matcher matcher = pattern.matcher(Email);
        return matcher.matches();
    }

    public void openDialog() {
        WrongEmailDialog exampleDialog = new WrongEmailDialog();
        exampleDialog.show(getSupportFragmentManager(),"wrong dialog");
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);
    }
}
