package com.example.moiz.indoorts.LightblueTabs;

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

import com.example.moiz.indoorts.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LightblueOthersFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<LightblueOthers, LightblueFridayOthersHolder> firebaseRecyclerAdapter;

    public LightblueOthersFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lightblue_others, container, false);

        myrecyclerview = (RecyclerView) view.findViewById(R.id.others_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("beacon_lightblue").child("others");

        FirebaseRecyclerOptions<LightblueOthers> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<LightblueOthers>()
                .setQuery(query, LightblueOthers.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<LightblueOthers, LightblueFridayOthersHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull LightblueFridayOthersHolder lightblueFridayOthersHolder, int position, @NonNull LightblueOthers lightblueOthers) {
                lightblueFridayOthersHolder.setLightblueFridayOthers(lightblueOthers);
            }

            @Override
            public LightblueFridayOthersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_others, parent, false);

                return new LightblueFridayOthersHolder(view);
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

    private class LightblueFridayOthersHolder extends RecyclerView.ViewHolder {
        private TextView other;

        LightblueFridayOthersHolder(View itemView) {
            super(itemView);
            other = itemView.findViewById(R.id.otherplaces);
        }

        void setLightblueFridayOthers(LightblueOthers lightblueOthers) {

            String others = lightblueOthers.getOther();
            other.setText(others);
        }
    }
}

