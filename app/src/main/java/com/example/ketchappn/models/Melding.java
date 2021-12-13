package com.example.ketchappn.models;

public class Melding {

    private String username, message, epost;

    public Melding(String username, String message, String epost) {
        this.username = username;
        this.message = message;
        this.epost = epost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEpost() {
        return epost;
    }

    public void setEpost(String epost) {
        this.epost = epost;
    }
}
