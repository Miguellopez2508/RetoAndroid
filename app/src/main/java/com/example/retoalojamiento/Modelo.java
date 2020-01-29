package com.example.retoalojamiento;

import android.app.Application;

import java.util.ArrayList;

public class Modelo extends Application {

    ArrayList<Alojamiento> alojamientos;
    Alojamiento alojamientoMapa;

    public Modelo() {
        alojamientos = new ArrayList<Alojamiento>();
        alojamientoMapa = new Alojamiento();
    }

    public ArrayList<Alojamiento> getAlojamientos() {
        return alojamientos;
    }

    public void setAlojamientos(ArrayList<Alojamiento> alojamientos) {
        this.alojamientos = alojamientos;
    }

    public void setAlojamientoMapa(Alojamiento alojamientoMapa) {
        this.alojamientoMapa = alojamientoMapa;
    }

    public Alojamiento getAlojamientoMapa() {
        return alojamientoMapa;
    }
}
