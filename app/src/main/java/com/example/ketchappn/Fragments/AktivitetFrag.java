package com.example.ketchappn.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ketchappn.R;
import com.example.ketchappn.functions.FirestoreFunctions;
import com.example.ketchappn.models.Aktivitet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AktivitetFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AktivitetFrag extends Fragment {

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
    public ArrayList<Aktivitet> aktiviteter;
    private FirestoreFunctions firestoreFunctions;

    private TextView text;

    public AktivitetFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AktivitetFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static AktivitetFrag newInstance(String param1, String param2) {
        AktivitetFrag fragment = new AktivitetFrag();
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


    //Lager knapper for hver aktivitet vi har registrert i databasen
    public void generateSymbols(View view) {

        ListView listView = (ListView) view.findViewById(R.id.acts);
        for (int i = 0; i < aktiviteter.size(); i++){
            Button btn = new Button(getActivity());
            btn.setText(String.valueOf(aktiviteter.get(i).getName()));
            int act = i;
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    text = (TextView) getView().findViewById(R.id.textView);
                    text.setText(String.valueOf(aktiviteter.get(act).getName()));
                }
            });
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            ArrayList<String> friendsUserName = new ArrayList<String>();

            for(com.example.ketchappn.models.Aktivitet fr : aktiviteter){
                friendsUserName.add(fr.getName());

            }
            ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,friendsUserName);

            listView.setAdapter(allItemsAdapter);

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
                                com.example.ketchappn.models.Aktivitet aktivitet = document.toObject(com.example.ketchappn.models.Aktivitet.class);
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


        View v = inflater.inflate(R.layout.fragment_aktivitet, container, false);

        firestore = FirebaseFirestore.getInstance();
        aktiviteter = new ArrayList<>();
        ListView lstItems = (ListView)v.findViewById(R.id.acts);

        CollectionReference colRef = firestore.collection("Aktiviteter");

        ArrayList<String> aktivnavner = new ArrayList<>();
        ArrayList<Button> btns = new ArrayList<>();
        colRef.whereGreaterThanOrEqualTo("id", 0).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Goteeem", document.getId() + " => " + document.getData());
                                Aktivitet aktivitet = document.toObject(Aktivitet.class);
                                aktiviteter.add(aktivitet);

                                Button btn = new Button(getContext());
                                btn.setText("String.valueOf(aktiviteter.get(i).getName())");

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        text = (TextView) getView().findViewById(R.id.textView);
                                        text.setText(String.valueOf((aktivitet.getName())));
                                    }
                                });
                                btns.add(btn);

                                ArrayAdapter<Button> allItemsAdapter = new ArrayAdapter<Button>(getContext(), android.R.layout.simple_list_item_1, btns);


                                lstItems.setAdapter(allItemsAdapter);


                                Log.d("Akriviter", aktivitet.toString());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }

                        for (int i = 0; i < aktiviteter.size(); i++) {

                        }


                    }
                });
        return v;
    }
}