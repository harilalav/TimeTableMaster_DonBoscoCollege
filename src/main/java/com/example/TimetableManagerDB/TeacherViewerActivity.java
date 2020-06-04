package com.example.TimetableManagerDB;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TeacherViewerActivity extends AppCompatActivity {
    TextView tk1, tk2, tk3, tk4, tk5;
    SwipeRefreshLayout swipe;

    CardView update_card;

    TextView classTitle, ft1, tt1, ft2, ft3, ft4, ft5, ft6, ft7, tt2, tt3, tt4, tt5, tt6, tt7;
    //ft1 stands for fromTime1st hour,tt1 stands for toTime1st hour
    String FromTime1, FromTime2, FromTime3, FromTime4, FromTime5, FromTime6, FromTime7, ToTime1,
            ToTime2, ToTime3, ToTime4, ToTime5, ToTime6, ToTime7;
    EditText ms1, tus1, ws1, ths1, fs1, ms2, ms3, ms4, ms5, ms6, ms7;
    EditText tus2, tus3, tus4, tus5, tus6, tus7, ws2, ws3, ws4, ws5, ws6;
    EditText ws7, ths2, ths3, ths4, ths5, ths6, ths7, fs2, fs3, fs4, fs5, fs6, fs7;
    //ms1,tus1,ws1,ths1,fs1 stands for mondaySub1 and respective other days.
    EditText ms1T, ms2T, ms3T, ms4T, ms5T, ms6T, ms7T, tus1T, tus2T, tus3T, tus4T, tus5T, tus6T,
            tus7T;
    EditText ws1T, ws2T, ws3T, ws4T, ws5T, ws6T, ws7T, ths1T, ths2T, ths3T, ths4T, ths5T, ths6T,
            ths7T;
    EditText fs1T, fs2T, fs3T, fs4T, fs5T, fs6T, fs7T;
    String MoSub1, TuSub1, WeSub1, ThSub1, FrSub1, className, MoSub2, MoSub3, MoSub4, MoSub5,
            MoSub6, MoSub7, TuSub2, TuSub3, TuSub4, TuSub5, TuSub6, TuSub7, WeSub2, WeSub3, WeSub4,
            WeSub5, WeSub6, WeSub7;
    String ThSub2, ThSub3, ThSub4, ThSub5, ThSub6, ThSub7, FrSub2, FrSub3, FrSub4, FrSub5, FrSub6,
            FrSub7;
    String MonT1, MonT2, MonT3, MonT4, MonT5, MonT6, MonT7, TueT1, TueT2, TueT3, TueT4, TueT5,
            TueT6, TueT7, WedT1, WedT2, WedT3, WedT4, WedT5, WedT6, WedT7, ThuT1;
    String ThuT2, ThuT3, ThuT4, ThuT5, ThuT6, ThuT7, FriT1, FriT2, FriT3, FriT4, FriT5, FriT6,
            FriT7;

    DatabaseReference mondayRef, tuesdayRef, wednesdayRef, thursdayRef, fridayRef, copyBase,
            databaseReference, TTmRef, TTtRef, TTwRef, TTthRef, TTfRef;


    FirebaseAuth mAuth;


    Monday monday;
    Tuesday tuesday;
    Wednesday wednesday;
    Thursday thursday;
    Friday friday;
    TimeTableMonday TTM = new TimeTableMonday();
    TimeTableTuesday TTT = new TimeTableTuesday();
    TimeTableWednesday TTW = new TimeTableWednesday();
    TimeTableThursday TTTh = new TimeTableThursday();
    TimeTableFriday TTF = new TimeTableFriday();

    LinearLayout llm1, llm2, llm3, llm4, llm5, llm6, llm7, llt1, llt2, llt3, llt4, llt5, llt6, llt7;
    LinearLayout llw1, llw2, llw3, llw4, llw5, llw6, llw7, llth1, llth2, llth3, llth4, llth5, llth6,
            llth7, llf1, llf2, llf3, llf4, llf5, llf6, llf7;
    Button done, logout, restore_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_viewer);

        mAuth = FirebaseAuth.getInstance();
        classTitle = findViewById(R.id.classIdTeachers);

        swipe = findViewById(R.id.swipe_refresh);

        update_card = findViewById(R.id.done_card);

        tk1 = findViewById(R.id.timeKeeper1);
        tk2 = findViewById(R.id.timeKeeper2);
        tk3 = findViewById(R.id.timeKeeper3);
        tk4 = findViewById(R.id.timeKeeper4);
        tk5 = findViewById(R.id.timeKeeper5);

        ft1 = findViewById(R.id.fromTime1T);
        tt1 = findViewById(R.id.toTime1T);
        ft2 = findViewById(R.id.fromTime2T);
        tt2 = findViewById(R.id.toTime2T);
        ft3 = findViewById(R.id.fromTime3T);
        tt3 = findViewById(R.id.toTime3T);
        ft4 = findViewById(R.id.fromTime4T);
        tt4 = findViewById(R.id.toTime4T);
        ft5 = findViewById(R.id.fromTime5T);
        tt5 = findViewById(R.id.toTime5T);
        ft6 = findViewById(R.id.fromTime6T);
        tt6 = findViewById(R.id.toTime6T);
        ft7 = findViewById(R.id.fromTime7T);
        tt7 = findViewById(R.id.toTime7T);

        ms1 = findViewById(R.id.mSub1T);
        ms2 = findViewById(R.id.mSub2T);
        ms3 = findViewById(R.id.mSub3t);
        ms4 = findViewById(R.id.mSub4T);
        ms5 = findViewById(R.id.mSub5T);
        ms6 = findViewById(R.id.mSub6T);
        ms7 = findViewById(R.id.mSub7T);

        tus1 = findViewById(R.id.tuSub1T);
        tus2 = findViewById(R.id.tuSub2T);
        tus3 = findViewById(R.id.tuSub3T);
        tus4 = findViewById(R.id.tuSub4T);
        tus5 = findViewById(R.id.tuSub5T);
        tus6 = findViewById(R.id.tuSub6T);
        tus7 = findViewById(R.id.tuSub7T);

        ws1 = findViewById(R.id.wSub1T);
        ws2 = findViewById(R.id.wSub2T);
        ws3 = findViewById(R.id.wSub3T);
        ws4 = findViewById(R.id.wSub4T);
        ws5 = findViewById(R.id.wSub5T);
        ws6 = findViewById(R.id.wSub6T);
        ws7 = findViewById(R.id.wSub7T);

        ths1 = findViewById(R.id.thSub1T);
        ths2 = findViewById(R.id.thSub2T);
        ths3 = findViewById(R.id.thSub3T);
        ths4 = findViewById(R.id.thSub4T);
        ths5 = findViewById(R.id.thSub5T);
        ths6 = findViewById(R.id.thSub6T);
        ths7 = findViewById(R.id.thSub7T);

        fs1 = findViewById(R.id.fSub1T);
        fs2 = findViewById(R.id.fSub2T);
        fs3 = findViewById(R.id.fSub3T);
        fs4 = findViewById(R.id.fSub4T);
        fs5 = findViewById(R.id.fSub5T);
        fs6 = findViewById(R.id.fSub6T);
        fs7 = findViewById(R.id.fSub7T);

        ms1T = findViewById(R.id.MonS1TNameT); //Teacher name Monday
        ms2T = findViewById(R.id.MoS2TNameT);
        ms3T = findViewById(R.id.MoS3TNameT);
        ms4T = findViewById(R.id.MoS4TNameT);
        ms5T = findViewById(R.id.MoS5TNameT);
        ms6T = findViewById(R.id.MoS6TNameT);
        ms7T = findViewById(R.id.MoS7TNameT);

        tus1T = findViewById(R.id.TuS1TNameT); //Teacher name Tuesday
        tus2T = findViewById(R.id.TuS2TNameT);
        tus3T = findViewById(R.id.TuS3TNameT);
        tus4T = findViewById(R.id.TuS4TNameT);
        tus5T = findViewById(R.id.TuS5TNameT);
        tus6T = findViewById(R.id.TuS6TNameT);
        tus7T = findViewById(R.id.TuS7TNameT);

        ws1T = findViewById(R.id.WeS1TNameT); //Teacher name Wednesday
        ws2T = findViewById(R.id.WeS2TNameT);
        ws3T = findViewById(R.id.WeS3TNameT);
        ws4T = findViewById(R.id.WeS4TNameT);
        ws5T = findViewById(R.id.WeS5TNameT);
        ws6T = findViewById(R.id.WeS6TNameT);
        ws7T = findViewById(R.id.WeS7TNameT);

        ths1T = findViewById(R.id.ThS1TNameT); //Teacher name Thursday
        ths2T = findViewById(R.id.ThS2TNameT);
        ths3T = findViewById(R.id.ThS3TNameT);
        ths4T = findViewById(R.id.ThS4TNameT);
        ths5T = findViewById(R.id.ThS5TNameT);
        ths6T = findViewById(R.id.ThS6TNameT);
        ths7T = findViewById(R.id.ThS7TNameT);


        fs1T = findViewById(R.id.FrS1TNameT); //Teacher name Friday
        fs2T = findViewById(R.id.FrS2TNameT);
        fs3T = findViewById(R.id.FrS3TNameT);
        fs4T = findViewById(R.id.FrS4TNameT);
        fs5T = findViewById(R.id.FrS5TNameT);
        fs6T = findViewById(R.id.FrS6TNameT);
        fs7T = findViewById(R.id.FrS7TNameT);

        llm1 = findViewById(R.id.llm1);      //LinearLayout ids Monday
        llm2 = findViewById(R.id.llm2);
        llm3 = findViewById(R.id.llm3);
        llm4 = findViewById(R.id.llm4);
        llm5 = findViewById(R.id.llm5);
        llm6 = findViewById(R.id.llm6);
        llm7 = findViewById(R.id.llm7);

        llt1 = findViewById(R.id.llt1);       //LinearLayout ids Tuesday
        llt2 = findViewById(R.id.llt2);
        llt3 = findViewById(R.id.llt3);
        llt4 = findViewById(R.id.llt4);
        llt5 = findViewById(R.id.llt5);
        llt6 = findViewById(R.id.llt6);
        llt7 = findViewById(R.id.llt7);

        llw1 = findViewById(R.id.llw1);       //LinearLayout ids Wednesday
        llw2 = findViewById(R.id.llw2);
        llw3 = findViewById(R.id.llw3);
        llw4 = findViewById(R.id.llw4);
        llw5 = findViewById(R.id.llw5);
        llw6 = findViewById(R.id.llw6);
        llw7 = findViewById(R.id.llw7);

        llth1 = findViewById(R.id.llth1);      //LinearLayout ids Thursday
        llth2 = findViewById(R.id.llth2);
        llth3 = findViewById(R.id.llth3);
        llth4 = findViewById(R.id.llth4);
        llth5 = findViewById(R.id.llth5);
        llth6 = findViewById(R.id.llth6);
        llth7 = findViewById(R.id.llth7);

        llf1 = findViewById(R.id.llf1);           //LinearLayout ids Friday
        llf2 = findViewById(R.id.llf2);
        llf3 = findViewById(R.id.llf3);
        llf4 = findViewById(R.id.llf4);
        llf5 = findViewById(R.id.llf5);
        llf6 = findViewById(R.id.llf6);
        llf7 = findViewById(R.id.llf7);

        done = findViewById(R.id.done);
        logout = findViewById(R.id.logout);
        restore_table = findViewById(R.id.restore);

        className = getIntent().getStringExtra("class name");

        databaseReference = FirebaseDatabase.getInstance().getReference("Timetable/" + className);
        databaseReference.keepSynced(true);
        copyBase = FirebaseDatabase.getInstance().getReference("Copytable/" + className);
        mondayRef =
                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Monday");
        mondayRef.keepSynced(true);
        tuesdayRef =
                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Tuesday");
        tuesdayRef.keepSynced(true);
        wednesdayRef =
                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Wednesday");
        wednesdayRef.keepSynced(true);
        thursdayRef =
                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Thursday");
        thursdayRef.keepSynced(true);
        fridayRef =
                FirebaseDatabase.getInstance().getReference("Copytable/" + className + "/Friday");
        fridayRef.keepSynced(true);
        TTmRef =
                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Monday");
        TTmRef.keepSynced(true);
        TTtRef =
                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Tuesday");
        TTtRef.keepSynced(true);
        TTwRef =
                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Wednesday");
        TTwRef.keepSynced(true);
        TTthRef =
                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Thursday");
        TTthRef.keepSynced(true);
        TTfRef =
                FirebaseDatabase.getInstance().getReference("Timetable/" + className + "/Friday");
        TTfRef.keepSynced(true);
        Log.d("dataPaths", TTmRef.toString() + "" + TTtRef.toString() + "" + TTwRef.toString() + ""
                + TTthRef.toString() + "" + TTfRef.toString());

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("u dd/MM/yyyy");
        Character dayCount = df.toString().charAt(0);
        String currentTime =
                new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate =
                new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time1 = "Day " + df.format(c.getTime());

        tk1.setText(time1);
        c.add(Calendar.DATE, 1);
        String time2 = "Day " + df.format(c.getTime());
        tk2.setText(time2);
        c.add(Calendar.DATE, 1);
        String time3 = "Day " + df.format(c.getTime());
        tk3.setText(time3);
        c.add(Calendar.DATE, 1);
        String time4 = "Day " + df.format(c.getTime());
        tk4.setText(time4);
        c.add(Calendar.DATE, 1);
        String time5 = "Day " + df.format(c.getTime());
        tk5.setText(time5);
        c.add(Calendar.DATE, 1);
        String time6 = df.format(c.getTime());
        String[] words = time6.split("\\s");
        className = getIntent().getStringExtra("class name");
        classTitle.setText(className);

        ViewTable();
        TTmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TTM = dataSnapshot.getValue(TimeTableMonday.class);
                if (TTM == null) {
                    Log.i("mon Data availability", "unavailable");
                } else
                    Log.i("mon Data availability", "available");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        TTtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TTT = dataSnapshot.getValue(TimeTableTuesday.class);
                if (TTT == null) {
                    Log.i("tue Data availability", "unavailable");
                } else
                    Log.i("tue Data availability", "available");
                Log.i("tue Data availability", "sub 1" + TTT.sub1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        TTwRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TTW = dataSnapshot.getValue(TimeTableWednesday.class);
                if (TTW == null) {
                    Log.i("wednes Data availability", "unavailable");
                } else
                    Log.i("wednes Data availability", "available");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        TTthRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TTTh = dataSnapshot.getValue(TimeTableThursday.class);
                if (TTTh == null) {
                    Log.i("Thurs Data availability", "unavailable");
                } else
                    Log.i("Thurs Data availability", "available");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        TTfRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TTF = dataSnapshot.getValue(TimeTableFriday.class);
                if (TTF == null) {
                    Log.i("Friday Data availability", "unavailable");
                } else
                    Log.i("Friday Data availability", "available");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
        restore_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TeacherViewerActivity.this, "Please long press to restore all Timetables!", Toast.LENGTH_LONG).show();
            }
        });
        restore_table.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RestoreAlert();
                return true;
            }
        });
