package com.example.massenger_application.Chat;

public class UserModel {

    private String phone,userName,createdTimeStamp,image;

    public UserModel() {
    }

    public UserModel(String phone, String userName, String createdTimeStamp, String image) {
        this.phone = phone;
        this.userName = userName;
        this.createdTimeStamp = createdTimeStamp;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
