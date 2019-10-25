package com.example.os.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.os.R;
import com.example.os.interfaces.StateSpinnerCheckBoxListener;
import com.example.os.model.Os;
import com.example.os.model.StateSpinner;
import com.example.os.adapter.SpinnerAdapter;
import com.example.os.util.CustomActivity;
import com.example.os.util.FirebaseDatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class NewOsActivity extends CustomActivity implements Validator.ValidationListener, StateSpinnerCheckBoxListener {


    private static final String TAG = "NewOsActivity";
    
    @NotEmpty
    @Length(min = 3)
    private EditText etSumario;
    @NotEmpty
    @Length(min = 3)
    private EditText etDesc;
    @NotEmpty
    @Length(min = 3)
    private EditText etLocal;
    @NotEmpty
    @Length(min = 3)
    private EditText etEquip;

    private View vOrigem;
    private View vContrato;
    private View vResp;

    private Button btSalva;

    private Spinner spOrigem;
    private Spinner spContrato;
    private Button mVoltar;
    private SpinnerAdapter mStateAdapter;
    private Spinner spResp;


    //DataPicker
    private EditText etData;
    private View vBtData;
    private DatePickerDialog mDatePickerDialog;
    private Calendar calendar;

    private Validator validator;

    private boolean spinnerChecks = false;
    private String origem = "";


    private FirebaseDatabaseHelper mDbHelper;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_oss);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDbHelper = new FirebaseDatabaseHelper();
        validator = new Validator(this);
        validator.setValidationListener(this);

        initViews();




        initSpinners();
        spResp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attSpinnerLine(spResp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spContrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                attSpinnerLine(spContrato);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        vBtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker();
            }
        });


        mVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoCheck();
            }
        });


    }

    private void infoCheck() {
        validator.validate();
        if(spinnerCheck(spOrigem) && spinnerCheck(spResp)){
                spinnerChecks = true;

        }
        if(spinnerCheck(spContrato)){
            spinnerChecks = true;
        }else{
            spinnerChecks = false;
        }
    }



    private List<StateSpinner> retrieveAllItems(Spinner sp){
        Adapter adapter = sp.getAdapter();
        int n = adapter.getCount();
        List<StateSpinner> list = new ArrayList<>();
        for(int i = 0; i < n; i++){
            StateSpinner stateSpinner = (StateSpinner) adapter.getItem(i);
            list.add(stateSpinner);
        }

        return list;
    }
    private boolean stateSpinnerCheck(List<StateSpinner> list){
        int n = list.size() -1;
        for(int i = 0;i < n;i++){
            if(list.get(i).isSelected()){
                return true;
            }
        }
        return false;
    }

    private boolean spinnerCheck(Spinner sp){
        Log.d(TAG, "spinnerCheck: "+sp.getItemAtPosition(0).toString());
        if(sp.getSelectedItem() instanceof StateSpinner){
            if(!stateSpinnerCheck(retrieveAllItems(spOrigem))){
                vOrigem.setBackground(ContextCompat.getDrawable(this,R.color.colorRed));
            }
        }else if(sp.getSelectedItem()
                .toString()
                .equalsIgnoreCase(
                        sp.getItemAtPosition(0)
                                .toString())){
            if(sp.getItemAtPosition(0).toString().equalsIgnoreCase("Contrato")) {
                vContrato.setBackground(ContextCompat.getDrawable(this, R.color.colorRed));
            }
            if(sp.getItemAtPosition(0).toString().equalsIgnoreCase("Responsavel")) {
                vResp.setBackground(ContextCompat.getDrawable(this, R.color.colorRed));
            }
            return false;
        }

        return true;
    }
    private void attSpinnerLine(Spinner sp){

        if(sp.getItemAtPosition(0) instanceof StateSpinner){
            if(((StateSpinner)sp.getItemAtPosition(0)).getText().equalsIgnoreCase("Origem")){
                vOrigem.setBackground(ContextCompat.getDrawable(this,R.color.colorAccent));
            }
        } else if(sp.getItemAtPosition(0).toString().equalsIgnoreCase("Contrato")){
                vContrato.setBackground(ContextCompat.getDrawable(this,R.color.colorAccent));
        } else if(sp.getItemAtPosition(0).toString().equalsIgnoreCase("Responsavel")){
                vResp.setBackground(ContextCompat.getDrawable(this,R.color.colorAccent));
            }
        }

    private void initViews(){
        etDesc = findViewById(R.id.et_newoss_desc);
        etEquip = findViewById(R.id.et_newoss_equip);
        etSumario = findViewById(R.id.et_newoss_sumario);
        etLocal = findViewById(R.id.et_newoss_local);
        etData = findViewById(R.id.et_newoss_data);
        vBtData = findViewById(R.id.view_newoss_btData);
        mVoltar = findViewById(R.id.bt_voltar_newoss);
        spOrigem = findViewById(R.id.sp_newoss_origem);
        spResp = findViewById(R.id.sp_newoss_resp);
        spContrato = findViewById(R.id.sp_newoss_contrato);
        btSalva = findViewById(R.id.bt_newoss_salvar);
        vOrigem = findViewById(R.id.v_newoss_origem);
        vContrato = findViewById(R.id.v_newoss_contrato);
        vResp = findViewById(R.id.v_newoss_resp);
    }
    private void initSpinners(){

        initStateSpinner(spOrigem,R.array.select_origem,this);
        initSpinner(spContrato,R.array.select_contrato);
        initSpinner(spResp,R.array.select_resp);

    }
    private void showDataPicker(){
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
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
    public void onValidationSucceeded() {
        if(spinnerChecks){
            Log.d(TAG, "onValidationSucceeded: ");
            makeToast("OS criada com sucesso");
            saveOss();
        }

    }

    private void saveOss() {
        Os os = new Os();
        os.setId(UUID.randomUUID().toString().split("-")[0]);
        os.setDesc(etDesc.getText().toString());
        os.setData(etData.getText().toString());
        os.setLocal(etLocal.getText().toString());
        os.setContrato(spContrato.getSelectedItem().toString());
        os.setOrigem(origem);
        os.setEquip(etEquip.getText().toString());
        os.setResponsavel(spResp.getSelectedItem().toString());
        os.setSolicitante(mUser.getDisplayName());

        mDbHelper.postNewOss(os);
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = getResources().getString(R.string.editTextInvalid);
            if(view instanceof EditText){
                ((EditText)view).setError(message);
            }else{
                makeToast(message);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void makeToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckBoxListener(String txt, boolean isChecked) {
        if(isChecked){
            origem += txt+" ";
        }else{
            origem = origem.replace(txt,"");
        }
        attSpinnerLine(spOrigem);
        Log.d(TAG, origem);

    }
}
