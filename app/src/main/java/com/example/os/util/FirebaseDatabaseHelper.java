package com.example.os.util;

import androidx.annotation.NonNull;

import com.example.os.model.Coment;
import com.example.os.model.Os;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {



    private static final String USER_PATH = "USERS";
    private static final String OS_PATH = "OSS";



    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    private List<Os> mListOs;
    private List<Coment> mListComents;




    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
    }


    public boolean isSign(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            return true;
        return false;
    }
    public List<Os> getOssList(String userUid){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListOs.clear();
                mListOs = new ArrayList<>();

                for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                    mListOs.add(datasnap.getValue(Os.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        return mListOs;
    }


    private String getUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void addComent(Coment coment){

    }


    public List<Coment> getComentsList(String osId){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListComents.clear();
                mListComents = new ArrayList<>();
                for (DataSnapshot datasnap: dataSnapshot.getChildren()) {
                    mListComents.add(datasnap.getValue(Coment.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        return mListComents;
    }





    public  DatabaseReference getUserRef(String uid){
        return mDatabase.getReference(USER_PATH).child(uid);

    }

    public DatabaseReference getOssRef() {
        return mDatabase.getReference(OS_PATH);
    }

    public void postNewOss(Os os){
        mRef = getOssRef();
        mRef.child(os.getId()).setValue(os);
        mRef = getUserRef(getUserUid());
        mRef.child(OS_PATH).child(os.getId()).setValue(os.getId());
    }
}
