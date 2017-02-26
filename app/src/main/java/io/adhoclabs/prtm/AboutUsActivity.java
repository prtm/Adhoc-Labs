package io.adhoclabs.prtm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.adhoclabs.communication.TitleInterface;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private static final String title = "About Us";
    private FirebaseRecyclerAdapter<AboutUsActivity.About, AboutUsActivity.FirebaseRvHolder> mFirebaseAdapter;

    public AboutUsActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.aboutus_rv);
        progressBar = (ProgressBar) view.findViewById(R.id.pb);
        TitleInterface appbarChange = (TitleInterface) getActivity();
        appbarChange.setTitle(title);


        setUpFirebaseAdapter();
        return view;
    }


    private void setUpFirebaseAdapter() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("About Us");

        mFirebaseAdapter = new FirebaseRecyclerAdapter<AboutUsActivity.About, AboutUsActivity.FirebaseRvHolder>
                (AboutUsActivity.About.class, R.layout.custom_aboutus, AboutUsActivity.FirebaseRvHolder.class,
                        reference) {

            @Override
            protected void populateViewHolder(AboutUsActivity.FirebaseRvHolder viewHolder,
                                              AboutUsActivity.About model, int position) {
                viewHolder.bindAboutus(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setAdapter(mFirebaseAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static class FirebaseRvHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, description;

        public FirebaseRvHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


        }

        public void bindAboutus(AboutUsActivity.About about) {
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

            title.setText(about.getTitle());
            description.setText(about.getDescription());
            try {
                Log.d("preetam", about.getTitle());
                Log.d("preetam", about.getDescription());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View view) {

        }
    }

    public static class About {

        public About() {

        }

        private String title, description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }


}
