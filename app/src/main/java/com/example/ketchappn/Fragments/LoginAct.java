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

import java.util.HashMap;
import java.util.Map;

public class LoginAct extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth mAuth;
    private EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Button loginbtn = (Button)findViewById(R.id.loginNow);
        Button registerbtn = (Button)findViewById(R.id.GoRegister);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        loginbtn.setOnClickListener(this);
        registerbtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginNow:
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignSuccess", "signInWithEmail:success");
                                    Intent sendToStart = new Intent(getApplicationContext(), Start_Page.class);

                                    startActivity(sendToStart);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("SignFailed", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginAct.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                break;
            case R.id.GoRegister:
                Intent reg = new Intent(getApplicationContext(), RegisterAct.class);
                startActivity(reg);
                break;



        }
    }
}