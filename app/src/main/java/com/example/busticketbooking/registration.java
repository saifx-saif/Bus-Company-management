package com.example.busticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {


    DatabaseReference reference;
    FirebaseAuth auth;
    EditText name,email,phone,password;
    Button register;
    TextView login;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id.name);
        email=findViewById(R.id.mail);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names=name.getText().toString().trim();
                String emails=email.getText().toString().trim();
                String phones=phone.getText().toString().trim();
                String pass=password.getText().toString().trim();
                User user=new User();
                user.setEmail(emails);
                user.setName(names);
                user.setPhone(phones);
                if (names.equals(null) || emails.equals(null) || phones.equals(null) || pass.equals(null)  ){
                    Toast.makeText(registration.this, "ALL Feilds Need to be Filled", Toast.LENGTH_SHORT).show();
                }
                else if(pass.length()<6){
                    Toast.makeText(registration.this,"Password length less than 6 character", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(Patterns.EMAIL_ADDRESS.matcher(emails).matches()){
                        auth.createUserWithEmailAndPassword(emails,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    reference.child("User").child(emails.replace('.',',')).setValue(user);
                                    Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(registration.this,login.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

            }
        });
    }
}