package com.example.retoalojamiento;

import android.app.Application;

import java.util.ArrayList;

public class Modelo extends Application {

    ArrayList<Alojamiento> alojamientos;

    public Modelo() {
        alojamientos = new ArrayList<Alojamiento>();
    }

}
