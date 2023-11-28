package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    TextView from,to,datae;
    TextView dept,route,coch,fares;
    TextView a1,a2,a3,a4;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        datae=findViewById(R.id.textView3);
        dept=findViewById(R.id.dept);
        route=findViewById(R.id.route);
        coch=findViewById(R.id.coach);
        fares=findViewById(R.id.fare);
        a1.setClickable(false);
        a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        a4=findViewById(R.id.a4);

        Intent intent=getIntent();
        String tto=intent.getStringExtra("to");
        String fromm=intent.getStringExtra("from");
        String mail=intent.getStringExtra("mail");
        String date=intent.getStringExtra("Date");
        String coach=intent.getStringExtra("coach");
        String fare=intent.getStringExtra("fare");
        String time=intent.getStringExtra("time");
        from.setText(fromm);
        to.setText(tto);
        datae.setText(date);
        dept.setText("Dept: "+time);
        route.setText("Route: "+fromm+"-"+tto);
        coch.setText("Coach: "+coach);
        fares.setText("Fare: "+fare);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
            }
        });

    }
}