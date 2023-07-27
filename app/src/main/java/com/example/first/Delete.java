package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Delete extends AppCompatActivity {
    Connection connection;
    Service service= new Service();

    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);
        DrawerLayout side=findViewById(R.id.drawer);
        NavigationView navigationView= findViewById(R.id.nav);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
        Context context= Delete.this;
        Navigation navigation= new Navigation( context, side, navigationView,topappbar);
        navigation.navexecute();
        Button b1,b2;
        String userstr,url_short;
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userstr = sharedPreferences.getString("username", null);
        url_short=sharedPreferences.getString("url_short",null);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject object= new JsonObject();
                        object.addProperty("username",userstr);
                        object.addProperty("url_short",url_short);
                        MediaType mediaType= MediaType.parse("application/json");
                        RequestBody requestBody=RequestBody.create(mediaType,object.toString());
//                        Log.e("*/*/**/dfsdfdffffff",userstr);
                        userAPI.delete(requestBody)
                                .enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        if(response.body()==false){
                                            Thread t3= new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            });
                                            t3.start();

                                            Thread t2= new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        t3.join();
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    Intent login=new Intent(Delete.this, MainActivity.class);
                                                    startActivity(login);
                                                }
                                            });
                                            t2.start();
                                        }
                                        else{
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "Account cannot be deleted due to errors!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {
                                        Toast.makeText(Delete.this, "Currently facing connection issues!", Toast.LENGTH_SHORT).show();
                                        Logger.getLogger(getClass().toString()).log(Level.SEVERE,"Error occured",t);
                                    }
                                });


                    }
                });
                t1.start();

                    }
                });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
