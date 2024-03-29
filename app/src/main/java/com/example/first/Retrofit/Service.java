package com.example.first.Retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    public Retrofit getRetrofit() {
        return retrofit;
    }

    Retrofit retrofit;
    public Service(){
        makeService();
    }

    private void makeService() {
        retrofit= new Retrofit.Builder()
        .baseUrl("http://129.21.124.108:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

}
