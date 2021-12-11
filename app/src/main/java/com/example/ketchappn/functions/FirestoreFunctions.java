package com.example.ketchappn.functions;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirestoreFunctions {

    private FirebaseFirestore firestore, firebore;
    private DocumentReference docReference;
    private CollectionReference collectionReference;
    private DatabaseReference eventRef;

    public void addObjectToFirebase(String collectionpath,String collectionName, Object object) {
        firestore = FirebaseFirestore.getInstance();
        docReference = firestore.collection(collectionpath).document(collectionName);
        docReference.set(object);
    }



    public void startArrangement(Arrangement arr) {

        firestore = FirebaseFirestore.getInstance();

        Map<String, Object> aktivitet = new HashMap<>();
        aktivitet.put("Name", arr.getCollectionname());
        aktivitet.put("Symbol", arr.getAktivitet().getSymbol());
        aktivitet.put("Sted", arr.getSted());

        firestore.collection("User").document(LoginAct.CurUser.getEmail())
                .set(aktivitet)
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




}
