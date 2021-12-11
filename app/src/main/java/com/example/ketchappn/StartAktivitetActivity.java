package com.example.ketchappn;

import androidx.annotation.RequiresApi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.database.AccesUser;
import com.example.ketchappn.database.FireBaseCallBack;
import com.example.ketchappn.functions.FirestoreFunctions;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;
import com.example.ketchappn.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class StartAktivitetActivity extends Activity {

    private final AccesUser accesUser = new AccesUser();
    private final FirestoreFunctions firestoreFunctions = new FirestoreFunctions();
    private ArrayList<String> venner = new ArrayList();
    private int count;
    private String dato;
    private TextView question;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Context context = this;

        setContentView(R.layout.activity_start_aktivitet);

        //Henter string fra fragment_activity
        String aktivitet = getIntent().getStringExtra("Aktivitet");

        //Henter array fra firebase
        List<Aktivitet> aktiviteter = AktivitetBtnAdapter.sendArray();

        //Lager objekt for valgt aktivitet, denne går gjennom arraylisten fra firebase.
        Aktivitet valgtAktivitet = new Aktivitet();

        for (Aktivitet a : aktiviteter){
            if (a.getName().equals(aktivitet)) {
               valgtAktivitet = a;
            }
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkboxlist);


        accesUser.getFriendsTask(new FireBaseCallBack() {
            @Override
            public void onCallback(ArrayList<User> friends) {

                if (friends.size() == 0){

                    question = (TextView) findViewById(R.id.friendListUnderline);
                    question.setText("");

                    TextView noFriends = new TextView(context);
                    noFriends.setText("Ooops, your friendlist is empty...\uD83E\uDD74");
                    noFriends.setTextColor(Color.BLACK);
                    noFriends.setTypeface(null, Typeface.ITALIC);
                    noFriends.setTextSize(30);
                    linearLayout.addView(noFriends);
                }

                else {

                    for (int i = 0; i < friends.size(); i++) {
                        CheckBox cn = new CheckBox(context);
                        cn.setText(friends.get(i).getUsername());
                        cn.setTextSize(20);
                        cn.setId(i);
                        count++;
                        linearLayout.addView(cn);
                    }
                }


            }
        });


        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(valgtAktivitet.getName() + " " + valgtAktivitet.getSymbol());

        EditText editText = (EditText) findViewById(R.id.editPlace);


        /*
        // Lager kalender hvor bruker kan velge når aktiviteten skal ta sted
        */
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Button etDate = (Button) findViewById(R.id.pickDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(StartAktivitetActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month +1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        etDate.setText(date);
                    }
                } , year, month, day);
                datePickerDialog.show();
            }
        });

        Button button =(Button)findViewById(R.id.button);
        Aktivitet finalValgtAktivitet = valgtAktivitet;

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int hour, minute;
                String place;

                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                Editable editable = editText.getText();
                place = editable.toString();


                for (int i = 0; i < count; i++){
                    CheckBox checkbox = (CheckBox) findViewById(i);
                    if (checkbox.isChecked()){
                        venner.add(checkbox.getText().toString());
                    }
                }

                if (count > 0) {

                    ArrayList unique = (ArrayList) venner.stream().distinct().collect(Collectors.toList());

                    dato = day + "/" + month + "/" + year + "/" + hour + ":" + minute;

                    @SuppressLint("DefaultLocale")
                    Arrangement arrangement = new Arrangement(finalValgtAktivitet, place, dato, LoginAct.CurUser, unique);
                    firestoreFunctions.addObjectToFirebase("Arrangement", arrangement.getCollectionname(), arrangement);
                    Log.d("Informasjon om arrangement", arrangement.toString());

                    textView.setText("");
                }

                else {
                    Toast.makeText(StartAktivitetActivity.this, "You need friends to invite" , Toast.LENGTH_SHORT).show();
                }
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
