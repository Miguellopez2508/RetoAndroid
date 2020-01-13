package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private EditText gmail;
    private EditText contraseña;
    private Statement st;
    private Connection con;
    private ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        gmail = (EditText) findViewById(R.id.editText);
        contraseña = (EditText) findViewById(R.id.editText2);

        abrirConexion();
    }

    public void abrirConexion(){
        Conexiones con = new Conexiones();

        if(con.conectarMySQL()==true){

            Toast.makeText(MainActivity.this, "entra mazo de bien aaaaah", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "no entra", Toast.LENGTH_LONG).show();
        }
    }














    public void conectar()
    {
        String pass = "";


        try{

            String driver = "com.mysql.jdbc.Driver";
            String urlMySQL = "jdbc:mysql://localhost:3306/";
            Class.forName(driver).newInstance();

            con = DriverManager.getConnection(urlMySQL + "alojamiento","root","");
            st = con.createStatement();
            rs = st.executeQuery("Select password from usuario where email = admin@gmail.com ");

            while(rs.next())
            {
                pass = rs.getString("password");
            }

            Toast.makeText(MainActivity.this, pass , Toast.LENGTH_LONG).show();

        }catch(Exception ex)
        {
            Toast.makeText(MainActivity.this, "Error al obtener resultados de la BBDD: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally
        {
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
