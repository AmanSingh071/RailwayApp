package com.example.railway.activities.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Activities.loginActivity;
import com.example.railway.activities.Activities.otpVerify;
import com.example.railway.activities.Adapter.bookrailway;
import com.example.railway.activities.Adapter.railhistoryAdapter;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.trainbookmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historyFragment extends Fragment {

    public historyFragment() {
        // Required empty public constructor
    }
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public static ArrayList<trainbookmodel> listhis;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_history, container, false);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
           @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linearLayout= (LinearLayout) v.findViewById(R.id.returnpage);
           linearLayout.setVisibility(View.VISIBLE);
            RecyclerView recyview=(RecyclerView) v.findViewById(R.id.recyrailhistory);
            recyview.setVisibility(View.GONE);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button=v.findViewById(R.id.btnhisreturn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), loginActivity.class);
                    startActivity(intent);
                }
            });

        }
        else{
            RecyclerView recyview=(RecyclerView) v.findViewById(R.id.recyrailhistory);
            listhis =new ArrayList<>();
            firebaseDatabase= FirebaseDatabase.getInstance();
            databaseReference= firebaseDatabase.getReference("userhistory").child(auth.getCurrentUser().getUid().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            trainbookmodel user= userSnapshot.getValue(trainbookmodel.class);
                            listhis.add(user);

                        }
                        recyview.setHasFixedSize(true);
                        railhistoryAdapter RailhistoryAdapter=new railhistoryAdapter(getContext(),listhis);
                        recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyview.setAdapter(RailhistoryAdapter);
                        RailhistoryAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return v;

    }
}