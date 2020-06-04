package com.example.TimetableManagerDB;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TimetableActivity extends AppCompatActivity {
    Button sub, TeacherAdd, logout, cancel_day;
    Context context = this;
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
    int yr, mm, dd;
    DatabaseReference databaseReference, copyBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);

        classTitle = findViewById(R.id.classid);
        logout = findViewById(R.id.logoutAdmin);
        cancel_day = findViewById(R.id.CancelDay);
        sub = findViewById(R.id.submit);
        TeacherAdd = findViewById(R.id.Add_Teacher);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "PLEASE LONG-PRESS TO LOGOUT", Toast.LENGTH_LONG).show();
            }
        });
        logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            }
        });
        TeacherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TeacherRegisterActivity.class);
                startActivity(i);
            }
        });
        cancel_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HolidayActivity.class));
            }

        });

        ft1 = findViewById(R.id.fromTime1);
        tt1 = findViewById(R.id.toTime1);
        ft2 = findViewById(R.id.fromTime2);
        tt2 = findViewById(R.id.toTime2);
        ft3 = findViewById(R.id.fromTime3);
        tt3 = findViewById(R.id.toTime3);
        ft4 = findViewById(R.id.fromTime4);
        tt4 = findViewById(R.id.toTime4);
        ft5 = findViewById(R.id.fromTime5);
        tt5 = findViewById(R.id.toTime5);
        ft6 = findViewById(R.id.fromTime6);
        tt6 = findViewById(R.id.toTime6);
        ft7 = findViewById(R.id.fromTime7);
        tt7 = findViewById(R.id.toTime7);

        ms1 = findViewById(R.id.msub1);
        ms2 = findViewById(R.id.msub2);
        ms3 = findViewById(R.id.msub3);
        ms4 = findViewById(R.id.msub4);
        ms5 = findViewById(R.id.msub5);
        ms6 = findViewById(R.id.msub6);
        ms7 = findViewById(R.id.msub7);

        tus1 = findViewById(R.id.tusub1);
        tus1 = findViewById(R.id.tusub1);
        tus2 = findViewById(R.id.tusub2);
        tus3 = findViewById(R.id.tusub3);
        tus4 = findViewById(R.id.tusub4);
        tus5 = findViewById(R.id.tusub5);
        tus6 = findViewById(R.id.tusub6);
        tus7 = findViewById(R.id.tusub7);

        ws1 = findViewById(R.id.wsub1);
        ws2 = findViewById(R.id.wsub2);
        ws3 = findViewById(R.id.wsub3);
        ws4 = findViewById(R.id.wsub4);
        ws5 = findViewById(R.id.wsub5);
        ws6 = findViewById(R.id.wsub6);
        ws7 = findViewById(R.id.wsub7);

        ths1 = findViewById(R.id.thsub1);
        ths2 = findViewById(R.id.thsub2);
        ths3 = findViewById(R.id.thsub3);
        ths4 = findViewById(R.id.thsub4);
        ths5 = findViewById(R.id.thsub5);
        ths6 = findViewById(R.id.thsub6);
        ths7 = findViewById(R.id.thsub7);

        fs1 = findViewById(R.id.fsub1);
        fs2 = findViewById(R.id.fsub2);
        fs3 = findViewById(R.id.fsub3);
        fs4 = findViewById(R.id.fsub4);
        fs5 = findViewById(R.id.fsub5);
        fs6 = findViewById(R.id.fsub6);
        fs7 = findViewById(R.id.fsub7);

        ms1T = findViewById(R.id.MonS1Tname); //Teacher name Monday
        ms2T = findViewById(R.id.MoS2Tname);
        ms3T = findViewById(R.id.MoS3Tname);
        ms4T = findViewById(R.id.MoS4Tname);
        ms5T = findViewById(R.id.MoS5Tname);
        ms6T = findViewById(R.id.MoS6Tname);
        ms7T = findViewById(R.id.MoS7Tname);

        tus1T = findViewById(R.id.TuS1Tname); //Teacher name Tuesday
        tus2T = findViewById(R.id.TuS2Tname);
        tus3T = findViewById(R.id.TuS3Tname);
        tus4T = findViewById(R.id.TuS4Tname);
        tus5T = findViewById(R.id.TuS5Tname);
        tus6T = findViewById(R.id.TuS6Tname);
        tus7T = findViewById(R.id.TuS7Tname);

        ws1T = findViewById(R.id.WeS1Tname); //Teacher name Wednesday
        ws2T = findViewById(R.id.WeS2Tname);
        ws3T = findViewById(R.id.WeS3Tname);
        ws4T = findViewById(R.id.WeS4Tname);
        ws5T = findViewById(R.id.WeS5Tname);
        ws6T = findViewById(R.id.WeS6Tname);
        ws7T = findViewById(R.id.WeS7Tname);

        ths1T = findViewById(R.id.ThS1Tname); //Teacher name Thursday
        ths2T = findViewById(R.id.ThS2Tname);
        ths3T = findViewById(R.id.ThS3Tname);
        ths4T = findViewById(R.id.ThS4Tname);
        ths5T = findViewById(R.id.ThS5Tname);
        ths6T = findViewById(R.id.ThS6Tname);
        ths7T = findViewById(R.id.ThS7Tname);


        fs1T = findViewById(R.id.FrS1Tname); //Teacher name Friday
        fs2T = findViewById(R.id.FrS2Tname);
        fs3T = findViewById(R.id.FrS3Tname);
        fs4T = findViewById(R.id.FrS4Tname);
        fs5T = findViewById(R.id.FrS5Tname);
        fs6T = findViewById(R.id.FrS6Tname);
        fs7T = findViewById(R.id.FrS7Tname);

        Intent newIntent = getIntent();
        className = newIntent.getStringExtra("class name");


        databaseReference = FirebaseDatabase.getInstance().getReference("Timetable/" + className);
        databaseReference.keepSynced(true);

        copyBase = FirebaseDatabase.getInstance().getReference("Copytable/" + className);

        classTitle.setText(className + " " + getString(R.string.time_table));
        ft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft1.setText(hourOfDay + ":" + minute);
                                FromTime1 = hourOfDay + ":" + minute;


                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt1.setText(hourOfDay + ":" + minute);
                                ToTime1 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft2.setText(hourOfDay + ":" + minute);
                                FromTime2 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt2.setText(hourOfDay + ":" + minute);
                                ToTime2 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft3.setText(hourOfDay + ":" + minute);
                                FromTime3 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt3.setText(hourOfDay + ":" + minute);
                                ToTime3 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft4.setText(hourOfDay + ":" + minute);
                                FromTime4 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt4.setText(hourOfDay + ":" + minute);
                                ToTime4 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft5.setText(hourOfDay + ":" + minute);
                                FromTime5 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt5.setText(hourOfDay + ":" + minute);
                                ToTime5 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft6.setText(hourOfDay + ":" + minute);
                                FromTime6 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt6.setText(hourOfDay + ":" + minute);
                                ToTime6 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        ft7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                ft7.setText(hourOfDay + ":" + minute);
                                FromTime7 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });
        tt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tt7.setText(hourOfDay + ":" + minute);
                                ToTime7 = hourOfDay + ":" + minute;
                            }
                        }, hour, min, false);
                timePickerDialog.show();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Subjects to string

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


                Alert();

            }
        });

    }

    public void Alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("Are you sure to upload Time Table of " + className);
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
        databaseReference.child("Monday").setValue(monday);
        copyBase.child("Monday").setValue(monday);


        Tuesday tuesday = new Tuesday(TuSub1, TuSub2, TuSub3, TuSub4, TuSub5, TuSub6, TuSub7,
                TueT1, TueT2, TueT3, TueT4, TueT5, TueT6, TueT7,
                FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                FromTime6, ToTime6, FromTime7, ToTime7);
        databaseReference.child("Tuesday").setValue(tuesday);
        copyBase.child("Tuesday").setValue(tuesday);

        Wednesday wednesday = new Wednesday
                (WeSub1, WeSub2, WeSub3, WeSub4, WeSub5, WeSub6, WeSub7,
                        WedT1, WedT2, WedT3, WedT4, WedT5, WedT6, WedT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        databaseReference.child("Wednesday").setValue(wednesday);
        copyBase.child("Wednesday").setValue(wednesday);


        Thursday thursday = new Thursday
                (ThSub1, ThSub2, ThSub3, ThSub4, ThSub5, ThSub6, ThSub7,
                        ThuT1, ThuT2, ThuT3, ThuT4, ThuT5, ThuT6, ThuT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        databaseReference.child("Thursday").setValue(thursday);
        copyBase.child("Thursday").setValue(thursday);


        Friday friday = new Friday
                (FrSub1, FrSub2, FrSub3, FrSub4, FrSub5, FrSub6, FrSub7,
                        FriT1, FriT2, FriT3, FriT4, FriT5, FriT6, FriT7,
                        FromTime1, ToTime1, FromTime2, ToTime2, FromTime3,
                        ToTime3, FromTime4, ToTime4, FromTime5, ToTime5,
                        FromTime6, ToTime6, FromTime7, ToTime7);
        databaseReference.child("Friday").setValue(friday);
        copyBase.child("Friday").setValue(friday);
        Toast.makeText(getApplicationContext(), "TimeTable of " + className + " uploaded", Toast.LENGTH_LONG).show();


    }
}
