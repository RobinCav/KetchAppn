package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GroupChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String symbol = getIntent().getStringExtra("Symbol");

        TextView textViewSymbol = (TextView) findViewById(R.id.symbolChat);

        textViewSymbol.setText(symbol);
    }
}