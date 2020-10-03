package com.example.moiz.indoorts;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class FindLocationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public Spinner floorspinner;
    public EditText from, to;
    public Button find;
    public LinearLayout layout1, layout2, layout3;
    private DatabaseReference floorref, groundfloorref, firstfloorref, secondfloorref, thirdfloorref, fourthfloorref, fifthfloorref,
            sixthfloorref, seventhfloorref, eighthfloorref, ninthfloorref, tenthfloorref, eleventhfloorref, rooftopfloorref;

    public static final String IMAGE_RES_ID = "IMAGE_RES";

    ProgressBar progressBar;
    TextView tvview;
    Animation fromBottom;
    private LinearLayout layouthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_location);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tvview = (TextView) findViewById(R.id.tvrandom);
        layouthome = (LinearLayout) findViewById(R.id.r1);
        layouthome.startAnimation(fromBottom);

        progressBar.setVisibility(View.GONE);
        tvview.setVisibility(View.GONE);

        floorref = FirebaseDatabase.getInstance().getReference().child("all_floor");
        groundfloorref = FirebaseDatabase.getInstance().getReference().child("ground_floor");
        firstfloorref = FirebaseDatabase.getInstance().getReference().child("first_floor");
        secondfloorref = FirebaseDatabase.getInstance().getReference().child("second_floor");
        thirdfloorref = FirebaseDatabase.getInstance().getReference().child("third_floor");
        fourthfloorref = FirebaseDatabase.getInstance().getReference().child("fourth_floor");
        fifthfloorref = FirebaseDatabase.getInstance().getReference().child("fifth_floor");
        sixthfloorref = FirebaseDatabase.getInstance().getReference().child("sixth_floor");
        seventhfloorref = FirebaseDatabase.getInstance().getReference().child("seventh_floor");
        eighthfloorref = FirebaseDatabase.getInstance().getReference().child("eighth_floor");
        ninthfloorref = FirebaseDatabase.getInstance().getReference().child("ninth_floor");
        tenthfloorref = FirebaseDatabase.getInstance().getReference().child("tenth_floor");
        eleventhfloorref = FirebaseDatabase.getInstance().getReference().child("eleventh_floor");
        rooftopfloorref = FirebaseDatabase.getInstance().getReference().child("rooftop_floor");

        from = (EditText) findViewById(R.id.efrom);
        to = (EditText) findViewById(R.id.eto);
        find = (Button) findViewById(R.id.btnfind);
        layout1 = (LinearLayout) findViewById(R.id.ll2);
        layout2 = (LinearLayout) findViewById(R.id.ll3);
        layout3 = (LinearLayout) findViewById(R.id.ll4);

        floorspinner = (Spinner) findViewById(R.id.sfloor);

        List<String> floorlist = new ArrayList<>();
        floorlist.add(0, "Choose");
        floorlist.add("Ground Floor");
        floorlist.add("First Floor");
        floorlist.add("Second Floor");
        floorlist.add("Third Floor");
        floorlist.add("Fourth Floor");
        floorlist.add("Fifth Floor");
        floorlist.add("Sixth Floor");
        floorlist.add("Seventh Floor");
        floorlist.add("Eighth Floor");
        floorlist.add("Ninth Floor");
        floorlist.add("Tenth Floor");
        floorlist.add("Eleventh Floor");
        floorlist.add("Rooftop");

        ArrayAdapter<String> flooradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, floorlist);
        flooradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorspinner.setAdapter(flooradapter);

        floorspinner.setOnItemSelectedListener(this);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String text = parent.getItemAtPosition(position).toString();

        if(text.equals("Choose")){
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
        }
        else if(text == "Ground Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, GroundFloorActivity.class);
                        startActivityForResult(intent, 01);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 02);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "First Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, FirstFloorActivity.class);
                        startActivityForResult(intent, 11);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 12);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Second Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, SecondFloorActivity.class);
                        startActivityForResult(intent, 21);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 22);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Third Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, ThirdFloorActivity.class);
                        startActivityForResult(intent, 31);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 32);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Fourth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, FourthFloorActivity.class);
                        startActivityForResult(intent, 41);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 42);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Fifth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, FifthFloorActivity.class);
                        startActivityForResult(intent, 51);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 52);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(to.getText().toString().isEmpty()|| from.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        //IDHAR
                        if(!haveNetwork()){
                            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            five();
                            progressBar.setVisibility(View.GONE);
                            tvview.setVisibility(View.GONE);
                        }

                    }
                }
            });
        }
        else if(text == "Sixth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, SixthFloorActivity.class);
                        startActivityForResult(intent, 61);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 62);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
        }
        else if(text == "Seventh Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, SeventhFloorActivity.class);
                        startActivityForResult(intent, 71);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 72);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Eighth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, EighthFloorActivity.class);
                        startActivityForResult(intent, 81);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 82);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Ninth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, NinthFloorActivity.class);
                        startActivityForResult(intent, 91);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 92);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Tenth Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, TenthFloorActivity.class);
                        startActivityForResult(intent, 101);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 102);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Eleventh Floor"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, EleventhFloorActivity.class);
                        startActivityForResult(intent, 111);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 112);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
        else if(text == "Rooftop"){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout3.setVisibility(View.VISIBLE);
            from.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, RooftopActivity.class);
                        startActivityForResult(intent, 121);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });
            to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!haveNetwork()){
                        Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        tvview.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(FindLocationActivity.this, AllFloorActivity.class);
                        startActivityForResult(intent, 122);
                        progressBar.setVisibility(View.GONE);
                        tvview.setVisibility(View.GONE);
                    }

                }
            });

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case 01 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        groundfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 02 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 11 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        firstfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 12 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 21 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        secondfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 22 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 31 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        thirdfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 32 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 41 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        fourthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 42 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 51 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        fifthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 52 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 61 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        sixthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 62 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 71 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        seventhfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 72 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 81 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        eighthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 82 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 91 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        ninthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 92 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 101 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        tenthfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 102 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 111 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        eleventhfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 112 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 121 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        rooftopfloorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                    EditText f = (EditText) findViewById(R.id.efrom);
                                    f.setText(roomFound);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            case 122 :{
                if(resultCode == Activity.RESULT_OK){
                    if(data.hasExtra("roomValue")){
                        String selectedroom = data.getStringExtra("roomValue");

                        floorref.child(selectedroom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                    String roomFound = dataSnapshot.child("room").getValue().toString();
                                   // to.setText(roomFound);
                                    EditText f = (EditText) findViewById(R.id.eto);
                                    f.setText(roomFound);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            break;

        }
    }

    public void five(){
        progressBar.setVisibility(View.VISIBLE);
        tvview.setVisibility(View.VISIBLE);
        Intent intent5 = new Intent(FindLocationActivity.this,SourceMapActivity.class);
        //5 to 5
        if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e51);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e52);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e53);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e54);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e55);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e56);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e57);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e58);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e59);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e591);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e592);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e593);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e594);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e595);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e596);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e597);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e598);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e599);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e61);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e62);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e63);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e64);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e65);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e66);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e67);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e68);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e69);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e691);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e692);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e693);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e694);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e695);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e696);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e697);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e698);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e699);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e71);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e72);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e73);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e74);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e75);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e76);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e77);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e78);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e79);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e791);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e792);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e793);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e794);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e795);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l12);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e796);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e797);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e798);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.e799);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.q18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l19);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l20);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.a18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l21);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l22);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l23);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l24);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.w18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l25);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l26);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l27);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l28);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.x18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l29);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l30);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l31);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l32);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.c18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l33);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l34);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l35);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l36);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.v18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l37);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l38);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l39);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l40);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.b18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l41);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l42);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l43);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l44);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.n18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m12);
            startActivity(intent5);
        }else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l45);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l46);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l47);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l48);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.m18);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[0])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s1);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[1])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s2);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[2])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s3);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[3])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s4);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[4])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s5);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[5])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s6);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[6])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s7);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[7])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s8);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[8])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s9);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[9])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s10);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[10])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s11);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[11])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s12);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[12])){
            Toast.makeText(getApplicationContext(),"Starting and Ending points cannot be same",Toast.LENGTH_LONG).show();
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[13])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l49);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[14])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s13);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[15])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s14);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[16])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l50);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[17])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l51);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[18])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.l52);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[19])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s15);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[20])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s16);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[21])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s17);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[22])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.s18);
            startActivity(intent5);
        }

        //5 to 8
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[0])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[1])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[2])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[3])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[4])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[5])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[6])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[7])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[8])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[9])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[10])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[11])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[12])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[13])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[14])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[15])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[16])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[17])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[18])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[19])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[20])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[21])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[45])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[46])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[47])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[48])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[49])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[50])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[51])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[52])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[53])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[54])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[55])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[56])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[57])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[58])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[59])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[60])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[61])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[62])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[63])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[64])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[65])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[66])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else if(from.getText().toString().matches(RoomInfo.roomNumberSource[22])&&to.getText().toString().matches(RoomInfo.roomNumberDest[67])){
            intent5.putExtra(IMAGE_RES_ID,R.drawable.five);
            startActivity(intent5);
        }
        else{
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
        }
    }
}
