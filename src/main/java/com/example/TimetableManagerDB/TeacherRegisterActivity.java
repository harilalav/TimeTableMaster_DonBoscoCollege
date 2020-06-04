package com.example.TimetableManagerDB;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TimetableManagerDB.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherRegisterActivity extends AppCompatActivity {

    EditText uNmae, uPswd, uConfPswd;
    Button Reg;
    String name, paswd, Cpswd;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        uNmae = findViewById(R.id.userNameReg);
        uPswd = findViewById(R.id.userPassReg);
        uConfPswd = findViewById(R.id.userPassConf);
        Reg = findViewById(R.id.TeacherReg);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = uNmae.getText().toString().toLowerCase().trim();
                paswd = uPswd.getText().toString().toLowerCase().trim();
                Cpswd = uConfPswd.getText().toString().toLowerCase().trim();

                if (TextUtils.isEmpty(name)) {
                    uNmae.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(paswd)) {

                    uPswd.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(Cpswd)) {

                    uPswd.setError("Please confirm password");
                    return;
                }
                Boolean check;
                check = paswd.equals(Cpswd);
                if (check != true) {
                    uConfPswd.setError("Passwords don't match");
                    return;
                }
                mDialog.setMessage("Registering");
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(name, paswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration complete", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();
                        }

                    }
                });


            }
        });
    }
}
