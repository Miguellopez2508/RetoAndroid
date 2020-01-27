package com.example.retoalojamiento;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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


public class Mapa extends AppCompatActivity {

    private MapView mapView;
    Connection con;
    ArrayList<String> latitud = new ArrayList<>();
    ArrayList<String> longitud = new ArrayList<>();
    ArrayList<String> nombre = new ArrayList<>();
    ArrayList<String> tipo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        new background1(this).execute();

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null)
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(43.284599, -2.964681))
                        .zoom(15)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_volver) {
            Intent i = new Intent(this, com.example.retoalojamiento.Menu.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
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

    public class background1 extends AsyncTask<Void, Void, Boolean> {

        Context context;


        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = new ConnectionClass().Conn();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select nombre, tipo, latitud, longitud from alojamientos");

                while (rs.next()) {
                    nombre.add(rs.getString("nombre"));
                    tipo.add(rs.getString("tipo"));
                    latitud.add(rs.getString("latitud"));
                    longitud.add(rs.getString("longitud"));
                }


            } catch (SQLException e) {
                e.printStackTrace();
                return false;
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

    public void cordenadas(ArrayList<String> latitud, ArrayList<String> longitud, MapboxMap mapboxMap){

        for (int i=0; i<latitud.size(); i++){

            String latitudd = latitud.get(i);
            String longitudd = longitud.get(i);

            if(latitudd.equals("") || longitudd.equals("")) {

            } else {

                Double a = Double.parseDouble(latitudd);
                Double b = Double.parseDouble(longitudd);

                LatLng cordenada = new LatLng(b, a);

                mapboxMap.addMarker(new MarkerOptions().position(cordenada).title(tipo.get(i) + ": " +nombre.get(i)));
            }

        }

    }


}
