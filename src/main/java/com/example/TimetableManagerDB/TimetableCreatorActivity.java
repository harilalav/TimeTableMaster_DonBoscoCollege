package com.example.TimetableManagerDB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TimetableCreatorActivity extends AppCompatActivity {

    Button Dept_Arts, Dept_Science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_creator);
        final int key = getIntent().getIntExtra("key", 0);
        Dept_Arts = findViewById(R.id.dept_arts);
        Dept_Science = findViewById(R.id.dept_sci);

        Dept_Arts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ArtsDepActivity.class);
                i.putExtra("key", key);
                startActivity(i);
                finish();
            }
        });
        Dept_Science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), SciDepActivity.class);
                j.putExtra("key", key);
                startActivity(j);
                finish();
            }
        });
    }
}
