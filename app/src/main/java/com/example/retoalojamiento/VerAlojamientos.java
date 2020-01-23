package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VerAlojamientos extends AppCompatActivity {
    com.mysql.jdbc.Connection con;
    private ListView listaAlojamientos;
    private String query;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamientos);

        listaAlojamientos = findViewById(R.id.listView_alojamientos);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        query = prefe.getString("query", "");

        System.out.println(query);
        new background1(this).execute();

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
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        ArrayList<String> aloj = new ArrayList();
                        while (rs.next()) {
                            aloj.add("Nombre: " + rs.getString("NOMBRE") + " Tipo: " + rs.getString("TIPO") + " Territorio: " + rs.getString("TERRITORIO") + " Municipio:" + rs.getString("MUNICIPIO") + "ID" + rs.getString("ID"));
                        }
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, aloj);
                        return true;
                    } catch (SQLException e) {
                        System.out.println(e.getSQLState());
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean cargaOk) {
                    if (cargaOk == true) {
                        listaAlojamientos.setAdapter(adapter);
                        listaAlojamientos.setOnItemClickListener(new OnItemClickListener() {
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
            }

        }
