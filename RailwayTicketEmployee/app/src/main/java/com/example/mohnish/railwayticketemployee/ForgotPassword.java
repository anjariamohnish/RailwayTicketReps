package com.example.mohnish.railwayticketemployee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.email);

        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this);

                progressDialog.setMessage("loading....");
                progressDialog.setTitle("Log In");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setText("");

                    email.setError("Enter Email Id");
                    return;
                }
                if (!(email.getText().toString().contains("@"))) {
                    email.setText("");

                    email.setError("Enter Valid Email");
                    return;
                }
                if (!(email.getText().toString().contains(".com"))) {
                    email.setText("");

                    email.setError("Enter Valid Email");
                    return;
                }
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(ForgotPassword.this,"Password Reset Email Sent Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this,login.class));
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(ForgotPassword.this,"ERROR",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
*/
    }

}
