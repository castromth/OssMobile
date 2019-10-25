package com.example.os.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.os.R;
import com.example.os.adapter.ComenteAdapter;
import com.example.os.model.Coment;
import com.example.os.model.Os;
import com.example.os.util.FirebaseDatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OsActivity extends AppCompatActivity {

    private static final String TAG = "OsActivity";

    private TextView tvEquip;
    private TextView tvLocal;
    private TextView tvConCheg;
    private TextView tvAcoes;
    private TextView tvConSaida;
    private TextView tvPecas;
    private TextView tvStatus;
    private TextView tvAbertOs;
    private TextView tvOrigem;
    private TextView tvDataLimite;
    private TextView tvContrato;
    private TextView tvResp;
    private TextView tvSolicitante;
    private TextView tvDesc;
    private TextView tvOsId;


    private Button btCancelar;
    private Button btFechar;
    private Button btVoltarAtend;
    private Button btVoltar;
    private Button btPecas;

    private RecyclerView rvComent;
    private ComenteAdapter mAdapter;

    private Os os;

    private FirebaseDatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oss);
        mDbHelper = new FirebaseDatabaseHelper();
        initViews();

        Intent intent = getIntent();
        String id = intent.getStringExtra("OsId");
        if(id != null){
            Log.d(TAG, id);
            getOss(id);
        }

        List<Coment> list = new ArrayList<>();
        Coment c = new Coment();
        list.add(c);
        list.add(c);
        list.add(c);
        mAdapter = new ComenteAdapter(list);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        rvComent.setLayoutManager(llm);
        rvComent.setHasFixedSize(true);
        rvComent.setAdapter(mAdapter);



        btVoltarAtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),CurrentService.class);
                intent.putExtra("os",tvOsId.getText().toString());
                startActivity(intent);
            }
        });
        btPecas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getOss(String id) {
        DatabaseReference ossRef = mDbHelper.getOssRef();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                Os os = dataSnapshot.getValue(Os.class);
                updateUi(os);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ossRef.child(id).addValueEventListener(listener);
    }



    private void updateUi(Os os) {
        if(os.getStatus() == 0){
            btVoltarAtend.setVisibility(View.INVISIBLE);
            btFechar.setVisibility(View.INVISIBLE);
            btPecas.setVisibility(View.VISIBLE);

        }else{
            btPecas.setVisibility(View.GONE);
            btFechar.setVisibility(View.VISIBLE);
        }
        tvSolicitante.setText(os.getSolicitante());
        tvContrato.setText(os.getContrato());
        tvResp.setText(os.getResponsavel());
        tvDataLimite.setText(os.getData());
        tvAbertOs.setText(os.getDataAbert());
        tvStatus.setText("ops");
        tvPecas.setText(os.getPecasUlti());
        tvAcoes.setText(os.getAcoes());
        tvConSaida.setText(os.getCondSaida());
        tvConCheg.setText(os.getCondCheg());
        tvLocal.setText(os.getLocal().replace(" ",""));
        tvEquip.setText(os.getEquip());
        tvOrigem.setText("");
        tvDesc.setText(os.getDesc());
        tvOsId.setText("OS:"+ os.getId());

    }

    void initViews(){

        tvEquip = findViewById(R.id.tvEquip);
        tvLocal = findViewById(R.id.tvLocal);
        tvConCheg = findViewById(R.id.tvCondCheg);
        tvAcoes = findViewById(R.id.tvAcoes);
        tvConSaida = findViewById(R.id.tvCondSaida);
        tvPecas = findViewById(R.id.tvPecas);
        tvStatus = findViewById(R.id.tvStatus);
        tvAbertOs = findViewById(R.id.tvOrigem_os);
        tvDataLimite = findViewById(R.id.tvDataLimit);
        tvContrato = findViewById(R.id.tvContrato);
        tvResp = findViewById(R.id.tvResp_oss);
        tvSolicitante = findViewById(R.id.tvSolicitante);
        tvOrigem = findViewById(R.id.tvOrigem_os);
        tvDesc = findViewById(R.id.tvDesc_os);
        tvOsId = findViewById(R.id.tvOs_os_id);

        btCancelar = findViewById(R.id.btCancelar_os);
        btFechar = findViewById(R.id.btFechar_os);
        btVoltarAtend = findViewById(R.id.btVoltarAtend_os);
        btVoltar = findViewById(R.id.btVoltar_os);
        btPecas = findViewById(R.id.btPecas_os);

        rvComent = findViewById(R.id.rv_os_coment);
    }
}
