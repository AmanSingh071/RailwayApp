package com.example.railway.activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.railway.R;
import com.example.railway.activities.Activities.bookTickets;
import com.example.railway.activities.Models.trainModel;

import java.util.ArrayList;

public class bookrailway extends RecyclerView.Adapter<bookrailway.MyViewHolder> {


    Context context;
    ArrayList<trainModel>list;


    public bookrailway(Context context, ArrayList<trainModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.bookrailwaylayout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        trainModel Trainmodel = list.get(position);
        int pos=position;
        holder.name.setText(Trainmodel.getName());
        holder.num.setText(Trainmodel.getTrainnnum());
        holder.city.setText(Trainmodel.getCity());
        holder.destination.setText(Trainmodel.getDestination());
        holder.date.setText(Trainmodel.getDate());



        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setintetnonclickitem("uploadAdapter",  pos);
            }
        });


    }

    private void setintetnonclickitem(String ref, int pos) {

        Intent intent=new Intent(context, bookTickets.class);

        intent.putExtra("index",pos);
        intent.putExtra("class",ref);
        ContextCompat.startActivity(context,intent,null);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,num,date,city,destination;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.railname);
            num=itemView.findViewById(R.id.railno);
            date=itemView.findViewById(R.id.raildate);
            city=itemView.findViewById(R.id.railcity);
            destination=itemView.findViewById(R.id.raildestination);

            card=itemView.findViewById(R.id.cardrail);
        }
    }
}

