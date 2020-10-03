package com.example.moiz.indoorts.DarkblueTabs;

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

public class DarkblueOthersFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private FirebaseRecyclerAdapter<DarkblueOthers, DarkblueFridayOthersHolder> firebaseRecyclerAdapter;

    public DarkblueOthersFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_darkblue_others, container, false);

        myrecyclerview = (RecyclerView) view.findViewById(R.id.others_recyclerview);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.addItemDecoration(new DividerItemDecoration(myrecyclerview.getContext(), DividerItemDecoration.VERTICAL));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("beacon_darkblue").child("others");

        FirebaseRecyclerOptions<DarkblueOthers> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<DarkblueOthers>()
                .setQuery(query, DarkblueOthers.class)
                .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DarkblueOthers, DarkblueFridayOthersHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull DarkblueFridayOthersHolder darkblueFridayOthersHolder, int position, @NonNull DarkblueOthers darkblueOthers) {
                darkblueFridayOthersHolder.setDarkblueFridayOthers(darkblueOthers);
            }

            @Override
            public DarkblueFridayOthersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_others, parent, false);

                return new DarkblueFridayOthersHolder(view);
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

    private class DarkblueFridayOthersHolder extends RecyclerView.ViewHolder {
        private TextView other;

        DarkblueFridayOthersHolder(View itemView) {
            super(itemView);
            other = itemView.findViewById(R.id.otherplaces);

        }

        void setDarkblueFridayOthers(DarkblueOthers darkblueOthers) {

            String others = darkblueOthers.getOther();
            other.setText(others);
        }
    }
}

