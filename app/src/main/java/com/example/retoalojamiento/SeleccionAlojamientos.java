package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class SeleccionAlojamientos extends AppCompatActivity {

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_alojamientos);

        /*
        File currentDir = new File("p");
        Toast notificacion = Toast.makeText(this,  currentDir.getAbsolutePath(), Toast.LENGTH_LONG);
        notificacion.show();

         */

        boolean uno = true;
        while (uno) {
            new cargarJson(this).execute();
            uno = false;
        }

        ArrayList<Alojamiento> listaAlojamientos = new ArrayList<>();

        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json_array = object.optJSONArray("");

        for (int i = 0; i < json_array.length(); i++) {
            try {
                listaAlojamientos.add(new Alojamiento(json_array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast notificacion = Toast.makeText(this, listaAlojamientos.get(0).getSignatura(), Toast.LENGTH_LONG);
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
}