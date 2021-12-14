

package com.example.ketchappn.Fragments;
import com.example.ketchappn.GroupChatActivity;
import com.example.ketchappn.R;

import com.example.ketchappn.functions.FirestoreFunctions;
import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;

import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Grupper extends Fragment  {
    private static final String ARRAGEMENTER = "Arrangement";
    private static final String USER = "User";

    ArrayList<QueryDocumentSnapshot> list;

    RecyclerView  recyclerView;
    LinearLayout linearLayout;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference database = db.collection(ARRAGEMENTER);
    FirestoreFunctions firestoreFunctions;
    ArrayList<String> JoinedActivity_names;

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

        linearLayout = view.findViewById(R.id.linearLayout_gruppe);

        list = new ArrayList<>();
        JoinedActivity_names = new ArrayList<>();


        //firestoreFunctions.getEventsToDisplay(recyclerView,getContext(),this);

        databaseUser.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ArrayList<HashMap<String, Object>> joinedActivity = (ArrayList<HashMap<String, Object>>) document.get("JoinedActivity");
                    if (LoginAct.CurUser.getEmail().equals(document.getId())) {
                        for(int i = 0; i< (joinedActivity != null ? joinedActivity.size() : 0); i++) {
                            Log.d("joined", " =>" + joinedActivity.get(0).get("Name"));
                            list.add(document);
                            recyclerView.setAdapter(new recyclerAdapter(getContext(), list, this));
                        }
                    }
                }
            }
            else{
                Log.d(USER,"Error ",task.getException());
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