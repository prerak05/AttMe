package com.attme.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by prerak on 16/10/18.
 */

public class Retro {
    // Todo: baseUrl
    public static String baseURL = "http://18.221.65.9/";


    // Todo: Setup Retrofit
    public static ApiService setupRetrofit(String url) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiService mApiService = retrofit.create(ApiService.class);
        return mApiService;
    }
}
