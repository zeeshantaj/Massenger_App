package com.example.massenger_application.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.massenger_application.Utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private EditText messageEd;
    private FloatingActionButton sendBtn;
    private ImageView cameraImg;
    private LinearLayout backBtn;
    private CircleImageView userImg;
    private TextView name,lastSeen;
    private String receiverId;
    private ChatRoom chatRoom;
    private String senderId;
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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        senderId = auth.getUid();
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
      sendBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String message = messageEd.getText().toString();
              if (message.isEmpty()){
                  return;
              }
              sendMessage(message);
          }

      });
      userInfo();
      initRecycler();
      CreateChatRoom();
    }
    private void sendMessage(String message) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String chatRoomId = auth.getUid();
        chatRoom.setTimestamp(Timestamp.now());
        chatRoom.setLastMessageSenderId(senderId);
        ChatMessageModel chatMessageModel = new ChatMessageModel(message,chatRoomId,Timestamp.now());

        FirebaseUtils.getChatRoomMessageReference(chatRoomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            messageEd.setText("");
                        }
                    }
                });

    }

    private void CreateChatRoom(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String chatRoomId = auth.getUid();
        FirebaseUtils.getChatRoomReference(chatRoomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                chatRoom = task.getResult().toObject(ChatRoom.class);
                if (chatRoom == null){
                    chatRoom = new ChatRoom(
                            chatRoomId,
                            receiverId,
                            Timestamp.now(),
                            ""
                    );
                    FirebaseUtils.getChatRoomReference(chatRoomId).set(chatRoom);
                }
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

    private void userInfo(){
        Intent intent = getIntent();
        String nameStr = intent.getStringExtra("name");
        String img = intent.getStringExtra("image");
        String lastSeenStr = intent.getStringExtra("lastSeen");
        receiverId = intent.getStringExtra("associatedId");


        name.setText(nameStr);
        lastSeen.setText(lastSeenStr);
        Glide.with(this)
                .load(img)
                .into(userImg);
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.chatRecycler);


        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance().getReference("Messages").child(senderId).child(receiverId).child("chat");
    }
}