

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;
import com.example.ketchappn.database.groupDB;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
//import com.google.firebase.database.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.app.ProgressDialog;
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


public class Grupper extends Fragment  {
    private static final String AKTIVITETER = "Aktiviteter";


    private TextView id, name, url;
    ArrayList<groupDB> list = new ArrayList<>();

    RecyclerView recyclerView;
    recyclerAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference database = db.collection(AKTIVITETER);

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grupper, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerviewGRUPPE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new recyclerAdapter(getContext(),list));
        Log.d(AKTIVITETER, " Hva skjer?");
        database.whereGreaterThanOrEqualTo("id", 0)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Log.d("Nigga", document.getId() + " => " + document.getData());
                            groupDB gdb = document.toObject(groupDB.class);
                            list.add(gdb);
                            recyclerView.setAdapter(new recyclerAdapter(getContext(),list));
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
        setupRecyclerView();

    }

    private void setupRecyclerView(){

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