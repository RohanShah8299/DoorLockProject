package com.example.doorlockproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewCircle extends AppCompatActivity {
    private static final String TAG="";
    Button create;
    EditText circleID;
    public static String circleID1;
    private static ArrayList<Type> mArrayList = new ArrayList<>();
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_circle);
        create = (Button) findViewById(R.id.buttonCreate);
        circleID = (EditText) findViewById(R.id.editTextCircle);

        db= FirebaseFirestore.getInstance();
        Log.d(TAG, "onCreate: LIST IN ONCREATE = " + mArrayList);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!circleID.getText().toString().equals(""))
                {
                    circleID1=circleID.getText().toString();
                    DocumentReference docRef = db.collection("inactive_circles").document(circleID.getText().toString());
                    docRef.get().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {Log.e(TAG, "Exception: "+Log.getStackTraceString(e));}
                    }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Circle user = documentSnapshot.toObject(Circle.class);

                                if (!user.getActive())
                                {
                                    DocumentReference myRef=db.collection("inactive_circles").document(circleID.getText().toString());
                                    myRef.update("active", true);
                                  //
                                  editor.putBoolean("is_admin",true);
                                    editor.apply();

                                    circleID1=circleID.getText().toString();
                                 editor.putString("circle",circleID1);
                                 editor.apply();
                                    Toast.makeText(NewCircle.this, "welcome", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(NewCircle.this,Registration.class);
                                    startActivity(it);
                                }
                                else
                                {
                                    Toast.makeText(NewCircle.this, "Circle already in use", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Check your Circle ID ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(NewCircle.this, "Circle ID Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
