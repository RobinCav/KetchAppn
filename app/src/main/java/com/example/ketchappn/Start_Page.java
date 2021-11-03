package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.ketchappn.Fragments.AktivitetFrag;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.Venner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        TextView tview = (TextView)findViewById(R.id.curUser);
        tview.setText("Welcome back, " + user.getDisplayName());

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Venner_Navigation);



    }


    Minner fragment_minner = new Minner();
    Venner fragment_venner = new Venner();
    Grupper fragment_grupper = new Grupper();
    AktivitetFrag fragment_aktivitetFrag = new AktivitetFrag();






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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_aktivitetFrag).commit();
                return true;
        }

        return false;
    }
}



