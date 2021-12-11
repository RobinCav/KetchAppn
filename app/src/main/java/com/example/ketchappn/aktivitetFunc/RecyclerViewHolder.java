package com.example.ketchappn.aktivitetFunc;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public String chosenAkt;
    private Button view;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String z = view.getText().toString();
                Aktiviteter.changeText(z);

            }

        });


    }


    public TextView getView(){
        return view;
    }
}
