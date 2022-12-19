package com.example.railway.activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.railway.R;
import com.example.railway.databinding.ActivityForgotPasswordBinding;
import com.example.railway.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    String email;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordfun();
            }
        });
        binding.loginforgort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(forgotPassword.this,loginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void forgotPasswordfun() {
        email= binding.emailsignin.getText().toString();
        if(email.isEmpty())
        {
            Toast.makeText(forgotPassword.this, "Email cannot be blank", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassword.this,"please check your spam Email to reset Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(forgotPassword.this,"Try again!!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}