package com.example.ketchappn.Activities;

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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ketchappn.R;
import com.example.ketchappn.aktivitetFunc.AktivitetBtnAdapter;
import com.example.ketchappn.database.AccessUser;
import com.example.ketchappn.database.FireBaseUserCallBack;
import com.example.ketchappn.database.FirestoreFunctions;
import com.example.ketchappn.models.Aktivitet;
import com.example.ketchappn.models.Arrangement;
import com.example.ketchappn.models.Melding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StartAktivitetActivity extends Activity {

    private final AccessUser accessUser = new AccessUser();
    private final FirestoreFunctions firestoreFunctions = new FirestoreFunctions();
    private ArrayList<String> venner = new ArrayList();
    private ArrayList<String> epostVenner = new ArrayList<>();
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

        // Finner hvilken aktivitet brukeren valgte på forrige side med String aktivitet.
        for (Aktivitet a : aktiviteter){
            if (a.getName().equals(aktivitet)) {
                valgtAktivitet = a;
            }
        }

        // Lager LinearLayout hvor checkbox med venner skal bli lagt til
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.checkboxlist);


        // Callback for å hente venner til bruker
        accessUser.getFriendsTask(new FireBaseUserCallBack() {
            @Override
            public void onCallBack(ArrayList<HashMap<String, Object>> friends, ArrayList<String> status) {

                // Hvis bruker ikke har noen venner, vil bruker bli infromert når denne siden bli åpnet
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

                // Hvis bruker har minst en venn vil denne funksjonen kjøre
                else {

                    for (int i = 0; i < friends.size(); i++) {
                        CheckBox cn = new CheckBox(context);
                        cn.setText(friends.get(i).get("username").toString());
                        cn.setTextSize(20);
                        cn.setId(i);
                        count++;
                        epostVenner.add(friends.get(i).get("email").toString());
                        linearLayout.addView(cn);
                    }
                }

            }


        });

        TimePicker timePicker = (TimePicker) findViewById(R.id.datePicker1);
        timePicker.setIs24HourView(true);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(valgtAktivitet.getName() + " " + valgtAktivitet.getSymbol());

        EditText choosePlace = (EditText) findViewById(R.id.editPlace);

        /*
        // Lager kalender hvor bruker kan velge når aktiviteten skal ta sted
        */
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Button etDate = findViewById(R.id.pickDate);

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

        Button btnInvite = (Button)findViewById(R.id.button);
        Aktivitet finalValgtAktivitet = valgtAktivitet;

        // Handlingen som skal skje når inviter-knappen blir trykket på
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int hour, minute;
                String place, minuteString, hourString;

                // henter ut brukerdefinerte verdier
                hour = timePicker.getHour();
                if (hour < 10){
                    hourString = Integer.toString(hour);
                    hourString = "0" + hourString;
                }
                else {
                    hourString = Integer.toString(hour);
                }

                minute = timePicker.getMinute();
                if (minute == 0){
                    minuteString = Integer.toString(minute);
                    minuteString = minuteString + minuteString;
                }
                else {
                    minuteString = Integer.toString(minute);
                }

                Editable editable = choosePlace.getText();
                place = editable.toString();

                // Sjekker hvilke venner som ble invitert av bruker
                for (int i = 0; i < count; i++){
                    CheckBox checkbox = (CheckBox) findViewById(i);
                    if (checkbox.isChecked()){
                        venner.add(epostVenner.get(i));
                    }
                }

                // Genererer en feilmelding med Toast hvis bruker ikke har fylt ut hvor aktiviteten skal ta sted
                if (choosePlace.getText().toString().equals("")){
                    Toast.makeText(StartAktivitetActivity.this, "You need to pick a place" , Toast.LENGTH_SHORT).show();
                }

                // Genererer en feilmelding med Toast hvis bruker ikke har fylt inn hvilken dato det skal ta sted
                else if(etDate.getText().toString().equals("Pick date") || etDate.getText().toString().equals("")){
                    Toast.makeText(StartAktivitetActivity.this, "You need to pick a date" , Toast.LENGTH_SHORT).show();
                }

                // Genererer en feilmelding med Toast hvis bruker ikke har definert hvem de skal invitere. Kan ikke lage arrangement med bare bruker, må ha venner
                else if (venner.size() == 0){
                    Toast.makeText(StartAktivitetActivity.this, "You need to invite someone" , Toast.LENGTH_SHORT).show();
                }

                // Hvis alt er fylt vil arrangementet bli lagt til i firestore
                else if (count > 0) {

                    // Gjør elementene i vennelisten unik så ikke samme bruker kommer opp, denne funksjonen lagde vi på grunn av en bug
                    ArrayList unique = (ArrayList) epostVenner.stream().distinct().collect(Collectors.toList());

                    // Lager en dato verdi med dato og tid
                    dato = day + "/" + month + "/" + year + " -- " + hourString + ":" + minuteString;

                    ArrayList<Melding> meldinger = new ArrayList<>();
                    Melding melding = new Melding(LoginAct.CurUser.getUsername(), "Hey", LoginAct.CurUser.getEmail());
                    meldinger.add(melding);

                    // Danner et arrangement objekt og legger det til i Arrangement collection i firebase
                    @SuppressLint("DefaultLocale")
                    Arrangement arrangement = new Arrangement(finalValgtAktivitet, place, dato, LoginAct.CurUser.getEmail(), unique, meldinger);
                    firestoreFunctions.addObjectToFirebase("Arrangement", arrangement.getCollectionname(), arrangement);

                    // Data for arrangementet som har blitt laget
                    Map<String, Object> data = new HashMap<>();
                    data.put("Name", arrangement.getCollectionname());
                    data.put("Symbol", arrangement.getAktivitet().getSymbol());
                    data.put("Sted", arrangement.getSted());
                    data.put("Tid", arrangement.getTid());

                    // Legger til arrangemententet til host
                    firestoreFunctions.sendInvite(LoginAct.CurUser.getEmail(), data);

                    // Legger til arrangemententet til de inviterte
                    for (String friend : venner){
                        firestoreFunctions.sendInvite(friend, data);
                    }

                    // Her skal bruker bli sendt tilbake til startpage
                    finish();
                }

                // Genererer en feilmelding med Toast hvis bruker trykker på invite uten noen venner på listen sin
                else {
                    Toast.makeText(StartAktivitetActivity.this, "You need friends to invite" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Tilbakeknappen vil sende bruker til forrige side, hvor ønsket aktivitet kan bli valgt
        Button buttonBack = (Button) findViewById(R.id.button2);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}