//
        tk1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                llm1.setBackgroundResource(R.drawable.grad_megatron);
                llm2.setBackgroundResource(R.drawable.grad_megatron);
                llm3.setBackgroundResource(R.drawable.grad_megatron);
                llm4.setBackgroundResource(R.drawable.grad_megatron);
                llm5.setBackgroundResource(R.drawable.grad_megatron);
                llm6.setBackgroundResource(R.drawable.grad_megatron);
                llm7.setBackgroundResource(R.drawable.grad_megatron);

                ms1.setEnabled(true);
                ms1.setBackgroundResource(R.drawable.clicked_background);

                ms1T.setEnabled(true);
                ms1T.setBackgroundResource(R.drawable.clicked_background);

                ms2.setEnabled(true);
                ms2.setBackgroundResource(R.drawable.clicked_background);

                ms2T.setEnabled(true);
                ms2T.setBackgroundResource(R.drawable.clicked_background);

                ms3.setEnabled(true);
                ms3.setBackgroundResource(R.drawable.clicked_background);

                ms3T.setEnabled(true);
                ms3T.setBackgroundResource(R.drawable.clicked_background);

                ms4.setEnabled(true);
                ms4.setBackgroundResource(R.drawable.clicked_background);

                ms4T.setEnabled(true);
                ms4T.setBackgroundResource(R.drawable.clicked_background);

                ms5.setEnabled(true);
                ms5.setBackgroundResource(R.drawable.clicked_background);

                ms5T.setEnabled(true);
                ms5T.setBackgroundResource(R.drawable.clicked_background);

                ms6.setEnabled(true);
                ms6.setBackgroundResource(R.drawable.clicked_background);

                ms6T.setEnabled(true);
                ms6T.setBackgroundResource(R.drawable.clicked_background);

                ms7.setEnabled(true);
                ms7.setBackgroundResource(R.drawable.clicked_background);

                ms7T.setEnabled(true);
                ms7T.setBackgroundResource(R.drawable.clicked_background);


                done.setVisibility(View.VISIBLE);
                update_card.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Now you can change schedules of " + tk1.getText().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        tk2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                llt1.setBackgroundResource(R.drawable.grad_megatron);
                llt2.setBackgroundResource(R.drawable.grad_megatron);
                llt3.setBackgroundResource(R.drawable.grad_megatron);
                llt4.setBackgroundResource(R.drawable.grad_megatron);
                llt5.setBackgroundResource(R.drawable.grad_megatron);
                llt6.setBackgroundResource(R.drawable.grad_megatron);
                llt7.setBackgroundResource(R.drawable.grad_megatron);

                tus1.setEnabled(true);
                tus1.setBackgroundResource(R.drawable.clicked_background);

                tus1T.setEnabled(true);
                tus1T.setBackgroundResource(R.drawable.clicked_background);

                tus2.setEnabled(true);
                tus2.setBackgroundResource(R.drawable.clicked_background);

                tus2T.setEnabled(true);
                tus2T.setBackgroundResource(R.drawable.clicked_background);

                tus3.setEnabled(true);
                tus3.setBackgroundResource(R.drawable.clicked_background);

                tus3T.setEnabled(true);
                tus3T.setBackgroundResource(R.drawable.clicked_background);

                tus4.setEnabled(true);
                tus4.setBackgroundResource(R.drawable.clicked_background);

                tus4T.setEnabled(true);
                tus4T.setBackgroundResource(R.drawable.clicked_background);

                tus5.setEnabled(true);
                tus5.setBackgroundResource(R.drawable.clicked_background);

                tus5T.setEnabled(true);
                tus5T.setBackgroundResource(R.drawable.clicked_background);

                tus6.setEnabled(true);
                tus6.setBackgroundResource(R.drawable.clicked_background);

                tus6T.setEnabled(true);
                tus6T.setBackgroundResource(R.drawable.clicked_background);

                tus7.setEnabled(true);
                tus7.setBackgroundResource(R.drawable.clicked_background);

                tus7T.setEnabled(true);
                tus7T.setBackgroundResource(R.drawable.clicked_background);

                done.setVisibility(View.VISIBLE);
                update_card.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Now you can change schedules of " + tk2.getText().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        tk3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                llw1.setBackgroundResource(R.drawable.grad_megatron);
                llw2.setBackgroundResource(R.drawable.grad_megatron);
                llw3.setBackgroundResource(R.drawable.grad_megatron);
                llw4.setBackgroundResource(R.drawable.grad_megatron);
                llw5.setBackgroundResource(R.drawable.grad_megatron);
                llw6.setBackgroundResource(R.drawable.grad_megatron);
                llw7.setBackgroundResource(R.drawable.grad_megatron);

                ws1.setEnabled(true);
                ws1.setBackgroundResource(R.drawable.clicked_background);

                ws1T.setEnabled(true);
                ws1T.setBackgroundResource(R.drawable.clicked_background);

                ws2.setEnabled(true);
                ws2.setBackgroundResource(R.drawable.clicked_background);

                ws2T.setEnabled(true);
                ws2T.setBackgroundResource(R.drawable.clicked_background);

                ws3.setEnabled(true);
                ws3.setBackgroundResource(R.drawable.clicked_background);

                ws3T.setEnabled(true);
                ws3T.setBackgroundResource(R.drawable.clicked_background);

                ws4.setEnabled(true);
                ws4.setBackgroundResource(R.drawable.clicked_background);

                ws4T.setEnabled(true);
                ws4T.setBackgroundResource(R.drawable.clicked_background);

                ws5.setEnabled(true);
                ws5.setBackgroundResource(R.drawable.clicked_background);

                ws5T.setEnabled(true);
                ws5T.setBackgroundResource(R.drawable.clicked_background);

                ws6.setEnabled(true);
                ws6.setBackgroundResource(R.drawable.clicked_background);

                ws6T.setEnabled(true);
                ws6T.setBackgroundResource(R.drawable.clicked_background);

                ws7.setEnabled(true);
                ws7.setBackgroundResource(R.drawable.clicked_background);

                ws7T.setEnabled(true);
                ws7T.setBackgroundResource(R.drawable.clicked_background);

                done.setVisibility(View.VISIBLE);
                update_card.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Now you can change schedules of " + tk3.getText().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        tk4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                llth1.setBackgroundResource(R.drawable.grad_megatron);
                llth2.setBackgroundResource(R.drawable.grad_megatron);
                llth3.setBackgroundResource(R.drawable.grad_megatron);
                llth4.setBackgroundResource(R.drawable.grad_megatron);
                llth5.setBackgroundResource(R.drawable.grad_megatron);
                llth6.setBackgroundResource(R.drawable.grad_megatron);
                llth7.setBackgroundResource(R.drawable.grad_megatron);

                ths1.setEnabled(true);
                ths1.setBackgroundResource(R.drawable.clicked_background);

                ths1T.setEnabled(true);
                ths1T.setBackgroundResource(R.drawable.clicked_background);

                ths2.setEnabled(true);
                ths2.setBackgroundResource(R.drawable.clicked_background);

                ths2T.setEnabled(true);
                ths2T.setBackgroundResource(R.drawable.clicked_background);

                ths3.setEnabled(true);
                ths3.setBackgroundResource(R.drawable.clicked_background);

                ths3T.setEnabled(true);
                ths3T.setBackgroundResource(R.drawable.clicked_background);

                ths4.setEnabled(true);
                ths4.setBackgroundResource(R.drawable.clicked_background);

                ths4T.setEnabled(true);
                ths4T.setBackgroundResource(R.drawable.clicked_background);

                ths5.setEnabled(true);
                ths5.setBackgroundResource(R.drawable.clicked_background);

                ths5T.setEnabled(true);
                ths5T.setBackgroundResource(R.drawable.clicked_background);

                ths6.setEnabled(true);
                ths6.setBackgroundResource(R.drawable.clicked_background);

                ths6T.setEnabled(true);
                ths6T.setBackgroundResource(R.drawable.clicked_background);

                ths7.setEnabled(true);
                ths7.setBackgroundResource(R.drawable.clicked_background);

                ths7T.setEnabled(true);
                ths7T.setBackgroundResource(R.drawable.clicked_background);

                done.setVisibility(View.VISIBLE);
                update_card.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Now you can change schedules of " + tk4.getText().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
        tk5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                llf1.setBackgroundResource(R.drawable.grad_megatron);
                llf2.setBackgroundResource(R.drawable.grad_megatron);
                llf3.setBackgroundResource(R.drawable.grad_megatron);
                llf4.setBackgroundResource(R.drawable.grad_megatron);
                llf5.setBackgroundResource(R.drawable.grad_megatron);
                llf6.setBackgroundResource(R.drawable.grad_megatron);
                llf7.setBackgroundResource(R.drawable.grad_megatron);

                fs1.setEnabled(true);
                fs1.setBackgroundResource(R.drawable.clicked_background);

                fs1T.setEnabled(true);
                fs1T.setBackgroundResource(R.drawable.clicked_background);

                fs2.setEnabled(true);
                fs2.setBackgroundResource(R.drawable.clicked_background);

                fs2T.setEnabled(true);
                fs2T.setBackgroundResource(R.drawable.clicked_background);

                fs3.setEnabled(true);
                fs3.setBackgroundResource(R.drawable.clicked_background);

                fs3T.setEnabled(true);
                fs3T.setBackgroundResource(R.drawable.clicked_background);

                fs4.setEnabled(true);
                fs4.setBackgroundResource(R.drawable.clicked_background);

                fs4T.setEnabled(true);
                fs4T.setBackgroundResource(R.drawable.clicked_background);

                fs5.setEnabled(true);
                fs5.setBackgroundResource(R.drawable.clicked_background);

                fs5T.setEnabled(true);
                fs5T.setBackgroundResource(R.drawable.clicked_background);

                fs6.setEnabled(true);
                fs6.setBackgroundResource(R.drawable.clicked_background);

                fs6T.setEnabled(true);
                fs6T.setBackgroundResource(R.drawable.clicked_background);

                fs7.setEnabled(true);
                fs7.setBackgroundResource(R.drawable.clicked_background);

                fs7T.setEnabled(true);
                fs7T.setBackgroundResource(R.drawable.clicked_background);

                done.setVisibility(View.VISIBLE);
                update_card.setVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Now you can change schedules of " + tk5.getText().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Timetable of " + className + " updated", Toast.LENGTH_SHORT).show();

                ms1.setEnabled(false);      //disabling touches
                ms1T.setEnabled(false);
                ms2.setEnabled(false);
                ms2T.setEnabled(false);
                ms3.setEnabled(false);
                ms3T.setEnabled(false);
                ms4.setEnabled(false);
                ms4T.setEnabled(false);
                ms5.setEnabled(false);
                ms5T.setEnabled(false);
                ms6.setEnabled(false);
                ms6T.setEnabled(false);
                ms7.setEnabled(false);
                ms7T.setEnabled(false);
                tus1.setEnabled(false);
                tus1T.setEnabled(false);
                tus2.setEnabled(false);
                tus2T.setEnabled(false);
                tus3.setEnabled(false);
                tus3T.setEnabled(false);
                tus4.setEnabled(false);
                tus4T.setEnabled(false);
                tus5.setEnabled(false);
                tus5T.setEnabled(false);
                tus6.setEnabled(false);
                tus6T.setEnabled(false);
                tus7.setEnabled(false);
                tus7T.setEnabled(false);
                ws1.setEnabled(false);
                ws1T.setEnabled(false);
                ws2.setEnabled(false);
                ws2T.setEnabled(false);
                ws3.setEnabled(false);
                ws3T.setEnabled(false);
                ws4.setEnabled(false);
                ws4T.setEnabled(false);
                ws5.setEnabled(false);
                ws5T.setEnabled(false);
                ws6.setEnabled(false);
                ws6T.setEnabled(false);
                ws7.setEnabled(false);
                ws7T.setEnabled(false);
                ths1.setEnabled(false);
                ths1T.setEnabled(false);
                ths2.setEnabled(false);
                ths2T.setEnabled(false);
                ths3.setEnabled(false);
                ths3T.setEnabled(false);
                ths4.setEnabled(false);
                ths4T.setEnabled(false);
                ths5.setEnabled(false);
                ths5T.setEnabled(false);
                ths6.setEnabled(false);
                ths6T.setEnabled(false);
                ths7.setEnabled(false);
                ths7T.setEnabled(false);
                fs1.setEnabled(false);
                fs1T.setEnabled(false);
                fs2.setEnabled(false);
                fs2T.setEnabled(false);
                fs3.setEnabled(false);
                fs3T.setEnabled(false);
                fs4.setEnabled(false);
                fs4T.setEnabled(false);
                fs5.setEnabled(false);
                fs5T.setEnabled(false);
                fs6.setEnabled(false);
                fs6T.setEnabled(false);
                fs7.setEnabled(false);
                fs7T.setEnabled(false);
                //reading updated timetable
                MoSub1 = ms1.getText().toString().trim();
                MoSub2 = ms2.getText().toString().trim();
                MoSub3 = ms3.getText().toString().trim();
                MoSub4 = ms4.getText().toString().trim();
                MoSub5 = ms5.getText().toString().trim();
                MoSub6 = ms6.getText().toString().trim();
                MoSub7 = ms7.getText().toString().trim();

                TuSub1 = tus1.getText().toString().trim();
                TuSub2 = tus2.getText().toString().trim();
                TuSub3 = tus3.getText().toString().trim();
                TuSub4 = tus4.getText().toString().trim();
                TuSub5 = tus5.getText().toString().trim();
                TuSub6 = tus6.getText().toString().trim();
                TuSub7 = tus7.getText().toString().trim();

                WeSub1 = ws1.getText().toString().trim();
                WeSub2 = ws2.getText().toString().trim();
                WeSub3 = ws3.getText().toString().trim();
                WeSub4 = ws4.getText().toString().trim();
                WeSub5 = ws5.getText().toString().trim();
                WeSub6 = ws6.getText().toString().trim();
                WeSub7 = ws7.getText().toString().trim();

                ThSub1 = ths1.getText().toString().trim();
                ThSub2 = ths2.getText().toString().trim();
                ThSub3 = ths3.getText().toString().trim();
                ThSub4 = ths4.getText().toString().trim();
                ThSub5 = ths5.getText().toString().trim();
                ThSub6 = ths6.getText().toString().trim();
                ThSub7 = ths7.getText().toString().trim();

                FrSub1 = fs1.getText().toString().trim();
                FrSub2 = fs2.getText().toString().trim();
                FrSub3 = fs3.getText().toString().trim();
                FrSub4 = fs4.getText().toString().trim();
                FrSub5 = fs5.getText().toString().trim();
                FrSub6 = fs6.getText().toString().trim();
                FrSub7 = fs7.getText().toString().trim();

                //Teacher names to string

                MonT1 = ms1T.getText().toString().trim();
                MonT2 = ms2T.getText().toString().trim();
                MonT3 = ms3T.getText().toString().trim();
                MonT4 = ms4T.getText().toString().trim();
                MonT5 = ms5T.getText().toString().trim();
                MonT6 = ms6T.getText().toString().trim();
                MonT7 = ms7T.getText().toString().trim();

                TueT1 = tus1T.getText().toString().trim();
                TueT2 = tus2T.getText().toString().trim();
                TueT3 = tus3T.getText().toString().trim();
                TueT4 = tus4T.getText().toString().trim();
                TueT5 = tus5T.getText().toString().trim();
                TueT6 = tus6T.getText().toString().trim();
                TueT7 = tus7T.getText().toString().trim();

                WedT1 = ws1T.getText().toString().trim();
                WedT2 = ws2T.getText().toString().trim();
                WedT3 = ws3T.getText().toString().trim();
                WedT4 = ws4T.getText().toString().trim();
                WedT5 = ws5T.getText().toString().trim();
                WedT6 = ws6T.getText().toString().trim();
                WedT7 = ws7T.getText().toString().trim();

                ThuT1 = ths1T.getText().toString().trim();
                ThuT2 = ths2T.getText().toString().trim();
                ThuT3 = ths3T.getText().toString().trim();
                ThuT4 = ths4T.getText().toString().trim();
                ThuT5 = ths5T.getText().toString().trim();
                ThuT6 = ths6T.getText().toString().trim();
                ThuT7 = ths7T.getText().toString().trim();

                FriT1 = fs1T.getText().toString().trim();
                FriT2 = fs2T.getText().toString().trim();
                FriT3 = fs3T.getText().toString().trim();
                FriT4 = fs4T.getText().toString().trim();
                FriT5 = fs5T.getText().toString().trim();
                FriT6 = fs6T.getText().toString().trim();
                FriT7 = fs7T.getText().toString().trim();

                sAlert();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "PLEASE LONG-PRESS TO LOGOUT", Toast.LENGTH_LONG).show();
            }
        });
        logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Alert();
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, NotificationService.class));
    }

    private void RestoreAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning!!!");
        alertDialog.setMessage("Are you sure to Restore TimeTables of all Classes?This process will remove all the updations done!!");
        alertDialog.setPositiveButton(
                "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Restoring", Toast.LENGTH_LONG).show();
                        restore();
                    }
                });
        alertDialog.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void restore() {
        putDataDay1();
        putDataDay2();
        putDataDay3();
        putDataDay4();
        putDataDay5();
        ViewTable();
    }

    private void putDataDay5() {
        copyBase.child("Friday").setValue(TTF);
        Toast.makeText(this, "" + TTF.sub1, Toast.LENGTH_SHORT).show();
    }

    private void putDataDay4() {
        copyBase.child("Thursday").setValue(TTTh);
    }

    private void putDataDay3() {
        copyBase.child("Wednesday").setValue(TTW);
    }

    private void putDataDay2() {
        copyBase.child("Tuesday").setValue(TTT);
    }

    private void putDataDay1() {
        copyBase.child("Monday").setValue(TTM);
    }

    private void ViewTable() {
        mondayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                monday = dataSnapshot.getValue(Monday.class);
                getDataDay1();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        tuesdayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tuesday = dataSnapshot.getValue(Tuesday.class);
                getDataDay2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        wednesdayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wednesday = dataSnapshot.getValue(Wednesday.class);
                getDataDay3();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to get TimeTable", Toast.LENGTH_SHORT).show();
            }
        });
        thursdayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                thursday = dataSnapshot.getValue(Thursday.class);
                getDataDay4();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fridayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friday = dataSnapshot.getValue(Friday.class);
                getDataDay5();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        copyBase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                notification();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =

                    new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")

                .setContentText("Code Sphere")

                .setSmallIcon(R.drawable.ic_timetable)

                .setAutoCancel(true)

                .setContentText("TimeTable has been updated");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        managerCompat.notify(999, builder.build());

    }


    private void getDataDay5() {
        Log.i("friday", "waiting");
        FrSub1 = friday.sub1;
        FrSub2 = friday.sub2;
        FrSub3 = friday.sub3;
        FrSub4 = friday.sub4;
        FrSub5 = friday.sub5;
        FrSub6 = friday.sub6;
        FrSub7 = friday.sub7;

        FriT1 = friday.sub1Teacher;
        FriT2 = friday.sub2Teacher;
        FriT3 = friday.sub3Teacher;
        FriT4 = friday.sub4Teacher;
        FriT5 = friday.sub5Teacher;
        FriT6 = friday.sub6Teacher;
        FriT7 = friday.sub7Teacher;

        fs1.setText(FrSub1);
        fs2.setText(FrSub2);
        fs3.setText(FrSub3);
        fs4.setText(FrSub4);
        fs5.setText(FrSub5);
        fs6.setText(FrSub6);
        fs7.setText(FrSub7);

        fs1T.setText(FriT1);
        fs2T.setText(FriT2);
        fs3T.setText(FriT3);
        fs4T.setText(FriT4);
        fs5T.setText(FriT5);
        fs6T.setText(FriT6);
        fs7T.setText(FriT7);
        Log.i("friday", "done");
    }

    private void getDataDay4() {
        Log.i("thursday", "waiting");
        ThSub1 = thursday.sub1;
        ThSub2 = thursday.sub2;
        ThSub3 = thursday.sub3;
        ThSub4 = thursday.sub4;
        ThSub5 = thursday.sub5;
        ThSub6 = thursday.sub6;
        ThSub7 = thursday.sub7;

        ThuT1 = thursday.sub1Teacher;
        ThuT2 = thursday.sub2Teacher;
        ThuT3 = thursday.sub3Teacher;
        ThuT4 = thursday.sub4Teacher;
        ThuT5 = thursday.sub5Teacher;
        ThuT6 = thursday.sub6Teacher;
        ThuT7 = thursday.sub7Teacher;

        ths1.setText(ThSub1);
        ths2.setText(ThSub2);
        ths3.setText(ThSub3);
        ths4.setText(ThSub4);
        ths5.setText(ThSub5);
        ths6.setText(ThSub6);
        ths7.setText(ThSub7);

        ths1T.setText(ThuT1);
        ths2T.setText(ThuT2);
        ths3T.setText(ThuT3);
        ths4T.setText(ThuT4);
        ths5T.setText(ThuT5);
        ths6T.setText(ThuT6);
        ths7T.setText(ThuT7);
        Log.i("thursday", "done");
    }

    private void getDataDay3() {
        Log.i("wednesday", "waiting");
        WeSub1 = wednesday.sub1;
        WeSub2 = wednesday.sub2;
        WeSub3 = wednesday.sub3;
        WeSub4 = wednesday.sub4;
        WeSub5 = wednesday.sub5;
        WeSub6 = wednesday.sub6;
        WeSub7 = wednesday.sub7;

        WedT1 = wednesday.sub1Teacher;
        WedT2 = wednesday.sub2Teacher;
        WedT3 = wednesday.sub3Teacher;
        WedT4 = wednesday.sub4Teacher;
        WedT5 = wednesday.sub5Teacher;
        WedT6 = wednesday.sub6Teacher;
        WedT7 = wednesday.sub7Teacher;

        ws1.setText(WeSub1);
        ws2.setText(WeSub2);
        ws3.setText(WeSub3);
        ws4.setText(WeSub4);
        ws5.setText(WeSub5);
        ws6.setText(WeSub6);
        ws7.setText(WeSub7);

        ws1T.setText(WedT1);
        ws2T.setText(WedT2);
        ws3T.setText(WedT3);
        ws4T.setText(WedT4);
        ws5T.setText(WedT5);
        ws6T.setText(WedT6);
        ws7T.setText(WedT7);
        Log.i("wednesday", "done");
    }

    private void getDataDay2() {
        Log.i("tuesday", "waiting");
        TuSub1 = tuesday.sub1;
        TuSub2 = tuesday.sub2;
        TuSub3 = tuesday.sub3;
        TuSub4 = tuesday.sub4;
        TuSub5 = tuesday.sub5;
        TuSub6 = tuesday.sub6;
        TuSub7 = tuesday.sub7;

        TueT1 = tuesday.sub1Teacher;
        TueT2 = tuesday.sub1Teacher;
        TueT3 = tuesday.sub1Teacher;
        TueT4 = tuesday.sub1Teacher;
        TueT5 = tuesday.sub1Teacher;
        TueT6 = tuesday.sub1Teacher;
        TueT7 = tuesday.sub1Teacher;

        tus1.setText(TuSub1);
        tus2.setText(TuSub2);
        tus3.setText(TuSub3);
        tus4.setText(TuSub4);
        tus5.setText(TuSub5);
        tus6.setText(TuSub6);
        tus7.setText(TuSub7);

        tus1T.setText(TueT1);
        tus2T.setText(TueT2);
        tus3T.setText(TueT3);
        tus4T.setText(TueT4);
        tus5T.setText(TueT5);
        tus6T.setText(TueT6);
        tus7T.setText(TueT7);
        Log.i("tuesday", "done");
    }

    private void getDataDay1() {
        Log.i("monday", "waiting");
        MoSub1 = monday.sub1;
        MoSub2 = monday.sub2;
        MoSub3 = monday.sub3;
        MoSub4 = monday.sub4;
        MoSub5 = monday.sub5;
        MoSub6 = monday.sub6;
        MoSub7 = monday.sub7;

        MonT1 = monday.sub1Teacher;
        MonT2 = monday.sub2Teacher;
        MonT3 = monday.sub3Teacher;
        MonT4 = monday.sub4Teacher;
        MonT5 = monday.sub5Teacher;
        MonT6 = monday.sub6Teacher;
        MonT7 = monday.sub7Teacher;

        FromTime1 = monday.fT1;
        FromTime2 = monday.fT2;
        FromTime3 = monday.fT3;
        FromTime4 = monday.fT4;
        FromTime5 = monday.fT5;
        FromTime6 = monday.fT6;
        FromTime7 = monday.fT7;

        ToTime1 = monday.tT1;
        ToTime2 = monday.tT2;
        ToTime3 = monday.tT3;
        ToTime4 = monday.tT4;
        ToTime5 = monday.tT5;
        ToTime6 = monday.tT6;
        ToTime7 = monday.tT7;

        ms1.setText(MoSub1);
        ms2.setText(MoSub2);
        ms3.setText(MoSub3);
        ms4.setText(MoSub4);
        ms5.setText(MoSub5);
        ms6.setText(MoSub6);
        ms7.setText(MoSub7);

        ms1T.setText(MonT1);
        ms2T.setText(MonT2);
        ms3T.setText(MonT3);
        ms4T.setText(MonT4);
        ms5T.setText(MonT5);
        ms6T.setText(MonT6);
        ms7T.setText(MonT7);

        ft1.setText(FromTime1);
        ft2.setText(FromTime2);
        ft3.setText(FromTime3);
        ft4.setText(FromTime4);
        ft5.setText(FromTime5);
        ft6.setText(FromTime6);
        ft7.setText(FromTime7);

        tt1.setText(ToTime1);
        tt2.setText(ToTime2);
        tt3.setText(ToTime3);
        tt4.setText(ToTime4);
        tt5.setText(ToTime5);
        tt6.setText(ToTime6);
        tt7.setText(ToTime7);
        Log.i("monday", "done");
    }


