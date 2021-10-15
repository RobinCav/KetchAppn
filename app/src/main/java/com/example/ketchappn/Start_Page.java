package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.ketchappn.Fragments.Aktivitet;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.Venner;
import com.example.ketchappn.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;

    private User user;
    private ArrayList<User> friends;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        firebaseDatabase = FirebaseDatabase.getInstance();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Venner_Navigation);

        friends = new ArrayList<>();
        friends.add(new User(1,"yaqub","yaqubsaid@gmail.com","rrr",null));
        friends.add(new User(2,"robin","robincalv@gmail.com","rrr",null));
        friends.add(new User(3,"aleks","aleks@gmail.com","rrr",null));

        user = new User(0, "karrar", "karrara@gmail.com", "okthendude",friends);

        database  = firebaseDatabase.getReference("user");
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());


    }


    Minner fragment_minner = new Minner();
    Venner fragment_venner = new Venner();
    Grupper fragment_grupper = new Grupper();
    Aktivitet fragment_aktivitet = new Aktivitet();






        @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Minner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment_minner).commit();
                return true;

            case R.id.Venner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment_venner).commit();
                return true;

            case R.id.Grupper_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment_grupper).commit();
                return true;

            case R.id.Aktivitet_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment_aktivitet).commit();
                return true;
        }

        return false;
    }
}



