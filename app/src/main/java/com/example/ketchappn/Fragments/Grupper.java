

package com.example.ketchappn.Fragments;
import com.example.ketchappn.R;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;



import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class Grupper extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grupper, container, false);
    }
}