package com.example.first;

import androidx.annotation.MainThread;
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

//    public void Btnclick(View v) throws SQLException, ClassNotFoundException {
//
//        getvalue_database(v);
//    }
//    public void getvalue_database(View view) throws SQLException, ClassNotFoundException {
//
//        Thread t= new Thread(new Runnable() {
//            TextView t1,t2 ;
//
//            @Override
//            public void run() {
//                t1=findViewById(R.id.user_name);
//                t2=findViewById(R.id.Pass);
//                EditText e1;
//                Connect_SQL connectSql = new Connect_SQL("JP", "jrpjp#321",
//                        "129.21.136.123", "first", "3306");
//                try {
//                    connection = connectSql.Connection_get();
//                } catch (ClassNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//
//                String query = "select * from samplespace1 where Username='JP';";
//                Statement s1 = null;
//                try {
//                    s1 = connection.createStatement();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//                ResultSet res = null;
//                try {
//                    res = s1.executeQuery(query);
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//
//                ResultSet finalRes = res;
//                runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            while (true) {
//                                try {
//                                    if (!finalRes.next()) break;
//                                } catch (SQLException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            try {
//                                t1.setText(finalRes.getString(2));
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//
//                            try {
//                                t2.setText(finalRes.getString(4));
//                            } catch (SQLException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                        }
//                    });
//
//
//
//            }
//        });
//        t.start();
//
//    }
}