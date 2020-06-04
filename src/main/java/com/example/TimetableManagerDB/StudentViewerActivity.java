package com.example.TimetableManagerDB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StudentViewerActivity extends AppCompatActivity {

    TextView tk1, tk2, tk3, tk4, tk5;
    TextView classTitle, ft1, tt1, ft2, ft3, ft4, ft5, ft6, ft7, tt2, tt3, tt4, tt5, tt6, tt7;
    //ft1 stands for fromTime1st hour,tt1 stands for toTime1st hour
    String FromTime1, FromTime2, FromTime3, FromTime4, FromTime5, FromTime6, FromTime7, ToTime1,
            ToTime2, ToTime3, ToTime4, ToTime5, ToTime6, ToTime7;
    TextView ms1, tus1, ws1, ths1, fs1, ms2, ms3, ms4, ms5, ms6, ms7;
    TextView tus2, tus3, tus4, tus5, tus6, tus7, ws2, ws3, ws4, ws5, ws6;
    TextView ws7, ths2, ths3, ths4, ths5, ths6, ths7, fs2, fs3, fs4, fs5, fs6, fs7;
    //ms1,tus1,ws1,ths1,fs1 stands for mondaySub1 and respective other days.
    TextView ms1T, ms2T, ms3T, ms4T, ms5T, ms6T, ms7T, tus1T, tus2T, tus3T, tus4T, tus5T, tus6T,
            tus7T;
    TextView ws1T, ws2T, ws3T, ws4T, ws5T, ws6T, ws7T, ths1T, ths2T, ths3T, ths4T, ths5T, ths6T,
            ths7T;
    TextView fs1T, fs2T, fs3T, fs4T, fs5T, fs6T, fs7T;
    String MoSub1, TuSub1, WeSub1, ThSub1, FrSub1, className, MoSub2, MoSub3, MoSub4, MoSub5,
            MoSub6, MoSub7, TuSub2, TuSub3, TuSub4, TuSub5, TuSub6, TuSub7, WeSub2, WeSub3, WeSub4,
            WeSub5, WeSub6, WeSub7;
    String ThSub2, ThSub3, ThSub4, ThSub5, ThSub6, ThSub7, FrSub2, FrSub3, FrSub4, FrSub5, FrSub6,
            FrSub7;
    String MonT1, MonT2, MonT3, MonT4, MonT5, MonT6, MonT7, TueT1, TueT2, TueT3, TueT4, TueT5,
            TueT6, TueT7, WedT1, WedT2, WedT3, WedT4, WedT5, WedT6, WedT7, ThuT1;
    String ThuT2, ThuT3, ThuT4, ThuT5, ThuT6, ThuT7, FriT1, FriT2, FriT3, FriT4, FriT5, FriT6,
            FriT7;

    DatabaseReference mondayRef, tuesdayRef, wednesdayRef, thursdayRef, fridayRef;

    Monday monday;
    Tuesday tuesday;
    Wednesday wednesday;
    Thursday thursday;
    Friday friday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_viewer);

        tk1 = findViewById(R.id.timeKeeperS1);
        tk2 = findViewById(R.id.timeKeeperS2);
        tk3 = findViewById(R.id.timeKeeperS3);
        tk4 = findViewById(R.id.timeKeeperS4);
        tk5 = findViewById(R.id.timeKeeperS5);
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("u dd/MM/yyyy");

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

        classTitle = findViewById(R.id.classIdStudents);

        ft1 = findViewById(R.id.fromTime1S);
        tt1 = findViewById(R.id.toTime1S);
        ft2 = findViewById(R.id.fromTime2S);
        tt2 = findViewById(R.id.toTime2S);
        ft3 = findViewById(R.id.fromTime3S);
        tt3 = findViewById(R.id.toTime3S);
        ft4 = findViewById(R.id.fromTime4S);
        tt4 = findViewById(R.id.toTime4S);
        ft5 = findViewById(R.id.fromTime5S);
        tt5 = findViewById(R.id.toTime5S);
        ft6 = findViewById(R.id.fromTime6S);
        tt6 = findViewById(R.id.toTime6S);
        ft7 = findViewById(R.id.fromTime7S);
        tt7 = findViewById(R.id.toTime7S);

        ms1 = findViewById(R.id.mSub1S);
        ms2 = findViewById(R.id.mSub2S);
        ms3 = findViewById(R.id.mSub3S);
        ms4 = findViewById(R.id.mSub4S);
        ms5 = findViewById(R.id.mSub5S);
        ms6 = findViewById(R.id.mSub6S);
        ms7 = findViewById(R.id.mSub7S);

        tus1 = findViewById(R.id.tuSub1S);
        tus2 = findViewById(R.id.tuSub2S);
        tus3 = findViewById(R.id.tuSub3S);
        tus4 = findViewById(R.id.tuSub4S);
        tus5 = findViewById(R.id.tuSub5S);
        tus6 = findViewById(R.id.tuSub6S);
        tus7 = findViewById(R.id.tuSub7S);

        ws1 = findViewById(R.id.wSub1S);
        ws2 = findViewById(R.id.wSub2S);
        ws3 = findViewById(R.id.wSub3S);
        ws4 = findViewById(R.id.wSub4S);
        ws5 = findViewById(R.id.wSub5S);
        ws6 = findViewById(R.id.wSub6S);
        ws7 = findViewById(R.id.wSub7S);

        ths1 = findViewById(R.id.thSub1S);
        ths2 = findViewById(R.id.thSub2S);
        ths3 = findViewById(R.id.thSub3S);
        ths4 = findViewById(R.id.thSub4S);
        ths5 = findViewById(R.id.thSub5S);
        ths6 = findViewById(R.id.thSub6S);
        ths7 = findViewById(R.id.thSub7S);

        fs1 = findViewById(R.id.fSub1S);
        fs2 = findViewById(R.id.fSub2S);
        fs3 = findViewById(R.id.fSub3S);
        fs4 = findViewById(R.id.fSub4S);
        fs5 = findViewById(R.id.fSub5S);
        fs6 = findViewById(R.id.fSub6S);
        fs7 = findViewById(R.id.fSub7S);

        ms1T = findViewById(R.id.MonS1TNameS); //Teacher name Monday
        ms2T = findViewById(R.id.MoS2TNameS);
        ms3T = findViewById(R.id.MoS3TNameS);
        ms4T = findViewById(R.id.MoS4TNameS);
        ms5T = findViewById(R.id.MoS5TNameS);
        ms6T = findViewById(R.id.MoS6TNameS);
        ms7T = findViewById(R.id.MoS7TNameS);

        tus1T = findViewById(R.id.TuS1TNameS); //Teacher name Tuesday
        tus2T = findViewById(R.id.TuS2TNameS);
        tus3T = findViewById(R.id.TuS3TNameS);
        tus4T = findViewById(R.id.TuS4TNameS);
        tus5T = findViewById(R.id.TuS5TNameS);
        tus6T = findViewById(R.id.TuS6TNameS);
        tus7T = findViewById(R.id.TuS7TNameS);

        ws1T = findViewById(R.id.WeS1TNameS); //Teacher name Wednesday
        ws2T = findViewById(R.id.WeS2TNameS);
        ws3T = findViewById(R.id.WeS3TNameS);
        ws4T = findViewById(R.id.WeS4TNameS);
        ws5T = findViewById(R.id.WeS5TNameS);
        ws6T = findViewById(R.id.WeS6TNameS);
        ws7T = findViewById(R.id.WeS7TNameS);

        ths1T = findViewById(R.id.ThS1TNameS); //Teacher name Thursday
        ths2T = findViewById(R.id.ThS2TNameS);
        ths3T = findViewById(R.id.ThS3TNameS);
        ths4T = findViewById(R.id.ThS4TNameS);
        ths5T = findViewById(R.id.ThS5TNameS);
        ths6T = findViewById(R.id.ThS6TNameS);
        ths7T = findViewById(R.id.ThS7TNameS);


        fs1T = findViewById(R.id.FrS1TNameS); //Teacher name Friday
        fs2T = findViewById(R.id.FrS2TNameS);
        fs3T = findViewById(R.id.FrS3TNameS);
        fs4T = findViewById(R.id.FrS4TNameS);
        fs5T = findViewById(R.id.FrS5TNameS);
        fs6T = findViewById(R.id.FrS6TNameS);
        fs7T = findViewById(R.id.FrS7TNameS);


        Intent newIntent = getIntent();
        className = newIntent.getStringExtra("class name");


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

        classTitle.setText(className + " " + getString(R.string.time_table));


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
    }

    private void getDataDay5() {
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
    }

    private void getDataDay4() {
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
    }

    private void getDataDay3() {
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
    }

    private void getDataDay2() {
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
    }

    private void getDataDay1() {
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
    }
}
