package com.example.first;

public class check_retype {

    boolean check(String pass, String retype){
        if(pass.equals(retype)) {
            return true;
        }
        else{
            return false;
        }
    }
}
