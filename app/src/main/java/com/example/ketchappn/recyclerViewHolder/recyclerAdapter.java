package com.example.ketchappn.recyclerViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.R;
import com.example.ketchappn.database.groupDB;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerviewholder> {
    Context context;
    ArrayList<groupDB> arrayList;

    public class recyclerviewholder extends RecyclerView.ViewHolder {
        private TextView sted;
        private TextView tid;


        public recyclerviewholder(@NonNull View itemView) {
            super(itemView);
            sted = itemView.findViewById(R.id.stedText);
            tid = itemView.findViewById(R.id.tidText);

        }
        public TextView getTid() {
            return tid;
        }
        public TextView getSted() {
            return sted;
        }
    }



    public recyclerAdapter(ArrayList<groupDB> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public recyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false);
        return new recyclerviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewholder viewholder, int position) {
        groupDB db = arrayList.get(position);
        viewholder.getSted().setText(db.getSted());
        viewholder.getTid().setText(Integer.toString(db.getTid()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}