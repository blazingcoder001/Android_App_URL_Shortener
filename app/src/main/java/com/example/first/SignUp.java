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

public class SignUp extends AppCompatActivity {
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
}
