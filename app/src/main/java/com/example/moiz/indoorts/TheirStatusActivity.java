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

public class TheirStatusActivity extends AppCompatActivity {

    Animation fromBottom;
    private RelativeLayout layouthome;

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<MyStatus, MyStatusHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_their_status);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (RelativeLayout) findViewById(R.id.rl1);
        layouthome.startAnimation(fromBottom);

        myrecyclerview = (RecyclerView) findViewById(R.id.facultylist_recyclerview);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("mystatus");

        FirebaseRecyclerOptions<MyStatus> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<MyStatus>()
                .setQuery(query, MyStatus.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MyStatus, MyStatusHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull MyStatusHolder myStatusHolder, int position, @NonNull MyStatus myStatus) {
                myStatusHolder.setMyStatus(myStatus);
            }

            @Override
            public MyStatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facultylist, parent, false);

                return new MyStatusHolder(view);
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

    private class MyStatusHolder extends RecyclerView.ViewHolder {
        private TextView teacher, theirstatus;

        MyStatusHolder(View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachername);
            theirstatus = itemView.findViewById(R.id.status);
        }

        void setMyStatus(MyStatus myStatus) {
            String teachername = myStatus.getFullname();
            teacher.setText(teachername);
            String status = myStatus.getStatus();
            theirstatus.setText(status);
        }
    }
}
