package com.example.ccnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class book extends AppCompatActivity {
    Button book,price;
    TextView tv;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    Spinner sp_from,sp_to;
    String to,from;
    // variables for our button and
    // strings and request queue.
    String SITE_KEY = "6LdY85YaAAAAAK2T3aSMcBWBeApRWl2XYvTmDPGb";
    String SECRET_KEY = "6LdY85YaAAAAALquC1TyOLjio8A6Yiht7TJnwCUK";
    RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        book=findViewById(R.id.book);
        price=findViewById(R.id.btn_price);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("bus");
        sp_from=findViewById(R.id.from);
        sp_to=findViewById(R.id.to);
        tv=findViewById(R.id.price);
        int random=new Random().nextInt(100000)+1000000;

        String fare="5000";
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(fare);
            }
        });
        queue = Volley.newRequestQueue(getApplicationContext());


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyGoogleReCAPTCHA();
                to=sp_to.getSelectedItem().toString();
                from=sp_from.getSelectedItem().toString();
                ticketHelperClass helperClass=new ticketHelperClass(to,from,fare,random);
                reference.child(user.getUid()).setValue(helperClass);
                Toast.makeText(book.this,"Ticket Successfully Booked",Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void verifyGoogleReCAPTCHA() {

        SafetyNet.getClient(book.this).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            handleVerification(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Log.d("TAG", "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            Toast.makeText(book.this, "Error found is : " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void handleVerification(final String responseToken) {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(book.this, "User verified with reCAPTCHA", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), String.valueOf(jsonObject.getString("error-codes")), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.d("TAG", "JSON exception: " + ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "Error message: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }
}
