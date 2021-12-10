package com.example.ketchappn.models;

public class Aktivitet {
    private String name;

    public Aktivitet(String name) {
        this.name = name;
    }

    public Aktivitet() {
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "aktivitet{" +
                "namewassup='" + name;
    }
}
