package com.example.busticketbooking;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    TextView from,to,datae,seats,total;
    TextView dept,route,coch,fares;
    private TextView[][] textViews = new TextView[9][4];
    private int[][] clickCount = new int[9][4];
    private String[] buttonIds = new String[36];
    private List<String> clickedButtonIds = new ArrayList<>();
    private Map<String, String> seatsData = new HashMap<>();
    private DatabaseReference databaseReference;
    int far;
    Button proc;
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
        proc=findViewById(R.id.proceed);




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

       // textViews[2][3].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8bbe1b")));
       // FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Seats").child(fromm+tto).child(mail).child(coach);
        readDataFromFirebase();

      /* a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
            }
        });*/

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity3.this,Payment.class);
                intent1.putStringArrayListExtra("clickedButtonIds", new ArrayList<>(clickedButtonIds));
                intent1.putExtra("dest",fromm+tto);
                intent1.putExtra("dat",mail);
                intent1.putExtra("coach",coach);
                intent1.putExtra("fare",String.valueOf(far*clickedButtonIds.size()));
                startActivity(intent1);
                finish();
            }
        });

    }

    private void readDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                seatsData.clear(); // Clear existing data

                for (DataSnapshot seatSnapshot : dataSnapshot.getChildren()) {
                    // Iterate through the seats data
                    String seatId = seatSnapshot.getKey();
                    String stringValue = seatSnapshot.getValue(String.class);

                    seatsData.put(seatId, stringValue);
                }

                // Update TextViews based on the retrieved data
                updateTextViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Toast.makeText(MainActivity3.this, "Failed to read data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTextViews() {
        // Iterate through the TextViews and update their clickability
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonId = String.format("%c%d", 'a' + i, j);
                boolean isSeatOccupied = seatsData.containsKey(buttonId) && seatsData.get(buttonId).equals("true");
               // textViews[i][j].setClickable(!isSeatOccupied);

                // Update TextView background tint based on occupancy

                // Update TextView clickability
               if(!isSeatOccupied) {
                   textViews[i][j].setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ff00")));
                   textViews[i][j].setClickable(false);
               }
            }
        }
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
                buttonIds[index] = String.format("%c%d", rowLabel, j );
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