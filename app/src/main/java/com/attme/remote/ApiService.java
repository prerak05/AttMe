package com.attme.remote;

import com.attme.HomeScreen.model.SubjectList;
import com.attme.LoginScreen.model.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by prerak on 16/10/18.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("api/login")
    Call<Login> loginCall(@FieldMap() Map<String, String> map);

    @FormUrlEncoded
    @POST("api/register")
    Call<Login> getRegistration(@FieldMap() Map<String, String> map);

    @GET("/api/subjects")
    Call<SubjectList> getSubjectListCall();
}
