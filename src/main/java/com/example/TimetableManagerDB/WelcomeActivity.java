package com.example.TimetableManagerDB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class WelcomeActivity extends AppCompatActivity {

    Button ws, wt;
    TextView greet;
    ScrollView welcomeScroll;
    CardView csw, ctw, cntw, cmw, cw;
    int key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        csw = findViewById(R.id.csw);
        ctw = findViewById(R.id.ctw);
        welcomeScroll = findViewById(R.id.scrollWelcome);
        greet = findViewById(R.id.greet);
        ws = findViewById(R.id.StudentsWelcome);
        wt = findViewById(R.id.TeachersWelcome);

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {

            greet.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greet.setText("Good Afternoon");

        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greet.setText("Good Evening");

        }


        wt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 1;
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("key", key);
                startActivity(i);
            }
        });
        ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 2;
                Intent j = new Intent(getApplicationContext(), TimetableCreatorActivity.class);
                j.putExtra("key", key);
                startActivity(j);
            }
        });
    }
}
