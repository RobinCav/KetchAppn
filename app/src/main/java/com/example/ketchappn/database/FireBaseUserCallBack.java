package com.example.ketchappn.database;

import com.example.ketchappn.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface FireBaseUserCallBack {


    void onCallBack(ArrayList<HashMap<String, Object>> friends, String status);


}
