package com.example.loginedmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText Emai,pass;
    Button btnlogin;
    String Emailpattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    ProgressDialog progressdialoge ;
    FirebaseAuth mAuth;
    FirebaseUser Muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Emai.findViewById(R.id.email);
        pass.findViewById(R.id.password);
        btnlogin.findViewById(R.id.login);
        progressdialoge = new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        Muser=mAuth.getCurrentUser();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String Email = Emai.getText().toString().trim();
        String Password = pass.getText().toString().trim();

        if (!Email.matches(Emailpattern)){
            Emai.setError("Enter Correct Email");
        }
        else if(Password.isEmpty() || Password.length()<6){
            pass.setError("Enter proper password");
        }
        else{
            progressdialoge.setMessage("Wait for Registration..");
            progressdialoge.setTitle("Registration");
            progressdialoge.setCanceledOnTouchOutside(false);
            progressdialoge.show();
            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 if (task.isSuccessful()){
                     progressdialoge.dismiss();
                     Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                     startActivity(intent);
                 }
                 else {
                     progressdialoge.dismiss();
                     Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                 }
                }
            });
        }

    }
}