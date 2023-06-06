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
    String connection_result;
    TextView t1,t2 ;
    EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Btnclick(View v){
        t1= findViewById(R.id.user_name);
        e1=findViewById(R.id.Plain_1);
        t1.setText(e1.getText());
    }
    public void getvalue_database(View view) throws SQLException, ClassNotFoundException {
        Connect_SQL  connectSql=new Connect_SQL("root","jrpjp#321",
                "127.0.0.1","first","3306");
        connection=connectSql.getConnection();
        String query="select * from 'samplespace1' where Username='JP';";
        Statement s1= connection.createStatement();
        ResultSet res=s1.executeQuery(query);
        t1.setText(res.getString(1));
        t2.setText(res.getString(3));
    }
}