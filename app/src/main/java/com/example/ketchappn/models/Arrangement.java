package com.example.ketchappn.models;

import com.google.type.DateTime;

import java.util.ArrayList;

public class Arrangement {
    private Aktivitet aktivitet;
    private String sted;
    private DateTime dateTime;
    private User host;
    private ArrayList<User> venner;

    public Arrangement(Aktivitet aktivitet, String sted, DateTime dateTime, User host, ArrayList<User> venner) {
        this.aktivitet = aktivitet;
        this.sted = sted;
        this.dateTime = dateTime;
        this.host = host;
        this.venner = venner;
    }

    public Arrangement(Aktivitet aktivitet, String sted) {
        this.aktivitet = aktivitet;
        this.sted = sted;
    }

    @Override
    public String toString() {
        return "Dagens aktivitet er " + aktivitet.getName() + "// " + sted;
    }
}

