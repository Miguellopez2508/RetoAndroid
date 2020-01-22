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
import android.widget.EditText;
import android.widget.Spinner;
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



        String consulta = "SELECT nombre,tipo,municipio,territorio FROM alojamientos WHERE ";
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
            anadimos += " territorio ='" + spinnerProvincias.getSelectedItem().toString() + "'";
        }
        if (!municipio.getText().toString().equals("")){
            if (!anadimos.equals("")){
                anadimos += " AND";
            }
            anadimos += " LOWER(municipio) like LOWER('%" + municipio.getText().toString() + "%')";
        }

        anadimos += " limit 2";
        consulta = consulta + anadimos;

//        If Not String.IsNullOrEmpty(Me.alojamientoDDL.SelectedValue.ToString) Then
//        If Not String.IsNullOrEmpty(anadimos) Then
//        anadimos += " AND "
//        End If
//        anadimos += " LOWER(tipo) like LOWER('%" & Me.alojamientoDDL.SelectedValue.ToString & "%')"
//        End If
//        If Not String.IsNullOrEmpty(Me.provinciaDDL.SelectedValue.ToString) Then
//        If Not String.IsNullOrEmpty(anadimos) Then
//        anadimos += " AND "
//        End If
//        anadimos += " LOWER(territorio) like LOWER('%" & Me.provinciaDDL.SelectedValue.ToString & "%')"
//        End If
//        If Not String.IsNullOrEmpty(Me.MunicipioTB.Text) Then
//        If Not String.IsNullOrEmpty(anadimos) Then
//        anadimos += " AND "
//        End If
//        anadimos += " LOWER(municipio) like LOWER('%" & Me.MunicipioTB.Text & "%')"
//        End If
//
//        If anadimos = "" Then
//        llenar()
//        Else
//        consulta += anadimos
//        Dim comando As New MySqlCommand(consulta)
//        comando.Connection = connection
//        Dim resultado As MySqlDataReader
//        resultado = comando.ExecuteReader
//
//        If resultado.HasRows Then
//        Me.ResultadoTabla.Text += " <div class=" & "tbl-header" & "><table><thead><tr><th>NOMBRE</th><th>TIPO</th><th>TERRITORIO</th><th>MUNICIPIO</th><th>DETALLES</th></tr></thead></div></table> <div class= " & "tbl-content" & "><table cellpadding=" & "0 " & " cellspacing=" & "0 " & " border=" & "0 " & "><tbody>"
//        While resultado.Read()
//        'Me.ResultadoTabla.Text += "" & resultado(1) & ", " & resultado(4) & ", " & resultado(12) & " , " & resultado(12) & " " & btn


        String SqlQuery = "SELECT nombre, tipo, territorio, municipio FROM alojamientos where LOWER(territorio) like LOWER('%" + spinnerProvincias.getSelectedItem().toString() + "%') limit 10";

        SharedPreferences preferencias = getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("query", consulta);
        editor.commit();

        Intent intent= new Intent(this, VerAlojamientos.class);
        startActivity(intent);
    }


}
