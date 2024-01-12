package com.example.massenger_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.massenger_application.Activities.Users_Activity;
import com.example.massenger_application.Adapter.UserAdapter;
import com.example.massenger_application.Home.UserChatsRecyclerAdapter;
import com.example.massenger_application.Home.Users_Chat_Model;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

        getStatus();
        initRecycler();
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        String uid = FirebaseUtils.currentUserId();
        Query query = FirebaseUtils.getChatRoomMessageReference(uid);

        FirestoreRecyclerOptions<Users_Chat_Model> options = new FirestoreRecyclerOptions.Builder<Users_Chat_Model>()
                .setQuery(query,Users_Chat_Model.class).build();
        UserChatsRecyclerAdapter adapter = new UserChatsRecyclerAdapter(options);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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