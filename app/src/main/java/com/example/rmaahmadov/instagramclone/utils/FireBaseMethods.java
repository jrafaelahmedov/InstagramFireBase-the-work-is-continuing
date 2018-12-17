package com.example.rmaahmadov.instagramclone.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.rmaahmadov.instagramclone.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FireBaseMethods {
    private static final String TAG = "FireBaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String user_id;
    DatabaseReference myRef;
    private Context mContex;

    public FireBaseMethods(Context context) {
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mContex = context;
        if (mAuth.getCurrentUser() != null) {
            user_id = mAuth.getCurrentUser().getUid();
        }
    }

    public boolean checkingIfUsernameExsists(String username, DataSnapshot dataSnapshot) {
        Log.d(TAG, "checkingIfUsernameExsists: checking " + username + " is alredy registrated or not...........................");
        User user = new User();
        for (DataSnapshot ds : dataSnapshot.child(user_id).getChildren()) {
            Log.d(TAG, "checkingIfUsernameExsists: datasnapshoot..................................." + ds);
            user.setUsername(ds.getValue(User.class).getUsername());

            if (StringManipulation.expendUsername(user.getUsername()).equals(username)) {
                Log.d(TAG, "checkingIfUsernameExsists: FOUND IN MACH.................");
                return true;
            }
        }
        return false;
    }


    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    } else {

                        Toast.makeText(mContex, "couldn't sent verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public void registerNewAccount(final String email, String password, String phonenumber, final String username) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContex, "failed",
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            user_id = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + user_id);
                            sendVerificationEmail();
                        }

                    }
                });
    }


    public void addNewUser(String email, String username, String description, String website, String profile_photo) {
//        DatabaseReference myuserkeyrefer=myRef
//                .child("users")
//                .child(mAuth.getCurrentUser().getUid())
//                .push();
//
//        DatabaseReference myUserKeyReferId=myRef
//                .child(mAuth.getCurrentUser().getUid())
//                .push();

        String usersRef = "users/" + mAuth.getCurrentUser().getUid();

        Map messagesMap = new HashMap();
        messagesMap.put("email", email);
        messagesMap.put("phone_number", 1);
        messagesMap.put("user_id", mAuth.getCurrentUser().getUid());
        messagesMap.put("username", username);


        Map messageBody = new HashMap();
        messageBody.put(usersRef, messagesMap);

        myRef.updateChildren(messageBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e == null) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                    System.out.println("Register user failed.............." + e.getLocalizedMessage().toString());
                }
            }
        });


//        DatabaseReference myuserkeyrefer2=myRef
//                .child("user_account_settings")
//                .child(mAuth.getCurrentUser().getUid())
//                .push();
//
//        DatabaseReference myUserKeyReferId2=myRef
//                .child(mAuth.getCurrentUser().getUid())
//                .push();

        String usersRef2 = "user_account_settings/" + mAuth.getCurrentUser().getUid();

        Map messagesMap1 = new HashMap();
        messagesMap1.put("description", description);
        messagesMap1.put("display_name", "");
        messagesMap1.put("followers", 0);
        messagesMap1.put("following", 0);
        messagesMap1.put("posts", 0);
        messagesMap1.put("profile_photo", "none");
        messagesMap1.put("username", StringManipulation.condenseUsername(username));
        messagesMap1.put("website", "");


        Map messageBody1 = new HashMap();
        messageBody1.put(usersRef2, messagesMap1);

        myRef.updateChildren(messageBody1).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e == null) {
                    Log.d(TAG, "Register user succesfully inserted............................");
                    Toast.makeText(mContex, "Register user succesfully inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Register user failed............................");
                    Toast.makeText(mContex, "Register user failed", Toast.LENGTH_SHORT).show();
                    System.out.println("Register user failed.............." + e.getLocalizedMessage().toString());
                }
            }
        });
    }
    
}
