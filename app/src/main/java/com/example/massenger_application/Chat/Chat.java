package com.example.massenger_application.Chat;

import com.google.firebase.Timestamp;

public class Chat {
    private String name,image,lastMessage;
    private Timestamp timestamp;

    public Chat() {
    }

    public Chat(String name, String image, String lastMessage, Timestamp timestamp) {
        this.name = name;
        this.image = image;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
