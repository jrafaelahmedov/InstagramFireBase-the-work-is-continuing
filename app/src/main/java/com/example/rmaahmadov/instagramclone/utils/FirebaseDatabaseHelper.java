package com.example.rmaahmadov.instagramclone.utils;

import android.support.annotation.NonNull;

import com.example.rmaahmadov.instagramclone.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUsers;
    private FirebaseAuth mAuth;
    private List<User> users = new ArrayList<>();


    public FirebaseDatabaseHelper() {
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mReferenceUsers=mDatabase.getReference("users/"+mAuth.getCurrentUser().getUid());
    }

    public FirebaseDatabaseHelper(FirebaseDatabase mDatabase, DatabaseReference mReferenceUsers, List<User> users) {
        this.mDatabase = mDatabase;
        this.mReferenceUsers = mReferenceUsers;
        this.users = users;
    }
    
    public interface DataStatus{
        void DataIsLoaded(List<User> users,List<String> keys);
        void DataIsInserted();
        void DataIsUploaded();
        void DataIsDeleted();
    }
    
    
    public void readUsers(final DataStatus dataStatus){
        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode:dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    users.add(user);
                }
                dataStatus.DataIsLoaded(users,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
