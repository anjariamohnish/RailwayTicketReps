package com.example.mohnish.railwayticket;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mohnish on 19-01-2018.
 */

public class LoginInfo {

    public static  String getEmail()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        return  firebaseAuth.getCurrentUser().getEmail();
    }

   /* public static String getUserName()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("passenger")
        return firebaseAuth.getCurrentUser().getDisplayName();
    }*/

    public static boolean checkLogin()
    {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null)
        {
            return true;
        }else {
            return false;
        }
    }

}
