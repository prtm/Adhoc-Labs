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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.adhoclabs.prtm.R;


class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.Myholder> {

    private final List<TrainingsInfoObj> list;
    private Context context;

    TrainingAdapter(Context context, List<TrainingsInfoObj> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_trainings, parent, false));
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        Glide.with(context).load(list.get(position).imageUrl).placeholder(R.mipmap.ic_launcher).override(64, 64)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
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
                    Intent i = new Intent(context, TrainingDActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TrainingItemClick", textView.getText().toString());
                    i.putExtras(bundle);
                    context.startActivity(i);

                }
            });
        }
    }
}
