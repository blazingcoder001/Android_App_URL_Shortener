package com.example.first;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import kotlin.jvm.Volatile;

public class SignUp extends AppCompatActivity {

    Connection connection;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        TextView t1;
        final String[] out = {null};
        final TextInputLayout[] passwordl = {findViewById(R.id.passwordinp)};
        EditText password= passwordl[0].getEditText();
        TextInputLayout retypepasswordl=findViewById(R.id.retypeinp);
        EditText retypepassword=retypepasswordl.getEditText();
        password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT =2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (password.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            }
                        } else {
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }


        });

        retypepassword.addTextChangedListener(new TextWatcher() {
            boolean b;
            String s;
            check_retype c=new check_retype();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s = passwordl[0].getEditText().getText().toString();
                b=c.check(s, charSequence.toString());
                if(b==false) {
                    out[0] ="Password does not match!";
                    retypepasswordl.setError(out[0]);
                }
                else{

                    retypepasswordl.setError(null);
                    out[0] =null;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do nothing
            }
        });

        retypepassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT =2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (retypepassword.getRight() - retypepassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (retypepassword.getTransformationMethod() == PasswordTransformationMethod.getInstance() && out[0] ==null) {
                            retypepassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                retypepassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            }
                        } else if(out[0] ==null) {
                            retypepassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                retypepassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        t1=findViewById(R.id.user);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(SignUp.this, MainActivity.class);
                startActivity(login);
            }
        });
    }
    public void Btnclick(View v)  {

        getvalue_database(v);


    }
    public void getvalue_database(View view)  {

        Thread t= new Thread(new Runnable() {
            TextInputLayout userl,passwordl,firstinpl,lastinpl;
            EditText user,password,firstinp,lastinp;
            volatile int z=0;


            @SuppressLint("ClickableViewAccessibility")
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
                userl = findViewById(R.id.usernameinp);
                user = userl.getEditText();
                passwordl = findViewById(R.id.passwordinp);
                password = passwordl.getEditText();
                firstinpl = findViewById(R.id.firstinp);
                firstinp = firstinpl.getEditText();
                lastinpl = findViewById(R.id.lastinp);
                lastinp = lastinpl.getEditText();
                String query1 = "select * from samplespace1 where upper(Username)=upper('" + user.getText().toString() + "');";
                Statement s1;

                try {
                    s1 = connection.createStatement();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ResultSet res = null;
                try {
                    res = s1.executeQuery(query1);
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
                                    Log.d("********",String.valueOf(z));
                                    break;
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                z=1;
                                Log.d("********",String.valueOf(z));

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        Log.d("********",String.valueOf(z));

                    }
                });
                Log.d("********final",String.valueOf(z));

                if (z== 0) {
                    String query2 = "INSERT INTO samplespace1 (Username, Password, firstname, lastname) VALUES (" + "'" + user.getText().toString() + "','" + password.getText().toString() + "','" +
                            firstinp.getText().toString() + "','" + lastinp.getText().toString() + "');";
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
                    Intent login=new Intent(SignUp.this, MainActivity.class);
                    startActivity(login);

                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userl.setError("User already exists. Please Login to continue.");
                        }
                    });
                }
            }
        });
        t.start();

    }
}
