package com.example.ketchappn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.R;
import com.example.ketchappn.recyclerViewHolder.recyclerviewholder;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerviewholder> {
    private Context context;
    private ArrayList<Grupper> arrayList;

    public recyclerAdapter(Context context, ArrayList<Grupper> arrayList){
        this.context=context;
        this.arrayList=arrayList;

    }

    @NonNull
    @Override
    public recyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_group, parent, false);
        return new recyclerviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewholder holder, int position) {
        Grupper gruppe = arrayList.get(position);
        holder.sted.setText(gruppe.getSted());
        holder.tid.setText(gruppe.getTid());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}