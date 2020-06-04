package com.example.TimetableManagerDB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArtsDepActivity extends AppCompatActivity {
    NiceSpinner bbaSpinner, mbaSpinner, bcomSpinner, bcomfSpinner;
    List<String> ugSem =
            new LinkedList<>(Arrays.asList("choose item", "1st Sem", "2nd Sem", "3rd Sem", "4th Sem", "5th Sem", "6th sem"));
    List<String> pgSem =
            new LinkedList<>(Arrays.asList("choose item", "1st Sem", "2nd Sem", "3rd Sem", "4th Sem"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor userPref = sharedPreferences.edit();
        final String s1 = sharedPreferences.getString("name", "");


        setContentView(R.layout.activity_arts_dep);
        bbaSpinner = findViewById(R.id.spinner_bba);
        mbaSpinner = findViewById(R.id.spinner_mba);
        bcomSpinner = findViewById(R.id.spinner_bcom_ca);
        bcomfSpinner = findViewById(R.id.spinner_bc_fin);
        bbaSpinner.attachDataSource(ugSem);
        mbaSpinner.attachDataSource(pgSem);
        bcomfSpinner.attachDataSource(ugSem);
        bcomSpinner.attachDataSource(ugSem);


        final int key = getIntent().getIntExtra("key", 0);
        Toast.makeText(getApplicationContext(), "" + key, Toast.LENGTH_SHORT).show();
        bbaSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = "BBA " + (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " choosed", Toast.LENGTH_LONG).show();

                if (key == 2) {
                    Intent i = new Intent(getApplicationContext(), StudentViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();
                } else if (key == 1) {
                    Intent j = new Intent(getApplicationContext(), TimetableActivity.class);
                    j.putExtra("class name", item);
                    startActivity(j);

                } else if (key == 3) {
                    Intent i = new Intent(getApplicationContext(), TeacherViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();

                }

            }
        });
        bcomSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = "BCom CA " + (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " choosed", Toast.LENGTH_LONG).show();
                if (key == 2) {
                    Intent i = new Intent(getApplicationContext(), StudentViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();
                } else if (key == 1) {
                    Intent j = new Intent(getApplicationContext(), TimetableActivity.class);
                    j.putExtra("class name", item);
                    startActivity(j);
                    finish();
                } else if (key == 3) {
                    Intent i = new Intent(getApplicationContext(), TeacherViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();

                }

            }
        });
        bcomfSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = "BCom Finance " + (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " choosed", Toast.LENGTH_LONG).show();
                if (key == 2) {
                    Intent i = new Intent(getApplicationContext(), StudentViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();
                } else if (key == 1) {
                    Intent j = new Intent(getApplicationContext(), TimetableActivity.class);
                    j.putExtra("class name", item);
                    startActivity(j);
                    finish();
                } else if (key == 3) {
                    Intent i = new Intent(getApplicationContext(), TeacherViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();

                }
            }
        });
        mbaSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = "MBA " + (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item + " choosed", Toast.LENGTH_LONG).show();
                if (key == 2) {
                    Intent i = new Intent(getApplicationContext(), StudentViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();
                } else if (key == 1) {
                    Intent j = new Intent(getApplicationContext(), TimetableActivity.class);
                    j.putExtra("class name", item);
                    startActivity(j);
                    finish();
                } else if (key == 3) {
                    Intent i = new Intent(getApplicationContext(), TeacherViewerActivity.class);
                    i.putExtra("class name", item);
                    startActivity(i);
                    finish();

                }
            }
        });
    }
}
