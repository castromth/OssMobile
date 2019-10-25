package com.example.os.activitys;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.os.R;
import com.example.os.adapter.OsAdpaterFB;
import com.example.os.adapter.OsAdapter;
import com.example.os.adapter.SpinnerAdapter;
import com.example.os.interfaces.OssRvListener;
import com.example.os.interfaces.StateSpinnerCheckBoxListener;
import com.example.os.model.Os;
import com.example.os.model.StateSpinner;
import com.example.os.util.CustomActivity;
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

public class MainActivity extends CustomActivity implements OssRvListener, StateSpinnerCheckBoxListener {
    private static final String TAG = "MainActivity";

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

    private TextView tvItemCount;

    //DataPocker
    private DatePickerDialog mDatePickerDialog;
    private Calendar mCalendar;


    private FirebaseDatabase mFirebaseDatabase;


    private FloatingActionButton mActionButton;

    private List<Os> mOsList = new ArrayList<Os>();


    private static final int PERMISSIONS_REQUEST_CODE = 2631;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.rv_oss);
        mActionButton = findViewById(R.id.fb_newoss_oss);
        mDbHelper = new FirebaseDatabaseHelper();
        etOs = findViewById(R.id.et_filter_os);
        etDataI = findViewById(R.id.et_filter_dataRange1);
        etDataF = findViewById(R.id.et_filter_dataRange2);
        spContrato = findViewById(R.id.sp_filter_contrato);
        spLocal = findViewById(R.id.sp_filter_local);
        spOrigem = findViewById(R.id.sp_filter_origem);
        spResp = findViewById(R.id.sp_filter_resp);
        spStatus = findViewById(R.id.sp_filter_status);
        tvItemCount = findViewById(R.id.tv_main_itens_count);

        verificaPermicoes();

        final String[] select_local = {
                "Local"
        };
        final String[] select_status = {
                "Status"
        };
        final String[] select_origem = {
                "Origem"
        };
        initStateSpinner(spContrato,R.array.select_contrato,this);
        initStateSpinner(spResp,R.array.select_resp, this);
        initStateSpinner(spLocal,select_local,this);
        initStateSpinner(spStatus,select_status,this);
        initStateSpinner(spOrigem,select_origem,this);


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




        ivFilterIcon = findViewById(R.id.filter_icon);
        ivFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFilters();
                hideKeyboard();
            }
        });

        contentLayout = findViewById(R.id.filter_content_layout);
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
                startActivity(new Intent(getBaseContext(), NewOsActivity.class));
            }
        });

    }



    private void verificaPermicoes() {
        Log.i(TAG, "pedindo permiçoes para o usuario");
        String[] permisoes = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permisoes[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permisoes[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permisoes[2]) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, permisoes, PERMISSIONS_REQUEST_CODE);
        }
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
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mOsAdpaterFB);
    }
    private void showDataPicker(EditText et){
        mCalendar = Calendar.getInstance();
        final EditText etData = et;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        Log.d(TAG, day+"/"+month+"/"+year);
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                etData.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
            }
        },year,month,day);
        mDatePickerDialog.show();
    }



    @Override
    public void onOssClickListener(View view, int position) {
        Log.d(TAG, "onOssClickListener: ");
        Intent intent = new Intent(getBaseContext(), OsActivity.class);
        intent.putExtra("OsId",mOsAdpaterFB.getItem(position).getId());
        startActivity(intent);

    }

    @Override
    public void onOssDataChangeListener() {
        tvItemCount.setText(mOsAdpaterFB.getItemCount()+" items");
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
    public void onCheckBoxListener(String text, boolean isChecked) {

    }
}
