package com.example.ketchappn.database;

public class groupDB {
     int tid;
     String sted;

    public groupDB(int tid, String sted){
        this.tid=tid;
        this.sted=sted;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
