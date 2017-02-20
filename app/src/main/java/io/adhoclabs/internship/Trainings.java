package io.adhoclabs.internship;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.adhoclabs.communication.AppbarChange;
import io.adhoclabs.prtm.L;
import io.adhoclabs.prtm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Trainings extends Fragment {
    private List<TrainingsInfoObj> obj;
    private static final String title = "Trainings";
    private DatabaseReference databasereference;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressbar;

    public Trainings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);
        AppbarChange appbarChange = (AppbarChange) getActivity();
        appbarChange.setTitle(title);
        databasereference = FirebaseDatabase.getInstance().getReference().child("Trainings");
        progressbar = (ProgressBar) view.findViewById(R.id.pb);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.training_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new Trainings_rvadapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);


        return view;
    }

    public List<TrainingsInfoObj> getData() {
        obj = new ArrayList<>();
        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //obj = Collections.emptyList();
                progressbar.setVisibility(View.VISIBLE);
                GenericTypeIndicator<Map<String, HashMap<String, HashMap<String, String>>>> genericTypeIndicator = new GenericTypeIndicator<Map<String, HashMap<String, HashMap<String, String>>>>() {
                };

                Map<String, HashMap<String, HashMap<String, String>>> maps = dataSnapshot.getValue(genericTypeIndicator);
                Object[] str = maps.keySet().toArray();
                for (Object aStr : str) {
                    TrainingsInfoObj tio = new TrainingsInfoObj();
                    tio.textT = (String) aStr;
                    tio.imageUrl = maps.get(tio.textT).get("img").get("ImageUrl");
                    //L.tmlong(getActivity(), "Title: " + tio.textT + " ImageUrl: " + tio.imageUrl);
                    obj.add(tio);


                    //L.tmlong(getActivity(),(String)s);
                }
                progressbar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                //L.tmlong(getActivity(), Arrays.toString(maps.values().toArray()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return obj;
    }
}
