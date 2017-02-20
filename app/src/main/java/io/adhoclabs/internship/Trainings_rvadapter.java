package io.adhoclabs.internship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.adhoclabs.prtm.R;


class Trainings_rvadapter extends RecyclerView.Adapter<Trainings_rvadapter.Myholder> {

    private final List<TrainingsInfoObj> list;
    private Context context;

    Trainings_rvadapter(Context context, List<TrainingsInfoObj> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_trainings, parent, false));
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        Picasso.with(context).load(list.get(position).imageUrl).placeholder(R.drawable.redasu).resize(64, 64).into(holder.imageView);
        holder.textView.setText(list.get(position).textT);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myholder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        Myholder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textTraining);
            imageView = (ImageView) itemView.findViewById(R.id.imageTraining);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, TrainingDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TrainingItemClick", textView.getText().toString());
                    i.putExtras(bundle);
                    context.startActivity(i);

                }
            });
        }
    }
}
