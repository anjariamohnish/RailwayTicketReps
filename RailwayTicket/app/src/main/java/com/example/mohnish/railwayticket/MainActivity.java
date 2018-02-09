package com.example.mohnish.railwayticket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohnish.railwayticket.SupportFiles.EncryptionHelper;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ImageView qrimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!LoginInfo.checkLogin()) {
            startActivity(new Intent(MainActivity.this, login.class));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);

        TextView useremail = headerView.findViewById(R.id.useremail);
        useremail.setText(LoginInfo.getEmail());
        /*********************************************************************/

        qrimg = (ImageView) findViewById(R.id.imageView3);

        qrimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Scan a QR Code");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.initiateScan();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            if (result.getContents() == null) {

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                long[] pattern = {0, 50, 50, 50, 50};
                v.vibrate(pattern, -1);


                String recd = result.getContents();
                try {

                    String decrypted = EncryptionHelper.decipher(recd.substring(recd.length() - 16, recd.length()), recd.substring(0, recd.length() - 16));
                    String[] qrData=decrypted.split(";");
                    HashMap<String, String> hm = new HashMap<String, String>();


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }


                // store in local and wait for sync with firebase !!
                //   Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

        } else if (id == R.id.bookinghistory) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.logout) {

        } else if (id == R.id.feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
