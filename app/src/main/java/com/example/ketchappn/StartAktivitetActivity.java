package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ketchappn.Fragments.Aktiviteter;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;

import java.util.ArrayList;
import java.util.List;


public class StartAktivitetActivity extends AppCompatActivity {

    private List<Aktivitet> aktiviteter = new ArrayList<>();

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

        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(valgtAktivitet.getName());

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

                textView.setText(hour +":"+ minute + " " + place);

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
