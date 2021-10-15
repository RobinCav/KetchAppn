package com.example.ketchappn.models;

import java.util.ArrayList;

public class User {

    private int id;
    private String username, email, password;
    private ArrayList<User> friends;

    //andre variabler skal være her etterhvert
    public User(int id, String username, String email, String password, ArrayList<User> friends) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
