package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends AppCompatActivity implements View.OnClickListener {

    public EditText gmail;
    private EditText contraseña;
    Connection con;

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
        new background1(this).execute();
        contraseña.setText("");
    }

    public void BotonRegistro (View view){
        Intent intent= new Intent(this, CargadorDeDatos.class);
        startActivity(intent);
    }

    public class background1 extends AsyncTask<Void, Void, Boolean> {

        String contrasena = md5(contraseña.getText().toString());
        String email = gmail.getText().toString();

        Context context;
        private String url = "jdbc:mysql://10.0.2.2:3306/alojamiento";
        private String user = "root";
        private String pass = "";


        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = (Connection) DriverManager.getConnection(url, user, pass);


                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT dni, nombre FROM usuario WHERE email='" + email +"' AND password='" + contrasena + "'");

                if (rs.next()){
                    st.close();
                    return true;
                } else {
                    st.close();
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if(cargaOk == true){

                Toast.makeText(context, "TODO BIEN", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context, Menu.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, "ERROR EN CORREO O CONTRASEÑA IDIOTA", Toast.LENGTH_LONG).show();
            }
        }
    }

}
