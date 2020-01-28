package com.example.retoalojamiento;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionClass {
    private String url = "jdbc:mysql://192.168.101.11:3306/alojamiento";
    private String user = "reto";
    private String pass = "";
    Connection conn;
    public Connection Conn(){
        try {
            conn = (Connection) DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
