package com.example.os.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.os.R;
import com.example.os.fragments.AfterServiceFragment;
import com.example.os.fragments.InServiceFragment;
import com.example.os.fragments.PreSerciceFragment;

import java.util.ArrayList;
import java.util.List;

public class CurrentService extends AppCompatActivity {



    private PreSerciceFragment preSerciceFragment;
    private InServiceFragment inServiceFragment;
    private AfterServiceFragment afterServiceFragment;

    private List<Fragment> fragmentList;

    private Button btVoltar;
    private Button btAvancar;

    private Button btLeftArrow;
    private TextView tvOsTitle;

    private View vEstagio1;
    private View vEstagio2;
    private View vEstagio3;

    private FrameLayout frameLayout;
    private int fragmentControler = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_service);

        initViews();
        updateUi(fragmentControler);


        Intent intent = getIntent();
        if(intent != null){
            String id = intent.getStringExtra("os");
            tvOsTitle.setText(id);
        }

        fragmentList = new ArrayList<>();




        preSerciceFragment = new PreSerciceFragment();
        inServiceFragment = new InServiceFragment();
        afterServiceFragment = new AfterServiceFragment();

        fragmentList.add(preSerciceFragment);
        fragmentList.add(inServiceFragment);
        fragmentList.add(afterServiceFragment);


        setFragment(fragmentList.get(fragmentControler));

        btLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btVoltar.setClickable(false);
                if(fragmentControler != 0){
                    fragmentControler--;
                    setFragment(fragmentList.get(fragmentControler));
                }
                updateUi(fragmentControler);
                btVoltar.setClickable(true);
            }
        });


        btAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btAvancar.setClickable(false);
                if(fragmentControler != 2){
                    fragmentControler++;
                    setFragment(fragmentList.get(fragmentControler));
                }
                updateUi(fragmentControler);
                btAvancar.setClickable(true);
            }
        });



    }

    private void initViews() {
        frameLayout = findViewById(R.id.frame_current_service);
        btAvancar = findViewById(R.id.bt_avancar_currentService);
        btVoltar = findViewById(R.id.bt_voltar_currentService);
        btLeftArrow = findViewById(R.id.bt_service_leftarrow);
        tvOsTitle = findViewById(R.id.tv_service_title);

        vEstagio1 = findViewById(R.id.v_estagio_1);
        vEstagio2 = findViewById(R.id.v_estagio_2);
        vEstagio3 = findViewById(R.id.v_estagio_3);
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.frame_current_service,fragment);
        mTransaction.commit();
    }

    private void updateUi(int i){
        if (i == 0){
            btVoltar.setVisibility(View.GONE);
            btAvancar.setText("Avançar");
            vEstagio2.setBackgroundTintList(getResources().getColorStateList(R.color.colorDivider));
            vEstagio3.setBackgroundTintList(getResources().getColorStateList(R.color.colorDivider));
        }
        if(i == 1){
            btAvancar.setText("Avançar");
            btVoltar.setVisibility(View.VISIBLE);
            vEstagio2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            vEstagio3.setBackgroundTintList(getResources().getColorStateList(R.color.colorDivider));
        }
        if(i == 2){
            btAvancar.setText("Concluir");
            btVoltar.setVisibility(View.VISIBLE);
            vEstagio2.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            vEstagio3.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }
    }
}
