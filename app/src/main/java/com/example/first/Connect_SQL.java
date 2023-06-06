package com.example.first;

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

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        connection=null;
        String url;
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        url="jdbc:jtds:sqlserver://"+ip_addr+":"+port+";"+"databasename="+database+
                ";user="+username+";password="+pass+";";
        connection= DriverManager.getConnection(url);
        return connection;
    }
}
