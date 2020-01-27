package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.mysql.jdbc.Connection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends AppCompatActivity  implements View.OnClickListener{

    public EditText gmail;
    private EditText contraseña;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        gmail = (EditText) findViewById(R.id.et_correo);
        contraseña = (EditText) findViewById(R.id.et_contrasena);

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
        Intent intent= new Intent(this, Registro.class);
        startActivity(intent);
    }

    public class background1 extends AsyncTask<Void, Void, Boolean> {

        String contrasena = md5(contraseña.getText().toString());
        String email = gmail.getText().toString();

        Context context;


        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = new ConnectionClass().Conn();


                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT dni, nombre FROM usuario WHERE email='" + email +"' AND password='" + contrasena + "'");

                if (rs.next()){
                    SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putString("dni", rs.getString("dni"));
                    editor.putString("nombre", rs.getString("nombre"));
                    editor.commit();
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
                Intent intent= new Intent(context, Menu.class);
                startActivity(intent);
            } else {
                Toast.makeText(context, R.string.usuario_o_contraseña_incorrecta, Toast.LENGTH_LONG).show();

            }
        }
    }

}
