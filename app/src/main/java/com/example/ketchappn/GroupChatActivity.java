package com.example.ketchappn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ketchappn.Fragments.LoginAct;

public class GroupChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Context context = this;

        String symbol = getIntent().getStringExtra("Symbol");
        String place = getIntent().getStringExtra("Place");
        String time = getIntent().getStringExtra("Time");
        String title = getIntent().getStringExtra("Title");

        TextView textViewSymbol = (TextView) findViewById(R.id.symbolChat);
        TextView textViewPlace = (TextView) findViewById(R.id.placeChat);
        TextView textViewTime = (TextView) findViewById(R.id.timeChat);
        Button button = (Button) findViewById(R.id.btnBack);
        Button sendMessage = (Button) findViewById(R.id.btnChat);
        EditText messageInput = (EditText) findViewById(R.id.chatInput);
        LinearLayout chatRecycler = (LinearLayout) findViewById(R.id.chatRecycler);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tx = new TextView(context);
                TextView tx2 = new TextView(context);


                tx.setText(LoginAct.CurUser.getUsername());
                tx2.setBackgroundResource(R.drawable.border);
                tx.setTextSize(20);
                tx2.setTextSize(20);
                tx2.setGravity(Gravity.TOP);
                tx.setGravity(Gravity.TOP);
                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0f);

                tx2.setText(messageInput.getText().toString());

                tx2.setLayoutParams(textParam );


                chatRecycler.addView(tx);
                chatRecycler.addView(tx2);
            }
        });


        textViewSymbol.setText(symbol);
        textViewPlace.setText(place);
        textViewTime.setText(time);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}