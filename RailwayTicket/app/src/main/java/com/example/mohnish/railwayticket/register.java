package com.example.mohnish.railwayticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class register extends AppCompatActivity {

    TextView email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, Login.class));
            }
        });


        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Email Id");
                    return;
                }
                if (!(email.getText().toString().contains("@"))) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Valid Email");
                    return;
                }
                if (!(email.getText().toString().contains(".com"))) {
                    email.setText("");
                    password.setText("");
                    email.setError("Enter Valid Email");
                    return;
                }

                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setText("");
                    password.setError("Enter Password");
                    return;
                }
                if (!(password.getText().toString().length() > 6)) {
                    password.setText("");
                    password.setError("Passowrd is too Short");
                    return;
                }

                Intent intent=new Intent(register.this,CustomerDetails.class);
                intent.putExtra("email",email.getText().toString().trim());
                intent.putExtra("password",password.getText().toString());
                startActivity(intent);



            }
        });


    }


}
