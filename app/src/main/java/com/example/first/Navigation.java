package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Navigation extends AppCompatActivity {

    DrawerLayout side;
    Context context;

    NavigationView navigationView;
    MaterialToolbar topappbar;

    Navigation(Context context, DrawerLayout side, NavigationView navigationView, MaterialToolbar topappbar ) {

        this.side=side;
        this.navigationView=navigationView;
        this.topappbar=topappbar;
        this.context= context;
    }
    public void navexecute() {

        side.setScrimColor(ContextCompat.getColor(context,R.color.background));
        side.closeDrawer(GravityCompat.START);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();
                Intent intent;
                if (id==R.id.home){
                    intent=new Intent(context, SignIn.class);
                    context.startActivity(intent);
                }
                if(id==R.id.Change){
                    intent= new Intent(context,ChangePass.class);
                    context.startActivity(intent);
                }
                else if(id==R.id.out){
                    intent= new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                }
                else if(id==R.id.delete) {
                    intent= new Intent(context,Delete.class);
                    context.startActivity(intent);
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


