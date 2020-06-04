package com.example.TimetableManagerDB;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.service.autofill.Validators.or;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 2500;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;
    private RotateLoading rotateLoading;

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screens);
        mAuth = FirebaseAuth.getInstance();
        final String[] BBA =
                {"BBA 1st Sem", "BBA 2nd Sem", "BBA 3rd Sem", "BBA 4th Sem", "BBA 5th Sem", "BBA 6th Sem"};
        final String[] BComCA =
                {"BCom CA 1st Sem", "BCom CA 2nd Sem", "BCom CA 3rd Sem", "BCom CA 4th Sem", "BCom CA 5th Sem", "BCom CA 6th Sem"};
        final String[] BComFinance =
                {"BCom Finance 1st Sem", "BCom Finance 2nd Sem", "BCom Finance 3rd Sem", "BCom Finance 4th Sem", "BCom Finance 5th Sem", "BCom Finance 6th Sem"};
        final String[] BCA =
                {"BCA 1st Sem", "BCA 2nd Sem", "BCA 3rd Sem", "BCA 4th Sem", "BCA 5th Sem", "BCA 6th Sem"};
        final String[] BcsCs =
                {"BSC CS 1st Sem", "BSC CS 2nd Sem", "BSC CS 3rd Sem", "BSC CS 4th Sem", "BSC CS 5th Sem", "BSC CS 6th Sem"};
        final String[] MBA = {"MBA 1st Sem", "MBA 2nd Sem", "MBA 3rd Sem", "MBA 4th Sem"};
        final String[] MscEl =
                {"MSC ELECTRONICS 1st Sem", "MSC ELECTRONICS 2nd Sem", "MSC ELECTRONICS 3rd Sem", "MSC ELECTRONICS 4th Sem"};

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Calendar date = Calendar.getInstance();
        //sets the calendar with starting day of week
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        //printing of first and last day of th week
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        final String FirstDay = dateformat.format(date.getTime());
        Log.i("1st date of weak", FirstDay);
        for (int i = 0; i < 6; i++) {
            date.add(Calendar.DATE, 1);
        }

        final String LastDay = dateformat.format(date.getTime());
        final String currentDate =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Log.i("last date of weak", LastDay);
        Log.i(" date of weak", currentDate);


        DatabaseReference[] mondayRef = new DatabaseReference[5];
        final DatabaseReference tuesdayRef;
        final DatabaseReference wednesdayRef;
        final DatabaseReference thursdayRef;
        final DatabaseReference fridayRef;
        final DatabaseReference copyBase;
        final DatabaseReference databaseReference;
        DatabaseReference[] TTmRef = new DatabaseReference[5];
        final DatabaseReference TTtRef;
        final DatabaseReference TTwRef;
        final DatabaseReference TTthRef;
        final DatabaseReference TTfRef;
        Monday monday;
        Tuesday tuesday;
        Wednesday wednesday;
        Thursday thursday;
        Friday friday;
        int i;
        TimeTableTuesday TTT = new TimeTableTuesday();
        TimeTableWednesday TTW = new TimeTableWednesday();
        TimeTableThursday TTTh = new TimeTableThursday();
        TimeTableFriday TTF = new TimeTableFriday();
        for (i = 0; i <= 5; i++) //iterate through 6 Sems
        {
            mondayRef[0] =
                    FirebaseDatabase.getInstance().getReference("Copytable/" + BBA[i] + "/Monday");
            mondayRef[1] =
                    FirebaseDatabase.getInstance().getReference("Copytable/" + BComCA[i] + "/Monday");
            mondayRef[2] =
                    FirebaseDatabase.getInstance().getReference("Copytable/" + BComFinance[i] + "/Monday");
            mondayRef[3] =
                    FirebaseDatabase.getInstance().getReference("Copytable/" + BCA[i] + "/Monday");
            mondayRef[4] =
                    FirebaseDatabase.getInstance().getReference("Copytable/" + BcsCs[i] + "/Monday");


            TTmRef[0] =
                    FirebaseDatabase.getInstance().getReference("Timetable/" + BBA[i] + "/Monday");
            TTmRef[1] =
                    FirebaseDatabase.getInstance().getReference("Timetable/" + BComCA[i] + "/Monday");
            TTmRef[2] =
                    FirebaseDatabase.getInstance().getReference("Timetable/" + BComFinance[i] + "/Monday");
            TTmRef[3] =
                    FirebaseDatabase.getInstance().getReference("Timetable/" + BCA[i] + "/Monday");
            TTmRef[4] =
                    FirebaseDatabase.getInstance().getReference("Timetable/" + BcsCs[i] + "/Monday");

            final int finalI = i;
            final DatabaseReference[] finalMondayRef = new DatabaseReference[5];
            finalMondayRef[0] = mondayRef[0];
            finalMondayRef[1] = mondayRef[1];
            finalMondayRef[2] = mondayRef[2];
            finalMondayRef[3] = mondayRef[3];
            finalMondayRef[4] = mondayRef[4];

            TTmRef[1].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    TimeTableMonday TTM = dataSnapshot.getValue(TimeTableMonday.class);
                    if (TTM != null) {
                        Log.i("mon Data availability", " " + BBA[finalI]);
                        if (currentDate.equals(FirstDay) || (currentDate.equals(LastDay))) {
                            Log.i("FirstDay or LastDay ", "condition satisfied");
                            finalMondayRef[1].setValue(TTM);
                        } else {
                            Log.i("FAILED", "conditions Not satisfied");
                        }
                    } else
                        Log.i("mon Data availability", "not available");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        tuesdayRef =
//                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Tuesday");
//        tuesdayRef.keepSynced(true);
//        wednesdayRef =
//                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Wednesday");
//        wednesdayRef.keepSynced(true);
//        thursdayRef =
//                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Thursday");
//        thursdayRef.keepSynced(true);
//        fridayRef =
//                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Friday");
//        fridayRef.keepSynced(true);
//
//        TTmRef.keepSynced(true);
//        TTtRef =
//                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Tuesday");
//        TTtRef.keepSynced(true);
//        TTwRef =
//                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Wednesday");
//        TTwRef.keepSynced(true);
//        TTthRef =
//                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Thursday");
//        TTthRef.keepSynced(true);
//        TTfRef =
//                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Friday");
//        TTfRef.keepSynced(true);

        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);

                    if (user != null) {
                        final String mail = user.getEmail();

                        if (mail.equals("admin@timetable.com")) {
                            int key = 1;
                            Intent in =
                                    new Intent(getApplicationContext(), TimetableCreatorActivity.class);
                            in.putExtra("key", key);
                            startActivity(in);


                            finish();
                        } else if (mail.contains("@teacher.com")) {
                            int key = 3;
                            Intent in =
                                    new Intent(getApplicationContext(), TimetableCreatorActivity.class);
                            in.putExtra("key", key);
                            startActivity(in);
                            Log.i("User", "" + user.getEmail());
                            Log.i("User", "" + user.getDisplayName());


                            finish();
                        }
                    } else {
                        startActivity(i);
                        finish();
                    }
                }
            }, SPLASH_SCREEN_TIME_OUT);


        }
    }
}
