package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reservar extends AppCompatActivity {

    private int dia, mes, año;
    private TextView fechaInicio;
    private TextView fechaFin;
    Connection con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservar);

        fechaInicio =(TextView)findViewById(R.id.textView14);
        fechaFin =(TextView)findViewById(R.id.textView15);
    }

    public void reservar (View v){

        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

        try {
            if(fechaInicio.getText().toString().equals("") || fechaFin.getText().toString().equals("")){
                Toast.makeText(this, R.string.fechas_vacias, Toast.LENGTH_LONG).show();
            }else {
                Date dateInicio = formato.parse(fechaInicio.getText().toString());
                Date dateFin = formato.parse(fechaFin.getText().toString());
                if (dateInicio.compareTo(dateFin) < 0 || dateInicio.compareTo(dateFin) == 0) {
                    new background1(this).execute();
                } else {
                    Toast.makeText(this, R.string.error_fechas, Toast.LENGTH_LONG).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void IntroducirFecha1(View v) {

        Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        año = calendar.get(Calendar.YEAR);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                fechaInicio.setText(año+"/"+(mes+1)+"/"+dia);
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("fecha_inicio", fechaInicio.getText().toString());
                editor.commit();
            }
        }
                ,dia,mes,año);
        datePickerDialog.updateDate(2020,1,1);
        datePickerDialog.show();
    }

    public void IntroducirFecha2(View v) {

        Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        año = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                fechaFin.setText(año+"/"+(mes+1)+"/"+dia);
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("fecha_fin", fechaFin.getText().toString());
                editor.commit();
            }
        }
                ,dia,mes,año);
        datePickerDialog.updateDate(2020,1,1);
        datePickerDialog.show();
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

                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                String dni = prefe.getString("dni", "");
                String id = prefe.getString("id", "");
                String fechaIni = prefe.getString("fecha_inicio", "");
                String fechaFini = prefe.getString("fecha_fin", "");

                String query = "insert into RESERVAS (DNI, FECHA_INICIO, FECHA_FIN, ID_ALOJAMIENTO)" + " values (?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString (1, dni);
                preparedStmt.setString (2, fechaIni);
                preparedStmt.setString   (3, fechaFini);
                preparedStmt.setString(4, id);
                preparedStmt.execute();

                con.close();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getSQLState());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean cargaOk) {
            if (cargaOk) {
          //      Toast.makeText(context, R.string.Reserva_realizada, Toast.LENGTH_LONG).show();
                Intent intent= new Intent(context, MisReservas.class);
                startActivity(intent);
            } else {

            }
        }

    }

}
