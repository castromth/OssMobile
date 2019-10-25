package com.example.os.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.os.R;
import com.example.os.activitys.NewOsActivity;
import com.example.os.activitys.OsActivity;
import com.example.os.adapter.OsAdpaterFB;
import com.example.os.adapter.OsAdapter;
import com.example.os.adapter.SpinnerAdapter;
import com.example.os.interfaces.OssRvListener;
import com.example.os.interfaces.StateSpinnerCheckBoxListener;
import com.example.os.model.Os;
import com.example.os.model.StateSpinner;
import com.example.os.util.FirebaseDatabaseHelper;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OsFragment extends Fragment implements OssRvListener, StateSpinnerCheckBoxListener {

    private static final String TAG = "OsFragment";

    private RecyclerView recyclerView;
    private OsAdapter osAdapter;
    private FirebaseRecyclerOptions<Os> options;
    private Query query;
    private FirebaseUser mUser;
    private OsAdpaterFB mOsAdpaterFB;
    private FirebaseDatabaseHelper mDbHelper;

    //FilterSheet
    private ImageView ivFilterIcon;
    private LinearLayout contentLayout;
    private BottomSheetBehavior mSheetBehavior;

    private EditText etOs;
    private EditText etDataI;
    private EditText etDataF;
    private Spinner spContrato;
    private Spinner spResp;
    private Spinner spLocal;
    private Spinner spStatus;
    private Spinner spOrigem;

    //DataPocker
    private DatePickerDialog mDatePickerDialog;
    private Calendar mCalendar;


    private FirebaseDatabase mFirebaseDatabase;


    private FloatingActionButton mActionButton;

    private List<Os> mOsList = new ArrayList<Os>();


    public OsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_oss, container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = v.findViewById(R.id.rv_oss);
        mActionButton = v.findViewById(R.id.fb_newoss_oss);
        mDbHelper = new FirebaseDatabaseHelper();
        etOs = v.findViewById(R.id.et_filter_os);
        etDataI = v.findViewById(R.id.et_filter_dataRange1);
        etDataF = v.findViewById(R.id.et_filter_dataRange2);
        spContrato = v.findViewById(R.id.sp_filter_contrato);
        spLocal = v.findViewById(R.id.sp_filter_local);
        spOrigem = v.findViewById(R.id.sp_filter_origem);
        spResp = v.findViewById(R.id.sp_filter_resp);
        spStatus = v.findViewById(R.id.sp_filter_status);



        final String[] select_contrato = {
                "Contrato", "ANTT", "AGETOP", "SMTA"};

        ArrayList<StateSpinner> contratoList = new ArrayList<>();
        for (int i = 0; i < select_contrato.length; i++) {
            StateSpinner state = new StateSpinner();
            state.setText(select_contrato[i]);
            state.setSelected(false);
            contratoList.add(state);
        }
        SpinnerAdapter contratoSpAdapter = new SpinnerAdapter(getContext(),0,contratoList);
        contratoSpAdapter.setStateSpinnerCheckBoxListener(this);
        spContrato.setAdapter(contratoSpAdapter);

        final String[] select_resp = {
                "Responsavel","Matheus","Harryson","Willian","1/2Kg"};

        ArrayList<StateSpinner> respList = new ArrayList<>();
        for(int i = 0;i < select_resp.length;i++){
            StateSpinner state = new StateSpinner();
            state.setText(select_resp[i]);
            state.setSelected(false);
            respList.add(state);
        }
        SpinnerAdapter respSpAdapter = new SpinnerAdapter(getContext(),0,respList);
        respSpAdapter.setStateSpinnerCheckBoxListener(this);
        spResp.setAdapter(respSpAdapter);



        final String[] select_local = {
          "Local"
        };
        ArrayList<StateSpinner> localList = new ArrayList<>();
        for(int i = 0; i < select_local.length; i++){
            StateSpinner state = new StateSpinner();
            state.setText(select_local[i]);
            state.setSelected(false);
            localList.add(state);
        }
        SpinnerAdapter localSpAdapter = new SpinnerAdapter(getContext(),0,localList);
        localSpAdapter.setStateSpinnerCheckBoxListener(this);
        spLocal.setAdapter(localSpAdapter);



        final String[] select_status = {
          "Status"
        };

        ArrayList<StateSpinner> statusList = new ArrayList<>();
        for(int i = 0; i < select_status.length;i++){
            StateSpinner state = new StateSpinner();
            state.setText(select_status[i]);
            state.setSelected(false);
            statusList.add(state);
        }
        SpinnerAdapter statusSpAdapter = new SpinnerAdapter(getContext(),0,statusList);
        statusSpAdapter.setStateSpinnerCheckBoxListener(this);
        spStatus.setAdapter(statusSpAdapter);


        final String[] select_origem = {
                "Origem"
        };
        ArrayList<StateSpinner> origemList = new ArrayList<>();
        for(int i = 0 ; i < select_origem.length;i++){
            StateSpinner state = new StateSpinner();
            state.setText(select_origem[i]);
            state.setSelected(false);
            origemList.add(state);
        }
        SpinnerAdapter origemSpAdapter = new SpinnerAdapter(getContext(),0,origemList);
        spOrigem.setAdapter(origemSpAdapter);

        etDataI.setFocusableInTouchMode(false);
        etDataI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker((EditText) view);
            }
        });
        etDataF.setFocusableInTouchMode(false);
        etDataF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker((EditText) view);
            }
        });




        ivFilterIcon = v.findViewById(R.id.filter_icon);
        ivFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFilters();
                hideKeyboard(getActivity());
            }
        });

        contentLayout = v.findViewById(R.id.filter_content_layout);
        mSheetBehavior = BottomSheetBehavior.from(contentLayout);
        mSheetBehavior.setFitToContents(false);
        mSheetBehavior.setHideable(false);
        mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        query = mDbHelper.getOssRef();
        options = new FirebaseRecyclerOptions.Builder<Os>()
                .setQuery(query, Os.class)
                .build();

        initRv(options);


        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getBaseContext(), NewOsActivity.class));
            }
        });
        return v;
    }

    private void toggleFilters() {
        if(mSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){

            mSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        }else {
            mSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void initRv(FirebaseRecyclerOptions options){
        Log.d(TAG, "initRv: ");

        mOsAdpaterFB = new OsAdpaterFB(options,this);
        mOsAdpaterFB.startListening();
        LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mOsAdpaterFB);
    }
    private void initRv(List<Os> list){
        Log.d(TAG, "initRv: ");
        osAdapter = new OsAdapter(list);
        osAdapter.setOssRvClickListener(this);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(osAdapter);
    }

    private void showDataPicker(EditText et){
        mCalendar = Calendar.getInstance();
        final EditText etData = et;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        Log.d(TAG, day+"/"+month+"/"+year);
        mDatePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                etData.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
            }
        },year,month,day);
        mDatePickerDialog.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onOssClickListener(View view, int position) {
        Log.d(TAG, "onOssClickListener: ");
        Intent intent = new Intent(getActivity().getBaseContext(), OsActivity.class);
        intent.putExtra("OsId",mOsAdpaterFB.getItem(position).getId());
        startActivity(intent);

    }

    @Override
    public void onOssDataChangeListener() {

    }

    @Override
    public void onStart() {
        super.onStart();
        if(mOsAdpaterFB != null)
            mOsAdpaterFB.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mOsAdpaterFB != null)
            mOsAdpaterFB.stopListening();
    }

    @Override
    public void onCheckBoxListener(String txt, boolean isChecked) {

    }
}
