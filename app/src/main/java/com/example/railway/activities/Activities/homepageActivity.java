package com.example.railway.activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Adapter.bookrailway;
import com.example.railway.activities.Fragments.historyFragment;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Fragments.profileFragment;
import com.example.railway.activities.Fragments.searchFragment;
import com.example.railway.activities.Fragments.settingFragment;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.users;
import com.example.railway.databinding.ActivityHomrpageBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homepageActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
ActivityHomrpageBinding binding;
    ActionBarDrawerToggle toogle;
    FirebaseDatabase firebaseDatabase;
    bookrailway Bookrailway;
    DatabaseReference databaseReference;
    public  static ArrayList<users> listuser=new ArrayList<>();
    public static ArrayList<trainModel> lis=new ArrayList<>();
     public   static FirebaseAuth auth=FirebaseAuth.getInstance();
    Boolean search=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomrpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toogle= new ActionBarDrawerToggle(this,binding.getRoot(),R.string.open,R.string.close);
        binding.getRoot().addDrawerListener(toogle);
        toogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


NavigationBarView navigationBarView;
navigationBarView=binding.navbar;
navigationBarView.setOnItemSelectedListener(this);
navigationBarView.setSelectedItemId(R.id.home);
NavigationView navigationBarView2;
navigationBarView2=binding.navbar21;

        if(auth.getCurrentUser()!=null){
            firebaseDatabase= FirebaseDatabase.getInstance();
            databaseReference= firebaseDatabase.getReference("user").child(auth.getCurrentUser().getUid().toString());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            users user = userSnapshot.getValue(users.class);


                            listuser.add(user);


                            if("admin@gmail.com".equals(listuser.get(0).getEmail().toString()))
                            {
                                Menu nav_Menu = navigationBarView2.getMenu();
                                nav_Menu.findItem(R.id.addtrain).setVisible(true);

                            }
                            else {
                                Menu nav_Menu = navigationBarView2.getMenu();
                                nav_Menu.findItem(R.id.addtrain).setVisible(false);
                            }
                        }




                    }
                    else
                    {
                        Menu nav_Menu = navigationBarView2.getMenu();
                        nav_Menu.findItem(R.id.addtrain).setVisible(false);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            Menu nav_Menu = navigationBarView2.getMenu();
            nav_Menu.findItem(R.id.addtrain).setVisible(false);
        }





        toogle= new ActionBarDrawerToggle(this,binding.getRoot(),R.string.open,R.string.close);
        binding.getRoot().addDrawerListener(toogle);
        toogle.syncState();


        binding .navbar21.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.orderHis:
                    replacefrag(new historyFragment());
                    return true;
                case R.id.signout:
                    Toast.makeText(homepageActivity.this,"sign out",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.addtrain:
                    Intent intent=new Intent(homepageActivity.this,train_schedule.class);
                    startActivity(intent);
                    finish();
                    return true;

            }
            return false;
        });



    }



    public void replacefrag(Fragment fragment) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag,fragment);
        fragmentTransaction.commit();

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toogle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                replacefrag(new homeFragment());
                return true;

            case R.id.profile:
                replacefrag(new profileFragment());
                return true;
            case R.id.history:
                replacefrag(new historyFragment());
                return true;
            case R.id.setting:
                replacefrag(new settingFragment());
                return true;
            case R.id.search:
                replacefrag(new searchFragment());
                return true;



        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbar,menu);

        SearchView searchView= (SearchView) menu.findItem(R.id.searchview).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                 return  true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.isEmpty()){
                    String userinput =s.toLowerCase();
                    for(trainModel trainmodel:lis){
                        if(trainmodel.getDestination().toString().toLowerCase().contains(userinput)){
                            lis.add(trainmodel);
                            search=true;
                            Bookrailway.updatesearchlist(lis);
                        }
                    }
                }
                 return true;

            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
