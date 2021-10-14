package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Aktiviteterr");
        person p = new person("Fotball", "2");
        collectionReference.add(p);
*/
        setContentView(R. layout.fragment_login);

        btn = findViewById(R.id.signInButton);
/*
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);

            }
        });*/
    }



    class person {
        private String name, email;

        public person(String name, String email){
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}