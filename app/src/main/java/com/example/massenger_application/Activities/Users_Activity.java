package com.example.massenger_application.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.massenger_application.Adapter.UserAdapter;
import com.example.massenger_application.Chat.ChatMessageModel;
import com.example.massenger_application.Chat.ChatRecyclerAdapter;
import com.example.massenger_application.MainActivity;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class Users_Activity extends AppCompatActivity {

    private Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        users = new Users();

        initRecycler();
        setToolbar();
    }
    private void initRecycler(){

        RecyclerView recyclerView = findViewById(R.id.userRecycler);

        Query query = FirebaseUtils.allUserCollectionReference();

        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(query,Users.class).build();
        UserAdapter adapter = new UserAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();

//        DatabaseReference databaseReference = FirebaseDatabase
//                .getInstance()
//                .getReference("Users");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                usersList.clear();
//                if (snapshot.exists()){
//                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                        String name = dataSnapshot.child("name").getValue(String.class);
//                        String image = dataSnapshot.child("image").getValue(String.class);
//                        String status = dataSnapshot.child("status").getValue(String.class);
//                        String associatedID = dataSnapshot.child("associatedId").getValue(String.class);
//                        String lastSeen = dataSnapshot.child("last_seen_status").getValue(String.class);
//                        users = new Users(associatedID,name,image,status,lastSeen);
//
//                        Log.e("MyApp","name"+associatedID);
//                    }
//                    usersList.add(users);
//                    UserAdapter adapter = new UserAdapter(usersList);
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Users_Activity.this, "Error "+ error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.userToolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}