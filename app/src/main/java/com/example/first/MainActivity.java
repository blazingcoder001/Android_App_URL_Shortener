package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Btnclick(View v) throws SQLException, ClassNotFoundException {
//        t1= findViewById(R.id.user_name);
//        e1=findViewById(R.id.Plain_1);
//        t1.setText(e1.getText());
        getvalue_database(v);
    }
    public void getvalue_database(View view) throws SQLException, ClassNotFoundException {
//        Connect_SQL  connectSql=new Connect_SQL("root","jrpjp#321",
//                "129.21.136.123","first","3306");
//        Connect_SQL  connectSql=new Connect_SQL("root","jrpjp#321",
//                "127.0.0.1","first","3306");
        Thread t= new Thread(new Runnable() {
            TextView t1,t2 ;

            @Override
            public void run() {
                t1=findViewById(R.id.user_name);
                t2=findViewById(R.id.Pass);
                EditText e1;
                Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
                        "129.21.136.123", "first", "3306");
                try {
                    connection = connectSql.Connection_get();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                String query = "select * from samplespace1 where Username='JP';";
                Statement s1 = null;
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
                while (true) {
                    try {
                        if (!res.next()) break;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        t1.setText(res.getString(2));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        t2.setText(res.getString(4));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
        t.start();

    }
}