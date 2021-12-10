package com.example.ketchappn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.database.AccesUser;
import com.example.ketchappn.database.FireBaseCallBack;
import com.example.ketchappn.functions.FirestoreFunctions;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;
import com.google.type.DateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class StartAktivitetActivity extends AppCompatActivity {

    private List<Aktivitet> aktiviteter = new ArrayList<>();
    private AccesUser accesUser = new AccesUser();
    private FirestoreFunctions firestoreFunctions = new FirestoreFunctions();
    private ArrayList venner = new ArrayList();
    private int count;
    private LocalTime localTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_aktivitet);

        //Henter string fra fragment_activity
        String aktivitet = getIntent().getStringExtra("Aktivitet");

        //Henter array fra firebase
        aktiviteter = AktivitetBtnAdapter.sendArray();

        //Lager objekt for valgt aktivitet, denne går gjennom arraylisten fra firebase.
        Aktivitet valgtAktivitet = new Aktivitet();

        for (Aktivitet a : aktiviteter){
            if (a.getName().equals(aktivitet)) {
               valgtAktivitet = a;
            }
        }

        Context context = this;

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkboxlist);


        accesUser.getFriendsTask(new FireBaseCallBack() {
            @Override
            public void onCallback(ArrayList<String> friends) {

                for (int i = 0; i < friends.size(); i++){
                    CheckBox cn = new CheckBox(context);
                    cn.setText(friends.get(i));
                    cn.setId(i);
                    count++;
                    linearLayout.addView(cn);
                }


            }
        });



        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(valgtAktivitet.getName());

        EditText editText = (EditText) findViewById(R.id.editPlace);

        Button button =(Button)findViewById(R.id.button);
        Aktivitet finalValgtAktivitet = valgtAktivitet;
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int hour, minute;
                String place;


                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                Editable editable = editText.getText();
                place = editable.toString();


                if (place.equals("")) {
                    textView.setText("Bestem sted");
                }

                else {

                    for (int i = 0; i < count; i++){
                        CheckBox checkbox = (CheckBox) findViewById(i);
                        if (checkbox.isChecked()){
                            venner.add(checkbox.getText().toString());
                        }
                    }

                    @SuppressLint("DefaultLocale")
                    Arrangement arrangement = new Arrangement(finalValgtAktivitet,place,String.format("%02d:%02d", hour, minute ),LoginAct.CurUser, venner );
                    firestoreFunctions.addObjectToFirebase("Arrangement",arrangement.getCollectionname(), arrangement );
                    Log.d("Informasjon om arrangement", arrangement.toString());

                    textView.setText("");
                }


            }
        });

        Button buttonBack = (Button) findViewById(R.id.button2);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aktiviteter.changeText("");
                finish();
            }
        });
    }
    }
