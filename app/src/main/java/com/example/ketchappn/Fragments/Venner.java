package com.example.ketchappn.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ketchappn.R;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Venner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Venner extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Venner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Venner.
     */
    // TODO: Rename and change types and number of parameters
    public static Venner newInstance(String param1, String param2) {
        Venner fragment = new Venner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseFirestore firestore;
    private User user;
    private ArrayList<User> friends;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        firestore = FirebaseFirestore.getInstance();

        // Get a reference to our posts
        DocumentReference docRef = firestore.collection("Users").document("karrar");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            private static final String TAG = "TAG";

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
    /*
        friends = new ArrayList<>();
        friends.add(new User(1,"yaqub","yaqubsaid@gmail.com","rrr",null));
        friends.add(new User(2,"robin","robincalv@gmail.com","rrr",null));
        friends.add(new User(3,"aleks","aleks@gmail.com","rrr",null));

        user = new User(0, "karrar", "karrara@gmail.com", "okthendude",friends);

        auth = FirebaseAuth.getInstance();

        setDocument(user);

     */
        //auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());



    public void setDocument(User user) {

        Map<String, Object> userHashMap = new HashMap<>();
        userHashMap.put("id", user.getId());
        userHashMap.put("username", user.getUsername());
        userHashMap.put("email", user.getEmail());
        userHashMap.put("password", user.getPassword());
        userHashMap.put("friends", user.getFriends());

        firestore.collection("Users").document(user.getUsername())
                .set(userHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venner, container, false);
    }
}