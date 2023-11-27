package com.example.busticketbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Trip> list;
    TripAdapter tripAdapter;
    DatabaseReference databaseReference;
    CardView cardView;
    TextView from,to,textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        cardView=findViewById(R.id.cardView);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        textView=findViewById(R.id.textView3);
        recyclerView=findViewById(R.id.recycle);
        Intent intent=getIntent();
        String tto=intent.getStringExtra("to");
        String fromm=intent.getStringExtra("from");
        String date=intent.getStringExtra("date");
        String dat=intent.getStringExtra("dat");
        from.setText(fromm);
        to.setText(tto);
        textView.setText(date);
        String mail="mahmudsaifx@gmail,com";

        String st=fromm+tto;

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Trips").child(st).child(dat);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter=new TripAdapter(this,list,date,mail);
        recyclerView.setAdapter(tripAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Trip tripAvailable=dataSnapshot.getValue(Trip.class);
                    list.add(tripAvailable);
                }
                tripAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}