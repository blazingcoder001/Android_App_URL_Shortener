package com.example.first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class ChangePass extends AppCompatActivity {
    Connection connection;

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
                        Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
                                "129.21.136.123", "first", "3306");
                        try {
                            connection = connectSql.Connection_get();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        String query = "select * from samplespace1 where upper(Username)=upper('" + userstr + "') and Password ='" + old_pas_ed.getText().toString() + "';";
                        Statement s1;
                        try {
                            s1 = connection.createStatement();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        ResultSet res = null;
                        try {
                            res = s1.executeQuery(query);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        ResultSet finalRes = res;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                while (true) {
                                    try {
                                        if (!finalRes.next()) {

                                            old_pa.setError("Password is incorrect.");
                                            break;
                                        }
                                        Thread t2= new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
                                                        "129.21.136.123", "first", "3306");

                                                String query2 = "UPDATE samplespace1 SET Password='" +new_pa_ed.getText().toString()+"' WHERE Username='"
                                                        +userstr+"';";

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
                                                    Intent change= new Intent(context,MainActivity.class);
                                                    context.startActivity(change);
                                                } catch (InterruptedException e) {
                                                    throw new RuntimeException(e);
                                                }

                                            }
                                        });
                                        t3.start();
                                        break;


                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }


                                }
                            }
                        });
                    }
                });
                t.start();

            }
        });


    }

}