package com.example.railway.activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Fragments.historyFragment;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Fragments.profileFragment;
import com.example.railway.activities.Fragments.searchFragment;
import com.example.railway.activities.Fragments.settingFragment;
import com.example.railway.databinding.ActivityForgotPasswordBinding;
import com.example.railway.databinding.ActivityHomrpageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class homepageActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
ActivityHomrpageBinding binding;
    ActionBarDrawerToggle toogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomrpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
NavigationBarView navigationBarView;
navigationBarView=binding.navbar;
navigationBarView.setOnItemSelectedListener(this);
navigationBarView.setSelectedItemId(R.id.home);

      FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frag,new homeFragment());
        fragmentTransaction.commit();

        toogle= new ActionBarDrawerToggle(this,binding.getRoot(),R.string.open,R.string.close);
        binding.getRoot().addDrawerListener(toogle);
        toogle.syncState();



      /*  if (savedInstanceState==null){
            binding.navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:
                            replacefrag(new homeFragment());
                            break;

                        case R.id.profile:
                            replacefrag(new profileFragment());
                            break;
                        case R.id.history:
                            replacefrag(new historyFragment());
                            break;
                        case R.id.setting:
                            replacefrag(new settingFragment());
                            break;
                        case R.id.search:
                            replacefrag(new searchFragment());
                            break;


                    }
                    return false;
                }
            }  );

        }*/

        binding .navbar21.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.orderHis:
                    replacefrag(new historyFragment());
                    break;
                case R.id.signout:
                    Toast.makeText(homepageActivity.this,"sign out",Toast.LENGTH_LONG).show();
                    break;






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
}
