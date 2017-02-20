package io.adhoclabs.internship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.adhoclabs.prtm.R;

public class TrainingDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_detail);
        int pos=getIntent().getExtras().getInt("TrainingItemClick");

    }
}
