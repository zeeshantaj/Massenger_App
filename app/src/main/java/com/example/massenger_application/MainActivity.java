package com.example.massenger_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.massenger_application.Activities.Users_Activity;
import com.example.massenger_application.Chat.ChatRoomModel;
import com.example.massenger_application.Home.UserChatsRecyclerAdapter;
import com.example.massenger_application.Settings.Setting_Activity;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private UserChatsRecyclerAdapter adapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);

        FloatingActionButton actionButton = findViewById(R.id.newChatFloatingActionBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Users_Activity.class));
            }
        });

        FirebaseUtils.updateCurrentStatus("online");
        getStatus();
        initRecycler();
        //startUpdateStatusService();
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        String uid = FirebaseUtils.currentUserId();
        Query query = FirebaseUtils.getAllChatRoomCollection()
                .whereArrayContains("senderIds",uid)
                .orderBy("lastMessageTimeStamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatRoomModel> options = new FirestoreRecyclerOptions.Builder<ChatRoomModel>()
                .setQuery(query, ChatRoomModel.class).build();
        adapter = new UserChatsRecyclerAdapter(options);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.setting){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Setting_Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void getStatus(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    String token = task.getResult();
                    FirebaseUtils.currentUserDetails().update("FCMToken",token);


                }
                else {
                    Log.e("MyApp","Notification Error "+ task.getException());
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        initRecycler();
        adapter.startListening();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initRecycler();
        adapter.startListening();
    }

    @Override
    protected void onDestroy() {
//        Date currentTime = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy hh:mm:a", Locale.getDefault());
//        String formattedTime = sdf.format(currentTime);
//
//        FirebaseUtils.updateCurrentStatus(formattedTime);
        super.onDestroy();
        //startUpdateStatusService();


    }

}