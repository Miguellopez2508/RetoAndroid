package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CargadorDeDatos extends AppCompatActivity {

    TextView tv;
    Connection con;
    String resul = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargador_de_datos);

        tv = (TextView) findViewById(R.id.textView6);

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
                ResultSet rs = st.executeQuery("select * from alojamientos");


                while (rs.next()) {
                    resul += rs.getString("nombre");
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
                tv.setText(resul);
            } else {

            }
        }
    }
}

