package com.example.ketchappn.models;

import android.annotation.SuppressLint;

import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class Arrangement {
    private Aktivitet aktivitet;
    private String sted;
    private String tid;
    private User host;
    private ArrayList<User> venner;

    public Arrangement(Aktivitet aktivitet, String sted, String tid, User host, ArrayList<User> venner) {
        this.aktivitet = aktivitet;
        this.sted = sted;
        this.tid = tid;
        this.host = host;
        this.venner = venner;
    }

    public Arrangement(Aktivitet aktivitet, String sted) {
        this.aktivitet = aktivitet;
        this.sted = sted;
    }


    public Aktivitet getAktivitet() {
        return aktivitet;
    }

    public void setAktivitet(Aktivitet aktivitet) {
        this.aktivitet = aktivitet;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public ArrayList<User> getVenner() {
        return venner;
    }

    public void setVenner(ArrayList<User> venner) {
        this.venner = venner;
    }

    public String getCollectionname() {
        return getAktivitet().getName() + "-" + getSted();
    }

    @Override
    public String toString() {
        return "Dagens aktivitet er " + aktivitet.getName() + " -- " + sted + " " +
                "-- Dere skal møtes på denne tida " + tid + ". " + host.getUsername() +
                " har bestemt å inviterere disse " + venner  ;
    }
}

