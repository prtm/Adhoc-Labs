package io.adhoclabs.communication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.adhoclabs.prtm.R;

public class EnquiryFragment extends Fragment {


    public EnquiryFragment() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);

        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText email_address = (EditText) view.findViewById(R.id.email);
        final EditText contactNum = (EditText) view.findViewById(R.id.contact);
        final EditText collegeName = (EditText) view.findViewById(R.id.college);
        final EditText msg = (EditText) view.findViewById(R.id.msg);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        name.setText(firebaseAuth.getCurrentUser().getDisplayName());
        email_address.setText(firebaseAuth.getCurrentUser().getEmail());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contactNum.getText()) || contactNum.getText().toString().length() < 10) {
                    showSnackBar("Check Your Contact Number");
                } else if (TextUtils.isEmpty(collegeName.getText())) {
                    showSnackBar(getResources().getString(R.string.enter_your_college_name));
                } else if (TextUtils.isEmpty(msg.getText())) {
                    showSnackBar(getResources().getString(R.string.enter_your_message));
                } else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EnquiryFragment");
                    Map<String, String> sh = new HashMap<>();
                    sh.put("Name", firebaseAuth.getCurrentUser().getDisplayName());
                    sh.put("Email Address", firebaseAuth.getCurrentUser().getEmail());
                    sh.put("Number", contactNum.getText().toString());
                    sh.put("College Name", collegeName.getText().toString());
                    sh.put("Message", msg.getText().toString());

                    final Snackbar snackbar = Snackbar.make(view, "Sending Message...", Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                    reference.push().setValue(sh, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            snackbar.dismiss();
                            showSnackBar("<-- Send Successful -->");
                        }
                    });
                }
            }
        });


        return view;
    }


    private void showSnackBar(String msg) {
        Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
    }
}
