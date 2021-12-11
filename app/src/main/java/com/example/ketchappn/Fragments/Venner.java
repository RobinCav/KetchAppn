package com.example.ketchappn.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ketchappn.R;
import com.example.ketchappn.database.AccesUser;
import com.example.ketchappn.database.FireBaseCallBack;


import java.util.ArrayList;


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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        



    }





    private AccesUser accesUser = new AccesUser() ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_venner, container, false);

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.friendList);

        Button myButton = (Button) v.findViewById(R.id.dialogButton);
        Fragment fragment = this;

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
                            accesUser.addFriendsTask(nUsername.getText().toString(), fragment);
                        }
                    });
                    mBuilder.setView(blue);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                }
            });




            accesUser.getFriendsTask(new FireBaseCallBack() {
                @Override
                public void onCallback(ArrayList<String> f) {
                    /*
                    ArrayAdapter<String> allItemsAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,f);
                    lstItems.setAdapter(adapter);
                     */

                    for(int i=0; i <f.size();i++){
                        Button btn = new Button(getContext());
                        btn.setText(f.get(i));
                        btn.setGravity(Gravity.CENTER);
                        btn.setTextSize(15);
                        layout.addView(btn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("Friend name " + btn.getText());
                            }
                        });
                    }

                }
            }
            );

        return v;


    }
}