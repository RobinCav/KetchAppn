package com.example.ketchappn.models;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private String username, email, password;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Aktivitet> activities = new ArrayList<>();

    private String Status;

    public User(){
        Status = "\uD83E\uDD75";
    }

    public User(String username){
        this.username = username;
        Status = "\uD83E\uDD75";

    }


    public User( String username, String email) {
        this.username = username;
        this.email = email;
        Status = "\uD83E\uDD75";

    }

    public void removeFriend(User friend){
        this.friends.remove(friend);
    }

    public void addFriend(User friend){
        this.friends.add(friend);
    }
    public void assignToActivity(Aktivitet activity){
        this.activities.add(activity);
    }
    public void removeFromActivity(Aktivitet activity){
        this.activities.remove(activity);
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Aktivitet> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Aktivitet> activities) {
        this.activities = activities;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public String toString(){
        return "{" +
                "  "   +
                "   username: " +getUsername() +
                "   email: " + getEmail() +
                "   password: " + getPassword() +
                "   friends: " + getFriends() +
                " " +
                "}";
    }
}
