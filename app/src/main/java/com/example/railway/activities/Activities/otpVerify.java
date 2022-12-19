package com.example.railway.activities.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.railway.databinding.ActivityOtpVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpVerify extends AppCompatActivity {
ActivityOtpVerifyBinding binding;

FirebaseAuth auth;
String storedVerificationId="";
    PhoneAuthProvider.ForceResendingToken resendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        // start verification on click of the button

        binding.getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp =binding.etph.getText().toString();
                String otp2="+91"+otp;
                startphonenumberverfication(otp2);
                Toast.makeText(otpVerify.this, "Code Sent", Toast.LENGTH_SHORT).show();
                Toast.makeText(otpVerify.this, "please wait for 20 sec ", Toast.LENGTH_SHORT).show();


            }
        });

        binding.vfotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.probar.setVisibility(View.VISIBLE);
                verifyphonenumberwithotp(storedVerificationId,binding.etotp.getText().toString());
            }
        });
        auth= FirebaseAuth.getInstance();
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG,"onVerificationCompleted:$credential");
                signInWithCredential1(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("fail" , "onVerificationFailed  $e");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Log.d("sent","onCodeSent: $verificationId");
                storedVerificationId = s;
                resendToken = forceResendingToken;
            }
        };
       
    }

    private void signInWithCredential1(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"signinwithcredentail success");
                    FirebaseUser user=task.getResult().getUser();


                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(otpVerify.this, "Welcome."+user, Toast.LENGTH_SHORT).show();
                    updateUI();
                    binding.probar.setVisibility(View.GONE);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG,"signinwithcredentail failure",task.getException());
                    Toast.makeText(otpVerify.this, "Failed.", Toast.LENGTH_SHORT).show();
                    binding.probar.setVisibility(View.GONE);

                }

            }
        });

    }

    private void updateUI() {
        Intent intent=new Intent(otpVerify.this, homepageActivity.class);
        startActivity(intent);
    }

    private void verifyphonenumberwithotp(String verificationId, String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId, code);
        // after getting credential we are
        // calling sign in method.
        signInWithCredential1(credential);
    }

    private void startphonenumberverfication(String number) {
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d("started" , "Auth started");

    }
}