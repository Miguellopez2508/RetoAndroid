package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText gmail;
    private EditText valortemporal;
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

        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);

        //abrirConexion();
    }
    public void abrirConexion(){
        Conexiones con = new Conexiones();

        if(con.conectarMySQL()==true){

            Toast.makeText(MainActivity.this, "entra mazo de bien aaaaah", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this, "no entra", Toast.LENGTH_LONG).show();
        }
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        String contrasena = md5(contraseña.getText().toString());
        String email = gmail.getText().toString();
        Toast.makeText(MainActivity.this, contrasena, Toast.LENGTH_LONG).show();
        background bg = new background(this);
        bg.execute(email,contrasena);

    }
}
