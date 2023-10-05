package com.example.ccnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class register extends AppCompatActivity {
    private EditText name, phone, email, password;
    private Button register;
    private FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseDatabase rootNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register = (Button) findViewById(R.id.btn_register);
        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        name = (EditText) findViewById(R.id.name1);
        phone = (EditText) findViewById(R.id.number);
        auth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String getPass = password.getText().toString();
                String getName = name.getText().toString();
                String getNumber = phone.getText().toString();
                auth.createUserWithEmailAndPassword(getEmail, getPass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                rootNode=FirebaseDatabase.getInstance();
                                reference=rootNode.getReference("users");
                                UserHelperClass helperClass = new UserHelperClass(getName,getEmail,getNumber);
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                reference.child(user.getUid()).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(register.this, "Database Updated", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(register.this, login.class));
                                        } else {
                                            Toast.makeText(register.this, "Unsuccessful Try Again ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(register.this, "Error in registration", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}