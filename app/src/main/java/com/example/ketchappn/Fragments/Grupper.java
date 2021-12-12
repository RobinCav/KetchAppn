

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;

import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import android.content.pm.ActivityInfo;
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
import java.util.List;


public class Grupper extends Fragment  {
    private static final String ARRAGEMENTER = "Arrangement";

    ArrayList<QueryDocumentSnapshot> list;

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference database = db.collection(ARRAGEMENTER);
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_grupper, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        recyclerView = view.findViewById(R.id.recyclerview_GRUPPE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        CollectionReference users = firestore.collection("User");
        list = new ArrayList<>();

        database.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> venner = (List<String>) document.get("venner");
                            System.out.println("Du har email: " + LoginAct.CurUser.getEmail());
                            if (venner != null) {
                                for (int i = 0; i < venner.size(); i++) {
                                    Log.d(ARRAGEMENTER, " => " + venner.get(i));
                                    if (LoginAct.CurUser.getEmail().equals(venner.get(i)) || LoginAct.CurUser.getEmail().equals(document.get("host").toString())) {
                                        list.add(document);
                                        recyclerView.setAdapter(new recyclerAdapter(getContext(), list));
                                        break;
                                    }
                                }
                            }

                        }
                    }
                    else{
                        Log.d("Nigga","Fisk",task.getException());
                    }
                });
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
}