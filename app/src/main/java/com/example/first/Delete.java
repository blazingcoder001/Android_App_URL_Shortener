package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete extends AppCompatActivity {
    Connection connection;
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
        String userstr;
        b1=findViewById(R.id.button1);
        b2=findViewById(R.id.button2);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userstr = sharedPreferences.getString("username", null);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
                                "129.21.136.123", "first", "3306");
                        try {
                            connection = connectSql.Connection_get();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        String query2 = "DELETE FROM samplespace1 WHERE upper(Username)=upper('"+userstr+"')";

                        Statement s2 = null;
                        try {
                            s2 = connection.createStatement();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            s2.executeUpdate(query2);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                t1.start();
                Thread t2= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            t1.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Intent login=new Intent(Delete.this, MainActivity.class);
                        startActivity(login);
                    }
                });
                t2.start();
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
