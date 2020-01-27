package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Menu extends AppCompatActivity {

    public TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        nombre = (TextView) findViewById(R.id.nombre);

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        nombre.setText(preferences.getString("nombre",""));

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
        this.startActivity(intent);
    }
    public void abusquedas (View view) {
        Intent intent= new Intent(this, FiltroBusqueda.class);
        startActivity(intent);
    }
}
