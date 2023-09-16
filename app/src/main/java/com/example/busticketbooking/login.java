package com.example.busticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email,password;
    Button sign;
    TextView forget,register;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.mail);
        sign=findViewById(R.id.signin);
        forget=findViewById(R.id.forget);
        register=findViewById(R.id.register);
        password=findViewById(R.id.password);

        auth=FirebaseAuth.getInstance();

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=password.getText().toString().trim();
                String emails=email.getText().toString().trim();
                auth.signInWithEmailAndPassword(emails,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(login.this,Welcome.class);
                            Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,registration.class);
                startActivity(intent);
            }
        });
    }
}