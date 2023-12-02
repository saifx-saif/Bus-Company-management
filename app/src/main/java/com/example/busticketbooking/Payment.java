package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Payment extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button btn;
    TextView textView,pay;
    ImageView bkash,nagad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        btn=findViewById(R.id.button);
        textView=findViewById(R.id.textView4);
        bkash=findViewById(R.id.bkash);
        nagad=findViewById(R.id.nagad);
        pay=findViewById(R.id.pay);
        final int[] x = {0};

        List<String> receivedClickedButtonIds = getIntent().getStringArrayListExtra("clickedButtonIds");
        String fromto=getIntent().getStringExtra("dest");
        String dat=getIntent().getStringExtra("dat");
        String coach=getIntent().getStringExtra("coach");
        String fare=getIntent().getStringExtra("fare");

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Seats").child(fromto).child(dat).child(coach);
        textView.setText("Your Fare: "+fare);

        bkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay.setText("Payment Method: Bkash");
                x[0] =1;
            }
        });

        nagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay.setText("Payment Method: Nagad");
                x[0] =1;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (x[0]==1) {
                   for (String buttonId : receivedClickedButtonIds) {
                       // Perform actions with each buttonId
                       // For example, you can log, display, or process each buttonId here
                       databaseReference.child(buttonId).setValue("false");

                   }
                   Toast.makeText(Payment.this, "Ticket Booked", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(Payment.this, Welcome.class);
                   startActivity(intent);
                   finish();
               }else {
                   Toast.makeText(Payment.this, "Select a payment method", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
}