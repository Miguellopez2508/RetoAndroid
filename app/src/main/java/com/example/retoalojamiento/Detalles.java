package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {

    TextView nombre1;
    TextView descripcion;
    TextView email;
    TextView telefono;
    TextView territorio;
    TextView municipio;
    TextView cp;
    TextView pagina;
    TextView capacidad;
    TextView direccion;
    TextView tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles);

        nombre1 = (TextView) findViewById(R.id.detalles_nombre);
        descripcion = (TextView) findViewById(R.id.detalles_descripcion);
        email = (TextView) findViewById(R.id.detalles_email);
        telefono = (TextView) findViewById(R.id.detalles_telefono);
        territorio = (TextView) findViewById(R.id.detalles_territorio);
        municipio = (TextView) findViewById(R.id.detalles_municipio);
        cp = (TextView) findViewById(R.id.detalles_cp);
        pagina = (TextView) findViewById(R.id.detalles_pagina);
        capacidad = (TextView) findViewById(R.id.detalles_capacidad);
        direccion = (TextView) findViewById(R.id.detalles_direccion);
        tipo = (TextView) findViewById(R.id.detalles_tipo);


        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);

        String id = prefe.getString("id", "");
         nombre1.setText(prefe.getString("nombreAloj", ""));
         descripcion.setText(prefe.getString("descripcion", ""));
         email.setText(prefe.getString("email", ""));
         telefono.setText(prefe.getString("telefono", ""));
         territorio.setText(prefe.getString("territorio", ""));
         municipio.setText(prefe.getString("municipio", ""));
         cp.setText(prefe.getString("cp", ""));
         pagina.setText(prefe.getString("pagina", ""));
         capacidad.setText(prefe.getString("capacidad", ""));
         direccion.setText(prefe.getString("direccion", ""));
         tipo.setText(prefe.getString("tipo", ""));

        descripcion.setMovementMethod(new ScrollingMovementMethod());
    }

    public void reservar(View v){
        Intent intent= new Intent(this, Reservar.class);
        startActivity(intent);
    }
    public void vermapa(View v){
        Intent intent= new Intent(this, MapaDetalle.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_volver) {
            Intent i = new Intent(this, SeleccionAlojamientos.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
