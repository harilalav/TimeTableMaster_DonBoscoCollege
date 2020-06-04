package com.example.TimetableManagerDB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText uid, pswd;
    String user, password;
    FirebaseAuth mAuth;
    String uuid;
    ProgressDialog mDialog;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int key = getIntent().getIntExtra("key", 0);
        final Intent adm = new Intent(getApplicationContext(), TimetableCreatorActivity.class);
        adm.putExtra("key", key);
        final Intent tc = new Intent(getApplicationContext(), TimetableCreatorActivity.class);


        login = findViewById(R.id.loginButton);
        uid = findViewById(R.id.UserID);
        pswd = findViewById(R.id.passwords);
        mAuth = FirebaseAuth.getInstance();
//        uuid=mAuth.getUid();
        mDialog = new ProgressDialog(this);
        lottieAnimationView = findViewById(R.id.lock);
        lottieAnimationView.setScale(2.5f);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = uid.getText().toString().trim() + ".com";
                password = pswd.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    uid.setError("This field is mandatory");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    pswd.setError("Please enter a password");
                    return;
                }

                if (user.contains("admin@timetable.com")) {

                    mDialog.setIcon(R.drawable.ic_lock_outline_black_24dp);
                    mDialog.setTitle("Logging In");
                    mDialog.setMessage("Please Wait");
                    mDialog.show();
                    mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                lottieAnimationView.playAnimation();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(adm);
                                        mDialog.dismiss();
                                        finish();
                                    }
                                }, 650);

                            } else {
                                Toast.makeText(getApplicationContext(), "Problem Logging In", Toast.LENGTH_LONG).show();
                                mDialog.dismiss();
                            }
                        }
                    });
                } else if (user.contains("@teacher.com")) {

                    mDialog.setIcon(R.drawable.ic_lock_outline_black_24dp);
                    mDialog.setTitle("Logging In");
                    mDialog.setMessage("Please Wait");
                    mDialog.show();
                    mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                lottieAnimationView.playAnimation();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        int key2 = 3;
                                        tc.putExtra("key", key2);
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(tc);
                                        mDialog.dismiss();
                                        finish();
                                    }
                                }, 650);

                            } else {
                                Toast.makeText(getApplicationContext(), "Problem Logging In", Toast.LENGTH_LONG).show();
                                mDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }
}
