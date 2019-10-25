package com.example.os.fragments;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.os.R;
import com.example.os.util.CustomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreSerciceFragment extends CustomFragment {



    private ConstraintLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pre_sercice, container, false);

        layout = v.findViewById(R.id.layout_preservice);



        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
        return v;
    }

}
