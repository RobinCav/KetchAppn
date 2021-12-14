package com.example.ketchappn.functions;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
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

    public void getEventsToDisplay(RecyclerView view, Context context){
        String ARRAGEMENTER = "Arrangement";
        String USER = "User";

        ArrayList<QueryDocumentSnapshot> list = new ArrayList<>();
        ArrayList<String> JoinedActivity_names = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference database = db.collection(ARRAGEMENTER);
        CollectionReference databaseUser = db.collection(USER);



        databaseUser.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ArrayList<HashMap<String, Object>> joinedActivity = (ArrayList<HashMap<String, Object>>) document.get("JoinedActivity");

                    if (joinedActivity != null && LoginAct.CurUser.getEmail().equals(document.getId())) {
                        for (int i = 0; i < joinedActivity.size(); i++) {
                            Log.d(USER, " Email: " + document.getId() + " => " + joinedActivity.get(i).get("Name").toString());
                            if(joinedActivity.get(i).get("Name") != null) {
                                JoinedActivity_names.add((joinedActivity.get(i).get("Name").toString()));
                                Log.d("Joined Activity names for active user: ", " => " + JoinedActivity_names);

                            }
                        }
                    }

                }
            }
            else{
                Log.d(USER,"Error ",task.getException());
            }
            database.get()
                    .addOnCompleteListener(task_Arrangement -> {
                        if(task_Arrangement.isSuccessful()) {

                            for (QueryDocumentSnapshot document_Arrangement : task_Arrangement.getResult()) {
                                if(JoinedActivity_names != null) {
                                    for (int h = 0; h < JoinedActivity_names.size(); h++) {
                                        if (document_Arrangement.getId().equals(JoinedActivity_names.get(h))) {
                                            //Log.d("GRUPPER_SOM_SKAL_VISES", "Arrangement: " + JoinedActivity_names.size() + " " + document_Arrangement.getId() + " = " + JoinedActivity_names.get(h));
                                            list.add(document_Arrangement);
                                            view.setAdapter(new recyclerAdapter(context, list));
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                        else{
                            Log.d(ARRAGEMENTER,"Error ",task_Arrangement.getException());
                        }
                    });
        });

    }

}
