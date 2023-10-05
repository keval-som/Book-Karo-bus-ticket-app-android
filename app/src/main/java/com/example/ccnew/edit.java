package com.example.ccnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class edit extends AppCompatActivity {
    private EditText name, phone, email;
    private Button edit;
    private FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edit = (Button) findViewById(R.id.btn_edit);
        email = (EditText) findViewById(R.id.email2);
        name = (EditText) findViewById(R.id.name2);
        phone = (EditText) findViewById(R.id.number2);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass helperClass= snapshot.getValue(UserHelperClass.class);
                Toast.makeText(edit.this,"Read Successful",Toast.LENGTH_LONG);
                email.setText(helperClass.getEmail());
                name.setText(helperClass.getName());
                phone.setText(helperClass.getNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(edit.this,"Fail",Toast.LENGTH_LONG);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String getName = name.getText().toString();
                String getNumber = phone.getText().toString();
                UserHelperClass helperClass = new UserHelperClass(getName,getEmail,getNumber);
                reference.setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(edit.this, "Database Updated", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(edit.this, "Unsuccessful Try Again ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }
}
