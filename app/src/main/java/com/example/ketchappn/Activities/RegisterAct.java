package com.example.ketchappn.Activities;

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
import com.example.ketchappn.database.AccessUser;
import com.example.ketchappn.database.FirestoreFunctions;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterAct extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {


    private FirebaseAuth mAuth;
    @NotEmpty
    @Email
    private EditText email;
    @NotEmpty
    @Password(min = 6, scheme= Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText password;
    @NotEmpty
    private EditText username;
    private FirebaseFirestore firestore;
    private FirestoreFunctions firestoreFunctions;
    private AccessUser accessUser;

    private Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validator = new Validator(this);
        accessUser = new AccessUser();
        validator.setValidationListener(this);
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




    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.RegisterNow:
                validator.validate();


                break;
            case R.id.GoLogin:
                Intent log = new Intent(getApplicationContext(), LoginAct.class);
                startActivity(log);
                break;

        }
    }

    @Override
    public void onValidationSucceeded() {
        firestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    ArrayList<String> allusers = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        allusers.add(document.get("Username").toString());
                    }

                    if (!allusers.contains(username.getText().toString())) {
                        Task<AuthResult> ta = mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("RegisterSuccess", "createUserWithEmail:success");
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    Intent sendToLogin = new Intent(getApplicationContext(), LoginAct.class);

                                    User user = new User(username.getText().toString(), email.getText().toString(), new ArrayList<>());
                                    accessUser.createUser(user);
                                    startActivity(sendToLogin);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("RegisterFail", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterAct.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }else{
                        username.setError("Navnet allerede eksisterer");
                        username.setBackgroundResource(R.drawable.edittexterror);
                    }

                    }

                }

        });



    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}