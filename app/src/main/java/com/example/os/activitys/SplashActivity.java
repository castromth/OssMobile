package com.example.os.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.os.R;
import com.example.os.util.FirebaseDatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private static final int DELAY_SPLASH_SCREEN = 2000;
    private FirebaseDatabaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        helper = new FirebaseDatabaseHelper();

        Intent intent = getIntent();
        if(intent != null){

        }
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(!task.isSuccessful()){
                    Log.d(TAG, "onComplete: ");
                    return;
                }

                String token = task.getResult().getToken();
                String msg = getString(R.string.fcm_token,token);
                Log.d(TAG, "onComplete: "+ msg);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(helper.isSign()){
                    startActivity(new Intent(getBaseContext(),MainActivity.class));
                }else{
                    startActivity(new Intent(getBaseContext(),LoginActivity.class));
                }

                finish();
            }
        },DELAY_SPLASH_SCREEN);
    }
}
