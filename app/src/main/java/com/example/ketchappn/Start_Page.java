package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.ketchappn.fragments.Aktivitet;
import com.example.ketchappn.fragments.Grupper;
import com.example.ketchappn.fragments.Minner;
import com.example.ketchappn.fragments.Venner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Venner_Navigation);
        System.out.println("lmao");
    }

    Minner fragment_minner = new Minner();
    Venner fragment_venner = new Venner();
    Grupper fragment_grupper = new Grupper();
    Aktivitet fragment_aktivitet = new Aktivitet();





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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_aktivitet).commit();
                return true;
        }

        return false;
    }
}



