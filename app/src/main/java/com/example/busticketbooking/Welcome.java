package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);


        btn=findViewById(R.id.search_buses);
        from=findViewById(R.id.from);
        destin=findViewById(R.id.destin);
        date=findViewById(R.id.date);
        calendar=Calendar.getInstance();
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        from.setAdapter(adapter);
        destin.setAdapter(adapter);

        DatePickerDialog.OnDateSetListener Date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateDate();
            }
        };

        date.setOnClickListener(view -> {
           // DatePickerDialog datePickerDialog;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Use setMinDate on API 11 and higher
                datePickerDialog = new DatePickerDialog(Welcome.this, Date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            } else {
                // For API levels lower than 11, restrict the date range programmatically
                datePickerDialog = new DatePickerDialog(Welcome.this, Date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            }

            datePickerDialog.show();
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fr=from.getText().toString().trim();
                String to=destin.getText().toString().trim();
                String dt=date.getText().toString().trim();

                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int mon=calendar.get(Calendar.MONTH);
                int yr=calendar.get(Calendar.YEAR);
                String datts=String.valueOf(day)+String.valueOf(mon)+String.valueOf(yr);

                Intent intent=new Intent(Welcome.this, MainActivity2.class);
                intent.putExtra("from",fr);
                intent.putExtra("to",to);
                intent.putExtra("date",dt);
                intent.putExtra("dat",datts);
                startActivity(intent);
                from.setText("");
                destin.setText("");
                date.setText("");
            }
        });
    }
    private void updateDate() {
        String mformat="dd/MM/yy EEEE";
        SimpleDateFormat dateFormat=new SimpleDateFormat(mformat, Locale.UK);
        date.setText(dateFormat.format(calendar.getTime()));
    }

}