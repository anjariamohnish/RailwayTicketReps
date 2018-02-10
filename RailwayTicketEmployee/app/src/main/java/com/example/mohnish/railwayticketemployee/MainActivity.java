package com.example.mohnish.railwayticketemployee;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private AutoCompleteTextView from, to;
    private RadioGroup classGroup;
    private RadioButton classButton = null;
    private CheckBox returnCheckBox;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (LoginInfo.session_id == null) {
            startActivity(new Intent(MainActivity.this, SecondaryScreenLogin.class));
        }
        IntializeActivity();
        final ProgressDialog myDialog = new ProgressDialog(MainActivity.this);


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classButton = findViewById(classGroup.getCheckedRadioButtonId());
                if (TextUtils.isEmpty(from.getText().toString())) {
                    from.setError("Enter Source");

                } else if (TextUtils.isEmpty(to.getText().toString())) {
                    to.setError("Enter Destination");
                }

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("loading....");
                progressDialog.setTitle("");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();


                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("transaction");

                /*
                * C - Ticket Created
                * V - Ticket Validetd
                * */

                final Date date = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR, 24);
                final HashMap<String, String> map = new HashMap<>();
                map.put("from", from.getText().toString());
                map.put("to", to.getText().toString());
                map.put("class", classButton.getText().toString());
                if (returnCheckBox.isChecked()) {
                    map.put("returnStatus", "1");
                } else {
                    map.put("returnStatus", "0");
                }
                map.put("counterEmployee", LoginInfo.empId);
                map.put("dateTime", dateFormat.format(date));
                map.put("expiry", dateFormat.format(calendar.getTime()));
                map.put("flag", "C");
                map.put("passengerId", "nil");
                databaseReference.child(databaseReference.push().getKey()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String rawUrl = "from=" + map.get("from") + ";to=" + map.get("to") + ";class=" + map.get("class") + ";returnStatus=" + map.get("returnStatus") + ";counterEmployee=" + map.get("counterEmployee") + ";dateTime=" + map.get("dateTime") + ";expiry=" + map.get("expiry") + ";flag=" + map.get("flag") + ";passengerId=" + map.get("passengerId") + "";
                            String key = String.valueOf(System.currentTimeMillis()).substring(0, 12);
                            String url = null;
                            try {
                                url = EncryptionHelper.cipher(key, rawUrl) + key;
                                // send url to secondary screen adding to db
                                final DatabaseReference ref = firebaseDatabase.getReference("counter");
                                ref.child("screenSession").child(LoginInfo.session_id).child("sessionData").child("url").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // make counter wait for scan and pin
                                            ref.child("screenSession").child(LoginInfo.session_id).child("sessionData").child("url").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    myDialog.setMessage("Waiting For Customer To Scan Code...");
                                                    //myDialog.setCancelable(false);
                                                    myDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Transaction", new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            myDialog.dismiss();
                                                            ref.child("screenSession").child(LoginInfo.session_id).child("sessionData").child("url").setValue("nil");
                                                            progressDialog.dismiss();
                                                        }
                                                    });
                                                    myDialog.show();

                                                    if (dataSnapshot.getValue().toString().equals("nil")) {
                                                        Log.d("TAG", "IN");
                                                        from.setText("");
                                                        to.setText("");
                                                        if (returnCheckBox.isChecked()) {
                                                            returnCheckBox.toggle();
                                                        }
                                                        progressDialog.dismiss();
                                                        myDialog.dismiss();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        } else {
                                            // error
                                        }
                                    }
                                });

                            } catch (Exception e) {

                            }

                        } else {

                        }
                    }
                });

            }
        });


    }

    void IntializeActivity() {


        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        classGroup = findViewById(R.id.classGroup);
        returnCheckBox = findViewById(R.id.returnchk);

        String[] stns = StationDatabase.stationList.toArray(new String[StationDatabase.stationList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, stns);

        from.setThreshold(1);
        to.setThreshold(1);
        from.setAdapter(adapter);
        to.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, login.class));
                break;
            case R.id.action_feedback:
                // open feedback page
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    protected void onDestroy() {

        FirebaseAuth.getInstance().signOut();
        super.onDestroy();
    }

}
