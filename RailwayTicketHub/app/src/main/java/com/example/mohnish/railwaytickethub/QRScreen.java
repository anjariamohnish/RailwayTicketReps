package com.example.mohnish.railwaytickethub;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Hashtable;

public class QRScreen extends AppCompatActivity implements TextWatcher, View.OnKeyListener {


    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    private EditText mPinSixthDigitEditText;
    private EditText mPinSeventhDigitEditText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    Hashtable<Integer, String> hashtable = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscreen);
        getSupportActionBar().hide();
        InitializeActivty();

        databaseReference.child("counter").child("screenSession").child(LoginInfo.sessionKey).child("sessionData").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAG",String.valueOf(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (mPinFirstDigitEditText.hasFocus()) {
            hashtable.put(1, charSequence.toString());

        } else if (mPinSecondDigitEditText.hasFocus()) {
            hashtable.put(2, charSequence.toString());


        } else if (mPinThirdDigitEditText.hasFocus()) {
            hashtable.put(3, charSequence.toString());


        } else if (mPinForthDigitEditText.hasFocus()) {
            hashtable.put(4, charSequence.toString());


        } else if (mPinFifthDigitEditText.hasFocus()) {
            hashtable.put(5, charSequence.toString());


        } else if (mPinSixthDigitEditText.hasFocus()) {
            hashtable.put(6, charSequence.toString());


        } else if (mPinSeventhDigitEditText.hasFocus()) {
            hashtable.put(7, charSequence.toString());

        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

        boolean chechDel = false;
        if (keyEvent.getAction() == KeyEvent.ACTION_UP) {

            if (keyCode == KeyEvent.KEYCODE_DEL) {
                chechDel = true;
                if (mPinFirstDigitEditText.hasFocus()) {
                    mPinFirstDigitEditText.setText("");
                    hashtable.remove(1);

                } else if (mPinSecondDigitEditText.hasFocus()) {
                    mPinSecondDigitEditText.setText("");
                    hashtable.remove(2);

                } else if (mPinThirdDigitEditText.hasFocus()) {
                    mPinThirdDigitEditText.setText("");
                    hashtable.remove(3);

                } else if (mPinForthDigitEditText.hasFocus()) {
                    mPinForthDigitEditText.setText("");
                    hashtable.remove(4);

                } else if (mPinFifthDigitEditText.hasFocus()) {
                    mPinFifthDigitEditText.setText("");
                    hashtable.remove(5);

                } else if (mPinSixthDigitEditText.hasFocus()) {
                    mPinSixthDigitEditText.setText("");
                    hashtable.remove(6);

                } else if (mPinSeventhDigitEditText.hasFocus()) {
                    mPinSeventhDigitEditText.setText("");
                    hashtable.remove(7);

                }
                Log.d("TAG", String.valueOf(hashtable.size()));
                if (hashtable.size() == 1) {
                    mPinFirstDigitEditText.requestFocus();
                } else if (hashtable.size() == 2) {
                    mPinSecondDigitEditText.requestFocus();
                } else if (hashtable.size() == 3) {
                    mPinThirdDigitEditText.requestFocus();
                } else if (hashtable.size() == 4) {
                    mPinForthDigitEditText.requestFocus();
                } else if (hashtable.size() == 5) {
                    mPinFifthDigitEditText.requestFocus();
                } else if (hashtable.size() == 6) {
                    mPinSixthDigitEditText.requestFocus();
                } else if (hashtable.size() == 7) {
                    mPinFirstDigitEditText.requestFocus();

                }

            }
        }

        if (keyEvent.getAction() == KeyEvent.ACTION_UP && (!chechDel)) {


            //   Log.d("TAG",String.valueOf(hashtable.size()));
            if (hashtable.size() == 1) {
                mPinSecondDigitEditText.requestFocus();
            } else if (hashtable.size() == 2) {
                mPinThirdDigitEditText.requestFocus();
            } else if (hashtable.size() == 3) {
                mPinForthDigitEditText.requestFocus();
            } else if (hashtable.size() == 4) {
                mPinFifthDigitEditText.requestFocus();
            } else if (hashtable.size() == 5) {
                mPinSixthDigitEditText.requestFocus();
            } else if (hashtable.size() == 6) {
                mPinSeventhDigitEditText.requestFocus();
            } else if (hashtable.size() == 7) {
                final ProgressDialog progressDialog = new ProgressDialog(QRScreen.this);
                progressDialog.setMessage("Proccesing....");
                progressDialog.setTitle("");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                final String secretCode = hashtable.get(1) + hashtable.get(2) + hashtable.get(3) + hashtable.get(4) + hashtable.get(5) + hashtable.get(6) + hashtable.get(7);

            }
        }

        return false;

    }


    private void InitializeActivty() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        mPinSixthDigitEditText = (EditText) findViewById(R.id.pin_sixth_edittext);
        mPinSeventhDigitEditText = (EditText) findViewById(R.id.pin_seveth_edittext);


        mPinFirstDigitEditText.addTextChangedListener(this);
        mPinSecondDigitEditText.addTextChangedListener(this);
        mPinThirdDigitEditText.addTextChangedListener(this);
        mPinForthDigitEditText.addTextChangedListener(this);
        mPinFifthDigitEditText.addTextChangedListener(this);
        mPinSixthDigitEditText.addTextChangedListener(this);
        mPinSeventhDigitEditText.addTextChangedListener(this);


        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinFifthDigitEditText.setOnKeyListener(this);
        mPinSixthDigitEditText.setOnKeyListener(this);
        mPinSeventhDigitEditText.setOnKeyListener(this);
    }
}
