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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.first.Retrofit.Service;
import com.example.first.Retrofit.UserAPI;
import com.example.first.user.User;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    Connection connection;
    final  boolean[] b = new boolean[1];
    Service service= new Service();
    UserAPI userAPI=service.getRetrofit().create(UserAPI.class);

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



        PasswordToggle toggle= new PasswordToggle(password);
        toggle.execute();

        retypepassword.addTextChangedListener(new TextWatcher() {

            String s;
            check_retype c=new check_retype();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s = passwordl[0].getEditText().getText().toString();
                b[0] =c.check(s, charSequence.toString());
                if(b[0] ==false) {
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
        PasswordToggle toggle1= new PasswordToggle(retypepassword);
        toggle1.execute();



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
            int z = 0;



            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void run() {




                userl = findViewById(R.id.usernameinp);
                user = userl.getEditText();
                passwordl = findViewById(R.id.passwordinp);
                password = passwordl.getEditText();
                firstinpl = findViewById(R.id.firstinp);
                firstinp = firstinpl.getEditText();
                lastinpl = findViewById(R.id.lastinp);
                lastinp = lastinpl.getEditText();
                //API Code*****
                User userinfo= new User();
                userinfo.setUsername(user.getText().toString());
                userinfo.setPassword((password.getText().toString()));
                userinfo.setFirstname(firstinp.getText().toString());
                userinfo.setLastname(lastinp.getText().toString());
                userinfo.setUrl_Full(null);
                userAPI.insert_user(userinfo)
                .enqueue(new Callback<Boolean>() {
                             @Override
                             public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                 if(response.body()==true) {
                                     Thread t2= new Thread(new Runnable() {
                                         @Override
                                         public void run() {
                                             runOnUiThread(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     Toast.makeText(SignUp.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                                 }
                                             });
                                         }
                                     });
                                     t2.start();
                                     try {
                                         t2.join();
                                         Intent login=new Intent(SignUp.this, MainActivity.class);
                                         startActivity(login);

                                     } catch (InterruptedException e) {
                                         throw new RuntimeException(e);
                                     }


                                 }
                                 else {
                                     runOnUiThread(new Runnable() {
                                         @Override
                                         public void run() {
                                             userl.setError("Username already exists. Please Login.");
                                         }
                                     });
                                 }


                             }

                             @Override
                             public void onFailure(Call<Boolean> call, Throwable t) {
                                 Toast.makeText(SignUp.this, "Currently facing connection issues!", Toast.LENGTH_SHORT).show();
                                 Logger.getLogger(getClass().toString()).log(Level.SEVERE,"Error occured",t);
                             }
                         });
                        //API code ends

//                        String query1 = "select * from samplespace1 where upper(Username)=upper('" + user.getText().toString() + "');";
//                Statement s1;
//                int ind = 0;
//                try {
//                    s1 = connection.createStatement();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                ResultSet res = null;
//                try {
//                    res = s1.executeQuery(query1);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//
//                ResultSet finalRes = res;
//                CountDownLatch count=new CountDownLatch(1);
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        while (true) {
//                            try {
//                                if (!finalRes.next()) {
//                                    break;
//                                }
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                            try {
//                                z=1;
//
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//
//                        }
//                        count.countDown();
//                    }
//
//                });
//                try {
//                    count.await();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                if (z== 0 && b[0]==true) {
//                    String ind_sel = "select max(Ind) from samplespace1;";
//                    Statement s3;
//                    try {
//                        s3 = connection.createStatement();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    ResultSet res2;
//                    try {
//                        res2 = s3.executeQuery(ind_sel);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        if(res2.next()) {
//                            try {
//                                ind=res2.getInt(1);
//                                ind++;
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//
//
//                    String query2 = "INSERT INTO samplespace1 (Ind, Username, Password, firstname, lastname) VALUES" +
//                            " (" +ind+ ",'" + user.getText().toString() + "','" + password.getText().toString() + "','" +
//                            firstinp.getText().toString() + "','" + lastinp.getText().toString() + "');";
//
//                    Statement s2 = null;
//                    try {
//                        s2 = connection.createStatement();
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    try {
//                        s2.executeUpdate(query2);
//
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    Intent login=new Intent(SignUp.this, MainActivity.class);
//                    startActivity(login);
//
//                }
//                else if (z!=0){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            userl.setError("User already exists. Please Login.");
//                        }
//                    });
//                }
            }
        });
        t.start();

    }
}
