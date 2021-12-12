package com.example.ketchappn.database;

import java.util.ArrayList;
import java.util.HashMap;

public interface FireBaseUserCallBack {


    void onCallBackGetFriends(ArrayList<HashMap<String, Object>> friends);
    void onCallBackGetStatus(String status);

}
