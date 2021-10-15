package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String aktivitet = getIntent().getStringExtra("Aktivitet");

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(aktivitet);
    }
}