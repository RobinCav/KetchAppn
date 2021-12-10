package com.example.ketchappn.database;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.ketchappn.Fragments.LoginAct;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccesUser  {



    private FirebaseFirestore firestore;
    public ArrayList<String> friends = new ArrayList<>();



    public AccesUser(){
        firestore = FirebaseFirestore.getInstance();

         /*
        friends = new ArrayList<>();
        friends.add(new User(1,"yaqub","yaqubsaid@gmail.com","rrr",null));
        friends.add(new User(2,"robin","robincalv@gmail.com","rrr",null));
        friends.add(new User(3,"aleks","aleks@gmail.com","rrr",null));

        user = new User(0, "karrar", "karrara@gmail.com", "okthendude",friends);

        auth = FirebaseAuth.getInstance();

        setDocument(user);

     */
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }



    public ArrayList<String> getFriendsTask (FireBaseCallBack callback){


         firestore.collection("FriendList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("Username").equals(LoginAct.CurUser.getUsername())){
                                    //We have our list view
                                    friends = (ArrayList<String>) document.get("UserFriendList");
                                    callback.onCallback(friends);

                                }


                            }
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

         return  null;

    }
    public void processData(ArrayList<String> f){
        friends = f;

    }

    public ArrayList<String> getFriends(){

        return  friends;

    }



    public void setDocument(com.example.ketchappn.models.User user) {

        Map<String, Object> userHashMap = new HashMap<>();
        userHashMap.put("User", user.getUsername());
        userHashMap.put("UserFriendList", user.getFriends());


        firestore.collection("FriendList").document(user.getUsername())
                .set(userHashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }



}
