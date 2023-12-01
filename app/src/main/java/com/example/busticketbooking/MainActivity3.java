package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    TextView from,to,datae,seats,total;
    TextView dept,route,coch,fares;
    private TextView[][] textViews = new TextView[9][4];
    private int[][] clickCount = new int[9][4];
    private String[] buttonIds = new String[36];
    private List<String> clickedButtonIds = new ArrayList<>();
    int far;
    TextView a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e1,e2,e3,e4,f1,f2,f3,f4,g1,g2,g3,g4,h1,h2,h3,h4,i1,i2,i3,i4;
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
        seats=findViewById(R.id.seats);
        total=findViewById(R.id.total);
       // a1.setClickable(false);
      /*  a1=findViewById(R.id.a1);
        a2=findViewById(R.id.a2);
        a3=findViewById(R.id.a3);
        a4=findViewById(R.id.a4);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        b3=findViewById(R.id.b3);
        b4=findViewById(R.id.b4);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        d1=findViewById(R.id.d1);
        d2=findViewById(R.id.d2);
        d3=findViewById(R.id.d3);
        d4=findViewById(R.id.d4);
        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        e3=findViewById(R.id.e3);
        e4=findViewById(R.id.e4);
        f1=findViewById(R.id.f1);
        f2=findViewById(R.id.f2);
        f3=findViewById(R.id.f3);
        f4=findViewById(R.id.f4);
        g1=findViewById(R.id.g1);
        g2=findViewById(R.id.g2);
        g3=findViewById(R.id.g3);
        g4=findViewById(R.id.g4);
        h1=findViewById(R.id.h1);
        h2=findViewById(R.id.h2);
        h3=findViewById(R.id.h3);
        h4=findViewById(R.id.h4);
        i1=findViewById(R.id.i1);
        i2=findViewById(R.id.i2);
        i3=findViewById(R.id.i3);
        i4=findViewById(R.id.i4);*/



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
        far=Integer.parseInt(fare);
        initializeTextViews();
        initializeButtonIds();

        //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Seats").child(fromm+tto).child()

      /* a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
            }
        });*/

    }

    private void initializeTextViews() {
        char rowLabel = 'a';
        String packageName = getPackageName();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                int textViewId = getResources().getIdentifier(String.format("%c%d", rowLabel, j + 1), "id", packageName);
                textViews[i][j] = findViewById(textViewId);
               // textViews[i][j].setOnClickListener((View.OnClickListener) this);
                if (textViews[i][j] != null) {
                    textViews[i][j].setOnClickListener((View.OnClickListener) this);
                } else {
                    // Log an error or handle the case where the TextView is not found
                    Log.e("MainActivity", "TextView not found for ID: " + String.format("%c%d", rowLabel, j + 1));
                }
            }
            rowLabel++;
        }
    }
    public void onClick(View view) {
        // Handle the click event for all TextViews here
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                if (textViews[i][j] != null && view.getId() == textViews[i][j].getId()) {
                    // TextView at row i, column j is clicked
                    // Perform actions accordingly
                    clickCount[i][j]++;
                    String buttonId = buttonIds[i * 4 + j];
                    updateBackgroundTint(i, j,buttonId);
                    break;
                }
            }
        }
    }

    private void initializeButtonIds() {
        char rowLabel = 'a';
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                buttonIds[index] = String.format("%c%d", rowLabel, j + 1);
                index++;
            }
            rowLabel++;
        }
    }
    private void updateBackgroundTint(int row, int column,String buttonid) {
        int currentClickCount = clickCount[row][column];
        if (currentClickCount % 2 != 0) {
            // Clicked odd times, change background tint to green
            if(clickedButtonIds.size()<4) {
                textViews[row][column].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F4820F")));
                clickedButtonIds.add(buttonid);
                String sets = "";
                for (int i = 0; i < clickedButtonIds.size(); i++) {
                    sets += clickedButtonIds.get(i);
                    sets += " ";
                }
                seats.setText("Seats: " + sets);
                int tot = far * clickedButtonIds.size();
                total.setText("Total: " + String.valueOf(tot));
            }
            else {
                Toast.makeText(this, "You can't book more then 4 seats", Toast.LENGTH_LONG).show();
            }
        } else {
            // Clicked even times, revert background tint to the original
            textViews[row][column].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E8E5E2")));
            clickedButtonIds.remove(buttonid);// Set to null for the default color
            String sets="";
            for (int i=0;i<clickedButtonIds.size();i++){
                sets+=clickedButtonIds.get(i);
                sets+=" ";
            }
            seats.setText("Seats: "+sets);
            int tot=far*clickedButtonIds.size();
            total.setText("Total: "+String.valueOf(tot));
        }
    }

}