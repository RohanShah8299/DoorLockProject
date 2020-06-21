package com.example.doorlockproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ToBeAccepted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_accepted);
        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1;
        myRef1 = database.getReference("users/"+pref.getString("requested_circle","hi")+"/accept/"+pref.getString("requested_name","hi")+"/accept");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(ToBeAccepted.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                Boolean ao=dataSnapshot.getValue(Boolean.TYPE);
                if(ao){
                   // DatabaseReference myRef2 = database.getReference("users/"+pref.getString("requested_circle","hi")+"/accept/"+pref.getString("requested_name","hi")
                    Toast.makeText(ToBeAccepted.this, "you've been accepted!", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(ToBeAccepted.this, Registration.class);
                    startActivity(i);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ToBeAccepted.this, "Databsw", Toast.LENGTH_SHORT).show();
            }});
    }
}
