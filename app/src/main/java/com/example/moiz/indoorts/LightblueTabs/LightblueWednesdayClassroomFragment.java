package com.example.moiz.indoorts.LightblueTabs;


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
import com.example.moiz.indoorts.LightblueClassroomActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class LightblueWednesdayClassroomFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<LightblueWednesdayClassroom, LightblueWednesdayClassroomHolder> firebaseRecyclerAdapter;

    public LightblueWednesdayClassroomFragment(){

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lightblue_wednesday_classroom, container, false);

        myrecyclerview = (RecyclerView) view.findViewById(R.id.classroom_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("beacon_lightblue").child("classroom").child("wednesday");

        FirebaseRecyclerOptions<LightblueWednesdayClassroom> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<LightblueWednesdayClassroom>()
                .setQuery(query, LightblueWednesdayClassroom.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<LightblueWednesdayClassroom, LightblueWednesdayClassroomHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull LightblueWednesdayClassroomHolder lightblueWednesdayClassroomHolder, int position, @NonNull LightblueWednesdayClassroom lightblueWednesdayClassroom) {
                lightblueWednesdayClassroomHolder.setLightblueWednesdayClassroom(lightblueWednesdayClassroom);

                final String userPosition = getRef(position).getKey();

                lightblueWednesdayClassroomHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!haveNetwork()){
                            Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getActivity(), LightblueClassroomActivity.class);
                            intent.putExtra("classroom", userPosition);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public LightblueWednesdayClassroomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classroom, parent, false);

                return new LightblueWednesdayClassroomHolder(view);
            }
        };
        myrecyclerview.setAdapter(firebaseRecyclerAdapter);

        return view;
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

    private class LightblueWednesdayClassroomHolder extends RecyclerView.ViewHolder {
        private TextView teacher, course, r, t;

        LightblueWednesdayClassroomHolder(View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachername);
            course = itemView.findViewById(R.id.coursename);
            r = itemView.findViewById(R.id.classroom);
            t = itemView.findViewById(R.id.time);
        }

        void setLightblueWednesdayClassroom(LightblueWednesdayClassroom lightblueWednesdayClassroom) {

            String teachername = lightblueWednesdayClassroom.getTeachername();
            teacher.setText(teachername);
            String coursename = lightblueWednesdayClassroom.getCoursename();
            course.setText(coursename);
            String room = lightblueWednesdayClassroom.getRoom();
            r.setText(room);
            String time = lightblueWednesdayClassroom.getTime();
            t.setText(time);
        }
    }
}

