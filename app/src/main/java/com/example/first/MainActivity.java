package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.example.first.user.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Connection connection;
    Service service= new Service();

    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView t1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextInputLayout userl=findViewById(R.id.username);
        EditText user=userl.getEditText();
        TextInputLayout passwordl=findViewById(R.id.password);
        EditText password=passwordl.getEditText();

        PasswordToggle toggle= new PasswordToggle(password);
        toggle.execute();
        t1=findViewById(R.id.not_user);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup=new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);
            }
        });

    }




    public void Btnclick(View v)  {

        getvalue_database(v);
    }
    public void getvalue_database(View view)  {

        Thread t= new Thread(new Runnable() {
            TextView t1 ;
            TextInputLayout userl,passwordl;
            EditText user,password;


            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {


                userl=findViewById(R.id.username);
                user=userl.getEditText();
                passwordl=findViewById(R.id.password);
                password=passwordl.getEditText();
                t1=findViewById(R.id.not_user);
                JsonObject object= new JsonObject();
                object.addProperty("username",user.getText().toString());
                object.addProperty("password",password.getText().toString());
                MediaType mediaType= MediaType.parse("application/json");
                RequestBody requestBody=RequestBody.create(mediaType,object.toString());
                userAPI.check_login(requestBody)
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Thread t2= new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(response.body().intValue()==3){

                                    Intent signin= new Intent(MainActivity.this, SignIn.class);
                                    startActivity(signin);


                                }
                                else if (response.body().intValue()==1){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            passwordl.setError("Password is incorrect.");
                                            userl.setError(null);
                                        }
                                    });
                                }
                                else if(response.body().intValue()==2){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            userl.setError("Username does not exist.");
                                            passwordl.setError(null);
                                        }
                                    });

                                }
                            }
                        });
                        t2.start();

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Currently facing connection issues!", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(getClass().toString()).log(Level.SEVERE,"Error occured",t);
                    }
                });



                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user.getText().toString());
                                editor.apply();


            }
        });
        t.start();

    }
}