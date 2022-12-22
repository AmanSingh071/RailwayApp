package com.example.railway.activities.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.databinding.ActivityBookTicketsBinding;
import com.example.railway.databinding.ActivityForgotPasswordBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class bookTickets extends AppCompatActivity {
    ActivityBookTicketsBinding binding;
    String [] items={"AC 2 Tier (2A)","AC 3 Tier (3A)","AC 1 Tier (1A)","Sleeper","First class(FC)","Second Sitting (2S)"};
    String [] seatsnum={"1","2","3","4","5","6","7","8","9"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    private TextView currentTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        autoCompleteTextView =binding.autoCompleteTextView;
        arrayAdapter=new ArrayAdapter<>(this,R.layout.dropdown_item,items);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();

            }
        });
        autoCompleteTextView =binding.autoCompleteTextView2;
        arrayAdapter=new ArrayAdapter<>(this,R.layout.dropdown_item,seatsnum);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();

            }
        });


        // on below line we are initializing our variables.
        currentTV = findViewById(R.id.idTVCurrent);

        // on below line we are creating and initializing
        // variable for simple date format.
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss z");

        // on below line we are creating a variable
        // for current date and time and calling a simple date format in it.
        String currentDateAndTime = sdf.format(new Date());

        // on below line we are setting current
        // date and time to our text view.
        currentTV.setText(currentDateAndTime);


    }
}