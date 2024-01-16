package com.example.massenger_application.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class Other_User_ProfileActivity extends AppCompatActivity {


    private ImageView otherProfileBackBtn;
    private CircleImageView profileImg;
    private TextView name,status,phoneNumber,lastSeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        otherProfileBackBtn = findViewById(R.id.otherProfileBackBtn);
        profileImg = findViewById(R.id.OtherUserProfileImg);
        name = findViewById(R.id.otherProfileName);
        phoneNumber = findViewById(R.id.textView8);
        lastSeen = findViewById(R.id.textView9);
        status = findViewById(R.id.otherUserStatus);


        otherProfileBackBtn.setOnClickListener(v -> {
            finish();
        });
        setProfileInfo();
    }

    private void setProfileInfo(){

        Intent intent = getIntent();
        String uid = intent.getStringExtra("otherUserId");
        Log.e("MyApp","uid"+uid);
        DocumentReference reference =FirebaseFirestore.getInstance().collection("users").document(uid);

        reference.get().addOnSuccessListener(documentSnapshot -> {
            Users users = documentSnapshot.toObject(Users.class);
            if (users != null) {
                String imageUrl = users.getImage();
                String nameStr = users.getName();
                String statusStr = users.getStatus();
                String lastSeenStr = users.getLast_seen_status();
                Glide.with(this)
                        .load(imageUrl)
                        .into(profileImg);
                name.setText(nameStr);
                status.setText(statusStr);
                lastSeen.setText(lastSeenStr);

            }
            }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });

    }

}