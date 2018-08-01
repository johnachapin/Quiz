package com.example.racarlson1.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderActivity extends AppCompatActivity {

    private TextView mFirstPlace;
    private TextView mSecondPlace;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public static class User {


        public String u_name;
        public int u_score;

        public User(String inName, int inScore) {
            u_name = inName;
            u_score = inScore;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);
        //Initialize Database link
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("server/saving-data/fireblog");
        DatabaseReference usersRef = myRef.child("users");


        myRef.orderByValue().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //System.out.println("The " + dataSnapshot.getKey() + " score is " + dataSnapshot.getValue());
                mNameTextView.setText("First Place: " + user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            // ...
        });

    }

    public void finish(View view) {
        this.finish();
    }
}
