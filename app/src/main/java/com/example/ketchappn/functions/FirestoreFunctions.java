package com.example.ketchappn.functions;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.recyclerViewHolder.recyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirestoreFunctions {

    private FirebaseFirestore firestore;
    private DocumentReference docReference;

    public void addObjectToFirebase(String collectionpath,String collectionName, Object object) {
        firestore = FirebaseFirestore.getInstance();
        docReference = firestore.collection(collectionpath).document(collectionName);
        docReference.set(object);
    }

    public DocumentReference getDocRef(String collectionpath, String collectionName) {
        firestore = FirebaseFirestore.getInstance();
        docReference = firestore.collection(collectionpath).document(collectionName);
        return docReference;
    }

    public void sendInvite(String email, Map<String, Object> data){
        docReference= getDocRef("User", email);

        docReference.update("JoinedActivity", FieldValue.arrayUnion(data)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            Log.d("Invite", "Invite was sent to " + email);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.d("Invite fail", "Could not send invite to " + email);
            }
        });

    }

    public void sendMessage(String name, Map<String, Object> data){
        docReference= getDocRef("Arrangement", name);

        docReference.update("meldinger", FieldValue.arrayUnion(data)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("Message", "Message was sent from ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Invite fail", "Could not send message");
            }
        });

    }

}
