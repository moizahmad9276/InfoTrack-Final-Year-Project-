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
import com.example.moiz.indoorts.LightblueFacultyroomActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class LightblueFacultyroomFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<LightblueFacultyroom, LightblueFridayFacultyroomHolder> firebaseRecyclerAdapter;

    public LightblueFacultyroomFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lightblue_facultyroom, container, false);

        myrecyclerview = (RecyclerView) view.findViewById(R.id.facultyroom_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("beacon_lightblue").child("facultyroom");

        FirebaseRecyclerOptions<LightblueFacultyroom> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<LightblueFacultyroom>()
                .setQuery(query, LightblueFacultyroom.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<LightblueFacultyroom, LightblueFridayFacultyroomHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull LightblueFridayFacultyroomHolder lightblueFridayFacultyroomHolder, int position, @NonNull LightblueFacultyroom lightblueFacultyroom) {
                lightblueFridayFacultyroomHolder.setLightblueFridayFacultyroom(lightblueFacultyroom);

                final String userPosition = getRef(position).getKey();

                lightblueFridayFacultyroomHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!haveNetwork()){
                            Toast.makeText(getActivity(), "Check your network connection", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getActivity(), LightblueFacultyroomActivity.class);
                            intent.putExtra("facultyroom", userPosition);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public LightblueFridayFacultyroomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facultyroom, parent, false);

                return new LightblueFridayFacultyroomHolder(view);
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

    private class LightblueFridayFacultyroomHolder extends RecyclerView.ViewHolder {
        private TextView teacher, designation;

        LightblueFridayFacultyroomHolder(View itemView) {
            super(itemView);
            teacher = itemView.findViewById(R.id.teachername);
            designation = itemView.findViewById(R.id.desig);

        }

        void setLightblueFridayFacultyroom(LightblueFacultyroom lightblueFacultyroom) {

            String teachername = lightblueFacultyroom.getFaculty();
            teacher.setText(teachername);
            String design = lightblueFacultyroom.getDesignation();
            designation.setText(design);
        }
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
}

