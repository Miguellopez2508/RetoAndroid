package com.example.retoalojamiento;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MisReservas extends AppCompatActivity {

    private ListView milista;

    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_reservas);

        milista = (ListView)findViewById(R.id.listView);

        new background1(this).execute();

    }

    public void VolverBtn (View view){
        Intent intent= new Intent(this, Menu.class);
        startActivity(intent);
    }


    public class background1 extends AsyncTask<Void, Void, Boolean> {

        Context context;
        String resul = "";
        Statement st;
        private String url = "jdbc:mysql://10.0.2.2:3306/alojamiento";
        private String user = "root";
        private String pass = "";

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dni = prefe.getString("dni", "");


        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = (Connection) DriverManager.getConnection(url, user, pass);

                st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT NOMBRE, FECHA_INICIO, FECHA_FIN FROM reservas, alojamientos where reservas.ID_ALOJAMIENTO = alojamientos.ID and DNI = '" + dni + "'");


                while (rs.next()) {
                    resul += rs.getString("nombre");
                    resul += rs.getString("fecha_inicio");
                    resul += rs.getString("fecha_fin");
                }
                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }finally {

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if (cargaOk) {

            } else {

            }
        }
    }

}
