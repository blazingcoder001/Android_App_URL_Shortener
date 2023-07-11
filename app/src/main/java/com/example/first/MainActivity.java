package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.first.user.User;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Connection connection;


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
//        ViewGroup.LayoutParams from= password.getLayoutParams();
//        user.setLayoutParams(from);
//        password.setOnTouchListener(new View.OnTouchListener() {
//            @SuppressLint("ClickableViewAccessibility")
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                final int DRAWABLE_RIGHT =2;
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                    if (motionEvent.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        // Toggle password visibility
//                        if (password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
//                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
//                            }
//                        } else {
//                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
//                            }
//                        }
//                        return true;
//                    }
//                }
//                return false;
//            }
//
//
//        });
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

//                Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
//                        "129.21.136.123", "first", "3306");
//                try {
//                    connection = connectSql.Connection_get();
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
                userl=findViewById(R.id.username);
                user=userl.getEditText();
                passwordl=findViewById(R.id.password);
                password=passwordl.getEditText();
                t1=findViewById(R.id.not_user);



                String query = "select * from samplespace1 where upper(Username)=upper('"+user.getText().toString()+"') and Password ='"+password.getText().toString()+"';";
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

                ResultSet  finalRes = res;
                 runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    if (!finalRes.next()) {
                                        int k;
                                        Executor parallel= new Executor(user,password, connection);
                                        k=parallel.perform_execute();
                                        if(k==0)
                                        {
                                            userl.setError("Username does not exist.");
                                            passwordl.setError(null);
                                        }
                                        else{
                                            passwordl.setError("Password is incorrect.");
                                            userl.setError(null);
                                        }

                                        break;
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ExecutionException e) {
                                    throw new RuntimeException(e);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user.getText().toString());
                                editor.apply();
                                //                                t1.setText(finalRes.getString(2));
                                Intent signin= new Intent(MainActivity.this, SignIn.class);
                                startActivity(signin);
                                break;


                            }
                        }
                    });

            }
        });
        t.start();

    }
}