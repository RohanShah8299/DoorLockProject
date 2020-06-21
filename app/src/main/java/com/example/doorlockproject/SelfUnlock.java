package com.example.doorlockproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import  android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelfUnlock extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String CHANNEL_ID="doorlock";
    private static final String CHANNEL_NAME="doorlock";
    private static final String CHANNEL_DESCRIPTION="doorlock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_unlock);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        final DatabaseReference myRef,myRef1;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        myRef = database.getReference("users/"+pref.getString("circle","123456")+"/AtDoor/AtDoor");
      //  myRef1 = database.getReference("users/"+pref.getString("circle","123456")+"/AtDoor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean ad=dataSnapshot.getValue(Boolean.TYPE);
                //Toast.makeText(SelfUnlock.this,ad.toString(), Toast.LENGTH_SHORT).show();
                if(ad) displayNotification();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }});
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if(pref.getBoolean("is_admin",true)){
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_remote, R.id.nav_unlock,R.id.nav_period)
                    .setDrawerLayout(drawer)
                    .build();
        }
        else {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_remote, R.id.nav_unlock)
                    .setDrawerLayout(drawer)
                    .build();
        }
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void displayNotification(){
        NotificationCompat.Builder mBuilder=
                new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_unlock)
                .setContentTitle("Someone's at your door")
                .setContentText("Picture of person at your door has been emailed to you.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat mnotificationManagerCompat=NotificationManagerCompat.from(this);
        mnotificationManagerCompat.notify(1,mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.self_unlock, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
