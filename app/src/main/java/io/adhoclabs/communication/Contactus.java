package io.adhoclabs.communication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import io.adhoclabs.prtm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contactus extends Fragment {
    private static final String title = "Contact Us";

    public Contactus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);

        //intialization
        final TextView email = (TextView) view.findViewById(R.id.email);
        final TextView number = (TextView) view.findViewById(R.id.num);
        final TextView address = (TextView) view.findViewById(R.id.address);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb);

        AppbarChange appbarChange = (AppbarChange) getActivity();
        appbarChange.setTitle(title);
        progressBar.setVisibility(View.VISIBLE);

        //Firebase get contact from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Contact");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, String>> genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                };

                Map<String, String> map = dataSnapshot.getValue(genericTypeIndicator);

                email.setText(map.get("support"));
                number.setText(map.get("number"));
                address.setText(map.get("address"));
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });


        return view;
    }

}
