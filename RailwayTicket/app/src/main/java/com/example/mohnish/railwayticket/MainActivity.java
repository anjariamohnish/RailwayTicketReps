package com.example.mohnish.railwayticket;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (!LoginInfo.checkLogin()) {
            startActivity(new Intent(MainActivity.this, Login.class));
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

        getFragmentManager().beginTransaction().replace(R.id.content_main, new Home()).commit();
        /*********************************************************************/


    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.home) {
            getFragmentManager().beginTransaction().replace(R.id.content_main, new Home()).commit();

        } else if (id == R.id.bookinghistory) {

            getFragmentManager().beginTransaction().replace(R.id.content_main, new BookingHistory()).commit();

        } else if (id == R.id.settings) {
            //   fragment = new BookingHistory();
        } else if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,Login.class));
        } else if (id == R.id.feedback) {
            getFragmentManager().beginTransaction().replace(R.id.content_main, new Feedback()).commit();

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
