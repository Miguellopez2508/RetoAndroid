package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;


public class SeleccionAlojamientos extends AppCompatActivity {

    String json;
    JSONArray array = null;
    ArrayList<Alojamiento> listaAlojamientos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_alojamientos);

        /*
        File currentDir = new File("p");
        Toast notificacion = Toast.makeText(this,  currentDir.getAbsolutePath(), Toast.LENGTH_LONG);
        notificacion.show();

         */

        boolean dos = true;
        while (dos) {
            background1 bg = new background1(this);
            bg.execute("select id, nombre from alojamientos limit 10", "select");
            dos = false;
        }

        Toast notificacion1 = Toast.makeText(this, "cargado el json", Toast.LENGTH_LONG);
        notificacion1.show();

        /*
        boolean uno = true;
        while (uno) {
            new cargarJson(this).execute();
            uno = false;
        }

         */
    }

    public void cargarDatos(View view) {
        listaAlojamientos = new ArrayList<>();

        /*
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json_array = object.optJSONArray("Alojamientos");

         */

        for (int i = 0; i < array.length(); i++) {
            try {
                listaAlojamientos.add(new Alojamiento(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast notificacion = Toast.makeText(this, listaAlojamientos.get(0).getId(), Toast.LENGTH_LONG);
        notificacion.show();
    }


    public class cargarJson extends AsyncTask<Void, Void, String> {

        Context context;

        public cargarJson(Context context) {
            this.context = context;
        }

        @Override
        public String doInBackground(Void... voids) {
            String json = null;
            try {
                InputStream is = context.getAssets().open("datos.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            json = result;
        }
    }

    public class background1 extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public background1(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Login Status");
        }

        protected void onPostExecute(String s) {
            if (!"0 results".equals(s)) {
                try {

                    array = new JSONArray(s);


                    for (int i = 0; i < array.length(); i++) {
                        listaAlojamientos.add(new Alojamiento(array.getJSONObject(i)));
                    }

                } catch (JSONException e) {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show();
            }
        }

        protected String doInBackground(String... voids) {
            StringBuilder result = new StringBuilder();
            String SqlQuery = voids[0];
            String tipo = voids[1];

            String connect = "http://10.0.2.2:80/connect.php";

            if (tipo.equals("select")) {
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "=" + URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&" + URLEncoder.encode("tipo", "UTF-8") + "=" + URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();

                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while ((line = reader.readLine()) != null) {
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
            } else if (tipo.equals("insert")) {
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "=" + URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&" + URLEncoder.encode("tipo", "UTF-8") + "=" + URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while ((line = reader.readLine()) != null) {
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