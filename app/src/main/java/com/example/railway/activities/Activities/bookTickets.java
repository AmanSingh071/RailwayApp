package com.example.railway.activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.trainbookmodel;
import com.example.railway.databinding.ActivityBookTicketsBinding;
import com.example.railway.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class bookTickets extends AppCompatActivity {
    ActivityBookTicketsBinding binding;
    String [] items={"AC 2 Tier (2A)","AC 3 Tier (3A)","AC 1 Tier (1A)","Sleeper","First class(FC)","Second Sitting (2S)"};
    String [] seatsnum={"1","2","3","4","5","6","7","8","9"};
    FirebaseDatabase firebaseDatabase;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;
    int position=0;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    ArrayList<trainModel> list;
    private TextView currentTV;
    Button pickDateBtn;
    TextView selectedDateTV;
   public String trainclass="";
  public   String trainseats="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pickDateBtn = findViewById(R.id.idBtnPickDatebook);
        selectedDateTV = findViewById(R.id.traindate);
        firebaseDatabase=FirebaseDatabase.getInstance();
        initializelayout(trainseats,trainclass, "");

        binding.onbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(bookTickets.this,homepageActivity.class);
                startActivity(intent);
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
        autoCompleteTextView =binding.autoCompleteTextView;
        arrayAdapter=new ArrayAdapter<>(this,R.layout.dropdown_item,items);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trainclass= adapterView.getItemAtPosition(i).toString();


                autoCompleteTextView =binding.autoCompleteTextView2;
                arrayAdapter=new ArrayAdapter<>(bookTickets.this,R.layout.dropdown_item,seatsnum);
                autoCompleteTextView.setAdapter(arrayAdapter);
                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        trainseats= adapterView.getItemAtPosition(i).toString();





                        pickDateBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // on below line we are getting
                                // the instance of our calendar.
                                final Calendar c = Calendar.getInstance();

                                // on below line we are getting
                                // our day, month and year.
                                int year = c.get(Calendar.YEAR);
                                int month = c.get(Calendar.MONTH);
                                int day = c.get(Calendar.DAY_OF_MONTH);

                                // on below line we are creating a variable for date picker dialog.
                                DatePickerDialog datePickerDialog = new DatePickerDialog(
                                        // on below line we are passing context.
                                        bookTickets.this,
                                        new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {
                                                // on below line we are setting date to our text view.
                                                selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                initializelayout(trainseats,trainclass,selectedDateTV.getText().toString());
                                            }
                                        },
                                        // on below line we are passing year,
                                        // month and day for selected date in our date picker.
                                        year, month, day);
                                // at last we are calling show to
                                // display our date picker dialog.
                                datePickerDialog.show();
                            }
                        });


                    }
                });
            }
        });


    }

    private void initializelayout(String trainseats,String trainclass,String selectedDateTVs) {
        Bundle extras=getIntent().getExtras();

        position = extras.getInt("index",0);
        switch (extras.getString("class"))
        {
            case  "uploadAdapter":
                list=new ArrayList<trainModel>();
                list.addAll(homeFragment.list);
                binding.trainanme.setText(list.get(position).getName());
                binding.trainnum.setText(list.get(position).getTrainnnum());
                binding.traincity.setText(list.get(position).getCity());


                binding.traindestination.setText(list.get(position).getDestination());




auth=FirebaseAuth.getInstance();
if(auth.getCurrentUser()!=null){
    binding.bookticketbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(trainseats.isEmpty() || trainclass.isEmpty())
            {
                Toast.makeText(bookTickets.this,"Train seats or Train class cannot be Blank",Toast.LENGTH_LONG).show();

            }
            else {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.progressBar.setProgress(1500);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        binding.progressBar.setVisibility(View.GONE);
                        binding.cardconfirm.setVisibility(View.VISIBLE);
                    }}, 3000);
                DatabaseReference databaseReference;
                databaseReference=  FirebaseDatabase.getInstance().getReference().child("userhistory").child(auth.getCurrentUser().getUid().toString()).push();
                 String id= databaseReference.getKey();
                trainbookmodel trainbookModel=new trainbookmodel(list.get(position).getName(),list.get(position).getDestination(),list.get(position).getCity(),selectedDateTVs,list.get(position).getTrainnnum(),trainclass,trainseats,id);
                databaseReference.setValue(trainbookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }





        }
    });

}
else
{
    Toast.makeText(bookTickets.this,"Please log in first",Toast.LENGTH_LONG).show();
    Intent intent=new Intent(bookTickets.this, loginActivity.class);
    startActivity(intent);
    finish();
}


                break;

        }
    }
}