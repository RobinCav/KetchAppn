

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;

import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;



import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Grupper extends Fragment  {
    private static final String ARRAGEMENTER = "Arrangement";
    private static final String USER = "User";

    ArrayList<QueryDocumentSnapshot> list;
    ArrayList<String> JoinedActivity_names;

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference database = db.collection(ARRAGEMENTER);
    CollectionReference databaseUser = db.collection(USER);

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_grupper, container, false);
        // Add the following lines to create RecyclerView

        recyclerView = view.findViewById(R.id.recyclerview_GRUPPE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        databaseUser.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ArrayList<HashMap<String, Object>> joinedActivity = (ArrayList<HashMap<String, Object>>) document.get("JoinedActivity");
                    //Log.d("GRUPPER_SOM_SKAL_VISES","Du har email: " + LoginAct.CurUser.getEmail());
                    if (joinedActivity != null && LoginAct.CurUser.getEmail().equals(document.getId())) {
                        for (int i = 0; i < joinedActivity.size(); i++) {
                            Log.d(USER, " Email: " + document.getId() + " => " + joinedActivity.get(i).get("Name").toString());
                            if(joinedActivity.get(i).get("Name") != null) {
                                JoinedActivity_names.add(String.valueOf(joinedActivity.get(i).get("Name")));
                            }
                        }
                    }

                }
            }
            else{
                Log.d(USER,"Error ",task.getException());
            }
        });
        list = new ArrayList<>();

        database.get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            List<String> venner = (List<String>) document.get("venner");
                            //Log.d("GRUPPER_SOM_SKAL_VISES","Du har email: " + LoginAct.CurUser.getEmail());
                            if (venner != null) {
                                for (int i = 0; i < venner.size(); i++) {
                                    //Log.d(ARRAGEMENTER, " => " + venner.get(i));
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
                        Log.d(ARRAGEMENTER,"Error ",task.getException());
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
}