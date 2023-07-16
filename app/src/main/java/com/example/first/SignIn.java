package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.util.NavigableMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SignIn extends AppCompatActivity {
    Service service= new Service();

    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        DrawerLayout side=findViewById(R.id.drawer);
        NavigationView navigationView= findViewById(R.id.nav);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
        Context context= SignIn.this;
        Navigation navigation= new Navigation( context, side, navigationView,topappbar);
        navigation.navexecute();
        TextInputLayout username,url,url_shorten;
        EditText url_inp,url_shorten_inp;
        String userstr;
        url=findViewById(R.id.to_shorten_inp);
        url_inp=url.getEditText();
        url_shorten=findViewById(R.id.wish_inp);
        url_shorten_inp=url_shorten.getEditText();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userstr = sharedPreferences.getString("username", null);
        JsonObject object= new JsonObject();
        object.addProperty("username",userstr);
        object.addProperty("url",url_inp.getText().toString());
        object.addProperty("url_shorten",url_shorten_inp.getText().toString());
        MediaType mediaType= MediaType.parse("application/json");
        RequestBody requestBody=RequestBody.create(mediaType,object.toString());
        userAPI.shortened(requestBody);
//        side.setScrimColor(ContextCompat.getColor(this,R.color.background));
//        side.closeDrawer(GravityCompat.START);
//        //navigationView.inflateMenu(R.menu.navigation_drawer);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id= item.getItemId();
//                if(id==R.id.Change){
//                    Intent change= new Intent(SignIn.this,ChangePass.class);
//                    startActivity(change);
//                }
//                else if(id==R.id.out){
//
//                }
//                else if(id==R.id.delete) {
//
//                }
//                side.closeDrawer(GravityCompat.START);
//                topappbar.setTitle(null);
//                return true;
//            }
//        });
//        topappbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!side.isDrawerOpen(GravityCompat.START)) {
//                    side.openDrawer(GravityCompat.START);
//                    topappbar.setTitle("Settings");
//
//                }
//                else{
//                    side.closeDrawer(GravityCompat.START);
//                    topappbar.setTitle(null);
//                }
//            }
//        });

    }
}
