package com.example.railway.activities.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Activities.loginActivity;
import com.example.railway.activities.Adapter.bookrailway;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.users;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class profileFragment extends Fragment {


    public profileFragment() {
        // Required empty public constructor
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public static ArrayList<users> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linearLayout= (LinearLayout) v.findViewById(R.id.returnpagepro);
            linearLayout.setVisibility(View.VISIBLE);
            MaterialCardView materialCardView= v.findViewById(R.id.cardpro);
            materialCardView.setVisibility(View.GONE);

            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button=v.findViewById(R.id.btnproreturn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), loginActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            list =new ArrayList<>();
            firebaseDatabase= FirebaseDatabase.getInstance();
            databaseReference= firebaseDatabase.getReference("user").child(auth.getCurrentUser().getUid().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            users user = userSnapshot.getValue(users.class);


                            list.add(user);
                        }

                        TextView proname=v.findViewById(R.id.proname);
                        proname.setText(list.get(0).getUsername());
                        TextView username=v.findViewById(R.id.username);
                        username.setText(list.get(0).getUsername());
                        TextView proemail=v.findViewById(R.id.proemail);
                        proemail.setText(list.get(0).getEmail());


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button=v.findViewById(R.id.checkhisbtn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.frag,new historyFragment());
                    fragmentTransaction.commit();
                }
            });
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonlogout=v.findViewById(R.id.logoutpro);
            buttonlogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), loginActivity.class);
                    startActivity(intent);
                }
            });





        }


        return v;

    }
}