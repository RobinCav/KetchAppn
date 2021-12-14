package com.example.ketchappn.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.Fragments.Grupper;
import com.example.ketchappn.Fragments.Minner;
import com.example.ketchappn.Fragments.Venner;
import com.example.ketchappn.R;
import com.example.ketchappn.database.AccessUser;
import com.example.ketchappn.database.GetStatusCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Start_Page extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MaterialToolbar.OnMenuItemClickListener {
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
        MaterialToolbar toolbar = findViewById(R.id.Top_toolbar);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Venner_Navigation);


        toolbar.setOnMenuItemClickListener(this);
        AccessUser accessUser = new AccessUser();
        accessUser.getStatusTask(LoginAct.CurUser, new GetStatusCallback() {


            @Override
            public void getStatus(String status) {
                MenuItem item = toolbar.getMenu().getItem(0);
                item.setTitle(status);

            }
        });

    }


    Minner fragment_minner = new Minner();
    Venner fragment_venner = new Venner();
    Grupper fragment_grupper = new Grupper();
    Aktiviteter fragment_aktiviteter = new Aktiviteter();






    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MaterialToolbar toolbar = findViewById(R.id.Top_toolbar);

        switch (item.getItemId()) {
            case R.id.Minner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_minner).commit();
                toolbar.setTitle("Minner");
                return true;

            case R.id.Venner_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_venner).commit();
                toolbar.setTitle("Venner");

                return true;

            case R.id.Grupper_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_grupper).commit();
                toolbar.setTitle("Grupper");

                return true;

            case R.id.Aktivitet_Navigation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment_aktiviteter).commit();
                toolbar.setTitle("Aktiviteter");

                return true;
        }

        return false;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Activity ac = this;
        switch (item.getItemId()){
            case R.id.changestatus:
                AccessUser accessUser = new AccessUser();
                AlertDialog.Builder d = new AlertDialog.Builder(this);

                View v = getLayoutInflater().inflate(R.layout.dialogstatus, null);
                ListView listView =(ListView) v.findViewById(R.id.emojies);

                listView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                firestore.collection("Statuser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<String> emojies = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                emojies.add(document.getId());
                            }

                            ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,emojies);
                            listView.setAdapter(allItemsAdapter);


                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String s = ( (TextView) view ).getText().toString();

                                    accessUser.changeStatusTask( s ,ac);

                                }
                            });



                        }
                    }
                });
                d.setView(v);
                AlertDialog dialog = d.create();
                dialog.show();

                break;
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
                AlertDialog dd = mBuilder.create();
                dd.show();

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


