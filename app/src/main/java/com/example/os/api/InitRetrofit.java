package com.example.os.api;

import com.example.os.api.service.OsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitRetrofit {

    private final Retrofit retrofit;
    public InitRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl("192.168.220.149:8080/os")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public OsService getOsService(){
        return retrofit.create(OsService.class);
    }
}
