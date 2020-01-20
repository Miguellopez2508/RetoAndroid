package com.example.retoalojamiento;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;


public class SeleccionAlojamientos extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_alojamientos);

        String json = jasonToString();
        ArrayList<Alojamiento> lista_frutas = new ArrayList<>();

        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray json_array = object.optJSONArray("");

        for (int i = 0; i < json_array.length(); i++) {
            try {
                lista_frutas.add(new Alojamiento(json_array.getJSONObject(i)));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast notificacion = Toast.makeText(this, lista_frutas.get(0).getSignatura(), Toast.LENGTH_LONG);
        notificacion.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String jasonToString() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("datos.json")))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
