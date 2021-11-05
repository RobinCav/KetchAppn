package com.example.ketchappn.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ketchappn.R;
import com.example.ketchappn.Start_Page;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterAct extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText username;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        Button loginbtn = (Button)findViewById(R.id.GoLogin);
        Button RegisterBtn = (Button)findViewById(R.id.RegisterNow);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        username = (EditText)findViewById(R.id.username);

        loginbtn.setOnClickListener(this);
        RegisterBtn.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void setDocument(User user) {

        Map<String, Object> userHashMap = new HashMap<>();
        userHashMap.put("Email", user.getEmail());
        userHashMap.put("Username", user.getUsername());
        userHashMap.put("UserFriendList", user.getFriends());


        firestore.collection("FriendList").document(user.getEmail())
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.RegisterNow:
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("RegisterSuccess", "createUserWithEmail:success");
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    Intent sendToLogin = new Intent(getApplicationContext(), LoginAct.class);

                                    User user = new User(username.getText().toString(), email.getText().toString() );
                                    setDocument(user);
                                    startActivity(sendToLogin);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("RegisterFail", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterAct.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

                break;
            case R.id.GoLogin:
                Intent log = new Intent(getApplicationContext(), LoginAct.class);
                startActivity(log);
                break;

        }
    }
}