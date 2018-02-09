package com.example.mohnish.railwaytickethub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private FirebaseAuth firebaseAuth;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                /*
                * check net
                * den get instance
                * check user
                * login or main
                *
                * */


                if (isNetworkAvailable()) {
                    // check for registration
                    final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    String imei = null;
                    if (ActivityCompat.checkSelfPermission(SplashScreen.this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        imei = telephonyManager.getImei();
                    } else {
                        imei = telephonyManager.getDeviceId();
                    }
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    final DatabaseReference databaseReference = firebaseDatabase.getReference("secondaryDevice");

                    final String finalImei = imei;
                    databaseReference.child(finalImei).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Log.d("TAG", "EXIST");
                                LoginInfo.deviceId=finalImei;

                                startActivity(new Intent(SplashScreen.this,MainActivity.class));

                            } else {

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("SERIAL", Build.SERIAL);
                                hashMap.put("MODEL", Build.MODEL);
                                hashMap.put("Manufacturer", Build.MANUFACTURER);
                                hashMap.put("Brand", Build.BRAND);
                                hashMap.put("Device", Build.DEVICE);
                                hashMap.put("SDK", String.valueOf(Build.VERSION.SDK_INT));

                                HashMap<String, String> map = new HashMap<>();
                                map.put("deviceData", "nil");
                                map.put("deviceName", Build.DEVICE);


                                databaseReference.child(finalImei).setValue(map);

                                databaseReference.child(finalImei).child("deviceData").setValue(hashMap);
                                LoginInfo.deviceId=finalImei;
                                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                } else {
                    finish(); // show no network

                }


                //  Intent i = new Intent(SplashScreen.this, MainActivity.class);
                // startActivity(i);

                // close this activity
                //finish();
            }
        }, SPLASH_TIME_OUT);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {

    }
}
