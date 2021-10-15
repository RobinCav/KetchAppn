package com.example.ketchappn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firestore = FirebaseFirestore.getInstance();

        String aktivitet = getIntent().getStringExtra("Aktivitet");

        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(aktivitet);

        EditText editText = (EditText) findViewById(R.id.editPlace);

        Button button =(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                String place;

                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                Editable editable = editText.getText();
                place = editable.toString();

                textView.setText("Selected time: "+ hour +":"+ minute + " " + place);
            }
        });

        Button buttonBack = (Button) findViewById(R.id.button2);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    }

    public void startArrangement(String name, String url, int id) {

        firestore = FirebaseFirestore.getInstance();

        Map<String, Object> aktivitet = new HashMap<>();
        aktivitet.put("name", name);
        aktivitet.put("url", url);
        aktivitet.put("id", id);

        firestore.collection("Aktiviteter").document("Football")
                .set(aktivitet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }
