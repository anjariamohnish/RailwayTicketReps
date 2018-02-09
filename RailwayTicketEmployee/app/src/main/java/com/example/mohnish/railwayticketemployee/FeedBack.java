package com.example.mohnish.railwayticketemployee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedBack extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        editText = findViewById(R.id.suggestion);

        if (!LoginInfo.checkLogin()) {
            startActivity(new Intent(FeedBack.this, login.class));
        }

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    editText.setError("");
                    return;
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("user", LoginInfo.getEmail());
                map.put("feedback", editText.getText().toString());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("feedback");
                databaseReference.child(databaseReference.push().getKey()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FeedBack.this, "Thank you for providing feedback", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FeedBack.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}
