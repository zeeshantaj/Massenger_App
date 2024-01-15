package com.example.massenger_application.Model;

public class Users {
    private String associatedId,name,image,status,last_seen_status,phoneNumber;

    public Users() {

    }


    public Users(String associatedId, String name, String image, String status, String last_seen_status,String phoneNumber) {
        this.associatedId = associatedId;
        this.name = name;
        this.image = image;
        this.status = status;
        this.last_seen_status = last_seen_status;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_seen_status() {
        return last_seen_status;
    }

    public void setLast_seen_status(String last_seen_status) {
        this.last_seen_status = last_seen_status;
    }
}
