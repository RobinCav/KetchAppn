package com.example.ketchappn.models;

import com.google.type.DateTime;

public class Arrangement {
    private Aktivitet aktivitet;
    private String sted;
    private DateTime dateTime;

    public Arrangement(Aktivitet aktivitet, String sted, DateTime dateTime) {
        this.aktivitet = aktivitet;
        this.sted = sted;
        this.dateTime = dateTime;
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

