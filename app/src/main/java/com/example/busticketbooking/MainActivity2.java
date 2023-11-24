package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

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
        Intent intent=getIntent();
        String tto=intent.getStringExtra("to");
        String fromm=intent.getStringExtra("from");
        String date=intent.getStringExtra("date");
        from.setText(fromm);
        to.setText(tto);
        textView.setText(date);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);


            }
        });
    }
}