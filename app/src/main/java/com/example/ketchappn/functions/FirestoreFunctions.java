package com.example.ketchappn.functions;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreFunctions {

    private FirebaseFirestore firestore;
    private DocumentReference docReference;
    private CollectionReference collectionReference;

    public void addActivityWithObject(String collectionName, Object object) {
        firestore = FirebaseFirestore.getInstance();
        docReference = firestore.collection("Aktiviteter").document(collectionName);
        docReference.set(object);
    }

    public void setDocument(String name, String url, int id) {

        firestore = FirebaseFirestore.getInstance();

        Map<String, Object> aktivitet = new HashMap<>();
        aktivitet.put("name", name);
        aktivitet.put("url", url);
        aktivitet.put("id", id);

        firestore.collection("Aktiviteter").document("Football")
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
