package com.example.doorlockproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doorlockproject.NewCircle;
import com.example.doorlockproject.R;
import com.example.doorlockproject.SelfUnlock;

public class MainActivity extends AppCompatActivity {
    Button newCircle;
    Button joinCircle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
     //   editor.putBoolean("is_admin", false);
     //   editor.apply();
     //   editor.putBoolean("logged_in",false);
     //   editor.apply();
     //   editor.putBoolean("requested",false);
     //   editor.apply();
        if(pref.getBoolean("logged_in",false)){
            Intent i=new Intent(MainActivity.this, SelfUnlock.class);
            startActivity(i);
        }
        if(pref.getBoolean("requested",true)){
            Intent i=new Intent(MainActivity.this, ToBeAccepted.class);
            startActivity(i);
        }

      //  if(pref.getBoolean("logged_in",false)){
      //      Intent i2=new Intent(MainActivity.this, SelfUnlock.class);
      //      startActivity(i2);
     //   }
        editor.putBoolean("is_admin", false);
        editor.putBoolean("logged_in",false);
        editor.commit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newCircle=(Button) findViewById(R.id.buttonNew);
        joinCircle=(Button) findViewById(R.id.buttonJoin);
        joinCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, JoinCircle.class);
                startActivity(i);
            }
        });
        newCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, NewCircle.class);
                startActivity(i);
            }
        });
    }
}

