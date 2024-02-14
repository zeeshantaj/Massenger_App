package com.example.massenger_application.Chat;

import static com.example.massenger_application.Utils.FirebaseUtils.currentUserId;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
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
import com.example.massenger_application.Activities.Other_User_ProfileActivity;
import com.example.massenger_application.Colors_fragment.Color_Selection_Activity;
import com.example.massenger_application.Interfaces.ImageUrlCallback;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.example.massenger_application.VoiceCall.Voice_Call_Activity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoVoiceCallButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class ChatActivity extends AppCompatActivity {
    private EditText messageEd;
    private FloatingActionButton sendBtn;
    private ImageView cameraImg;
    private LinearLayout backBtn;
    private CircleImageView userImg;
    private TextView name,lastSeen;
    private ListenerRegistration listenerRegistration;

    private String receiverId,receiverFCMToken;
    private ChatRoom chatRoom;
    private String senderId,chatRoomId;
    private ChatRoomModel chatRoomModel;
    private ImageView menuItemBtn;
    private ConstraintLayout chat_background_container;
    private LinearLayout otherPersonProfileLinearLay;
    //private ImageView voiceCallBtn;
    //private ZegoVoiceCallButton voiceCallBtn;
    private ZegoSendCallInvitationButton voiceCallBtn,videoCall;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageEd = findViewById(R.id.chatEd);
        sendBtn = findViewById(R.id.sendBtn);
        lastSeen = findViewById(R.id.chat_lastSeen);

        voiceCallBtn = findViewById(R.id.imageView2);
        name = findViewById(R.id.chat_name);
        userImg = findViewById(R.id.chat_img);
        cameraImg = findViewById(R.id.cameraImg);
        otherPersonProfileLinearLay = findViewById(R.id.otherPersonProfileLinearLay);
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

               if (count > 0){
                   FirebaseUtils.updateCurrentStatus("typing");
               }
               else {
                   FirebaseUtils.updateCurrentStatus("online");
               }

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

      otherPersonProfileLinearLay.setOnClickListener(v -> {
          Intent intent = new Intent(this,Other_User_ProfileActivity.class);
          intent.putExtra("otherUserId",receiverId);
          startActivity(intent);
      });
      voiceCallBtn.setOnClickListener(v -> {
//          Intent intent = new Intent(this, Voice_Call_Activity.class);
//          intent.putExtra("otherUserId",receiverId);
//          startActivity(intent);
          setVoiceCall(receiverId);
      });

      userInfo();
      initRecycler();
      CreateChatRoom();
      setMenuItem();
      setColor();
      setLastSeen();



      startService(senderId,userName);
      Log.e("MyApp","name"+userName);
    }

    private void setVoiceCall(String targetedId){
        voiceCallBtn.setIsVideoCall(false);
        voiceCallBtn.setResourceID("zego_uikit_call");
        voiceCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetedId)));
    }

    private void startService(String userID,String userName){
        Application application = getApplication();
        long appId =1453071286;
        String appSign ="3d5332b2094993b3adf3a8899e06ecff8f6d7242d773ff9581446c715e4163fb";

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(),appId,appSign,userID,userID,callInvitationConfig);


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
        receiverFCMToken = intent.getStringExtra("FCMToken");
        Log.e("MyApp","RID intent"+receiverId);
        chatRoomId = FirebaseUtils.getChatRoomId(senderId,receiverId);

        name.setText(nameStr);
        //lastSeen.setText(lastSeenStr);
        Glide.with(this)
                .load(img)
                .into(userImg);

    }
    private void setLastSeen(){
//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = firestore.collection("users").document(receiverId);
//
//        documentReference.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            // Document exists, you can retrieve the data
//                            String lastSeenStatus = documentSnapshot.getString("last_seen_status");
//                            lastSeen.setText(lastSeenStatus);
//                            Log.e("MyApp","status retrieved");
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle errors
//                        Log.e("MyApp","status retrieve  "+e.getLocalizedMessage());
//                    }
//                });

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("users").document(receiverId);

        // Use addSnapshotListener to listen for real-time updates
        listenerRegistration = documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    // Handle errors
                    Log.e("MyApp", "Error listening for real-time updates: " + e.getLocalizedMessage());
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Document exists, you can retrieve the data
                    String lastSeenStatus = documentSnapshot.getString("last_seen_status");
                    lastSeen.setText(lastSeenStatus);
                    Log.e("MyApp", "Status retrieved");
                }
            }
        });

    }
    private void stopListening() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopListening();
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
                            sendNotification(message);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Error "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void sendNotification(String message) {
        FirebaseUtils.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    UserModel currentUser = task.getResult().toObject(UserModel.class);
                    try {
                        JSONObject jsonObject = new JSONObject();
                        JSONObject notificationObject = new JSONObject();
                        notificationObject.put("title",currentUser.getName());
                        notificationObject.put("body",message);

                        JSONObject dataObj = new JSONObject();
                        dataObj.put("userId",currentUser.getAssociatedId());

                        jsonObject.put("notification",notificationObject);
                        jsonObject.put("data",dataObj);
                        jsonObject.put("to",receiverFCMToken);
                        callApi(jsonObject);

                    }catch (Exception e){
                        e.getLocalizedMessage();
                    }
                }
                else {
                    Log.e("MyApp","FCM ERROR "+task.getException());
                }
            }
        });

    }
    void callApi(JSONObject jsonObject){
        MediaType json = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String url = "https://fcm.googleapis.com/fcm/send";
        RequestBody body = RequestBody.create(jsonObject.toString(),json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization","Bearer AAAA7reCDoQ:APA91bHKOMdHeoUOtVUznGapVkZe27TYREQXUNAcON5FUepw3w_GOAer_ODZhNCmYC-ihXlagEojfs8dvj3DcWgeJqQpvYbxmRz8FMXWrb_b1keKYtRsv1agAC1fKnyiyJ1WWWKCO65F")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

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