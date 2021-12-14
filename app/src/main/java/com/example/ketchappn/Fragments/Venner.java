package com.example.ketchappn.Fragments;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ketchappn.R;
import com.example.ketchappn.database.AccessUser;
import com.example.ketchappn.database.FireBaseUserCallBack;


import java.util.ArrayList;
import java.util.HashMap;


public class Venner extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }





    private AccessUser accessUser = new AccessUser() ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        View v = inflater.inflate(R.layout.fragment_venner, container, false);


        LinearLayout layout = (LinearLayout) v.findViewById(R.id.friendList);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(8,8,8,8);

        Button addFriend = (Button) v.findViewById(R.id.dialogButton);
        Fragment fragment = this;

        addFriend.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                    View blue = getLayoutInflater().inflate(R.layout.dialog_venner, null);
                    EditText nUsername = (EditText) blue.findViewById(R.id.addUsername);
                    Button nButton = (Button) blue.findViewById(R.id.addID);

                    nButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            accessUser.addFriendsTask(nUsername.getText().toString(), fragment);
                        }
                    });
                    mBuilder.setView(blue);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            });




            accessUser.getFriendsTask(new FireBaseUserCallBack() {

                     @Override
                     public void onCallBack(ArrayList<HashMap<String, Object>> f) {

                    System.out.println("friendList from venner : " + f);
                        for (int i = 0; i < f.size(); i++) {

                                    Button btn = new Button(v.getContext());
                                    btn.setText( f.get(i).get("status").toString() + " " + f.get(i).get("username").toString()   );
                                    btn.setGravity(Gravity.CENTER);
                                    btn.setTextSize(20);
                                    btn.setTextColor(Color.BLACK);
                                    btn.setWidth(v.getWidth());
                                    btn.setAllCaps(false);
                                    btn.setBackgroundResource(R.drawable.custom_button);
                                    layout.addView(btn, lp);


                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("Friend name " + btn.getText());
                                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
                                            View blue = getLayoutInflater().inflate(R.layout.dialog_showuserdialog, null);
                                            TextView nUsername = (TextView) blue.findViewById(R.id.displayname);
                                            nUsername.setTextColor(Color.BLACK);
                                            nUsername.setText(btn.getText().toString().split(" ") [1]);
                                            TextView status = (TextView) blue.findViewById(R.id.userstatus);
                                            status.setTextColor(Color.BLACK);
                                            status.setText(btn.getText().toString().split(" ")[0]);

                                            Button removeFriend = (Button) blue.findViewById(R.id.delete);


                                            removeFriend.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    accessUser.removeFriendTask(fragment, btn.getText().toString().split(" ")[1]);

                                                }
                                            });

                                            mBuilder.setView(blue);

                                            AlertDialog dialog = mBuilder.create();
                                            dialog.setTitle("User Profile");
                                            dialog.show();


                                        }
                                    });
                                }
                            }




            }
            );

        return v;


    }
}