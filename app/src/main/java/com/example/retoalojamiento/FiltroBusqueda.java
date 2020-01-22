package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FiltroBusqueda extends AppCompatActivity {

    Spinner spinnerAlojamientos;
    Spinner spinnerProvincias;
    EditText nombre;
    EditText municipio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_busqueda);

        spinnerAlojamientos = (Spinner) findViewById(R.id.spinnerAlojamientos);
        spinnerProvincias = (Spinner) findViewById(R.id.spinnerProvincias);

        nombre = (EditText) findViewById(R.id.nombre_aloj);
        municipio = (EditText) findViewById(R.id.municipio_aloj);


        ArrayAdapter<CharSequence> alojamientos = ArrayAdapter.createFromResource(this,R.array.tipo_alojamiento, android.R.layout.simple_spinner_item);
        alojamientos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlojamientos.setAdapter(alojamientos);

        String[] provinciasArray = new String[]{"","Bizkaia","Araba/Alava","Gipuzkoa"};
        ArrayAdapter<String> provincias= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, provinciasArray);
        provincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincias.setAdapter(provincias);

    }

    public void BusquedaOnClick(View view){


        String consulta = "SELECT id,nombre,tipo,municipio,territorio FROM alojamientos WHERE ";
        String anadimos = "";
        if (!nombre.getText().toString().equals("")){
            anadimos += " LOWER(nombre) like LOWER('%" + nombre.getText().toString() + "%')";
        }
        if (!spinnerAlojamientos.getSelectedItem().toString().equals("")){
            if (!anadimos.equals("")){
                anadimos += " AND";
            }
            anadimos += " LOWER(tipo) like LOWER('%" + spinnerAlojamientos.getSelectedItem().toString() + "%')";
        }
        if (!spinnerProvincias.getSelectedItem().toString().equals("")){
            if (!anadimos.equals("")){
                anadimos += " AND";
            }
            anadimos += "LOWER(territorio) like LOWER('%" + spinnerProvincias.getSelectedItem().toString() + "%')";
        }
        if (!municipio.getText().toString().equals("")){
            if (!anadimos.equals("")){
                anadimos += " AND";
            }
            anadimos += " LOWER(municipio) like LOWER('%" + municipio.getText().toString() + "%')";
        }
        if(anadimos.equals("")){
            consulta = "SELECT id,nombre,tipo,municipio,territorio FROM alojamientos";
        }

        anadimos += " limit 10";
        consulta = consulta + anadimos;


        SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("query", consulta);
        editor.commit();

        Intent intent= new Intent(this, VerAlojamientos.class);
        startActivity(intent);
    }


}
