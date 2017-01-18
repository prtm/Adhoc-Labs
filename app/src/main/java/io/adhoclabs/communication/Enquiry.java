package io.adhoclabs.communication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.adhoclabs.prtm.R;

public class Enquiry extends Fragment {


    public Enquiry() {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry, container, false);

        EditText name = (EditText) view.findViewById(R.id.name);
        EditText email_address = (EditText) view.findViewById(R.id.email);
        EditText contactNum = (EditText) view.findViewById(R.id.contact);
        EditText collegeName = (EditText) view.findViewById(R.id.college);
        EditText msg = (EditText) view.findViewById(R.id.msg);

        


        return view;
    }

}
