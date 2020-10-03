package com.example.moiz.indoorts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SecondFloorActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<SecondFloor, SecondFloorHolder> firebaseRecyclerAdapter;
    Animation fromBottom;
    private RelativeLayout layouthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_floor);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (RelativeLayout) findViewById(R.id.rl1);
        layouthome.startAnimation(fromBottom);

        myrecyclerview = (RecyclerView) findViewById(R.id.floor_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("second_floor").orderByChild("room");

        FirebaseRecyclerOptions<SecondFloor> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<SecondFloor>()
                .setQuery(query, SecondFloor.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SecondFloor, SecondFloorHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull SecondFloorHolder secondFloorHolder, int position, @NonNull SecondFloor secondFloor) {
                secondFloorHolder.setSecondFloor(secondFloor);
            }

            @Override
            public SecondFloorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor, parent, false);

                return new SecondFloorHolder(view);
            }
        };
        myrecyclerview.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (firebaseRecyclerAdapter!= null) {
            firebaseRecyclerAdapter.stopListening();
        }

    }

    private class SecondFloorHolder extends RecyclerView.ViewHolder {
        private TextView room;

        SecondFloorHolder(View itemView) {
            super(itemView);
            room = itemView.findViewById(R.id.roomnumber);
        }

        void setSecondFloor(SecondFloor secondFloor) {

            String roomname = secondFloor.getRoom();
            room.setText(roomname);
        }
    }
}
