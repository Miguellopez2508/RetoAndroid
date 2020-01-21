package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FiltroBusqueda extends AppCompatActivity {
    Spinner spinnerAlojamientos;
    Spinner spinnerProvincias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_busqueda);

        spinnerAlojamientos = (Spinner) findViewById(R.id.spinnerAlojamientos);
        spinnerProvincias = (Spinner) findViewById(R.id.spinnerProvincias);

        ArrayAdapter<CharSequence> alojamientos = ArrayAdapter.createFromResource(this,R.array.tipo_alojamiento, android.R.layout.simple_spinner_item);
        alojamientos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlojamientos.setAdapter(alojamientos);

        String[] provinciasArray = new String[]{"","Bizkaia","Alava","Gipuzkoa"};
        ArrayAdapter<String> provincias= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, provinciasArray);
        provincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincias.setAdapter(provincias);

    }

    public void BusquedaOnClick(View view){

    }

}
