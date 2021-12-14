package com.example.ketchappn.database;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ketchappn.Activities.LoginAct;
import com.example.ketchappn.Activities.Start_Page;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccessUser {


    private FirebaseFirestore firestore;

    public AccessUser() {
        firestore = FirebaseFirestore.getInstance();

    }

    public void createUser(User user) {

        Map<String, Object> userHashMap = new HashMap<>();
        userHashMap.put("Email", user.getEmail());
        userHashMap.put("Username", user.getUsername());
        userHashMap.put("UserFriendList", user.getFriends());
        userHashMap.put("Status", user.getStatus());
        userHashMap.put("JoinedActivity", user.getActivities());

        firestore.collection("User").document(user.getEmail())
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

    public void getStatusTask(User user, GetStatusCallback callback) {


        firestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("Username").equals(user.getUsername())) {
                                    String status = (String) document.get("Status");

                                    callback.getStatus(status);
                                }


                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void removeFriendTask(Fragment fragment, String friendName) {


            firestore.collection("User")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DocumentReference ref = firestore.collection("User").document(LoginAct.CurUser.getEmail());
                            User friend = new User(document.get("Username").toString(), document.get("Email").toString());

                            HashMap<String, Object> data = new HashMap<>();
                            ArrayList<HashMap<String, Object>> friendsList = new ArrayList<>();
                            ArrayList<HashMap<String, Object>> friendsList2 = new ArrayList<>();
                            if(document.get("Username").equals(LoginAct.CurUser.getUsername())){
                                friendsList = (ArrayList<HashMap<String, Object>>) document.get("UserFriendList");

                            }else if(document.get("Username").equals(friendName)){
                                friendsList2 = (ArrayList<HashMap<String, Object>>) document.get("UserFriendList");

                            }
                            for (HashMap<String, Object> obj : friendsList) {
                                if (obj.get("username").equals(friendName)) {
                                    data = obj;
                                }
                            }
                            ref
                                    .update("UserFriendList", FieldValue.arrayRemove(data)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("friendremoved", "DocumentSnapshot successfully updated!");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("friendwasntremoved", "Error updating document", e);
                                }
                            });




                            HashMap<String, Object> data2 = new HashMap<>();
                            for (HashMap<String, Object> obj : friendsList2) {
                                if (obj.get("username").equals(LoginAct.CurUser.getUsername())) {
                                    data2 = obj;
                                }
                            }
                            DocumentReference friendRef = firestore.collection("User").document(friend.getEmail());

                            friendRef
                                    .update("UserFriendList",  FieldValue.arrayRemove(data2))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("friendloaded", "DocumentSnapshot successfully updated!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("frienddidntload", "Error updating document", e);
                                        }
                                    });

                        }

                    }
                    fragment.startActivity(new Intent(fragment.getContext(), Start_Page.class));

                }


            });

        }





        public void getFriendsTask (FireBaseUserCallBack callback){
            firestore.collection("User")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<String> friendsStatus = new ArrayList<>();
                                ArrayList<HashMap<String, Object>> friendsList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User curuser = new User(document.get("Username").toString());
                                    if (Objects.equals(document.get("Username"), LoginAct.CurUser.getUsername())) {
                                        //We have our list view
                                        friendsList = (ArrayList<HashMap<String, Object>>) document.get("UserFriendList");
                                    }

                                }
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User curuser = new User(document.get("Username").toString());
                                    for(int i=0; i < friendsList.size();i++){
                                        if(curuser.getUsername().equals(friendsList.get(i).get("username"))){
                                            friendsList.get(i).put("status", document.get("Status"));
                                        }
                                    }

                                }
                                System.out.println(friendsList);
                                callback.onCallBack(friendsList, friendsStatus);
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }


        public void changeStatusTask (String status, Activity activity){

            DocumentReference curUserRef = firestore.collection("User").document(LoginAct.CurUser.getEmail());

            curUserRef.update("Status", status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    activity.startActivity(new Intent(activity.getApplicationContext(), Start_Page.class));

                }
            });


        }

        public void addFriendsTask (String username, Fragment fragment){
            if (!username.isEmpty()) {
                firestore.collection("User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                        User friend = new User(document.get("Username").toString(), document.get("Email").toString());
                                        System.out.println("user from colleciton : " + friend.getUsername());
                                        System.out.println("ENTERTED TEXT: " + username);
                                        if (username.equals(friend.getUsername())) {
                                            System.out.println("THIS SHIT EXIST......");
                                            friend.addFriend(LoginAct.CurUser);
                                            LoginAct.CurUser.addFriend(friend);
                                            DocumentReference curUserRef = firestore.collection("User").document(LoginAct.CurUser.getEmail());
                                            Map<String, Object> data = new HashMap<>();
                                            data.put("username", friend.getUsername());
                                            data.put("email", friend.getEmail());
                                            curUserRef
                                                    .update("UserFriendList", FieldValue.arrayUnion(data))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("friendloaded", "DocumentSnapshot successfully updated!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("frienddidntload", "Error updating document", e);
                                                        }
                                                    });


                                            DocumentReference friendRef = firestore.collection("User").document(friend.getEmail());
                                            Map<String, Object> data2 = new HashMap<>();
                                            data2.put("username", LoginAct.CurUser.getUsername());
                                            data2.put("email", LoginAct.CurUser.getEmail());
                                            friendRef
                                                    .update("UserFriendList", FieldValue.arrayUnion(data2))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d("friendloaded", "DocumentSnapshot successfully updated!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("frienddidntload", "Error updating document", e);
                                                        }
                                                    });
                                            FragmentTransaction ft = fragment.getFragmentManager().beginTransaction();
                                            ft.detach(fragment).attach(fragment).commit();
                                            Toast.makeText(fragment.getContext(),
                                                    "User added!",
                                                    Toast.LENGTH_SHORT).show();

                                            fragment.startActivity(new Intent(fragment.getContext(), Start_Page.class));


                                        }
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            } else {
                Toast.makeText(fragment.getContext(),
                        "error user not found",
                        Toast.LENGTH_SHORT).show();
            }
        }

        public void setDocument (com.example.ketchappn.models.User user){
            Map<String, Object> userHashMap = new HashMap<>();
            userHashMap.put("User", user.getUsername());
            userHashMap.put("UserFriendList", user.getFriends());
            firestore.collection("User").document(user.getUsername())
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




