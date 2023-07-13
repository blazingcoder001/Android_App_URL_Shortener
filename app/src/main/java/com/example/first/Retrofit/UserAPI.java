package com.example.first.Retrofit;

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
    @POST("/user/get-shortened")
    Call<String> shortened(@Body String Original, @Body String short_pref);
    @POST("/user/signup")
    Call<Boolean> insert_user(@Body User user);
    @POST("/user/login-check")
    Call<Integer> check_login(@Body RequestBody requestBody);
    @POST("/user/delete-user")
    Call<Boolean> delete(@Body String username);
    @POST("/user/change-password")
    Call<Integer> change_password(RequestBody requestBody);

}
