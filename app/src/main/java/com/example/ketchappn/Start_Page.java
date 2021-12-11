package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ketchappn.Activities.UserSettingsActivity;
import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.Venner;
import com.example.ketchappn.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.time.Duration;

public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;



    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();


         Toast.makeText(getBaseContext(),"Velkommen tilbake " + LoginAct.CurUser.getUsername(), Toast.LENGTH_LONG);
//        tview.setText("Welcome back, " + LoginAct.CurUser.getUsername());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Venner_Navigation);


    }


    Minner fragment_minner = new Minner();
    Venner fragment_venner = new Venner();
    Grupper fragment_grupper = new Grupper();
    Aktiviteter fragment_aktiviteter = new Aktiviteter();






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Minner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_minner).commit();
                return true;

            case R.id.Venner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_venner).commit();
                return true;

            case R.id.Grupper_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_grupper).commit();
                return true;

            case R.id.Aktivitet_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_aktiviteter).commit();
                return true;
        }

        return false;
    }




}



