package com.example.massenger_application.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.massenger_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatActivity extends AppCompatActivity {
    private EditText messageEd;
    private FloatingActionButton sendBtn;
    private ImageView cameraImg;
    private LinearLayout backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageEd = findViewById(R.id.chatEd);
        sendBtn = findViewById(R.id.sendBtn);
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
}