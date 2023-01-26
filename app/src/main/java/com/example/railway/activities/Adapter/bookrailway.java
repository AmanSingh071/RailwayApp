package com.example.railway.activities.Adapter;

import static com.example.railway.activities.Activities.homepageActivity.listuser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.railway.R;
import com.example.railway.activities.Activities.bookTickets;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.users;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class bookrailway extends RecyclerView.Adapter<bookrailway.MyViewHolder> {


    Context context;
    ArrayList<trainModel>list;

    FirebaseDatabase firebaseDatabase;
    bookrailway Bookrailway;
    DatabaseReference databaseReference;
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

        FirebaseUser auth= FirebaseAuth.getInstance().getCurrentUser();
        if (auth!=null){
            firebaseDatabase= FirebaseDatabase.getInstance();
            databaseReference= firebaseDatabase.getReference("user").child(auth.getUid().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            users user = userSnapshot.getValue(users.class);


                            listuser.add(user);


                            if("admin@gmail.com".equals(listuser.get(0).getEmail().toString()))
                            {

                                holder.dltbtn.setVisibility(View.VISIBLE);

                            }
                            else {
                                holder.dltbtn.setVisibility(View.GONE);
                            }
                        }




                    }
                    else
                    {
                        holder.dltbtn.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            holder.dltbtn.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setintetnonclickitem("uploadAdapter",  pos);
            }
        });
        holder.dltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseReference= firebaseDatabase.getReference("trainModel").child(Trainmodel.getId().toString());
                databaseReference.removeValue();
                FragmentManager fragmentManager =   ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag,new homeFragment());
                fragmentTransaction.commit();
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
        ImageView dltbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.railname);
            num=itemView.findViewById(R.id.railno);
            date=itemView.findViewById(R.id.raildate);
            city=itemView.findViewById(R.id.railcity);
            destination=itemView.findViewById(R.id.raildestination);
            dltbtn=itemView.findViewById(R.id.delete);

            card=itemView.findViewById(R.id.cardrail);
        }
    }
    public void updatesearchlist(ArrayList<trainModel> searchlist){
        list= new ArrayList();
        list.addAll(searchlist);
        notifyDataSetChanged();
    }
}

