package com.example.ketchappn.aktivitetFunc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.R;
import com.example.ketchappn.models.Aktivitet;

import java.util.ArrayList;
import java.util.List;

public class AktivitetBtnAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Aktivitet> akt = new ArrayList<>();
    int counter = 0;


    public AktivitetBtnAdapter(List<Aktivitet> akt) {
        this.akt = akt;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.button_list_aktiviteter;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_list_aktiviteter, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.getView().setText(akt.get(position).getName());
        counter++;
    }

    @Override
    public int getItemCount() {
        return akt.size();
    }



}

