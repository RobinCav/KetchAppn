

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;

import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;



import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;


public class Grupper extends Fragment  {
    private static final String ARRAGEMENTER = "Arrangement";

    ArrayList<QueryDocumentSnapshot> list = new ArrayList<>();

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference database = db.collection(ARRAGEMENTER);

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grupper, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerviewGRUPPE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        database.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            HashMap<String, Object> d = (HashMap<String, Object>) document.get("aktivitet");
                            String t =  document.get("tid").toString();
                            Log.d(ARRAGEMENTER, " => " + document.get("aktivitet"));
                            list.add(document);
                            recyclerView.setAdapter(new recyclerAdapter(getContext(), list));
                        }
                    }
                    else{
                        Log.d("Nigga","Fisk",task.getException());
                    }
                });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
    /*
    private void EventChangeListener(){
        database.collection("Aktiviteter")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if(error!=null){
                            if(pd.isShowing()){
                                pd.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                list.add(dc.getDocument().toObject(groupDB.class));
                            }

                            adapter.notifyDataSetChanged();
                            if(pd.isShowing()){
                                pd.dismiss();
                            }
                        }
                    }
                });
    }*/
}