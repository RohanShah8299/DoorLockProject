package com.example.doorlockproject.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doorlockproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Button unlock;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef;


        final SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0);
        myRef = database.getReference("users/"+pref.getString("circle","123456"));
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
       // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
              //  textView.setText(s);
            }
        });
        unlock=(Button)root.findViewById(R.id.buttonUnlock);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("/unlock/unlock").setValue(true);
                myRef.child("/AtDoor/AtDoor").setValue(false);
            }
        });
        return root;
    }
}
