package com.example.massenger_application.Utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtils {
    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
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
}
