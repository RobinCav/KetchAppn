package com.example.ketchappn;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ketchappn.Activities.UserSettingsActivity;
import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.RegisterAct;
import com.example.ketchappn.Fragments.RegisterLoginAct;
import com.example.ketchappn.Fragments.Venner;
import com.example.ketchappn.database.AccesUser;
import com.example.ketchappn.database.FireBaseUserCallBack;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;


public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MaterialToolbar.OnMenuItemClickListener {
    BottomNavigationView bottomNavigationView;
    private MaterialToolbar toolbar;
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
        toolbar = findViewById(R.id.Top_toolbar);

        toolbar.setOnMenuItemClickListener(this);
        AccesUser accesUser = new AccesUser();
        /*
        accesUser.getStatusTask(LoginAct.CurUser, new FireBaseUserCallBack() {
            @Override
            public void onCallBackGetFriends(ArrayList<HashMap<String, Object>> friends) {

            }

            @Override
            public void onCallBackGetStatus(String status) {
                toolbar.getMenu().getItem(0).setTitle(status);

            }
        });

         */

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


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.changepass:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                View blue = getLayoutInflater().inflate(R.layout.dialog_changepass, null);
                EditText oldpass = (EditText) blue.findViewById(R.id.oldpass);
                EditText newpass = (EditText) blue.findViewById(R.id.newpass);

                MaterialButton nButton = (MaterialButton) blue.findViewById(R.id.changepassbtn);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                nButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(oldpass.getText().toString().length() != 0 && newpass.getText().toString().length() !=0 ){
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(LoginAct.CurUser.getEmail(), oldpass.getText().toString());
                            mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Pass", "Password updated");
                                                    Toast.makeText(getApplicationContext(), "Passord har blitt endret",
                                                            Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), Start_Page.class));

                                                } else {
                                                    Log.d("Pass", "Error password not updated");
                                                    Toast.makeText(getApplicationContext(), "kunne ikke endre passordet",
                                                            Toast.LENGTH_SHORT).show();
                                                    newpass.setError("passordet er for kort");
                                                    newpass.setBackgroundResource(R.drawable.edittexterror);

                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error ",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d("Auth", "Error auth failed");
                                        oldpass.setError("Gammel passord er skrevet feil");

                                        oldpass.setBackgroundResource(R.drawable.edittexterror);
                                    }
                                }
                            });
                        }else {
                            oldpass.setError("må fylles");
                            oldpass.setBackgroundResource(R.drawable.edittexterror);
                            newpass.setError("må fylles");
                            newpass.setBackgroundResource(R.drawable.edittexterror);

                        }




                    }
                });
                mBuilder.setView(blue);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                return true;
            case R.id.Loggout:
                mAuth.signOut();
                Intent sendOut = new Intent(getApplicationContext(), RegisterLoginAct.class);
                startActivity(sendOut);
                return true;
        }
        return false;
    }
}



