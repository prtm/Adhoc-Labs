package io.adhoclabs.newfeeds;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import io.adhoclabs.prtm.R;

public class News extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.feeds_rv);

        

        return view;
    }

    public static class infoFeeds{

    }

}
