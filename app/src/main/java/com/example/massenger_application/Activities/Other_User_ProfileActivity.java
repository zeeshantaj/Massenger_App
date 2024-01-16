package com.example.massenger_application.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.massenger_application.R;

public class Other_User_ProfileActivity extends AppCompatActivity {


    private ImageView otherProfileBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        otherProfileBackBtn = findViewById(R.id.otherProfileBackBtn);
        otherProfileBackBtn.setOnClickListener(v -> {
            finish();
        });

    }
}