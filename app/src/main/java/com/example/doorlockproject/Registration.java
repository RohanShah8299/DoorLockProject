package com.example.doorlockproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.auth.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class Registration extends AppCompatActivity {
    EditText Fname,Lname,uid,pass,auth,email1;
    Button reg;
    User1 user1;
    FirebaseFirestore db;
    Integer Uid,mem_no=0;
    String hash1;
    Map<String, Object> user;
    String prev_hash="",next_hash="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private static final String TAG="";
    private DocumentSnapshot mLastQueriedDocument;
    private ArrayList<User> mNotes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef;
        myRef = database.getReference("users");
        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Fname=(EditText)findViewById(R.id.editTextFname);
        email1=(EditText)findViewById(R.id.editTextEmail);
        Lname=(EditText)findViewById(R.id.editTextLname);
        uid=(EditText)findViewById(R.id.editTextID);
        pass=(EditText)findViewById(R.id.editTextPass);
        auth=(EditText)findViewById(R.id.editTextAuth);
        reg=(Button)findViewById(R.id.buttonRegister);

        db=FirebaseFirestore.getInstance();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstn = Fname.getText().toString();
                String lastn = Lname.getText().toString();
                Uid = Integer.parseInt(uid.getText().toString());
                Integer Auth = Integer.parseInt(auth.getText().toString());
                String Email1=email1.getText().toString();
                String Pass = pass.getText().toString();
                myRef.child(pref.getString("circle","123456")+"/emails/"+Uid.toString()).setValue(Email1);
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] ans = digest.digest(Uid.toString().getBytes());
                    hash1 = ans.toString();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException("Couldn't find a " + "SHA3" + " provider", e);
                }
                user = new HashMap<>();
                user.put("period",54750);
                user.put("first_name", firstn);
                user.put("last_name", lastn);
                user.put("auth", Auth);
                user.put("email",Email1);
                user.put("pass", Pass);
                user.put("self_hash", hash1);
                user.put("user_ID", Uid.toString());
                editor.putString("user_id",Uid.toString());
                final CollectionReference reference1 = db.collection(pref.getString("circle","123456"));
                Query q1 = reference1.orderBy("mem_no", Query.Direction.DESCENDING).limit(1);
                q1.get().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {Log.e(TAG, "Exception: "+Log.getStackTraceString(e));}
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().size() == 0) {
                                prev_hash=hash1;
                                next_hash=hash1;
                                mem_no=1;
                                   user.put("prev_hash",prev_hash);                                                                                  
                                   user.put("next_hash",next_hash);                                                                                  
                                   user.put("mem_no",mem_no);                                                                                        
                                      db.collection(pref.getString("circle","123456")).document(Uid.toString())
                                              .set(user)                                                                                              
                                              .addOnSuccessListener(new OnSuccessListener<Void>() {                                                  
                                                  @Override                                                                                          
                                                  public void onSuccess(Void aVoid) {
                                                     editor.putBoolean("logged_in",true);
                                                      editor.apply();
                                                     //editor.putBoolean("is_admin",true);
                                                    //  editor.putString("circle",pref.getString("circle","123456"));
                                                    // editor.apply();
                                                      Intent i=new Intent(Registration.this, SelfUnlock.class);
                                                      startActivity(i);
                                                  }
                                              })                                                                                                      
                                              .addOnFailureListener(new OnFailureListener() {                                                        
                                                  @Override                                                                                          
                                                  public void onFailure(@NonNull Exception e) {Log.e(TAG, "Exception: "+Log.getStackTraceString(e));}
                                              });                                                                                                    
                            }
                            if (task.getResult().size() != 0) {
                                Toast.makeText(Registration.this, String.valueOf(task.getResult().size()), Toast.LENGTH_SHORT).show();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.e(TAG, "Exception: task fail");
                                    user1 = document.toObject(User1.class);
                                    break;
                                }
                                Toast.makeText(Registration.this, user1.getSelf_hash(), Toast.LENGTH_SHORT).show();
                                prev_hash = user1.getSelf_hash();
                                next_hash = user1.getNext_hash();
                                mem_no = user1.getMem_no() + 1;
                                DocumentReference myRef = db.collection(pref.getString("circle","123456")).document(user1.getUser_ID());
                                myRef.update("next_hash", hash1);
                                   user.put("prev_hash",prev_hash);
                                   user.put("next_hash",next_hash);
                                   user.put("mem_no",mem_no);
                                      db.collection(pref.getString("circle","123456")).document(Uid.toString())
                                              .set(user)
                                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                     // CollectionReference reference2=db.collection().document("Accept_Requests").collection("name");
                                                     // SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                                    //  SharedPreferences.Editor editor = pref.edit();
                                                      editor.putBoolean("logged_in",true);
                                                      editor.apply();
                                                      editor.putBoolean("is_admin",false);
                                                      editor.apply();
                                                      editor.putString("circle",pref.getString("circle","123456"));
                                                     editor.apply();
                                                     Intent i=new Intent(Registration.this, SelfUnlock.class);
                                                      startActivity(i);
                                                  }
                                              })
                                              .addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {Log.e(TAG, "Exception: "+Log.getStackTraceString(e));}
                                              });
                            }
                        }
                    }
                });
            }

        });
    }
}
