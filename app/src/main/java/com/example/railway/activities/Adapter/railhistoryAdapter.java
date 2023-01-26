package com.example.railway.activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.railway.R;
import com.example.railway.activities.Activities.bookTickets;
import com.example.railway.activities.Fragments.historyFragment;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.trainbookmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class railhistoryAdapter extends RecyclerView.Adapter<railhistoryAdapter.MyViewHolder> {


    Context context;
    ArrayList<trainbookmodel>list;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    public railhistoryAdapter(Context context, ArrayList<trainbookmodel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.railhistory_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        trainbookmodel Trainmodel = list.get(position);
        int pos=position;
        holder.name.setText(Trainmodel.getName());
        holder.num.setText(Trainmodel.getTrainnnum());
        holder.city.setText(Trainmodel.getCity());
        holder.destination.setText(Trainmodel.getDestination());
        holder.date.setText(Trainmodel.getDate());
        holder.trainclass.setText(Trainmodel.getTrainclass());
        holder.seats.setText(Trainmodel.getTrainseats());


 holder.cancel.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();
         databaseReference= FirebaseDatabase.getInstance().getReference().child("userhistory").child(auth.getUid().toString()).child(Trainmodel.getId().toString());
         databaseReference.removeValue();

         FragmentManager fragmentManager =   ((AppCompatActivity) context).getSupportFragmentManager();
         FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
         fragmentTransaction.replace(R.id.frag,new homeFragment());
         fragmentTransaction.commit();

     }
 });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,num,date,city,destination,trainclass,seats,cancel;
        CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.railnamehis);
            num=itemView.findViewById(R.id.railnohis);
            date=itemView.findViewById(R.id.raildatehis);
            city=itemView.findViewById(R.id.railcityhis);
            trainclass=itemView.findViewById(R.id.railtrainclasshis);
            seats=itemView.findViewById(R.id.railtrainseathis);
            destination=itemView.findViewById(R.id.raildestinationhis);
            cancel=itemView.findViewById(R.id.railcancel);
/*
            card=itemView.findViewById(R.id.cardrail);*/
        }
    }
}

