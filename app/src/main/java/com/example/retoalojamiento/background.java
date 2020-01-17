package com.example.retoalojamiento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class background extends AsyncTask<String, Void,String> {

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
        //dialog.setMessage(s);
        //dialog.show();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        if(!"0 results".equals(s)){
            super.onPostExecute(s);
            context.startActivity(new Intent(context, Menu.class));
        } else {
            Toast.makeText(context, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_LONG).show();
        }

    }

    protected String doInBackground(String... voids){
        String result = "";
        String user = voids[0];
        String pass = voids[1];


        String connect = "http://10.0.2.2:80/connect.php";

        try {
            URL url = new URL(connect);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8")
                    + "&&"+URLEncoder.encode("pass", "UTF-8") + "="+URLEncoder.encode(pass, "UTF-8");

            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
            String line = "";

            while((line = reader.readLine()) != null){
                result += line;
            }

            reader.close();
            ips.close();
            http.disconnect();

            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage() + "ERROR 1";
        } catch (IOException e) {
            result = e.getMessage() + "ERROR 2";
        }

        return result;
    }

}
