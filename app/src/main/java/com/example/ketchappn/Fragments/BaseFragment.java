package com.example.ketchappn.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ActivityInfo;

import com.example.ketchappn.Start_Page;


public abstract class BaseFragment extends Fragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null)
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }
}
