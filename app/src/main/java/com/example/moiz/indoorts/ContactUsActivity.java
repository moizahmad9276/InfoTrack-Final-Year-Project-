package com.example.moiz.indoorts;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class ContactUsActivity extends AppCompatActivity {

    Animation fromBottom;
    private LinearLayout layouthome;
    ProgressBar progressBar;
    TextView tvview;

    Button submit;
    EditText ename, eemail, etitle, emessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (LinearLayout) findViewById(R.id.text);
        layouthome.startAnimation(fromBottom);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        submit = (Button) findViewById(R.id.btn_submit);
        eemail = (EditText) findViewById(R.id.email);
        etitle = (EditText) findViewById(R.id.title);
        ename = (EditText) findViewById(R.id.name);
        emessage = (EditText) findViewById(R.id.body);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                tvview.setVisibility(View.VISIBLE);
                if(eemail.getText().toString().isEmpty()
                        || etitle.getText().toString().isEmpty() || ename.getText().toString().isEmpty()
                        || emessage.getText().toString().isEmpty()){
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
                else{
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        EnquiryDialog enquiryDialog = new EnquiryDialog();
                        enquiryDialog.show(getSupportFragmentManager(),"enquiry dialog");
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
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

}
