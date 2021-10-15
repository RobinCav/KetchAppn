package com.example.ketchappn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ketchappn.functions.FirestoreFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private DocumentReference docReference;
    private CollectionReference collectionReference;
    public ArrayList<Aktivitet> aktiviteter;
    private FirestoreFunctions firestoreFunctions;

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R. layout.activity_main);

        firestore = FirebaseFirestore.getInstance();
        aktiviteter = new ArrayList<>();
        getMultipleDocs();

        //System.out.println(aktiviteter.size());

        text = (TextView) findViewById(R.id.textView);
//        text.setText(String.valueOf(aktiviteter.size()));

       // Log.d("Størrelse på array", String.valueOf(aktiviteter.size()));


        //setDocument("Volleyball", "jjs", 5);

        Button buttonOne = findViewById(R.id.symbolChosen);
        buttonOne.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.out.println("Button Clicked");

                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity2.class);
                activity2Intent.putExtra("Aktivitet", text.getText());
                startActivity(activity2Intent);
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void onClick(View v) {

       generateSymbols();

    }

    //Lager knapper for hver aktivitet vi har registrert i databasen
    public void generateSymbols() {

        LinearLayout layout = (LinearLayout) findViewById(R.id.rootlayout);
        for (int i = 0; i < aktiviteter.size(); i++){
            Button btn = new Button(this);
            btn.setText(String.valueOf(aktiviteter.get(i).getName()));
            int act = i;
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    text = (TextView) findViewById(R.id.textView);
                    text.setText(String.valueOf(aktiviteter.get(act).getName()));
                }
            });
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(btn);
        }
    }




    public void getDocument(){
        DocumentReference docRef = firestore.collection("Aktiviteter").document("Fotball");
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
                                Aktivitet aktivitet = document.toObject(Aktivitet.class);
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

    public void createObjectFromFB() {
        DocumentReference docRef = firestore.collection("Aktiviteter").document("Fotball");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Aktivitet aktivitet = documentSnapshot.toObject(Aktivitet.class);
                Log.d("TAG", aktivitet.toString());
            }
        });
    }

    public void setDocument(String name, String url, int id) {

        Map<String, Object> aktivitet = new HashMap<>();
        aktivitet.put("name", name);
        aktivitet.put("url", url);
        aktivitet.put("id", id);

        firestore.collection("Aktiviteter").document(name)
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
}}