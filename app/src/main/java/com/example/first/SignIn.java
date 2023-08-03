package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.example.first.user.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.NavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    Service service= new Service();

    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        String userstr,passtr,firststr,laststr;
        TextInputLayout url,url_shorten;
        EditText url_inp,url_shorten_inp;
        url=findViewById(R.id.to_shorten_inp);
        url_inp=url.getEditText();
        url_shorten=findViewById(R.id.wish_inp);
        url_shorten_inp=url_shorten.getEditText();
        DrawerLayout side=findViewById(R.id.drawer);
        NavigationView navigationView= findViewById(R.id.nav);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
        Context context= SignIn.this;
        Navigation navigation= new Navigation( context, side, navigationView,topappbar);
        navigation.navexecute();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url_short", url_shorten_inp.getText().toString());
        userstr = sharedPreferences.getString("username", null);
        passtr = sharedPreferences.getString("password", null);
        firststr = sharedPreferences.getString("firstname", null);
        laststr = sharedPreferences.getString("lastname", null);
        editor.apply();

//        Log.e("/*/***/*/*/", url_shorten_inp.getText().toString() );
        Button b1=findViewById(R.id.button1);

        String finalUserstr = userstr;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_shorten_string=url_shorten_inp.getText().toString();
//                JsonObject object= new JsonObject();
//                object.addProperty("username", finalUserstr);
//                object.addProperty("url",url_inp.getText().toString());
//                object.addProperty("url_shorten",url_shorten_inp.getText().toString());
//                MediaType mediaType= MediaType.parse("application/json");
//                RequestBody requestBody=RequestBody.create(mediaType,object.toString());
                User user=new User();
                user.setUsername(finalUserstr);
                user.setPassword(passtr);
                user.setFirstname(firststr);
                user.setLastname(laststr);
                user.setUrl_Full(url_inp.getText().toString());
                user.setUrl_shorten(url_shorten_inp.getText().toString());
//                userAPI.shortened(requestBody)
                userAPI.shortened(user)
                        .enqueue(new Callback<StringPass>() {
                            @Override
                            public void onResponse(Call<StringPass> call, Response<StringPass> response) {
                                if(response.body().getUrl_shorten().equals("success")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                else if(response.body().getUrl_shorten().equals("failed0")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "Failed to shorten the given URL due to database issues!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else if(response.body().getUrl_shorten().equals("failed1")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "URL already taken! Please try another one.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<StringPass> call, Throwable t) {
                                Toast.makeText(SignIn.this, "Currently facing connection issues!", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(getClass().toString()).log(Level.SEVERE,"Error occured",t);

                            }
                        });
            }
        });



    }
}
