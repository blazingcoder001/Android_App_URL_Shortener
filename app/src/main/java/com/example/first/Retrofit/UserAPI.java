package com.example.first.Retrofit;

import com.example.first.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
//    @GET("/user/get-shortened")
//    Call<String> shortened();
    @POST("/user/get-shortened")
    Call<String> shortened(@Body String Original, String short_pref);
    @POST("/user/signup")
    Call<User> insert_user(@Body User user);
    @POST("/user/login-check")
    Call<Integer> check_login(@Body String username, String password);
    @POST("/user/delete-user")
    Call<Integer> delete(@Body String username, String password);
    @POST("/user/change-password")
    Call<Integer> change_password(@Body String username, String old_pas, String new_pas);

}
