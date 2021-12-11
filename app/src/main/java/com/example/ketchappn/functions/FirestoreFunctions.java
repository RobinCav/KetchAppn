package com.example.ketchappn.functions;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
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

}
