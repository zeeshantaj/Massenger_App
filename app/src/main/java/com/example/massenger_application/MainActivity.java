package com.example.massenger_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.massenger_application.Activities.ChatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
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
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });
        getStatus();
    }
    private void getStatus(){
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy hh:mm:a", Locale.getDefault());
        String formattedTime = sdf.format(currentTime);
        System.out.println("Current time: " + formattedTime);
    }

}