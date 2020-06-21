package com.example.doorlockproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    Button login;
    EditText user_id,passwordlogin,circle_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.buttonLogin);
        user_id=(EditText)findViewById(R.id.editTextLoginUser);
        passwordlogin=(EditText)findViewById(R.id.editTextLoginPass);
        circle_login=(EditText)findViewById(R.id.editTextLoginCircle);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
