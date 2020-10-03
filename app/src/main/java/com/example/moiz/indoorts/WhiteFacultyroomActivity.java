package com.example.moiz.indoorts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WhiteFacultyroomActivity extends AppCompatActivity {

    TextView mon1, mon2, mon3, mon4, tue1, tue2, tue3, tue4, wed1, wed2, wed3, wed4, thu1, thu2, thu3, thu4,
    fri1, fri2, fri3, fri4, sat1, sat2, sat3, sat4, sun1, sun2, sun3, sun4, teacher, designation;

    private DatabaseReference facultyroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_facultyroom);

        facultyroom = FirebaseDatabase.getInstance().getReference().child("beacon_white").child("facultyroom");

        mon1 = (TextView) findViewById(R.id.mon09to12);
        mon2 = (TextView) findViewById(R.id.mon12to03);
        mon3 = (TextView) findViewById(R.id.mon03to06);
        mon4 = (TextView) findViewById(R.id.mon06to09);

        tue1 = (TextView) findViewById(R.id.tue09to12);
        tue2 = (TextView) findViewById(R.id.tue12to03);
        tue3 = (TextView) findViewById(R.id.tue03to06);
        tue4 = (TextView) findViewById(R.id.tue06to09);

        wed1 = (TextView) findViewById(R.id.wed09to12);
        wed2 = (TextView) findViewById(R.id.wed12to03);
        wed3 = (TextView) findViewById(R.id.wed03to06);
        wed4 = (TextView) findViewById(R.id.wed06to09);

        thu1 = (TextView) findViewById(R.id.thu09to12);
        thu2 = (TextView) findViewById(R.id.thu12to03);
        thu3 = (TextView) findViewById(R.id.thu03to06);
        thu4 = (TextView) findViewById(R.id.thu06to09);

        fri1 = (TextView) findViewById(R.id.fri09to12);
        fri2 = (TextView) findViewById(R.id.fri12to03);
        fri3 = (TextView) findViewById(R.id.fri03to06);
        fri4 = (TextView) findViewById(R.id.fri06to09);

        sat1 = (TextView) findViewById(R.id.sat09to12);
        sat2 = (TextView) findViewById(R.id.sat12to03);
        sat3 = (TextView) findViewById(R.id.sat03to06);
        sat4 = (TextView) findViewById(R.id.sat06to09);

        sun1 = (TextView) findViewById(R.id.sun09to12);
        sun2 = (TextView) findViewById(R.id.sun12to03);
        sun3 = (TextView) findViewById(R.id.sun03to06);
        sun4 = (TextView) findViewById(R.id.sun06to09);

        teacher = (TextView) findViewById(R.id.teachername);
        designation = (TextView) findViewById(R.id.desig);

        facultySchedule();
    }

    private void facultySchedule(){
        if(getIntent().hasExtra("facultyroom")){
            String position = getIntent().getStringExtra("facultyroom");

            facultyroom.child(position).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("faculty"))) {
                        String facultyFound = dataSnapshot.child("faculty").getValue().toString();
                        String designationFound = dataSnapshot.child("designation").getValue().toString();
                        String m1 = dataSnapshot.child("mon09to12").getValue().toString();
                        String m2 = dataSnapshot.child("mon12to03").getValue().toString();
                        String m3 = dataSnapshot.child("mon03to06").getValue().toString();
                        String m4 = dataSnapshot.child("mon06to09").getValue().toString();
                        String m5 = dataSnapshot.child("tue09to12").getValue().toString();
                        String m6 = dataSnapshot.child("tue12to03").getValue().toString();
                        String m7 = dataSnapshot.child("tue03to06").getValue().toString();
                        String m8 = dataSnapshot.child("tue06to09").getValue().toString();
                        String m9 = dataSnapshot.child("wed09to12").getValue().toString();
                        String m10 = dataSnapshot.child("wed12to03").getValue().toString();
                        String m11 = dataSnapshot.child("wed03to06").getValue().toString();
                        String m12 = dataSnapshot.child("wed06to09").getValue().toString();
                        String m13 = dataSnapshot.child("thu09to12").getValue().toString();
                        String m14 = dataSnapshot.child("thu12to03").getValue().toString();
                        String m15 = dataSnapshot.child("thu03to06").getValue().toString();
                        String m16 = dataSnapshot.child("thu06to09").getValue().toString();
                        String m17 = dataSnapshot.child("fri09to12").getValue().toString();
                        String m18 = dataSnapshot.child("fri12to03").getValue().toString();
                        String m19 = dataSnapshot.child("fri03to06").getValue().toString();
                        String m20 = dataSnapshot.child("fri06to09").getValue().toString();
                        String m21 = dataSnapshot.child("sat09to12").getValue().toString();
                        String m22 = dataSnapshot.child("sat12to03").getValue().toString();
                        String m23 = dataSnapshot.child("sat03to06").getValue().toString();
                        String m24 = dataSnapshot.child("sat06to09").getValue().toString();
                        String m25 = dataSnapshot.child("sun09to12").getValue().toString();
                        String m26 = dataSnapshot.child("sun12to03").getValue().toString();
                        String m27 = dataSnapshot.child("sun03to06").getValue().toString();
                        String m28 = dataSnapshot.child("sun06to09").getValue().toString();

                        designation.setText(designationFound);
                        teacher.setText(facultyFound);
                        mon1.setText(m1);mon2.setText(m2);mon3.setText(m3);mon4.setText(m4);
                        tue1.setText(m5);tue2.setText(m6);tue3.setText(m7);tue4.setText(m8);
                        wed1.setText(m9);wed2.setText(m10);wed3.setText(m11);wed4.setText(m12);
                        thu1.setText(m13);thu2.setText(m14);thu3.setText(m15);thu4.setText(m16);
                        fri1.setText(m17);fri2.setText(m18);fri3.setText(m19);fri4.setText(m20);
                        sat1.setText(m21);sat2.setText(m22);sat3.setText(m23);sat4.setText(m24);
                        sun1.setText(m25);sun2.setText(m26);sun3.setText(m27);sun4.setText(m28);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
