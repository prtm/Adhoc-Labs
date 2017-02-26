package io.adhoclabs.newfeeds;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.adhoclabs.prtm.R;

public class NewsFragment extends Fragment {

    public NewsFragment() {

    }


    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Feeds, FirebaseRvHolder> mFirebaseAdapter;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.pb);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.feeds_rv);
        setUpFirebaseAdapter();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

    private void setUpFirebaseAdapter() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("New Feeds");

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Feeds, FirebaseRvHolder>
                (Feeds.class, R.layout.custom_feeds, FirebaseRvHolder.class,
                        reference) {

            @Override
            protected void populateViewHolder(FirebaseRvHolder viewHolder,
                                              Feeds model, int position) {
                viewHolder.bindFeeds(model);
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
        TextView title, time, description;
        ImageView upcomingImg;

        public FirebaseRvHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


        }

        public void bindFeeds(Feeds feeds) {
            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
            description = (TextView) itemView.findViewById(R.id.description);
            upcomingImg = (ImageView) itemView.findViewById(R.id.upcomingImg);

            title.setText(feeds.getTitle());
            time.setText(feeds.getTime());
            description.setText(feeds.getDescription());


            Glide.with(itemView.getContext()).load(feeds.getImg()).placeholder(R.mipmap.ic_launcher).centerCrop().override(250, 250)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(upcomingImg);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public static class Feeds {

        private String title, description, img, time;

        public Feeds() {
        }

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }

}
