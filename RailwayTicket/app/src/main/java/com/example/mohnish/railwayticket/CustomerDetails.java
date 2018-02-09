package com.example.mohnish.railwayticket;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohnish.railwayticket.SupportFiles.station;
import com.google.android.gms.internal.wh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomerDetails extends AppCompatActivity {

    EditText dob, idCardNumber, phonenumber, name;
    RadioGroup gender;
    Spinner idCardSpinner, citySpinner, stnSpinner;
    private final String[] idTypes = {"Select Id Card Type", "Driving License", "PAN Card", "Aadhaar Card", "Voter ID", "Passport"};
    ArrayList<station> stations = new ArrayList<>();
    String spinneritem, stnCode, email, password;
    final Calendar myCalendar = Calendar.getInstance();
    RadioButton radioButton;
    String uid;
    DataSnapshot pinDataSnapshot;
    ProgressDialog progressDoalog;
    List stationList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        stationList.clear();
      final ProgressDialog  init = new ProgressDialog(CustomerDetails.this);
        init.setMessage("Loading....");
        init.setTitle("");
        init.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        init.show();
        initializeActivity();
        FirebaseDatabase firedata = FirebaseDatabase.getInstance();
        final DatabaseReference db = firedata.getReference("station");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAGg", String.valueOf(dataSnapshot));
                stationList = (List) dataSnapshot.getValue();

                stationList.remove(0);

                stations.add(new station("Select Home Station", "000"));
                for (int i = 1; i <= stationList.size()-1; i++) {
                    stations.add(new station(stationList.get(i).toString(), String.valueOf(i)));
                }


                ArrayAdapter stnAdapter = new ArrayAdapter(CustomerDetails.this, R.layout.support_simple_spinner_dropdown_item, stations);

                stnSpinner.setAdapter(stnAdapter);
                init.dismiss();
                stnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        stnCode = stations.get(i).getStnCode();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        password = extras.getString("password");


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CustomerDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(CustomerDetails.this,phonenumber.getText().toString().length(),Toast.LENGTH_SHORT).show();
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
                }
                if (stnCode == null || stnCode.equals("000")) {
                    Toast.makeText(CustomerDetails.this, "Please Choose Home Station", Toast.LENGTH_SHORT).show();
                    stnSpinner.performClick();
                } else if (TextUtils.isEmpty(phonenumber.getText().toString().trim())) {
                    phonenumber.setError("Enter Phone Number");
                    phonenumber.setText("");
                    return;
                } else if (!(phonenumber.getText().toString().length() == 10)) {
                    Toast.makeText(CustomerDetails.this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                    phonenumber.setError("Enter Valid Phone Number");
                    phonenumber.setText("");
                    return;
                } else if (TextUtils.isEmpty(dob.getText().toString().trim())) {
                    dob.setError("Enter Date of Birth");
                    dob.setText("");
                    return;
                } else if (radioButton == null) {
                    Toast.makeText(CustomerDetails.this, "Please Choose Gender", Toast.LENGTH_SHORT).show();
                    return;
                } else if (spinneritem == null || spinneritem.equals(idTypes[0])) {
                    Toast.makeText(CustomerDetails.this, "Please Choose ID Card Type", Toast.LENGTH_SHORT).show();
                    idCardSpinner.performClick();
                    return;
                } else if (TextUtils.isEmpty(idCardNumber.getText().toString().trim())) {
                    idCardNumber.setError("Enter ID Card Number");
                    idCardNumber.setText("");
                    return;

                } else if (idCardNumber.getText().toString().length() < 4) {
                    idCardNumber.setError("Enter Valid ID Card Number");
                    idCardNumber.setText("");
                    return;

                }
                progressDoalog = new ProgressDialog(CustomerDetails.this);
                progressDoalog.setMessage("Registration in Progress....");
                progressDoalog.setTitle("");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();

                new Thread() {
                    public void run() {
                        registerUser();
                    }
                }.start();


                new Thread() {
                    public void run() {
                        firebaseTask();
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

    public void initializeActivity() {
        spinneritem = null;

        stnCode = null;
        idCardNumber = findViewById(R.id.idcardnumber);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        name = findViewById(R.id.username);

        idCardSpinner = findViewById(R.id.idcardtype);

        phonenumber = findViewById(R.id.mobilenumber);
        stnSpinner = findViewById(R.id.homestn);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("pin");


        ArrayAdapter idTypeAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, idTypes);

        idCardSpinner.setAdapter(idTypeAdapter);
        idCardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinneritem = idTypes[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                pinDataSnapshot = dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    synchronized void registerUser() {

        try {

            wait();
        } catch (Exception e) {

        }


        final HashMap<String, String> passengerMap = new HashMap<>();
        passengerMap.put("name", name.getText().toString());
        passengerMap.put("email", email);
        passengerMap.put("homeStation", stnCode);
        passengerMap.put("mobileNumber", phonenumber.getText().toString().trim());
        passengerMap.put("dob", dob.getText().toString().trim());
        passengerMap.put("gender", radioButton.getText().toString());
        passengerMap.put("city", "Mumbai");
        passengerMap.put("idType", spinneritem);
        passengerMap.put("idNumber", idCardNumber.getText().toString().trim());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference passengerReference = firebaseDatabase.getReference("passenger");
        final DatabaseReference pinReference = firebaseDatabase.getReference("pin");

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    passengerReference.child(uid).setValue(passengerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pinReference.child(uid.substring(8)).setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDoalog.dismiss();
                                            startActivity(new Intent(CustomerDetails.this, login.class));
                                        } else {
                                            progressDoalog.dismiss();
                                            Toast.makeText(CustomerDetails.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                progressDoalog.dismiss();
                                Toast.makeText(CustomerDetails.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    progressDoalog.dismiss();
                    Toast.makeText(CustomerDetails.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    synchronized void firebaseTask() {

        while (true) {
            String id = String.valueOf(System.currentTimeMillis()).substring(0, 12);
            if (!pinDataSnapshot.child(id.substring(8) + "-" + stnCode).exists()) {
                uid = id + "-" + stnCode;
                notify();
                break;
            }
        }


    }

}


