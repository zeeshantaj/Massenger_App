package com.example.massenger_application.Chat;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatRoom {
    private String chatroomId;
    private String userId;
    private Timestamp timestamp;
    private String lastMessageSenderId;

    public ChatRoom() {
    }

    public ChatRoom(String chatroomId, String userId, Timestamp timestamp, String lastMessageSenderId) {
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.lastMessageSenderId = lastMessageSenderId;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
