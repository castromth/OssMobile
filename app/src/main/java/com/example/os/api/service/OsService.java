package com.example.os.api.service;


import com.example.os.model.Os;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OsService {


    @POST("os")
    Call insere(@Body Os os);

    @GET("os")
    Call<List<Os>> getOsList();

    @GET("os/{id}")
    Call<Os> getOs(@Path("id") long id);

    @POST("os/{id}")
    Call<Os> atualiza(@Path("id") long id);

    @DELETE("os/{id}")
    Call delete(@Path("id") long id);

    @GET("os/search")
    Call<List<Os>> search(@Body Os os);


}
