package com.example.doorlockproject.ui.slideshow;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doorlockproject.AcceptRequest;
import com.example.doorlockproject.NewCircle;
import com.example.doorlockproject.R;
import com.example.doorlockproject.User1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SlideshowFragment extends Fragment {
    public int a;
    public Button[] b=new Button[20];
    public TextView[] t=new TextView[20];
    public AcceptRequest[] ar= new AcceptRequest[20];

    private SlideshowViewModel slideshowViewModel;

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final Button button=new Button(getActivity());

        button.setId(a);
        button.setText("Get Accept Requests");
        final SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0);
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        final CollectionReference ref1 = db.collection("Accept Requests");
        Toast.makeText(this.getContext(), pref.getString("circle","1234456"), Toast.LENGTH_SHORT).show();
        final Query query = ref1.whereEqualTo("circle_id", pref.getString("circle","1234456"));
      slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View mContainer = inflater.inflate(R.layout.fragment_slideshow, null);
        final LinearLayout linearLayout = mContainer.findViewById(R.id.gallery_slide);
        try{
            linearLayout.addView(button);
        }catch(Exception e){
            e.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ar[a] = document.toObject(AcceptRequest.class);
                                    t[a]=new TextView(getActivity());
                                    b[a]=new Button(getActivity());
                                    b[a].setId(a);
                                    t[a].setId(a);
                                    t[a].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    b[a].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    t[a].setText(ar[a].getName());
                                    b[a].setText("Accept");
                                    b[a].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            a=0;
                                            CollectionReference ref2=db.collection(ar[a].getName());
                                            ref2.document(pref.getString("user_id","123456")).delete();
                                            Query q1 = ref2;
                                            q1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().size() == 0) {
                                                            ref1.document(ar[a].getName()).delete();
                                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                            DatabaseReference myRef1;
                                                            myRef1 = database.getReference("users/"+pref.getString("circle","hi")+"/accept/"+ar[a].getName());
                                                            myRef1.child("accept").setValue(true);
                                                        }
                                                    }
                                                }
                                            });
                                        }

                                    });
                                    try{
                                        linearLayout.addView(t[a]);
                                        linearLayout.addView(b[a]);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    a++;
                                }
                            }
                        }
                    }
                });
            }
        });


        return mContainer;
    }
}
