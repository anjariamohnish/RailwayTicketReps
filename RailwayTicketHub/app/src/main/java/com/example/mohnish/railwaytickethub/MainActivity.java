package com.example.mohnish.railwaytickethub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    Hashtable<Integer, String> hashtable = new Hashtable<>();
    DatabaseReference listnerReference;
    ChildEventListener listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference("counter");
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().toUpperCase();
        mPinFirstDigitEditText.setText(key.substring(0, 1));
        mPinSecondDigitEditText.setText(key.substring(1, 2));
        mPinThirdDigitEditText.setText(key.substring(2, 3));
        mPinForthDigitEditText.setText(key.substring(3, 4));
        mPinFifthDigitEditText.setText(key.substring(4, 5));
        LoginInfo.secretCode = key.substring(0, 5);
        LoginInfo.sessionKey = databaseReference.push().getKey();
        HashMap<String, String> session = new HashMap<>();
        session.put("deviceId", LoginInfo.deviceId);
        session.put("secretCode", key.substring(0, 5));
        session.put("employeeId", "nil");
        databaseReference.child("screenSession").child(LoginInfo.sessionKey).setValue(session);
        databaseReference.child("screenSession").child(LoginInfo.sessionKey).child("sessionData").child("url").setValue("nil");


        listnerReference = firebaseDatabase.getReference("counter");
        listner = listnerReference.child("screenSession").child(LoginInfo.sessionKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG", String.valueOf(dataSnapshot));
                 ChangeActivity();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        progressDialog.dismiss();
        startService(new Intent(getBaseContext(), AppKillListner.class));


    }


    private void ChangeActivity() {
        listnerReference.child("screenSession").child(LoginInfo.sessionKey).removeEventListener(listner);
        startActivity(new Intent(MainActivity.this, QRScreen.class));

    }

    @Override
    public void onBackPressed() {
        // od nothing
    }


    @Override
    protected void onDestroy() {
        Log.d("TAG", "ONDESTROy");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("counter");
        databaseReference.child("screenSession").child(LoginInfo.sessionKey).removeValue();
        super.onDestroy();
    }
}


