package com.example.os.api.service;

import com.example.os.model.form.LoginForm;

import retrofit2.Call;
import retrofit2.http.POST;

public interface AuthService {


    @POST("/auth")
    Call auth(LoginForm loginForm);
}
