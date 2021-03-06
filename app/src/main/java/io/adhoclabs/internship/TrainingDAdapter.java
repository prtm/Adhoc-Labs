package io.adhoclabs.internship;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.adhoclabs.prtm.R;

/**
 * Created by ghost on 20/2/17.
 */

class TrainingDAdapter extends RecyclerView.Adapter<TrainingDAdapter.Myholder> {
    private List<TrainingsInfoObj> list;

    TrainingDAdapter(List<TrainingsInfoObj> list) {
        this.list = list;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_aboutus, parent, false));
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.tv1.setText(list.get(position).textT);
        holder.tv2.setText(list.get(position).imageUrl);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Myholder extends RecyclerView.ViewHolder {
        private TextView tv1, tv2;

        Myholder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.title);
            tv2 = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
