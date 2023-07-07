package com.example.first;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.NavigableMap;

public class SignIn extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        DrawerLayout side=findViewById(R.id.drawer);
        side.setScrimColor(ContextCompat.getColor(this,R.color.background));
        side.closeDrawer(GravityCompat.START);
        NavigationView navigationView= findViewById(R.id.nav);
        //navigationView.inflateMenu(R.menu.navigation_drawer);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                if(id==R.id.Change){
                    Intent change= new Intent(SignIn.this,ChangePass.class);
                    startActivity(change);
                }
                else if(id==R.id.out){

                }
                else if(id==R.id.delete) {

                }
                side.closeDrawer(GravityCompat.START);
                topappbar.setTitle(null);
                return true;
            }
        });
        topappbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!side.isDrawerOpen(GravityCompat.START)) {
                    side.openDrawer(GravityCompat.START);
                    topappbar.setTitle("Settings");

                }
                else{
                    side.closeDrawer(GravityCompat.START);
                    topappbar.setTitle(null);
                }
            }
        });

    }
}
