package com.example.mohnish.railwayticketemployee;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class register extends AppCompatActivity {


    EditText name, dob, mobilenumber, email, password;
    RadioGroup gender;
    Spinner typeSpinner;
    String spinneritem;
    final String[] types = {"Select Type", "Counter", "Ticket Checker"};
    final Calendar myCalendar = Calendar.getInstance();
    RadioButton radioButton;
    ProgressDialog progressDoalog;
    DataSnapshot empIdDataSnapshot;
    String empId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeActivity();

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton = findViewById(gender.getCheckedRadioButtonId());
                if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setText("");
                    name.setError("Enter Name");
                    return;
                }
                if (name.getText().toString().length() < 6) {
                    name.setError("Enter Full Name");
                    name.setText("");
                    return;
                } else if (TextUtils.isEmpty(mobilenumber.getText().toString().trim())) {
                    mobilenumber.setError("Enter Phone Number");
                    mobilenumber.setText("");
                    return;
                } else if (!(mobilenumber.getText().toString().length() == 10)) {
                    Toast.makeText(register.this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    mobilenumber.setError("Enter Valid Phone Number");
                    mobilenumber.setText("");
                    return;
                } else if (TextUtils.isEmpty(dob.getText().toString().trim())) {
                    dob.setError("Enter Date of Birth");
                    dob.setText("");
                    return;
                } else if (radioButton == null) {
                    Toast.makeText(register.this, "Please Choose Gender", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spinneritem == null || spinneritem.equals(types[0])) {
                    Toast.makeText(register.this, "Please Choose Employee Type", Toast.LENGTH_SHORT).show();
                    typeSpinner.performClick();
                    return;
                }

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
                if (!(password.getText().toString().length() > 6)) {
                    password.setText("");
                    password.setError("Passowrd is too Short");
                    return;
                }


                progressDoalog = new ProgressDialog(register.this);
                progressDoalog.setMessage("Registration in Progress....");
                progressDoalog.setTitle("");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();

                new Thread() {
                    public void run() {
                        RegisterUser();
                    }
                }.start();


                new Thread() {
                    public void run() {
                        FirebaseTask();
                    }
                }.start();


            }
        });


    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dob.setText(sdf.format(myCalendar.getTime()));
        }

    };

    void initializeActivity() {

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        mobilenumber = findViewById(R.id.mobilenumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        typeSpinner = findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter(register.this, R.layout.support_simple_spinner_dropdown_item, types);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinneritem = types[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("employee");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                empIdDataSnapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    synchronized void RegisterUser() {
        try {

            wait();
        } catch (Exception e) {

        }

        final HashMap<String, String> map = new HashMap<>();

        map.put("name", name.getText().toString());
        map.put("mobileNumber", mobilenumber.getText().toString());
        map.put("dob", dob.getText().toString());
        map.put("gender", radioButton.getText().toString());
        map.put("empType", spinneritem);
        map.put("email", email.getText().toString());


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("employee");

        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    databaseReference.child(empId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDoalog.dismiss();
                                startActivity(new Intent(register.this, login.class));
                            } else {
                                progressDoalog.dismiss();
                                Toast.makeText(register.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressDoalog.dismiss();
                    Toast.makeText(register.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    synchronized void FirebaseTask() {
        while (true) {
            String id = String.valueOf(System.currentTimeMillis()).substring(0, 12);
            if (!empIdDataSnapshot.child(id).exists()) {
                empId = id;
                notify();
                break;
            }
        }
    }

}
