package com.example.ccnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class view extends AppCompatActivity {
    TextView name,email,to,from,ticketid;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference,reference1;
    FirebaseDatabase rootNode;
    ImageView qrc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        qrc=findViewById(R.id.qrcode);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        ticketid=findViewById(R.id.ticket);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass helperClass=snapshot.getValue(UserHelperClass.class);
                name.setText(helperClass.getName());
                email.setText(helperClass.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference1=rootNode.getReference("bus").child(user.getUid());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ticketHelperClass tick=snapshot.getValue(ticketHelperClass.class);
                to.setText(tick.getTo());
                from.setText(tick.getFrom());
                String ti=String.valueOf(tick.getId());
                ticketid.setText(ti);

                MultiFormatWriter writer=new MultiFormatWriter();
                BitMatrix matrix= null;
                try {
                    matrix = writer.encode(ti, BarcodeFormat.QR_CODE,650,650);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                BarcodeEncoder encoder=new BarcodeEncoder();
                Bitmap bitmap=encoder.createBitmap(matrix);
                qrc.setImageBitmap(bitmap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}