package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.Venner;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;


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



