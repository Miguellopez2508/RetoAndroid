package com.example.retoalojamiento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

public class background extends AsyncTask<String, Void, String> {

    AlertDialog dialog;
    Context context;
    public background(Context context){
        this.context = context;
    }
    protected void onPreExecute(){
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }

    protected void onPostExecute(String s){

        //Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        if(!"0 results".equals(s)){
            try {
                JSONArray array = new JSONArray(s);
                JSONObject json_data = array.getJSONObject(0);

                Toast.makeText(context, json_data.getString("dni"), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Toast.makeText(context, "LA CONCHA DE SU MADRE ", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
            context.startActivity(new Intent(context, Menu.class));
        } else {
            Toast.makeText(context, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show();
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
