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
    Call<String> shortened(@Body String Original, @Body String short_pref);
    @POST("/user/signup")
    Call<Boolean> insert_user(@Body User user);
    @POST("/user/login-check")
    Call<Boolean> check_login(@Body String username);
    @POST("/user/delete-user")
    Call<Boolean> delete(@Body String username);
    @POST("/user/change-password")
    Call<Boolean> change_password(@Body String username, @Body String old_pas, @Body String new_pas);

}
