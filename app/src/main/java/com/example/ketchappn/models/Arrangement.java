package com.example.ketchappn.models;

import android.annotation.SuppressLint;

import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Date;

public class Arrangement {
    private Aktivitet aktivitet;
    private String sted;
    private String tid;
    private String host;
    private ArrayList<User> venner;
    private ArrayList<Melding> meldinger;

    public Arrangement(Aktivitet aktivitet, String sted, String tid, String host, ArrayList<User> venner, ArrayList<Melding> meldinger) {
        this.aktivitet = aktivitet;
        this.sted = sted;
        this.tid = tid;
        this.host = host;
        this.venner = venner;
        this.meldinger = meldinger;
    }

    public Arrangement(Aktivitet aktivitet, String sted) {
        this.aktivitet = aktivitet;
        this.sted = sted;
    }

    public ArrayList<Melding> getMeldinger() {
        return meldinger;
    }

    public void setMeldinger(ArrayList<Melding> meldinger) {
        this.meldinger = meldinger;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
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
                "-- Dere skal møtes på denne tida " + tid + ". " + host +
                " har bestemt å inviterere disse " + venner  ;
    }
}

