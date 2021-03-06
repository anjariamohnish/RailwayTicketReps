package com.example.mohnish.railwaytickethub;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Hashtable;

public class QRScreen extends AppCompatActivity implements TextWatcher, View.OnKeyListener {


    private ImageView qrImageView;
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
    private AdView mAdView;
    String ticketId = null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscreen);
        getSupportActionBar().hide();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        InitializeActivty();
        mAdView = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);


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
                ClearPin();

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
                progressDialog = new ProgressDialog(QRScreen.this);
                progressDialog.setMessage("Proccesing....");
                progressDialog.setTitle("");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                String secretCode = hashtable.get(1) + hashtable.get(2) + hashtable.get(3) + hashtable.get(4) + hashtable.get(5) + hashtable.get(6) + hashtable.get(7);
               CreateAlertBox(secretCode);


            }
        }

        return false;

    }


    private void InitializeActivty() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        mPinSixthDigitEditText = (EditText) findViewById(R.id.pin_sixth_edittext);
        mPinSeventhDigitEditText = (EditText) findViewById(R.id.pin_seveth_edittext);
        qrImageView = findViewById(R.id.imageView);

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


        new Thread() {
            public void run() {
                SetListner();
            }
        }.start();
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


    private void CreateAlertBox(final String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Use This Code : " + code)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseReference.child("transaction").child(ticketId).child("passengerId").setValue(code);

                        databaseReference.child("counter").child("screenSession").child(LoginInfo.sessionKey).child("sessionData").child("url").setValue("nil");
                        databaseReference.child("counter").child("screenSession").child(LoginInfo.sessionKey).child("sessionData").child("ticketId").setValue("nil");

                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Your Trip is Started", Snackbar.LENGTH_SHORT)
                                .show();
                        ClearPin();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        hashtable.clear();
                        progressDialog.dismiss();
                        ClearPin();
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Confirmation");
        alert.show();
    }

    private synchronized void SetListner() {


        databaseReference.child("counter").child("screenSession").child(LoginInfo.sessionKey).child("sessionData").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("ticketId")) {
                    ticketId = (String) dataSnapshot.getValue();

                }

                if (dataSnapshot.getKey().equals("url")) {
                    String recivedValue = (String) dataSnapshot.getValue();
                    if (recivedValue.equals("nil")) {
                        qrImageView.setVisibility(View.INVISIBLE);
                    } else {
                        qrImageView.setVisibility(View.VISIBLE);
                    }
                    try {
                        Bitmap qrCode = TextToImageEncode(recivedValue);
                        qrImageView.setImageBitmap(qrCode);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }


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


    }

private void ClearPin()
{
    mPinFirstDigitEditText.setText("");
    mPinSecondDigitEditText.setText("");
    mPinThirdDigitEditText.setText("");
    mPinForthDigitEditText.setText("");
    mPinFifthDigitEditText.setText("");
    mPinSixthDigitEditText.setText("");
    mPinSeventhDigitEditText.setText("");
    hashtable.clear();
    mPinFirstDigitEditText.requestFocus();
}
}
