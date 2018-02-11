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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mohnish.railwayticket.SupportFiles.EncryptionHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileOutputStream;
import java.io.IOException;
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

              /*  IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Scan a QR Code");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setOrientationLocked(true);
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(CaptureActivityPortrait.class);
                integrator.initiateScan();*/

                startActivityForResult(new Intent(MainActivity.this, QRScanner.class), 2);

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


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }


        if (requestCode == 2) {
            HashMap<String, String> hm = (HashMap<String, String>) data.getSerializableExtra("hashmap");
            try {
                StoreLocally(hm);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

   
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.home) {

        } else if (id == R.id.bookinghistory) {
            fragment = new BookingHistory();

        } else if (id == R.id.settings) {
            //   fragment = new BookingHistory();
        } else if (id == R.id.logout) {

        } else if (id == R.id.feedback) {

        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
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

    private void StoreLocally(HashMap map) throws IOException {

        String FILENAME = map.get("from").toString().replace("from=", "") + "-" + map.get("to").toString().replace("to=", "") + " " + map.get("dateTime").toString().replace("dateTime=", "").replace("/", "").replace(".", "").replace(":", "").trim();
        String string = map.get("from") + ";" + map.get("to") + ";" + map.get("class") + ";" + map.get("returnStatus") + ";" + map.get("counterEmployee") + ";" + map.get("dateTime") + ";" + map.get("expiry") + ";" + map.get("flag") + ";" + map.get("passengerId");

        FileOutputStream out = openFileOutput(FILENAME, MODE_PRIVATE);
        out.write(string.getBytes());
        out.close();

        DatabaseHandler db = new DatabaseHandler(this);
        db.AddFile(FILENAME);


    }


    /*private  void ReadLocally(String file)
    {
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput(file);

            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            StringBuilder sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                sb.append(s).append("\n");
            }
          Toast.makeText(this,sb.toString(),Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        DatabaseHandler db = new DatabaseHandler(this);
        List<String> files=db.GetFile();
        for (String d:files){
            Log.d("TAG",d);
        }




    }
*/

}
