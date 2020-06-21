package com.example.doorlockproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinCircle extends AppCompatActivity {
    private static final String TAG="";
    EditText name,circle_id;
    Button join;
    FirebaseFirestore db;
    String name1,circle_id1;
    User1 user1;
    String location;
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        name=(EditText)findViewById(R.id.editTextName);
        circle_id=(EditText)findViewById(R.id.editTextCircleID);
        join=(Button)findViewById(R.id.buttonJoin);
        db=FirebaseFirestore.getInstance();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1=name.getText().toString();
                circle_id1=circle_id.getText().toString();
                final Map<String, Object> data = new HashMap<>();
                
                if (!circle_id.getText().toString().equals(""))
                {

                    DocumentReference docRef = db.collection("inactive_circles").document(circle_id.getText().toString());
                    docRef.get().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {Log.e(TAG, "Exception: "+Log.getStackTraceString(e));}
                    }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Circle user = documentSnapshot.toObject(Circle.class);

                                if (user.getActive())
                                {
                                    final DatabaseReference myRef1,myRef;
                                    editor.putBoolean("requested",true);
                                    editor.apply();
                                    Toast.makeText(JoinCircle.this, "before here", Toast.LENGTH_SHORT).show();
                                    myRef = database.getReference("users/");

                                    myRef.child(circle_id1+"/accept/"+name1+"/accept").setValue(false);
                                    editor.putString("requested_name",name1);
                                    editor.apply();
                                    editor.putString("requested_circle",circle_id1);
                                    editor.apply();
                                    editor.putBoolean("requested",true);
                                    editor.apply();
                                    Toast.makeText(JoinCircle.this, "after here", Toast.LENGTH_SHORT).show();
                                    CollectionReference ref2=db.collection(circle_id1);
                                    final DocumentReference ref=db.collection("Accept Requests").document(name1);
                                    data.put("circle_id",circle_id1);
                                    data.put("name",name1);
                                    Query q1=ref2.orderBy("mem_no", Query.Direction.DESCENDING);
                                    q1.get().addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(JoinCircle.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if (task.isSuccessful()) {
                                                int a=1;
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    user1 = document.toObject(User1.class);
                                                    data.put(user1.getUser_ID(),false);
                                                    db.collection(name1).document(user1.getUser_ID()).set(data);
                                                }
                                                ref.set(data);
                                            }}
                                    });
                                    editor.putBoolean("is_admin",false);
                                    editor.apply();
                                   // circle_id1=circle_id.getText().toString();
                                    //editor.putString("circle",circle_id1);
                                    //editor.apply();

                                    Toast.makeText(JoinCircle.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(JoinCircle.this, ToBeAccepted.class);
                                    startActivity(i);
                                    
                                }
                                else
                                {
                                    Toast.makeText(JoinCircle.this, "Circle not in use yet", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(JoinCircle.this, "Circle ID Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
