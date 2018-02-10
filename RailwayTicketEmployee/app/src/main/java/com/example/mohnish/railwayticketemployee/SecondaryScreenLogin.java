package com.example.mohnish.railwayticketemployee;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Hashtable;

public class SecondaryScreenLogin extends AppCompatActivity implements TextWatcher, View.OnKeyListener {
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    Hashtable<Integer, String> hashtable = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_screen_login);


        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);


        mPinFirstDigitEditText.addTextChangedListener(this);
        mPinSecondDigitEditText.addTextChangedListener(this);
        mPinThirdDigitEditText.addTextChangedListener(this);
        mPinForthDigitEditText.addTextChangedListener(this);
        mPinFifthDigitEditText.addTextChangedListener(this);

        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinFifthDigitEditText.setOnKeyListener(this);


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //null
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


        }


    }

    @Override
    public void afterTextChanged(Editable editable) {
//null

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
                final ProgressDialog progressDialog = new ProgressDialog(SecondaryScreenLogin.this);
                progressDialog.setMessage("Proccesing....");
                progressDialog.setTitle("");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                final String secretCode = hashtable.get(1) + hashtable.get(2) + hashtable.get(3) + hashtable.get(4) + hashtable.get(5);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference("counter");
                Query query = databaseReference.child("screenSession").orderByChild("secretCode").equalTo(secretCode);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                LoginInfo.session_id = issue.getKey();
                                LoginInfo.secretCode = secretCode;
                                databaseReference.child("screenSession").child(issue.getKey()).child("employeeId").setValue(LoginInfo.empId);
                                progressDialog.dismiss();
                                startActivity(new Intent(SecondaryScreenLogin.this,MainActivity.class));
                            }
                        } else {
                            progressDialog.dismiss();
                            mPinFirstDigitEditText.getText().clear();
                            mPinSecondDigitEditText.getText().clear();
                            mPinThirdDigitEditText.getText().clear();
                            mPinForthDigitEditText.getText().clear();
                            mPinFifthDigitEditText.getText().clear();
                            hashtable.clear();
                            mPinFirstDigitEditText.requestFocus();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        }

        return false;
    }
}
