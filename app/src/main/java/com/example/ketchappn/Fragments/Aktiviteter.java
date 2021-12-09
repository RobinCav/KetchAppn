package com.example.ketchappn.Fragments;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.TextView;

import com.example.ketchappn.StartAktivitetActivity;
import com.example.ketchappn.R;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;

import com.example.ketchappn.models.Aktivitet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;

public class Aktiviteter extends Fragment {

    List<Aktivitet> aktivitetArray = new ArrayList<>();
    private RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private static TextView chosenAkt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        aktivitetArray.clear();

        View view = inflater.inflate(R.layout.fragment_aktiviteter, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new AktivitetBtnAdapter(aktivitetArray));

        chosenAkt = (TextView) view.findViewById(R.id.textViewAkt);

        // Denne funksjonen lagrer aktivitetene fra firebase, dette må skje etter koblingen med firebase
        aktivitetArray = AktivitetBtnAdapter.sendArray();


        Button btnVidere = (Button) view.findViewById(R.id.videreBtn);
        btnVidere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chosenAkt.getText().toString().equals("")){
                    chosenAkt.setText("Velg aktivitet");
                }

                else {

                    Intent i = new Intent(getActivity(), StartAktivitetActivity.class);
                    i.putExtra("Aktivitet", chosenAkt.getText().toString());
                    startActivity(i);
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("Aktiviteter").whereGreaterThanOrEqualTo("id", 0).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            aktivitetArray.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Aktivitet aktivitet = document.toObject(Aktivitet.class);
                                aktivitetArray.add(aktivitet);
                                recyclerView.setAdapter(new AktivitetBtnAdapter(aktivitetArray));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });



        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static void changeText(String x){
       chosenAkt.setText(x);
    }

}
