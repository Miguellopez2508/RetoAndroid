package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Login extends AppCompatActivity implements View.OnClickListener {

    public EditText gmail;
    private EditText contraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        gmail = (EditText) findViewById(R.id.et_correo);
        contraseña = (EditText) findViewById(R.id.et_contrasena);

        Button button = findViewById(R.id.btn_iniciarSesion);
        button.setOnClickListener(this);

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

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
        String SqlQuery = "SELECT dni, nombre FROM usuario WHERE email='" + email +"' AND password='" + contrasena + "'";
        background bg = new background(this);
        bg.execute(SqlQuery, "select");
        contraseña.setText("");
    }

    public void BotonRegistro (View view){
        Intent intent= new Intent(this, Registro.class);
        startActivity(intent);
    }


}
