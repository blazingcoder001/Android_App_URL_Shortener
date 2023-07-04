package com.example.first;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;

public class SignIn extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        DrawerLayout side=findViewById(R.id.drawer);
        side.setScrimColor(ContextCompat.getColor(this,R.color.scrim));
        side.closeDrawer(GravityCompat.START);
        MaterialToolbar topappbar= findViewById(R.id.topAppBar);
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
