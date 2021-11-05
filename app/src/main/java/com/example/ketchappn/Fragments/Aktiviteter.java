package com.example.ketchappn.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ketchappn.R;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;

import com.example.ketchappn.aktivitetFunc.RecyclerViewHolder;
import com.example.ketchappn.models.Aktivitet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;

public class Aktiviteter extends Fragment {

    List<Aktivitet> akt = new ArrayList<>();
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private static TextView chosenAkt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_aktiviteter, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        firestore = FirebaseFirestore.getInstance();

        chosenAkt = (TextView) view.findViewById(R.id.textViewAkt);

        changeText("Heyzz");






        firestore.collection("Aktiviteter")
                .whereGreaterThanOrEqualTo("id", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Goteeem", document.getId() + " => " + document.getData());
                                Aktivitet aktivitet = document.toObject(Aktivitet.class) ;
                                Log.d("HHH", aktivitet.getName());
                                akt.add(aktivitet);
                                Log.d("Kolleksjon", String.valueOf(akt));
                                recyclerView.setAdapter(new AktivitetBtnAdapter(akt));
                            }
                        }

                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }


                    }
                });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void changeText(String x){
       chosenAkt.setText(x);
    }

}

/*public class AktivitetFrag extends Fragment {

    ArrayList<Aktivitet> list = new ArrayList<>();
    RecyclerView recyclerView;

    private FirebaseFirestore firestore;
    private DocumentReference docReference;
    private CollectionReference collectionReference;
    private FirestoreFunctions firestoreFunctions;

    private TextView text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMultipleDocs();


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_aktivitet, container, false);

        //recyclerAdapter adapter = new recyclerAdapter(akt);

        firestore = FirebaseFirestore.getInstance();


        ListView lstItems = (ListView)v.findViewById(R.id.acts);



        CollectionReference colRef = firestore.collection("Aktiviteter");

        ArrayList<Button> btns = new ArrayList<>();

        colRef.whereGreaterThanOrEqualTo("id", 0).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Goteeem", document.getId() + " => " + document.getData());
                                Aktivitet aktivitet = document.toObject(Aktivitet.class);
                                list.add(aktivitet);

                                Button btn = new Button(v.getContext());



                                btns.add(btn);

                            }

                        }

                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }


                });



        return v;

    }

    public void getMultipleDocs() {

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Aktiviteter")
                .whereGreaterThanOrEqualTo("id", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Aktivitet> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Goteeem", document.getId() + " => " + document.getData());
                                Aktivitet aktivitet = document.toObject(Aktivitet.class);
                                Log.d("HHH", aktivitet.getName());
                                list.add(aktivitet);
                                Log.d("Kolleksjon", String.valueOf(list));
                            }

                            processData(list);
                        }

                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }


                    }
                });

    }

    void processData(List<Aktivitet> data){
        list.addAll(data);
    }



}
*/
