package com.example.moiz.indoorts.WhiteTabs;


import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moiz.indoorts.R;
import com.example.moiz.indoorts.WhiteClassroomActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import static android.content.Context.CONNECTIVITY_SERVICE;

public class WhiteMondayClassroomFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<WhiteMondayClassroom, WhiteMondayClassroomHolder> firebaseRecyclerAdapter;

    public WhiteMondayClassroomFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whitemonday_classroom, container, false);

        myrecyclerview = (RecyclerView) view.findViewById(R.id.classroom_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("beacon_white").child("classroom").child("monday");

        FirebaseRecyclerOptions<WhiteMondayClassroom> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<WhiteMondayClassroom>()
                .setQuery(query, WhiteMondayClassroom.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<WhiteMondayClassroom, WhiteMondayClassroomHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull WhiteMondayClassroomHolder whiteMondayClassroomHolder, int position, @NonNull WhiteMondayClassroom whiteMondayClassroom) {
                whiteMondayClassroomHolder.setWhiteMondayClassroom(whiteMondayClassroom);

                final String userPosition = getRef(position).getKey();

                whiteMondayClassroomHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!haveNetwork()){
                            Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getActivity(), WhiteClassroomActivity.class);
                            intent.putExtra("classroom", userPosition);
                            startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public WhiteMondayClassroomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classroom, parent, false);

                return new WhiteMondayClassroomHolder(view);
            }
        };

        myrecyclerview.setAdapter(firebaseRecyclerAdapter);

        return view;
    }

    public boolean haveNetwork(){
        boolean haveWifi = false;
        boolean haveData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
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

    private class WhiteMondayClassroomHolder extends RecyclerView.ViewHolder {
        private TextView teacher, course, r, t;

        WhiteMondayClassroomHolder(View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachername);
            course = itemView.findViewById(R.id.coursename);
            r = itemView.findViewById(R.id.classroom);
            t = itemView.findViewById(R.id.time);
        }

        void setWhiteMondayClassroom(WhiteMondayClassroom whiteMondayClassroom) {

            String teachername = whiteMondayClassroom.getTeachername();
            teacher.setText(teachername);
            String coursename = whiteMondayClassroom.getCoursename();
            course.setText(coursename);
            String room = whiteMondayClassroom.getRoom();
            r.setText(room);
            String time = whiteMondayClassroom.getTime();
            t.setText(time);
        }
    }
}

