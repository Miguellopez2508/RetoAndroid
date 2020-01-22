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
        background1 bg = new background1(this);
        bg.execute(SqlQuery, "select");
        contraseña.setText("");
    }

    public void BotonRegistro (View view){
        Intent intent= new Intent(this, CargadorDeDatos.class);
        startActivity(intent);
    }

    public class background1 extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;
        public background1(Context context){
            this.context = context;
        }
        protected void onPreExecute(){
            dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Login Status");
        }

        protected void onPostExecute(String s){

//            if(!"0 results".equals(s)){
//                try {
//                    JSONArray array = new JSONArray(s);
//                    JSONObject json_data = array.getJSONObject(0);
//                    super.onPostExecute(s);
//
//                    Intent intent = new Intent(context, Menu.class);
//                    SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferencias.edit();
//                    editor.putString("dni", json_data.getString("dni"));
//                    editor.putString("nombre", json_data.getString("nombre"));
//                    editor.commit();
//                    context.startActivity(intent);
//
//                } catch (JSONException e) {
//
//                }
//
//            } else {
//                Toast.makeText(context, R.string.usuario_o_contraseña_incorrecta, Toast.LENGTH_LONG).show();
//            }

            Intent intent = new Intent(context, Menu.class);
            context.startActivity(intent);

        }


        protected String doInBackground(String... voids){
            StringBuilder result = new StringBuilder();
            String SqlQuery = voids[0];
            String tipo = voids[1];

            String connect = "http://10.0.2.2:80/connect.php";

            if(tipo.equals("select")){
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "="+URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&"+URLEncoder.encode("tipo", "UTF-8") + "="+URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();

                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    reader.close();
                    ips.close();
                    http.disconnect();

                    return result.toString();

                } catch (MalformedURLException e) {
                    result.append(e.getMessage() + "ERROR 1");
                } catch (IOException e) {
                    result.append(e.getMessage() + "ERROR 2");
                }

                return result.toString();
            } else if (tipo.equals("insert")){
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "="+URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&"+URLEncoder.encode("tipo", "UTF-8") + "="+URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    reader.close();
                    ips.close();
                    http.disconnect();


                } catch (MalformedURLException e) {
                    result.append(e.getMessage() + "ERROR 1");
                } catch (IOException e) {
                    result.append(e.getMessage() + "ERROR 2");
                }
                return result.toString();
            }

            return result.toString();
        }



    }

}
