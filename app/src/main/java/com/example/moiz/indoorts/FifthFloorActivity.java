package com.example.moiz.indoorts;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FifthFloorActivity extends AppCompatActivity {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<FifthFloor, FifthFloorHolder> firebaseRecyclerAdapter;

    Animation fromBottom;
    private RelativeLayout layouthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_floor);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.fromnow);
        layouthome = (RelativeLayout) findViewById(R.id.rl1);
        layouthome.startAnimation(fromBottom);

        myrecyclerview = (RecyclerView) findViewById(R.id.floor_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("fifth_floor").orderByChild("room");

        FirebaseRecyclerOptions<FifthFloor> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<FifthFloor>()
                .setQuery(query, FifthFloor.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<FifthFloor, FifthFloorHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FifthFloorHolder fifthFloorHolder, final int position, @NonNull FifthFloor fifthFloor) {
                fifthFloorHolder.setFifthFloor(fifthFloor);

                fifthFloorHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!haveNetwork()){
                            Toast.makeText(getApplicationContext(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent();
                            intent.putExtra("roomValue", getRef(position).getKey());
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            }

            @Override
            public FifthFloorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor, parent, false);

                return new FifthFloorHolder(view);
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

    private class FifthFloorHolder extends RecyclerView.ViewHolder {
        private TextView room;

        FifthFloorHolder(View itemView) {
            super(itemView);
            room = itemView.findViewById(R.id.roomnumber);
        }

        void setFifthFloor(FifthFloor fifthFloor) {
            String roomname = fifthFloor.getRoom();
            room.setText(roomname);

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
