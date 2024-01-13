package com.example.massenger_application.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.massenger_application.Colors_fragment.Color_Selection_Activity;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

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
    private String senderId,chatRoomId;
    private ChatRoomModel chatRoomModel;
    private ImageView menuItemBtn;
    private ConstraintLayout chat_background_container;
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
        chat_background_container = findViewById(R.id.chat_background_container);
        backBtn = findViewById(R.id.backLinearLayout);
        menuItemBtn = findViewById(R.id.chat_menuItem);
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
      setMenuItem();
      setColor();
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

        chatRoomId = FirebaseUtils.getChatRoomId(senderId,receiverId);
        Log.e("MyApp","uiss"+senderId+"   "+receiverId);
        name.setText(nameStr);
        lastSeen.setText(lastSeenStr);
        Glide.with(this)
                .load(img)
                .into(userImg);
    }
    private void sendMessage(String message) {

        chatRoomModel.setLastMessageTimeStamp(Timestamp.now());
        chatRoomModel.setLastMessageSenderId(senderId);
        chatRoomModel.setLastMessage(message);
        FirebaseUtils.getChatRoomReference(chatRoomId).set(chatRoomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message,senderId,Timestamp.now());
        FirebaseUtils.getChatRoomMessageReference(chatRoomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){
                            messageEd.setText("");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void CreateChatRoom(){
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUtils.getChatRoomReference(chatRoomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                chatRoomModel = task.getResult().toObject(ChatRoomModel.class);
                if (chatRoomModel == null){
                    chatRoomModel = new ChatRoomModel(
                            chatRoomId,
                            Arrays.asList(senderId,receiverId),
                            Timestamp.now(),
                            ""
                    );
                    FirebaseUtils.getChatRoomReference(chatRoomId).set(chatRoomModel);
                }
            }
        });
    }
    private void initRecycler(){
        Query query = FirebaseUtils.getChatRoomMessageReference(chatRoomId)
                .orderBy("timestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();
        RecyclerView recyclerView = findViewById(R.id.chatRecycler);
        ChatRecyclerAdapter adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        // todo showing the send message on front not on behind on keyboard
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }
    private void setMenuItem(){
        menuItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.chat_options_menu, popupMenu.getMenu());

                // Set a click listener on the menu items
                popupMenu.setOnMenuItemClickListener(item -> {
                    // Handle menu item clicks here
                    int id = item.getItemId();
                    if (id == R.id.wallpaper) {
                      Intent intent = new Intent(ChatActivity.this, Color_Selection_Activity.class);
                      intent.putExtra("chatId",chatRoomId);
                      startActivity(intent);
                    }
                    if (id == R.id.audioCall){
                        Toast.makeText(ChatActivity.this, "Audio Call Feature Under Process", Toast.LENGTH_SHORT).show();
                    }
                    if (id == R.id.videoCall){
                        Toast.makeText(ChatActivity.this, "Video Call Feature Under Process", Toast.LENGTH_SHORT).show();
                    }

                return true;
                });
                // Show the PopupMenu
                popupMenu.show();
            }
        });
    }
    private void setColor(){
        SharedPreferences sharedPreferences = getSharedPreferences("colorChangerPreference",MODE_PRIVATE);
        int savedColor = sharedPreferences.getInt("color",-1);
        String chatId = sharedPreferences.getString("chatId","");
        if (!chatId.isEmpty() && chatId.equals(chatRoomId)){
            chat_background_container.setBackgroundColor(savedColor);
        }else {
            chat_background_container.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setColor();
    }
}