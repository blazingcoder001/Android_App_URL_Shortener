package com.example.first;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_SQL {
    private final String username,pass,ip_addr,database,port;
    Connection connection;
    Connect_SQL(String username, String pass,String ip_addr,String database,
                String port) {
     this.username=username;
     this.pass=pass;
     this.ip_addr=ip_addr;
     this.database=database;
     this.port=port;
    }

    public Connection Connection_get() throws ClassNotFoundException, SQLException {
        connection=null;
        String url;
        Class.forName("com.mysql.jdbc.Driver");

        url="jdbc:mysql://"+ip_addr+":"+port+"/"+database;
        connection= DriverManager.getConnection(url,username,pass);
        return connection;
    }
}
