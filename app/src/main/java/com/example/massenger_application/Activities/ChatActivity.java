package com.example.massenger_application.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.massenger_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private EditText messageEd;
    private FloatingActionButton sendBtn;
    private ImageView cameraImg;
    private LinearLayout backBtn;
    private CircleImageView userImg;
    private TextView name,lastSeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageEd = findViewById(R.id.chatEd);
        sendBtn = findViewById(R.id.sendBtn);
        lastSeen = findViewById(R.id.chat_lastSeen);
        name = findViewById(R.id.chat_name);
        userImg = findViewById(R.id.chat_img);
        cameraImg = findViewById(R.id.cameraImg);
        backBtn = findViewById(R.id.backLinearLayout);
        setIconVisibility();

        messageEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setIconVisibility();
            }
        });

      backBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });
      userInfo();
    }
    private void setIconVisibility(){
        if (!messageEd.getText().toString().isEmpty()){
            sendBtn.setImageResource(R.drawable.send_icon);
            cameraImg.setVisibility(View.GONE);
        }
        else {
            sendBtn.setImageResource(R.drawable.mic_icon);
            cameraImg.setVisibility(View.VISIBLE);
        }
    }
    private void userInfo(){
        Intent intent = getIntent();
        String nameStr = intent.getStringExtra("name");
        String img = intent.getStringExtra("image");
        String lastSeenStr = intent.getStringExtra("lastSeen");


        name.setText(nameStr);
        lastSeen.setText(lastSeenStr);
        Glide.with(this)
                .load(img)
                .into(userImg);
    }
}