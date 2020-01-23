package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SeleccionAlojamientos extends AppCompatActivity {

    ArrayList<Alojamiento> listaAlojamientos;
    Modelo mod;
    ListView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_alojamientos);

        mod = (Modelo) getApplication();
        listaAlojamientos = mod.getAlojamientos();
        rv = (ListView) findViewById(R.id.lista);

        AdaptadorAlojamientos adpt = new AdaptadorAlojamientos(this);
        rv.setAdapter(adpt);

        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // cambia al login porque no hay ficha de alojamiento aun
                cambiar(i);

            }
        });
    }

    public void cambiar(int i) {
        Toast.makeText(this, mod.alojamientos.get(i).getId(), Toast.LENGTH_LONG).show();
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }

    class AdaptadorAlojamientos extends ArrayAdapter<Alojamiento> {
        AppCompatActivity appCompatActivity;

        AdaptadorAlojamientos(AppCompatActivity context) {
            super(context, R.layout.alojamiento, listaAlojamientos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.alojamiento, null);
            TextView textView1 = (TextView)item.findViewById(R.id.textView2);
            TextView textView2 = (TextView)item.findViewById(R.id.textView5);
            textView1.setText(listaAlojamientos.get(position).getNombre());
            textView2.setText(listaAlojamientos.get(position).getDescripcion());

            return(item);
        }
    }
}