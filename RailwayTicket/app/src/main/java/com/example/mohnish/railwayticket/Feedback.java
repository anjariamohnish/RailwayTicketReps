package com.example.mohnish.railwayticket;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Administrator on 11/02/2018.
 */

public class Feedback extends Fragment {
    private TextView textView;
    private Button submit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback,
                container, false);
        textView = (TextView) view.findViewById(R.id.suggestion);
        submit = (Button) view.findViewById(R.id.submit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("feedback");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textView.getText().toString())) {
                    textView.setError("Please Write Feedback");
                    return;
                }

                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().replace("@", "").replace(".", "")).setValue(textView.getText().toString());
                textView.setText("");

                Snackbar snackbar = Snackbar
                        .make(v, "Thanks For your valuable feedback", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });


        return view;
    }
}
