

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;
import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;
import com.example.ketchappn.database.groupDB;
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


public class Grupper extends Fragment {
    static final String TAG = "watafak";
    Integer tid;
    String sted;
    ArrayList<groupDB> list = new ArrayList<>();
    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "Yolo");
        View view = inflater.inflate(R.layout.fragment_grupper, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerviewP);
        recyclerAdapter adapter = new recyclerAdapter(list);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Yolo");
        list = new ArrayList<>();
        list.add(new groupDB(1400, "Fredrikstad"));

    }

    public Integer getTid(){return tid;}
    public String getSted(){return sted;}
}