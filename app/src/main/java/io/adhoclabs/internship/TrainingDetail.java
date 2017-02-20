package io.adhoclabs.internship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.adhoclabs.prtm.L;
import io.adhoclabs.prtm.R;

public class TrainingDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<TrainingsInfoObj> obj;
    private DatabaseReference databasereference;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);
        String name = getIntent().getExtras().getString("TrainingItemClick");
        recyclerView = (RecyclerView) findViewById(R.id.rv_trainingDetail);
        progressbar = (ProgressBar) findViewById(R.id.pb);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databasereference = FirebaseDatabase.getInstance().getReference().child("Trainings/" + name);
        adapter = new TrainingDetails_rvadapter(this, getData());
        recyclerView.setAdapter(adapter);


    }


    public List<TrainingsInfoObj> getData() {
        obj = new ArrayList<>();
        databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //obj = Collections.emptyList();
                progressbar.setVisibility(View.VISIBLE);
                GenericTypeIndicator<Map<String, HashMap<String, String>>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, HashMap<String, String>>>() {
                        };

                Map<String, HashMap<String, String>> maps = dataSnapshot.getValue(genericTypeIndicator);
                Object[] str = maps.keySet().toArray();
                for (int i = 0; i < str.length; i++) {
                    TrainingsInfoObj tio = new TrainingsInfoObj();
                    String t = (String) str[i];

                    tio.textT = maps.get(t).get("title");
                    tio.imageUrl = maps.get(t).get("description");

                    L.tlog("Title: " + tio.textT + " ImageUrl: " + tio.imageUrl);
                    if (tio.textT != null) {
                        obj.add(tio);
                    }

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
