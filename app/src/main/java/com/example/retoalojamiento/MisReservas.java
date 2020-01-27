package com.example.retoalojamiento;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MisReservas extends AppCompatActivity {

    private ListView milista;
    ArrayAdapter<String> adapter;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_reservas);

        milista = (ListView)findViewById(R.id.listView);

        new background1(this).execute();


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

            Intent i = new Intent(this, Menu.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    public class background1 extends AsyncTask<Void, Void, Boolean> {

        Context context;
        Statement st;
        private String url = "jdbc:mysql://10.0.2.2:3306/alojamiento";
        private String user = "root";
        private String pass = "";
        ArrayList<String> reservas;

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String dni = prefe.getString("dni", "");

        public background1(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                con = (Connection) DriverManager.getConnection(url, user, pass);

                st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT NOMBRE, FECHA_INICIO, FECHA_FIN FROM reservas, alojamientos where reservas.ID_ALOJAMIENTO = alojamientos.ID and DNI = '" + dni + "'");

                reservas = new ArrayList();

                while (rs.next()) {

                    reservas.add("Nombre:" + rs.getString("NOMBRE") + " Fecha_inicio: " + rs.getString("FECHA_INICIO") + " Fecha_fin: " + rs.getString("FECHA_FIN"));
                }
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, reservas);


            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if (cargaOk) {
                milista.setAdapter(adapter);
            } else {

            }
        }
    }

}
