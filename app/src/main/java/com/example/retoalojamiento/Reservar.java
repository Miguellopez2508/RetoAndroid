package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class Reservar extends AppCompatActivity {

    private int dia, mes, año;
    private TextView fechaInicio;
    private TextView fechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservar);

        fechaInicio =(TextView)findViewById(R.id.textView14);
        fechaFin =(TextView)findViewById(R.id.textView15);
    }


    public void IntroducirFecha1(View v) {

        Calendar calendar = Calendar.getInstance();
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH);
        año = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                fechaInicio.setText(dia+"/"+(mes+1)+"/"+año);
            }
        }
                ,dia,mes,año);
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
                fechaFin.setText(dia+"/"+(mes+1)+"/"+año);
            }
        }
                ,dia,mes,año);
        datePickerDialog.show();
    }

}
