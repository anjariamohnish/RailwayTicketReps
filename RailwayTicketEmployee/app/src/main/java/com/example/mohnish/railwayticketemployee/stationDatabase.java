package com.example.mohnish.railwayticketemployee;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohnish on 28-01-2018.
 */

public class stationDatabase {

   static List stationList = new ArrayList();

    public void GetStationDatabase() {
        stationList.clear();


        FirebaseDatabase firedata = FirebaseDatabase.getInstance();
        final DatabaseReference db = firedata.getReference("station");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                stationList = (List) dataSnapshot.getValue();

                stationList.remove(0);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


}