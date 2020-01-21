package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class Menu extends AppCompatActivity {

    public TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        nombre = (TextView) findViewById(R.id.nombre);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);

        nombre.setText(preferences.getString("dni",""));


    }

    public void SalirMenuBtn (View view){
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);

    }

    public void MapaBtn (View view){
        Intent intent= new Intent(this, Mapa.class);
        startActivity(intent);
    }

    public void MisReservasBtn (View view){
        Intent intent = new Intent(this, MisReservas.class);
        String recuperamos_variable_dni = getIntent().getStringExtra("variable_dni");
        intent.putExtra("dni", recuperamos_variable_dni);
        this.startActivity(intent);
    }
}
