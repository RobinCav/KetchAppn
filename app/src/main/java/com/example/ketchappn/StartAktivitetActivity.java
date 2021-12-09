package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
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
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;

import java.util.ArrayList;
import java.util.List;


public class StartAktivitetActivity extends AppCompatActivity {

    private List<Aktivitet> aktiviteter = new ArrayList<>();
    private AccesUser accesUser = new AccesUser();

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

        ArrayList<String> venner = new ArrayList<>();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkboxlist);

        for (int i = 0; i < 9; i++){
            CheckBox cn = new CheckBox(this);
            cn.setText(String.valueOf(i));
            cn.setId(i);
            linearLayout.addView(cn);
        }


        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(valgtAktivitet.getName());

        /*ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.drawable.ball);
        linearLayout.addView(imageButton);*/

        EditText editText = (EditText) findViewById(R.id.editPlace);



        Button button =(Button)findViewById(R.id.button);
        Aktivitet finalValgtAktivitet = valgtAktivitet;
        button.setOnClickListener(new View.OnClickListener() {
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

                    for (int i = 0; i < 9; i++){
                        CheckBox checkbox = (CheckBox) findViewById(i);
                        if (checkbox.isChecked()){
                            venner.add(checkbox.getText().toString());
                        }
                    }

                    textView.setText(hour + ":" + minute + " " + place + ". Med disse vennene " + venner);

                }

                Arrangement arrangement = new Arrangement(finalValgtAktivitet,place);
                Log.d("!!!!!", arrangement.toString());
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
