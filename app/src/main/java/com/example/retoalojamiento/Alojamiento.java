package com.example.retoalojamiento;

import org.json.JSONException;
import org.json.JSONObject;

public class Alojamiento {

    public String id;
    public String Nombre;
    public String Descripcion;
    public String Telefono;
    public String Direccion;
    public String Email;
    public String Web;
    public String TipoDeAlojamiento;
    public int Capacidad;
    public int CodigoPostal;
    public String Longitud;
    public String Latitud;
    public String Municipio;
    public String Territorio;

    public Alojamiento(String id, String nombre, String descripcion, String telefono, String direccion, String email, String web, String tipoDeAlojamiento, int capacidad, int codigoPostal, String longitud, String latitud, String municipio, String territorio) {
        this.id = id;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
        this.Telefono = telefono;
        this.Direccion = direccion;
        this.Email = email;
        this.Web = web;
        this.TipoDeAlojamiento = tipoDeAlojamiento;
        this.Capacidad = capacidad;
        this.CodigoPostal = codigoPostal;
        this.Longitud = longitud;
        this.Latitud = latitud;
        this.Municipio = municipio;
        this.Territorio = territorio;
    }

    public Alojamiento() {

    }

    public Alojamiento(JSONObject objetoJSON) throws JSONException {
        this.id = objetoJSON.getString("id");
        this.Nombre = objetoJSON.getString("nombre");
        this.Descripcion = objetoJSON.getString("Descripcion");
        this.Telefono = objetoJSON.getString("Telefono");
        this.Direccion = objetoJSON.getString("Direccion");
        this.Email = objetoJSON.getString("Email");
        this.Web = objetoJSON.getString("Web");
        this.TipoDeAlojamiento = objetoJSON.getString("TipoDeAlojamiento");
        this.Capacidad = objetoJSON.getInt("Capacidad");
        this.CodigoPostal = objetoJSON.getInt("CodigoPostal");
        this.Longitud = objetoJSON.getString("Longitud");
        this.Latitud = objetoJSON.getString("Latitud");
        this.Municipio = objetoJSON.getString("Municipio");
        this.Territorio = objetoJSON.getString("Territorio");
    }

    public String getMunicipio() {
        return Municipio;
    }

    public String getTerritorio() {
        return Territorio;
    }


    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }


    public void setTerritorio(String territorio) {
        Territorio = territorio;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getDescripcion() {
        return Descripcion;
    }
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getWeb() {
        return Web;
    }
    public void setWeb(String web) {
        Web = web;
    }
    public String getTipoDeAlojamiento() {
        return TipoDeAlojamiento;
    }
    public void setTipoDeAlojamiento(String tipoDeAlojamiento) {
        TipoDeAlojamiento = tipoDeAlojamiento;
    }
    public int getCapacidad() {
        return Capacidad;
    }
    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }
    public int getCodigoPostal() {
        return CodigoPostal;
    }
    public void setCodigoPostal(int codigoPostal) {
        CodigoPostal = codigoPostal;
    }
    public String getLongitud() {
        return Longitud;
    }
    public void setLongitud(String longitud) {
        Longitud = longitud;
    }
    public String getLatitud() {
        return Latitud;
    }
    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

}
