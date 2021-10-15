package com.example.ketchappn.recyclerViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.R;

public class recyclerviewholder extends RecyclerView.ViewHolder {
    public TextView sted;
    public TextView tid;

    public recyclerviewholder(@NonNull View itemView) {
        super(itemView);
        sted = itemView.findViewById(R.id.stedText);
        tid = itemView.findViewById(R.id.tidText);

    }

    public TextView getTid() {
        return tid;
    }

    public void setTid(TextView tid) {
        this.tid = tid;
    }

    public TextView getSted() {
        return sted;
    }

    public void setSted(TextView sted) {
        this.sted = sted;
    }
}
