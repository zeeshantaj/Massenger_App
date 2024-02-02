package com.example.massenger_application.Utils;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.massenger_application.Interfaces.ImageUrlCallback;
import com.example.massenger_application.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseUtils {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static void getFirebaseName(final ImageUrlCallback callback) {
        DocumentReference userRef = FirebaseUtils.currentUserDetails();

        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);
                if (users != null) {
                    String imageUrl = users.getImage();
                    String name = users.getName();
                    String status = users.getStatus();

                    callback.onImageUrlReady(imageUrl);
                    callback.onNameReady(name);
                    callback.onStatusReady(status);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure
                callback.onImageUrlReady(null); // You might want to handle this case as well
            }
        });
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");

    }



    public static DocumentReference getChatRoomReference(String chatRoomId) {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatRoomId);
    }
    public static CollectionReference getChatRoomMessageReference(String chatRoomId) {
        return getChatRoomReference(chatRoomId).collection("chats");
    }

    public static CollectionReference getAllChatRoomCollection(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public static DocumentReference getOtherUserFromChatRoom(List<String> userIds){
        if (userIds.get(0).equals(FirebaseUtils.currentUserId())){
            return allUserCollectionReference().document(userIds.get(1));
        }
        else {
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    public static String getTimestampToString(Timestamp timestamp){
        return new SimpleDateFormat("hh:mm").format(timestamp.toDate());
    }

    public static String getChatRoomId(String userId1,String userId2){
        if (userId1.hashCode() < userId2.hashCode()){
            return userId1 +"_"+userId2;
        }else {
            return userId2 +"_"+userId1;
        }
    }
    public static void updateCurrentStatus(String status){
        Map<String, Object> updates = new HashMap<>();
        updates.put("last_seen_status",status);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firestore.collection("users").document(currentUserId());
        documentReference.update(updates).addOnSuccessListener(unused -> {
            Log.e("MyApp","update current status updated");
        }).addOnFailureListener(e -> {
            Log.e("MyApp","update current status "+e.getLocalizedMessage());
        });
    }

}
