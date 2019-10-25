package com.example.os.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


import com.example.os.R;
import com.example.os.adapter.SpinnerAdapter;
import com.example.os.interfaces.StateSpinnerCheckBoxListener;
import com.example.os.model.StateSpinner;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {






    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        view.clearFocus();
    }

    protected void initStateSpinner(Spinner sp, int listId, StateSpinnerCheckBoxListener listener){
        String[] list = getResources().getStringArray(listId);
        ArrayList<StateSpinner> stateList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            StateSpinner state = new StateSpinner();
            state.setText(list[i]);
            state.setSelected(false);
            stateList.add(state);
        }
        SpinnerAdapter contratoSpAdapter = new SpinnerAdapter(getBaseContext(),0,stateList);
        contratoSpAdapter.setStateSpinnerCheckBoxListener(listener);
        sp.setAdapter(contratoSpAdapter);
    }
    protected void initStateSpinner(Spinner sp,String[] list,StateSpinnerCheckBoxListener listener){
        ArrayList<StateSpinner> stateList = new ArrayList<>();
        for(int i = 0; i < list.length; i++){
            StateSpinner state = new StateSpinner();
            state.setText(list[i]);
            state.setSelected(false);
            stateList.add(state);
        }
        SpinnerAdapter localSpAdapter = new SpinnerAdapter(getBaseContext(),0,stateList);
        localSpAdapter.setStateSpinnerCheckBoxListener(listener);
        sp.setAdapter(localSpAdapter);
    }
    protected void initSpinner(Spinner sp,int stringList){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                stringList,
                R.layout.item_spinner_default);
        sp.setAdapter(adapter);
    }
}
