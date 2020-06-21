package com.example.doorlockproject.ui.period;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doorlockproject.R;
import com.example.doorlockproject.ui.period.PeriodViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class PeriodFragment extends Fragment {
    EditText period,ID;
    Button setPeriod;
    FirebaseFirestore db;
    private PeriodViewModel periodViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final SharedPreferences pref = this.getActivity().getSharedPreferences("pref", 0);
        periodViewModel =
                ViewModelProviders.of(this).get(PeriodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_period, container, false);
     //   final TextView textView = root.findViewById(R.id.text_gallery);
       period=(EditText) root.findViewById(R.id.editTextPeriod);
       ID=(EditText)root.findViewById(R.id.editTextUid);
       setPeriod=(Button)root.findViewById(R.id.buttonSetPeriod);
       setPeriod.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DocumentReference myRef=db.collection(pref.getString("circle","123456")).document(ID.getText().toString());
               myRef.update("period", Integer.parseInt(period.getText().toString()));
           }
       });

        return root;
    }
}
