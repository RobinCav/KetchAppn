package com.example.ketchappn.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Activities.GroupChatActivity;
import com.example.ketchappn.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.recyclerviewholder> {
    private Context context;
    private final ArrayList<QueryDocumentSnapshot> list;
    private Grupper grupper;

    public recyclerAdapter(Context ctx, ArrayList<QueryDocumentSnapshot> list){
        this.context = ctx;
        this.list = list;
    }



    @NonNull
    @Override
    public recyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group, parent, false);
        return new recyclerviewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull recyclerviewholder viewholder, int position) {
        ArrayList<HashMap<String, Object>> d = (ArrayList<HashMap<String, Object>>) list.get(position).get("JoinedActivity");
        //QueryDocumentSnapshot bd  = list.get(position);
        viewholder.sted.setText(d.get(position).get("Sted").toString());
        viewholder.symbol.setText(d.get(position).get("Symbol").toString());
        viewholder.tid.setText(d.get(position).get("Tid").toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onAttachedToRecyclerView(
            @NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class recyclerviewholder extends RecyclerView.ViewHolder {
        private TextView sted, symbol, tid;
        private LinearLayout layout;

        public recyclerviewholder(@NonNull View itemView) {
            super(itemView);
            sted = itemView.findViewById(R.id.nameText);
            symbol = itemView.findViewById(R.id.symbolText);
            tid = itemView.findViewById(R.id.tid_text);
            layout = itemView.findViewById(R.id.linearLayout_gruppe);


            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GroupChatActivity.class);
                    intent.putExtra("Symbol", symbol.getText().toString());
                    intent.putExtra("Place",sted.getText().toString());
                    intent.putExtra("Time",tid.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        private void startActivity() {
        }

        public TextView getSted() { return sted; }
        public void setSted(TextView sted) { this.sted = sted; }
        public TextView getSymbol() { return symbol; }
        public void setSymbol(TextView symbol) { this.symbol = symbol; }
        public TextView getTid() { return tid; }
        public void setTid(TextView tid) { this.tid = tid; }
        public void getLinear(LinearLayout layout) {this.layout = layout; }
    }
}