package com.example.mohnish.railwayticketemployee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    TextView email, password;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (LoginInfo.checkLogin()) {
            startActivity(new Intent(this, MainActivity.class));
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));
            }
        });


        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, ForgotPassword.class));
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Email Id");
                    return;
                }
                if (!(email.getText().toString().contains("@"))) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Valid Email");
                    return;
                }
                if (!(email.getText().toString().contains(".com"))) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Valid Email");
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setText("");
                    password.setError("Enter Password");
                    return;
                }
                progressDoalog = new ProgressDialog(login.this);

                progressDoalog.setMessage("loading....");
                progressDoalog.setTitle("Log In");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();


                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            new Thread() {
                                public void run() {
                                    GetEmployeeId();
                                }
                            }.start();


                            new Thread() {
                                public void run() {
                                    try {
                                        FinishLogin();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }.start();

                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(login.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDoalog.dismiss();
                        Toast.makeText(login.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    synchronized void GetEmployeeId() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        Query query = databaseReference.child("employee").orderByChild("email").equalTo(email.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        LoginInfo.empId = data.getKey();

                        notify();
                    }
                } else {

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    synchronized void FinishLogin() throws InterruptedException {
        wait();
        progressDoalog.dismiss();
        startActivity(new Intent(login.this, MainActivity.class));
    }

}
