package com.example.ketchappn.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class groupDB {
     int id;
     String name;
     String url;

    public groupDB(int id, String name, String url){
        this.name = name;
        this.id=id;
        this.url = url;
    }
    public groupDB(){}
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
    public void setId(int id) { this.id = id; }
}
