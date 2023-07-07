package com.example.first;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class PasswordToggle {
    EditText pass;

    PasswordToggle(EditText pass){
        this.pass=pass;

    }
    public void execute(){
        pass.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT =2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (pass.getRight() - pass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Toggle password visibility
                        if (pass.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye_off, 0);
                            }
                        } else {
                            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                pass.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }


        });
    }
}
