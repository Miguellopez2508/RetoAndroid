package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class VerAlojamientos extends AppCompatActivity {

    private ListView listaAlojamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alojamientos);

        listaAlojamientos = (ListView)findViewById(R.id.listView_alojamientos);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String query = prefe.getString("query", "");

        System.out.println(query);

        background1 bg = new background1(this);
        bg.execute(query, "select");
    }



    public class background1 extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;
        public background1(Context context){
            this.context = context;
        }
        protected void onPreExecute(){
            dialog = new AlertDialog.Builder(context).create();
            dialog.setTitle("Login Status");
        }

        protected void onPostExecute(String s){

            if(!"0 results".equals(s)){
                try {

                    ArrayList<String> aloj = new ArrayList();

                    JSONArray array = new JSONArray(s);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject json_data = array.getJSONObject(i);
                        aloj.add("Nombre: " + json_data.getString("nombre") + " Tipo: " + json_data.getString("tipo") + " Territorio: " + json_data.getString("territorio") + " Municipio:" + json_data.getString("municipio"));
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, aloj);
                    listaAlojamientos.setAdapter(adapter);

                } catch (JSONException e) {
                    Toast.makeText(context, "Json vacio", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "No hay ningun alojamiento.", Toast.LENGTH_LONG).show();
            }

        }


        protected String doInBackground(String... voids){
            StringBuilder result = new StringBuilder();
            String SqlQuery = voids[0];
            String tipo = voids[1];

            String connect = "http://10.0.2.2:80/connect.php";

            if(tipo.equals("select")){
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "="+URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&"+URLEncoder.encode("tipo", "UTF-8") + "="+URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();

                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    reader.close();
                    ips.close();
                    http.disconnect();

                    return result.toString();

                } catch (MalformedURLException e) {
                    result.append(e.getMessage() + "ERROR 1");
                } catch (IOException e) {
                    result.append(e.getMessage() + "ERROR 2");
                }

                return result.toString();
            } else if (tipo.equals("insert")){
                try {
                    URL url = new URL(connect);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);

                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("SqlQuery", "UTF-8") + "="+URLEncoder.encode(SqlQuery, "UTF-8")
                            + "&&"+URLEncoder.encode("tipo", "UTF-8") + "="+URLEncoder.encode(tipo, "UTF-8");

                    writer.write(data);
                    writer.flush();
                    writer.close();
                    ops.close();
                    InputStream ips = http.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                    String line = "";

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    reader.close();
                    ips.close();
                    http.disconnect();


                } catch (MalformedURLException e) {
                    result.append(e.getMessage() + "ERROR 1");
                } catch (IOException e) {
                    result.append(e.getMessage() + "ERROR 2");
                }
                return result.toString();
            }

            return result.toString();
        }



    }

}
