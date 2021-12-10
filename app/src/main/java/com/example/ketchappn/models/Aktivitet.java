package com.example.ketchappn.models;

public class Aktivitet {
    private String name;
    private int id;
    private String symbol;

    public Aktivitet(String name, int id, String symbol) {
        this.name = name;
        this.id = id;
        this.symbol = symbol;
    }

    public Aktivitet(String name) {
        this.name = name;
    }

    public Aktivitet() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
