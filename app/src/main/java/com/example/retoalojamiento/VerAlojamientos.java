package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VerAlojamientos extends AppCompatActivity {
    com.mysql.jdbc.Connection con;
    private ListView la;
    private String query;
    private ArrayAdapter<String> adapter;
    Modelo mod;
    ArrayList<Alojamiento> listaAlojamientos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamientos);

        la = findViewById(R.id.listView_alojamientos);
        mod = (Modelo) getApplication();

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        query = prefe.getString("query", "");

        new background1(this).execute();
    }



    public class background1 extends AsyncTask<Void, Void, Boolean> {

        Context context;
        private String url = "jdbc:mysql://10.0.2.2:3306/alojamiento";
        private String user = "root";
        private String pass = "";
        AdaptadorAlojamientos adpt;


        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = (Connection) DriverManager.getConnection(url, user, pass);
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
                adpt = new AdaptadorAlojamientos(context);

                return true;
            } catch (SQLException e) {
                System.out.println(e.getSQLState());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if (cargaOk == true) {
                la.setAdapter(adpt);
                la.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String id = Integer.toString(view.getId());
                        Toast.makeText(context,id, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(context, "DATOS INCORRECTOS", Toast.LENGTH_LONG).show();
            }
        }

        class AdaptadorAlojamientos extends ArrayAdapter<Alojamiento> {
            AppCompatActivity appCompatActivity;

            AdaptadorAlojamientos(Context context) {
                super(context, R.layout.alojamiento, listaAlojamientos);
                this.appCompatActivity = (AppCompatActivity) context;
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



}
