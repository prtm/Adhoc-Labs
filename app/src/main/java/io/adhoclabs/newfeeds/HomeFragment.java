package io.adhoclabs.newfeeds;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import io.adhoclabs.prtm.R;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<HomeFragment.Tech, HomeFragment.FirebaseRvHolder> mFirebaseAdapter;
    private ProgressBar progressBar;
    private SliderLayout sliderV;
    private DatabaseReference drHome;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderV = (SliderLayout) view.findViewById(R.id.slider);

        progressBar = (ProgressBar) view.findViewById(R.id.pb);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        //disabling scroll due as it is already inside scrollview
        mRecyclerView.setNestedScrollingEnabled(false);
        final ImageView serviceImg = (ImageView) view.findViewById(R.id.serviceImg);
        final TextView serviceText = (TextView) view.findViewById(R.id.services);
        final TextView cu1 = (TextView) view.findViewById(R.id.cu1);  //contact us first line
        final TextView cu2 = (TextView) view.findViewById(R.id.cu2);
        final Button button = (Button) view.findViewById(R.id.contactus);


        drHome = FirebaseDatabase.getInstance().getReference("HomeFragment");

        //Setting up services in view
        drHome.child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };

                Map<String, String> maps = dataSnapshot.getValue(genericTypeIndicator);
                serviceText.setText(maps.get("s1") + "\n\n" + maps.get("s2") + "\n\n" + maps.get("s3"));

                Glide.with(getActivity()).load(maps.get("img")).placeholder(R.mipmap.ic_launcher).centerCrop().override(300, 300)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(serviceImg);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Setting up Contact referal in view
        drHome.child("contactus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };

                final Map<String, String> maps = dataSnapshot.getValue(genericTypeIndicator);

                cu1.setText(maps.get("cu1"));
                cu2.setText(maps.get("cu2"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(maps.get("ref")));
                        startActivity(i);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setUpFirebaseAdapter();

        FirebaseDatabase.getInstance().getReference("Slider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };

                Map<String, String> maps = dataSnapshot.getValue(genericTypeIndicator);

                for (String name : maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(HomeFragment.this);

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    sliderV.addSlider(textSliderView);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        sliderV.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderV.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderV.setCustomAnimation(new DescriptionAnimation());
        sliderV.setDuration(4000);

        return view;
    }


    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        sliderV.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity().getApplicationContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //Log.d("Contact Adhoc Labs", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public void onDestroy() {
        mFirebaseAdapter.cleanup();
        super.onDestroy();

    }

    private void setUpFirebaseAdapter() {
        DatabaseReference reference = drHome.child("tech");

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Tech, HomeFragment.FirebaseRvHolder>
                (Tech.class, R.layout.custom_home, HomeFragment.FirebaseRvHolder.class,
                        reference) {

            @Override
            protected void populateViewHolder(HomeFragment.FirebaseRvHolder viewHolder,
                                              HomeFragment.Tech model, int position) {
                viewHolder.bindTech(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


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
        ImageView img;

        public FirebaseRvHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);


        }

        public void bindTech(HomeFragment.Tech feeds) {
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            img = (ImageView) itemView.findViewById(R.id.techImg);

            title.setText(feeds.getTitle());
            description.setText(feeds.getDescription());
            System.out.println(feeds.getTitle());
            System.out.println(feeds.getImg());
            Glide.with(itemView.getContext()).load(feeds.getImg()).placeholder(R.mipmap.ic_launcher).centerCrop().override(100, 100)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public static class Tech {

        private String title, description, img;

        public Tech() {
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

    }

}
