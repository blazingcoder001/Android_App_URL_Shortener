package com.example.first;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ChangePass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass);
        TextInputLayout old_pa=findViewById(R.id.password_old_inp);
        EditText old_pas_ed=old_pa.getEditText();
        TextInputLayout new_pa=findViewById(R.id.password_new_inp);
        EditText new_pa_ed=new_pa.getEditText();
        TextInputLayout new_pa_ret=findViewById(R.id.password_retype_inp);
        EditText new_pa_ret_ed=new_pa_ret.getEditText();
        new_pa_ret_ed.addTextChangedListener(new TextWatcher() {
            String s,out;
            boolean b;
            check_retype c=new check_retype();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                s = new_pa_ed.getText().toString();
                b =c.check(s, charSequence.toString());
                if(b ==false) {
                    out ="Password does not match!";
                    new_pa_ret.setError(out);
                }
                else{

                    new_pa_ret.setError(null);
                    out =null;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do nothing
            }
        });

    }
}
