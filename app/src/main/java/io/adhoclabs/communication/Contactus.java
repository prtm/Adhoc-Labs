package io.adhoclabs.communication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import io.adhoclabs.prtm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contactus extends Fragment {


    public Contactus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);
        final TextView email = (TextView) view.findViewById(R.id.email);
        final TextView number = (TextView) view.findViewById(R.id.num);
        final TextView address = (TextView) view.findViewById(R.id.address);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Contact");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Map<String, String> map = dataSnapshot.getValue(Map.class);

                    Toast.makeText(getActivity().getApplicationContext(), map.values().toString(), Toast.LENGTH_SHORT).show();
                    email.setText(map.get("support"));
                    number.setText(map.get("number"));
                    address.setText(map.get("address"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
