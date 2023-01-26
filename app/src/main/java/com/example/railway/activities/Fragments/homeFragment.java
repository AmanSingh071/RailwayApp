package com.example.railway.activities.Fragments;

import static com.example.railway.activities.Activities.homepageActivity.auth;
import static com.example.railway.activities.Activities.homepageActivity.listuser;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Activities.homepageActivity;
import com.example.railway.activities.Adapter.bookrailway;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.users;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class homeFragment extends Fragment {

    public homeFragment() {
        // Required empty public constructor
    }
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;





    public static ArrayList<trainModel> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);


        RecyclerView recyview=(RecyclerView) v.findViewById(R.id.recyrail);
        list =new ArrayList<>();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("trainModel");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        trainModel user= userSnapshot.getValue(trainModel.class);

                        list.add(user);
                    }
                    recyview.setHasFixedSize(true);
                    bookrailway BookRailway=new bookrailway(getContext(),list);
                    recyview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyview.setAdapter(BookRailway);
                    BookRailway.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }


}