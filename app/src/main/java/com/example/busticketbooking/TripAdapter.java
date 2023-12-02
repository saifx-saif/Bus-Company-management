package com.example.busticketbooking;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> {

    String mail;
    Context context;
    ArrayList<Trip>list;
    String date;

    public TripAdapter(Context context, ArrayList<Trip> list, String date, String mail) {
        this.context = context;
        this.list = list;
        this.date=date;
        this.mail=mail;
    }

    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tripdetails,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.MyViewHolder holder, int position) {
        Trip tripAvailable=list.get(position);

        //holder.from.setText(tripAvailable.getFrom());
        holder.fromto.setText("Route: "+tripAvailable.getFrom()+"-"+tripAvailable.getDestine());
        holder.coach.setText("Coach: "+tripAvailable.getCoach());
        holder.dept.setText("Dept: "+tripAvailable.getTimes());
       // holder.time.setText(tripAvailable.getTimes());
        holder.fare.setText("Fare: "+tripAvailable.getFare());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView coach,dept,fromto,fare;
        Button select;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fromto=itemView.findViewById(R.id.fromto);
            dept=itemView.findViewById(R.id.dept);
            coach=itemView.findViewById(R.id.coch);
            fare=itemView.findViewById(R.id.fares);
            itemView.findViewById(R.id.selectseat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,MainActivity3.class);
                    intent.putExtra("Date", TripAdapter.this.date);
                    intent.putExtra("mail", TripAdapter.this.mail);
                    intent.putExtra("from",list.get(getAdapterPosition()).getFrom());
                    intent.putExtra("to",list.get(getAdapterPosition()).getDestine());
                    intent.putExtra("coach",list.get(getAdapterPosition()).getCoach());
                    intent.putExtra("fare",list.get(getAdapterPosition()).getFare());
                    intent.putExtra("time",list.get(getAdapterPosition()).getTimes());
                    context.startActivity(intent);
                }
            });
        }
    }
}