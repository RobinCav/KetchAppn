package com.example.ketchappn.Fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ketchappn.R;
import com.example.ketchappn.Start_Page;
import com.example.ketchappn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Venner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Venner extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Venner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Venner.
     */
    // TODO: Rename and change types and number of parameters
    public static Venner newInstance(String param1, String param2) {
        Venner fragment = new Venner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseFirestore firestore;
    private ArrayList<User> friends;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        firestore = FirebaseFirestore.getInstance();


    }
    /*
        friends = new ArrayList<>();
        friends.add(new User(1,"yaqub","yaqubsaid@gmail.com","rrr",null));
        friends.add(new User(2,"robin","robincalv@gmail.com","rrr",null));
        friends.add(new User(3,"aleks","aleks@gmail.com","rrr",null));

        user = new User(0, "karrar", "karrara@gmail.com", "okthendude",friends);

        auth = FirebaseAuth.getInstance();

        setDocument(user);

     */




    public void setDocument(User user) {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_venner, container, false);

            ListView lstItems = (ListView)v.findViewById(R.id.friendList);
            Button myButton = (Button) v.findViewById(R.id.dialogButton);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                   View blue = getLayoutInflater().inflate(R.layout.dialog_venner, null);
                    EditText nUsername = (EditText) blue.findViewById(R.id.addUsername);
                    Button nButton = (Button) blue.findViewById(R.id.addID);
                    nButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!nUsername.getText().toString().isEmpty()){

                                firestore.collection("FriendList")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                                        User friend = new User(document.get("Username").toString(), document.get("Email").toString());
                                                        System.out.println("user from colleciton : " + friend.getUsername());
                                                        System.out.println("ENTERTED TEXT: " + nUsername.getText().toString());
                                                        if(nUsername.getText().toString().equals(friend.getUsername())){
                                                            System.out.println("THIS SHIT EXIST......");
                                                            friend.addFriend(LoginAct.CurUser);
                                                            LoginAct.CurUser.addFriend(friend);
                                                            DocumentReference curUserRef = firestore.collection("FriendList").document(LoginAct.CurUser.getEmail());

                                                            curUserRef
                                                                    .update("UserFriendList",  FieldValue.arrayUnion(friend.getUsername()))
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

                                                            DocumentReference friendRef = firestore.collection("FriendList").document(friend.getEmail());

                                                            friendRef
                                                                    .update("UserFriendList",  FieldValue.arrayUnion(LoginAct.CurUser.getUsername()))
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
                                                            FragmentTransaction ft = getFragmentManager().beginTransaction();

                                                            ft.detach(Venner.this).attach(Venner.this).commit();

                                                            Toast.makeText(v.getContext(),
                                                                    "User added!",
                                                                    Toast.LENGTH_SHORT).show();


                                                        }
                                                    }
                                                } else {
                                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                                }
                                            }
                                        });


                            }
                            else {
                                Toast.makeText(v.getContext(),
                                        "error user not found",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mBuilder.setView(blue);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            });




        firestore.collection("FriendList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("Username").equals(LoginAct.CurUser.getUsername())){
                                    //We have our list view
                                    ArrayList<String> friends = (ArrayList<String>) document.get("UserFriendList");




                                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,friends);
                                    lstItems.setAdapter(allItemsAdapter);
                                }


                                }
                            }
                         else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        // Inflate the layout for this fragment
        return v;


    }
}