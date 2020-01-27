package com.example.retoalojamiento;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mysql.jdbc.Connection;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.MenuItem;

public class MapaDetalle extends AppCompatActivity {
    private MapView mapView;
    String latitud;
    String longitud;
    String nombre;
    String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        latitud = prefe.getString("latitud","");
        longitud = prefe.getString("longitud","");
        nombre = prefe.getString("nombreAloj","");
        tipo = prefe.getString("tipo","");

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null)
                .camera(new CameraPosition.Builder()
                        .target(new LatLng( Double.parseDouble(longitud),Double.parseDouble(latitud)))
                        .zoom(10)
                        .build());
        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                cordenadas(latitud,longitud,mapboxMap);

                mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });
            }
        });

        setContentView(mapView);

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
            Intent i = new Intent(this, Detalles.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void cordenadas(String latitud, String longitud, MapboxMap mapboxMap){

                LatLng cordenada = new LatLng( Double.parseDouble(longitud), Double.parseDouble(latitud));

                mapboxMap.addMarker(new MarkerOptions().position(cordenada).title(tipo + ": " +nombre));
    }



    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
