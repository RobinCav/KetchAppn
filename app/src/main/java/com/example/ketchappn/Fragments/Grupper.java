

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

                    if (joinedActivity != null && LoginAct.CurUser.getEmail().equals(document.getId())) {
                        for (int i = 0; i < joinedActivity.size(); i++) {
                            Log.d(USER, " Email: " + document.getId() + " => " + joinedActivity.get(i).get("Name").toString());
                            if(joinedActivity.get(i).get("Name") != null) {
                                JoinedActivity_names.add((joinedActivity.get(i).get("Name").toString()));
                                Log.d("Joined Activity names for active user: ", " => " + JoinedActivity_names);

                            }
                        }
                    }

                }
            }
            else{
                Log.d(USER,"Error ",task.getException());
            }
            database.get()
                    .addOnCompleteListener(task_Arrangement -> {
                        if(task_Arrangement.isSuccessful()) {

                            for (QueryDocumentSnapshot document_Arrangement : task_Arrangement.getResult()) {
                                if(JoinedActivity_names != null) {
                                    for (int h = 0; h < JoinedActivity_names.size(); h++) {
                                        if (document_Arrangement.getId().equals(JoinedActivity_names.get(h))) {
                                            //Log.d("GRUPPER_SOM_SKAL_VISES", "Arrangement: " + JoinedActivity_names.size() + " " + document_Arrangement.getId() + " = " + JoinedActivity_names.get(h));
                                            list.add(document_Arrangement);
                                            recyclerView.setAdapter(new recyclerAdapter(getContext(), list, this));
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                        else{
                            Log.d(ARRAGEMENTER,"Error ",task_Arrangement.getException());
                        }
                    });
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