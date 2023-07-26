package com.example.first;

import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserCheck {
    String query1;
    String query2;
    EditText user;
    EditText password;
    Connection connection;

    UserCheck(EditText user, EditText password, Connection connection) {
        query1 = "select * from samplespace1 where upper(Username)=upper('" + user.getText().toString() + "');";
        query2 = "select * from samplespace1 where upper(Password)=upper('" + password.getText().toString() + "');";
        this.user = user;
        this.password = password;
        this.connection = connection;
    }

    int check() throws SQLException {
        Statement s1;
        ResultSet res;
        s1 = connection.createStatement();
        res = s1.executeQuery(query1);
        if (!res.next())
            return 0;
        else
            return 1;

    }
}
