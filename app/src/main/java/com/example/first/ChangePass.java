package com.example.first;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePass extends AppCompatActivity {
    Connection connection;
    Service service= new Service();

    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        TextInputLayout old_pa = findViewById(R.id.password_old_inp);
        EditText old_pas_ed = old_pa.getEditText();
        TextInputLayout new_pa = findViewById(R.id.password_new_inp);
        EditText new_pa_ed = new_pa.getEditText();
        TextInputLayout new_pa_ret = findViewById(R.id.password_retype_inp);
        EditText new_pa_ret_ed = new_pa_ret.getEditText();
        EditText username;
        DrawerLayout side=findViewById(R.id.drawer);
        NavigationView navigationView= findViewById(R.id.nav);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
        Context context= ChangePass.this;
        Navigation navigation= new Navigation( context, side, navigationView,topappbar);
        navigation.navexecute();
        ConstraintLayout layout=(ConstraintLayout) findViewById(R.id.change_pass);
        layout.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                // Hide the soft keyboard when touch outside
                InputMethodManager imm = (InputMethodManager)getSystemService (Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow (v.getWindowToken (), 0);
                return false;
            }
        });

        PasswordToggle toggle= new PasswordToggle(old_pas_ed);
        toggle.execute();
        PasswordToggle toggle1=new PasswordToggle(new_pa_ed);
        toggle1.execute();
        PasswordToggle toggle2=new PasswordToggle(new_pa_ret_ed);
        toggle2.execute();

        new_pa_ret_ed.addTextChangedListener(new TextWatcher() {
            String s, out;
            boolean b;
            check_retype c = new check_retype();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s = new_pa_ed.getText().toString();
                b = c.check(s, charSequence.toString());
                if (b == false) {
                    out = "Password does not match!";
                    new_pa_ret.setError(out);
                } else {

                    new_pa_ret.setError(null);
                    out = null;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do nothing
            }
        });
        Button b1=findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            String userstr;
            @Override
            public void onClick(View view) {
                Thread t= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        userstr = sharedPreferences.getString("username", null);
                        JsonObject object= new JsonObject();
                        object.addProperty("username",userstr);
                        object.addProperty("old_password",old_pas_ed.getText().toString());
                        object.addProperty("new_password",new_pa_ed.getText().toString());
                        MediaType mediaType= MediaType.parse("application/json");
                        RequestBody requestBody=RequestBody.create(mediaType,object.toString());
                        userAPI.change_password(requestBody)
                                .enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if(response.body().intValue()==1){
                                            Thread t2= new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, "Password Successfully Changed!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            });
                                            t2.start();
                                            Thread t3= new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        t2.join();
                                                    } catch (InterruptedException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    Intent change= new Intent(context,MainActivity.class);
                                                    context.startActivity(change);
                                                }
                                            });
                                            t3.start();

                                        }
                                        else if (response.body().intValue()==2){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    old_pa.setError("Password is incorrect.");
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        Toast.makeText(ChangePass.this, "Account Cannot be created!", Toast.LENGTH_SHORT).show();
                                        Logger.getLogger(getClass().toString()).log(Level.SEVERE,"Error occured",t);
                                    }
                                });

                    }
                });
                t.start();

            }
        });


    }

}