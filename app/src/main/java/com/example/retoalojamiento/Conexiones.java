package com.example.retoalojamiento;

import android.content.Context;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiones {


    public boolean conectarMySQL()
    {

        boolean estadoConexion = false;

        Connection conexionMySQL = null;

        String driver = "com.mysql.jdbc.Driver";

        String urlMySQL = "jdbc:mysql://localhost:3306/";

        try {
            Class.forName(driver).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conexionMySQL = DriverManager.getConnection(urlMySQL + "alojamiento", "root", "");

            if(!conexionMySQL.isClosed())
            {
                estadoConexion = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return  estadoConexion;
    }

}