//    private void putDataDay5() {
//        Friday friday = new Friday();
//        copyBase.child("Friday").setValue(friday);
//    }
//
//    private void putDataDay4() {
//        Thursday thursday = new Thursday();
//        copyBase.child("Thursday").setValue(thursday);
//    }
//
//    private void putDataDay3() {
//        Wednesday wednesday = new Wednesday();
//        copyBase.child("Wednesday").setValue(wednesday);
//    }
//
//    private void putDataDay2() {
//        Tuesday tuesday = new Tuesday();
//        copyBase.child("Tuesday").setValue(tuesday);
//
//    }
//
//    private void putDataDay1() {
//        Monday monday = new Monday();
//        copyBase.child("Monday").setValue(monday);
//
//    }

    private void sAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("Are you sure to upload the changes in Time Table of " + className);
        alertDialog.setPositiveButton(
                "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploadTimeTable();
                    }
                });
        alertDialog.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void uploadTimeTable() {
        Monday monday = new Monday
                (MoSub1, MoSub2, MoSub3, MoSub4, MoSub5, MoSub6, MoSub7,
                        MonT1, MonT2, MonT3, MonT4, MonT5, MonT6, MonT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        copyBase.child("Monday").setValue(monday);
        Tuesday tuesday = new Tuesday(TuSub1, TuSub2, TuSub3, TuSub4, TuSub5, TuSub6, TuSub7,
                TueT1, TueT2, TueT3, TueT4, TueT5, TueT6, TueT7,
                FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                FromTime6, ToTime6, FromTime7, ToTime7);
        copyBase.child("Tuesday").setValue(tuesday);

        Wednesday wednesday = new Wednesday
                (WeSub1, WeSub2, WeSub3, WeSub4, WeSub5, WeSub6, WeSub7,
                        WedT1, WedT2, WedT3, WedT4, WedT5, WedT6, WedT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        copyBase.child("Wednesday").setValue(wednesday);


        Thursday thursday = new Thursday
                (ThSub1, ThSub2, ThSub3, ThSub4, ThSub5, ThSub6, ThSub7,
                        ThuT1, ThuT2, ThuT3, ThuT4, ThuT5, ThuT6, ThuT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        copyBase.child("Thursday").setValue(thursday);


        Friday friday = new Friday
                (FrSub1, FrSub2, FrSub3, FrSub4, FrSub5, FrSub6, FrSub7,
                        FriT1, FriT2, FriT3, FriT4, FriT5, FriT6, FriT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);

        copyBase.child("Friday").setValue(friday);
        Toast.makeText(getApplicationContext(), "TimeTable of " + className + " updated", Toast.LENGTH_LONG).show();
    }

    private void Alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("Are you sure to Logout?");
        alertDialog.setPositiveButton(
                "yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
        alertDialog.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

}

