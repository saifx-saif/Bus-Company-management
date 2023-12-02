package com.example.busticketbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class addTrip extends AppCompatActivity {

    AutoCompleteTextView from,destin;
    Button btn;
    EditText date,time,number,coach,fare;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    DatabaseReference reference;
    String[] places={"Dhaka","Cumilla","Chittagong","Rajshahi","Khulna","Rangpur","Dinajpur","Panchagarh","Nilphamari","Saidpur"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_add_trip);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_trip);


        btn=findViewById(R.id.search_buses);
        from=findViewById(R.id.from);
        destin=findViewById(R.id.destin);
        date=findViewById(R.id.date);
        time=findViewById(R.id.time);
        number=findViewById(R.id.duration);
        coach=findViewById(R.id.coach);
        fare=findViewById(R.id.fare);
        calendar=Calendar.getInstance();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        from.setAdapter(adapter);
        destin.setAdapter(adapter);

        reference= FirebaseDatabase.getInstance().getReference();

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
                datePickerDialog = new DatePickerDialog(addTrip.this, Date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            } else {
                // For API levels lower than 11, restrict the date range programmatically
                datePickerDialog = new DatePickerDialog(addTrip.this, Date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            }

            datePickerDialog.show();
        });

        TimePickerDialog.OnTimeSetListener Time=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,Hour);
                calendar.set(Calendar.MINUTE,minute);
                showTime();
            }
        };

        time.setOnClickListener(view -> {
            new TimePickerDialog(addTrip.this,Time,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fr=from.getText().toString().trim();
                String to=destin.getText().toString().trim();
                String dates=date.getText().toString().trim();
                String times=time.getText().toString().trim();
                String num=number.getText().toString().trim();
                String coch=coach.getText().toString().trim();
                String far=fare.getText().toString().trim();

                int numbers=Integer.parseInt(num);

                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int mon=calendar.get(Calendar.MONTH);
                int yr=calendar.get(Calendar.YEAR);

                //LocalDate date1=LocalDate.of(yr,mon,day);
                calendar.set(yr,mon,day);
             //  int dayofweek=calendar.get(Calendar.DAY_OF_WEEK);
             //  String datename=getDayName(dayofweek);

               // String datt= String.valueOf(calendar.get(Calendar.YEAR))+String.valueOf(calendar.get(Calendar.MONTH)+1)+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
              //  date.setText(datename);

                Trip trip=new Trip();
                trip.setFrom(fr);
                trip.setDestine(to);
                //trip.setDate(dates);
                trip.setFare(far);
                trip.setCoach(coch);
                trip.setTimes(times);
              //  Seats seats=new Seats();
                int cnt=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i=day;i<=cnt;i++){
                    calendar.set(yr,mon,i);

                    int dayofweek=calendar.get(Calendar.DAY_OF_WEEK);
                    String datename=getDayName(dayofweek);
                    String datts=String.valueOf(i)+String.valueOf(mon)+String.valueOf(yr);
                    String dattes=String.valueOf(i)+"/"+String.valueOf(mon)+"/"+String.valueOf(yr)+"  "+datename;
                    trip.setDate(dattes);

                    HashMap<String,String> seats=new HashMap<>();
                    char nam='a';
                    for (int j=0;j<9;j++){
                        for(int k=0;k<4;k++) {
                            seats.put(nam+String.valueOf(k),"true");
                        }
                        nam++;
                    }

                    reference.child("Trips").child(fr+to).child(datts).child(coch).setValue(trip);
                    reference.child("Seats").child(fr+to).child(datts).child(coch).setValue(seats);

                }
                Toast.makeText(addTrip.this, "Trip Added Successfull", Toast.LENGTH_SHORT).show();

            }
        });



    }
    private void updateDate() {
        String mformat="dd/MM/yy EEEE";
        SimpleDateFormat dateFormat=new SimpleDateFormat(mformat, Locale.UK);
        date.setText(dateFormat.format(calendar.getTime()));
    }
    private void showTime(){
        String timeFormat="h:mm a";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(timeFormat,Locale.US);
        time.setText(simpleDateFormat.format((calendar.getTime())));
    }

    private static String getDayName(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Invalid day";
        }
    }
}