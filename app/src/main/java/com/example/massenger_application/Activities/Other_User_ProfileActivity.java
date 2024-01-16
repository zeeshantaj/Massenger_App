package com.example.massenger_application.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.massenger_application.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Other_User_ProfileActivity extends AppCompatActivity {


    private ImageView otherProfileBackBtn;
    private CircleImageView profileImg;
    private TextView name,status,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        otherProfileBackBtn = findViewById(R.id.otherProfileBackBtn);
        profileImg = findViewById(R.id.OtherUserProfileImg);
        name = findViewById(R.id.otherProfileName);
        phoneNumber = findViewById(R.id.textView8);
        status = findViewById(R.id.otherUserStatus);


        otherProfileBackBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void setProfileInfo(){

    }

}