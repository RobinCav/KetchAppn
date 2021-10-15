

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;
import com.example.ketchappn.adapter.recyclerAdapter;
import com.example.ketchappn.database.groupDB;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;



import android.view.ViewGroup;

import java.util.ArrayList;


public class Grupper extends Fragment {

    Integer tid;
    String sted;
    ArrayList<Grupper> list = new ArrayList<>();
    private RecyclerView recyclerView;
    recyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grupper, container, false);

        // Add the following lines to create RecyclerView
        setupRecyclerview();

        return view;
    }

    private void setupRecyclerview(){
        recyclerView.findViewById(R.id.recyclerview);
        adapter = new recyclerAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        list.add(new groupDB(R.id.tidText, R.id.stedText));

    }

    public Integer getTid(){return tid;}
    public String getSted(){return sted;}
}