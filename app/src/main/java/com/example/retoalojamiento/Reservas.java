package com.example.retoalojamiento;

public class Reservas {

    public String aloj;
    public String fechaInicio;
    public String fechaFin;


    public Reservas(String aloj, String fechaInicio, String fechaFin){

        this.aloj=aloj;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;

    }

    public Reservas(){

    }

    public String getAloj() {
        return this.aloj;
    }

    public void setAloj(String aloj) {
        this.aloj = aloj;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

}
