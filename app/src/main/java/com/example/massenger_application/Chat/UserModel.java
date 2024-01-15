package com.example.massenger_application.Chat;

public class UserModel {

    private String associatedId,name,message,image;

    public UserModel() {
    }

    public UserModel(String associatedId, String name, String message, String image) {
        this.associatedId = associatedId;
        this.name = name;
        this.message = message;
        this.image = image;
    }

    public String getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(String associatedId) {
        this.associatedId = associatedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
