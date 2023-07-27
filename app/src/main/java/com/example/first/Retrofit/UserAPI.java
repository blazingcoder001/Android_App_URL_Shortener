package com.example.first.Retrofit;

import com.example.first.StringPass;
import com.example.first.user.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
//    @GET("/user/get-shortened")
//    Call<String> shortened();
    @POST("/user/get-shortened")// Post changed to get
    Call<StringPass> shortened(@Body User user);
    @POST("/user/signup")
    Call<Boolean> insert_user(@Body User user);
    @POST("/user/login-check")// Post changed to get
    Call<Integer> check_login(@Body RequestBody requestBody);
    @POST("/user/delete-user")
    Call<Boolean> delete(@Body RequestBody requestBody);
    @POST("/user/change-password")
    Call<Integer> change_password(@Body RequestBody requestBody);

}
