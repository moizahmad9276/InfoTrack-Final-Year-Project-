package com.example.moiz.indoorts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class WhiteClassroomActivity extends AppCompatActivity {

    private TextView crs, rm, tch, tm;
    private DatabaseReference sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_classroom);

        sunday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("sunday");
        monday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("monday");
        tuesday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("tuesday");
        wednesday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("wednesday");
        thursday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("thursday");
        friday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("friday");
        saturday = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("classroom").child("saturday");

        crs = (TextView) findViewById(R.id.coursename);
        rm = (TextView) findViewById(R.id.classroom);
        tch = (TextView) findViewById(R.id.teachername);
        tm = (TextView) findViewById(R.id.time);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int) (height*.2));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    sunday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;

            case Calendar.MONDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    monday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
            case Calendar.TUESDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    tuesday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
            case Calendar.WEDNESDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    wednesday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
            case Calendar.THURSDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    thursday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
            case Calendar.FRIDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    friday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
            case Calendar.SATURDAY:
                if (getIntent().hasExtra("classroom")) {
                    String position = getIntent().getStringExtra("classroom");

                    saturday.child(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("room"))) {
                                String roomFound = dataSnapshot.child("room").getValue().toString();
                                String courseFound = dataSnapshot.child("coursename").getValue().toString();
                                String teacherFound = dataSnapshot.child("teachername").getValue().toString();
                                String timeFound = dataSnapshot.child("time").getValue().toString();
                                crs.setText(courseFound);
                                rm.setText(roomFound);
                                tch.setText(teacherFound);
                                tm.setText(timeFound);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
        }
    }
}
