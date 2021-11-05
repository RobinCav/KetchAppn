package com.example.ketchappn.models;

public class Aktivitet {
    private String name, url;
    private int id;


    public Aktivitet(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public Aktivitet() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "aktivitet{" +
                "namewassup='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id=" + id +
                '}';
    }
}
