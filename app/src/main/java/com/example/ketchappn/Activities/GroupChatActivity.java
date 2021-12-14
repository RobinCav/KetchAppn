package com.example.ketchappn.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ketchappn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupChatActivity extends AppCompatActivity {


    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Context context = this;

        String symbol = getIntent().getStringExtra("Symbol");
        String place = getIntent().getStringExtra("Place");
        String time = getIntent().getStringExtra("Time");

        String docPath = symbol + "-" + place;

        TextView textViewSymbol = (TextView) findViewById(R.id.symbolChat);
        TextView textViewPlace = (TextView) findViewById(R.id.placeChat);
        TextView textViewTime = (TextView) findViewById(R.id.timeChat);
        Button button = (Button) findViewById(R.id.btnBack);
        Button sendMessage = (Button) findViewById(R.id.btnChat);
        EditText messageInput = (EditText) findViewById(R.id.chatInput);
        LinearLayout chatRecycler = (LinearLayout) findViewById(R.id.chatRecycler);
        ScrollView scrollView = (ScrollView) findViewById(R.id.chatscroll);
        chatRecycler.setGravity(Gravity.BOTTOM);


        documentReference = firestore.collection("Arrangement").document(docPath);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                chatRecycler.removeAllViews();

                ArrayList<HashMap<String, Object>> joinedActivity = (ArrayList<HashMap<String, Object>>) value.get("meldinger");
                for (int i = 0; i < joinedActivity.size(); i++){
                    TextView tx = new TextView(context);
                    TextView from = new TextView(context);
                    from.setText(joinedActivity.get(i).get("username").toString());
                    from.setTextColor(Color.WHITE);
                    from.setTextSize(20);
                    tx.setText(joinedActivity.get(i).get("message").toString());
                    tx.setPadding(20,5,20,5);
                    tx.setTextSize(20);





                    if (joinedActivity.get(i).get("epost").equals(LoginAct.CurUser.getEmail())){
                        tx.setTextColor(Color.BLACK);
                        tx.setGravity(Gravity.RIGHT);
                        from.setGravity(Gravity.RIGHT);
                        tx.setBackgroundColor(Color.rgb(	173, 216, 230));
                        from.setPadding(100,0,100,0);

                        tx.setPadding(0,0,100,0);

                    }

                    else {
                        tx.setTextColor(Color.BLACK);
                        tx.setBackgroundColor(Color.rgb(	237, 237, 237));

                        from.setGravity(Gravity.LEFT);
                        tx.setGravity(Gravity.LEFT);

                    }

                    chatRecycler.addView(from);
                    chatRecycler.addView(tx);

                }
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                System.out.println(joinedActivity.get(0).get("message"));
            }
        });


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> data = new HashMap<>();
                data.put("epost", LoginAct.CurUser.getEmail());
                data.put("message", messageInput.getText().toString());
                data.put("username", LoginAct.CurUser.getUsername());

                documentReference.update("meldinger", FieldValue.arrayUnion(data)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                messageInput.setText("");

               /* tx.setText(LoginAct.CurUser.getUsername());
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
                messageInput.setText("");*/
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