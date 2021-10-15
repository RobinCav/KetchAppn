package com.example.ketchappn.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ketchappn.MainActivity2;
import com.example.ketchappn.R;
import com.example.ketchappn.functions.FirestoreFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Aktivitet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Aktivitet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseFirestore firestore;
    private DocumentReference docReference;
    private CollectionReference collectionReference;
    public ArrayList<com.example.ketchappn.Aktivitet> aktiviteter;
    private FirestoreFunctions firestoreFunctions;

    private TextView text;

    public Aktivitet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Aktivitet.
     */
    // TODO: Rename and change types and number of parameters
    public static Aktivitet newInstance(String param1, String param2) {
        Aktivitet fragment = new Aktivitet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }


    public void onClick(View v){
        generateSymbols(v);
    }
    //Lager knapper for hver aktivitet vi har registrert i databasen
    public void generateSymbols(View v) {

        ListView layout = (LinearLayout) v.findViewById(R.id.rootlayout);
        for (int i = 0; i < aktiviteter.size(); i++){
            Button btn = new Button(getActivity());
            btn.setText(String.valueOf(aktiviteter.get(i).getName()));
            int act = i;
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    text = (TextView) v.findViewById(R.id.textView);
                    text.setText(String.valueOf(aktiviteter.get(act).getName()));
                }
            });
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(btn);
        }
    }


    public void getMultipleDocs() {

        // [START get_multiple]
        firestore.collection("Aktiviteter")
                .whereGreaterThanOrEqualTo("id", 0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Goteeem", document.getId() + " => " + document.getData());
                                com.example.ketchappn.Aktivitet aktivitet = document.toObject(com.example.ketchappn.Aktivitet.class);
                                aktiviteter.add(aktivitet);
                                Log.d("Akriviter", aktivitet.toString());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        ;
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.activity_main, container, false);


        firestore = FirebaseFirestore.getInstance();
        aktiviteter = new ArrayList<>();
        getMultipleDocs();

        //System.out.println(aktiviteter.size());

        text = (TextView) v.findViewById(R.id.textView);

        Button buttonOne = (Button) v.findViewById(R.id.imageButton1);

        buttonOne.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                    generateSymbols(v);
            }

        });


//        text.setText(String.valueOf(aktiviteter.size()));

        // Log.d("Størrelse på array", String.valueOf(aktiviteter.size()));


        //setDocument("Volleyball", "jjs", 5);

        /*
        buttonOne.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent activity2Intent = new Intent((getContext(), MainActivity2.class);
                activity2Intent.putExtra("Aktivitet", text.getText());
                startActivity(activity2Intent);
            }

        });

         */

        // Inflate the layout for this fragment
        return v;
    }
}