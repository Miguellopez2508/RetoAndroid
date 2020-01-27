package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class SeleccionAlojamientos extends AppCompatActivity {

    ArrayList<Alojamiento> listaAlojamientos;
    Modelo mod;
    ListView rv;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_alojamientos);

        mod = (Modelo) getApplication();
        rv = (ListView) findViewById(R.id.lista);

        new background1(this).execute();
    }

    @Override
    public void onBackPressed (){
        mod.alojamientos = new ArrayList<>();

        Intent intent= new Intent(this, FiltroBusqueda.class);
        startActivity(intent);
    }

    public void cambiar(int i) {
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("id", mod.alojamientos.get(i).getId());
        editor.putString("nombreAloj", mod.alojamientos.get(i).getNombre());
        editor.putString("descripcion", mod.alojamientos.get(i).getDescripcion());
        editor.putString("email", mod.alojamientos.get(i).getEmail());
        editor.putString("telefono", mod.alojamientos.get(i).getTelefono());
        editor.putString("territorio", mod.alojamientos.get(i).getTerritorio());
        editor.putString("municipio", mod.alojamientos.get(i).getMunicipio());
        editor.putString("cp", String.valueOf(mod.alojamientos.get(i).getCodigoPostal()));
        editor.putString("pagina", mod.alojamientos.get(i).getWeb());
        editor.putString("capacidad", String.valueOf(mod.alojamientos.get(i).getCapacidad()));
        editor.putString("direccion", mod.alojamientos.get(i).getDireccion());
        editor.putString("tipo", mod.alojamientos.get(i).getTipoDeAlojamiento());
        editor.commit();
        Intent intent= new Intent(this, Detalles.class);
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
            ImageView image = (ImageView)item.findViewById(R.id.imageView);
            if(listaAlojamientos.get(position).getTipoDeAlojamiento().equals("Camping")){
                image.setImageResource(R.drawable.campamento);
            }
            if(listaAlojamientos.get(position).getTipoDeAlojamiento().equals("Albergue")){
                image.setImageResource(R.drawable.albergue);
            }
            if(listaAlojamientos.get(position).getTipoDeAlojamiento().equals("Alojamiento Rural")){
                image.setImageResource(R.drawable.casa);
            }
            textView1.setText(listaAlojamientos.get(position).getNombre());
            textView2.setText(listaAlojamientos.get(position).getDescripcion());

            return(item);
        }
    }


    public class background1 extends AsyncTask<Void, Void, Boolean> {

        Context context;
        private String url = "jdbc:mysql://10.0.2.2:3306/alojamiento";
        private String user = "root";
        private String pass = "";

        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = (Connection) DriverManager.getConnection(url, user, pass);

                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                String query = prefe.getString("query", "");

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);


                while (rs.next()) {
                    Alojamiento alojamiento = new Alojamiento();
                    alojamiento.setNombre(rs.getString("nombre"));
                    alojamiento.setId(rs.getString("id"));
                    alojamiento.setDescripcion(rs.getString("descripcion"));
                    alojamiento.setTelefono(rs.getString("telefono"));
                    alojamiento.setDireccion(rs.getString("direccion"));
                    alojamiento.setEmail(rs.getString("email"));
                    alojamiento.setWeb(rs.getString("web"));
                    alojamiento.setTipoDeAlojamiento(rs.getString("tipo"));
                    alojamiento.setCapacidad(rs.getInt("capacidad"));
                    alojamiento.setCodigoPostal(rs.getInt("codigo_postal"));
                    alojamiento.setLatitud(rs.getString("latitud"));
                    alojamiento.setLongitud(rs.getString("longitud"));
                    alojamiento.setMunicipio(rs.getString("municipio"));
                    alojamiento.setTerritorio(rs.getString("territorio"));
                    mod.alojamientos.add(alojamiento);
                }

                st.close();

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if (cargaOk) {
                exexute();
            } else {

            }
        }

    }

    public void exexute(){

        mod = (Modelo) getApplication();
        listaAlojamientos = mod.getAlojamientos();
        rv = (ListView) findViewById(R.id.lista);

        AdaptadorAlojamientos adpt = new AdaptadorAlojamientos(this);
        rv.setAdapter(adpt);

        rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cambiar(i);
            }
        });
    }
}