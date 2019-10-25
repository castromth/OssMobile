package com.example.os.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.os.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Button btLogin;
    private EditText etEmail;
    private EditText etSenha;



    private FirebaseAuth mAuth;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btLogin =  findViewById(R.id.bt_login);
        etSenha = findViewById(R.id.et_login_senha);
        etEmail = findViewById(R.id.et_login_email);


        btLogin.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
                btLogin.setClickable(false);
                Log.d(TAG, "onClick: ");
                if(infoCheck()){
                    sign(etEmail.getText().toString(),etSenha.getText().toString());
                }
            }
        });


    }


    private void sign(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:success");
                        startActivity(new Intent(getBaseContext(),MainActivity.class));
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "signInWithEmail:failure");
            }
        });
    }

    private boolean infoCheck(){
        String txt = etEmail.getText().toString();
        Log.d(TAG, "infoCheck: "+txt);
        if(!(txt.contains("@") && txt.length() > 8)){
            return false;
        }
        if(etSenha.getText().toString().length() < 8){
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if(mUser != null){

        }
    }
}
