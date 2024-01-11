package com.example.massenger_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.massenger_application.Activities.ChatActivity;
import com.example.massenger_application.Activities.Users_Activity;
import com.example.massenger_application.Adapter.UserAdapter;
import com.example.massenger_application.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton actionButton = findViewById(R.id.newChatFloatingActionBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Users_Activity.class));
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        HashMap<String, Object> updateData = new HashMap<>();
        updateData.put("last_seen_status", "Online");
        databaseReference.updateChildren(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        getStatus();
    }


    private void getStatus(){

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy hh:mm:a", Locale.getDefault());
        String formattedTime = sdf.format(currentTime);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        HashMap<String, Object> updateData = new HashMap<>();
        updateData.put("last_seen_status", formattedTime);
        databaseReference.updateChildren(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}