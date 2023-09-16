package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Welcome extends AppCompatActivity {

    AutoCompleteTextView from,destin;
    Button btn;
    TextView date;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    String[] places={"Dhaka","Cumilla","Chittagong","Rajshahi","Khulna","Rangpur","Dinajpur","Panchagarh","Nilphamari","Saidpur"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        btn=findViewById(R.id.search_buses);
        from=findViewById(R.id.from);
        destin=findViewById(R.id.destin);
        date=findViewById(R.id.date);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        from.setAdapter(adapter);
        destin.setAdapter(adapter);

        

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

}