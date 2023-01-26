package com.example.railway.activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.activities.Fragments.homeFragment;
import com.example.railway.activities.Models.trainModel;
import com.example.railway.activities.Models.users;
import com.example.railway.databinding.ActivityLoginBinding;
import com.example.railway.databinding.ActivityTrainScheduleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class train_schedule extends AppCompatActivity {
    EditText train_name,train_no,city,destination;
      Button pickDateBtn;
      TextView selectedDateTV;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
 trainModel trainmodel;
    ActivityTrainScheduleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrainScheduleBinding.inflate(getLayoutInflater());
        String date;
        setContentView(binding.getRoot());
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        selectedDateTV = findViewById(R.id.idTVSelectedDate);
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
                        train_schedule.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                               selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

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
        Toast.makeText(train_schedule.this, selectedDateTV.getText().toString(), Toast.LENGTH_SHORT).show();
        binding.submitdatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                senddata();
            }
        });
        binding.returnto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(train_schedule.this,homepageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void senddata() {

        train_name=findViewById(R.id.trainanmetext);
        train_no=findViewById(R.id.trainnumtext);
        city=findViewById(R.id.traincitytext);
        destination=findViewById(R.id.traindestinationtext);



        String train_name1 = train_name.getText().toString();
        String train_no1 = train_no.getText().toString();
        String city1= city.getText().toString();
        String destination1= destination.getText().toString();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference("trainModel").push();
        String id1=databaseReference.getKey().toString();

        trainmodel=new trainModel(train_name1,destination1,city1,selectedDateTV.getText().toString(),train_no1,id1);

        databaseReference.setValue(trainmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(train_schedule.this, " Successfully Added Data", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}