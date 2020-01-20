package com.example.retoalojamiento;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    private EditText dni;
    private EditText nombre;
    private EditText apellidos;
    private EditText correo;
    private EditText telefono;
    private EditText contraseña;
    private EditText confirmarContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        dni=(EditText)findViewById(R.id.et_dni);
        nombre =(EditText)findViewById(R.id.et_nombre);
        apellidos=(EditText)findViewById(R.id.et_apellidos);
        correo=(EditText)findViewById(R.id.et_email);
        telefono=(EditText)findViewById(R.id.et_telefono);
        contraseña=(EditText)findViewById(R.id.et_contrasena);
        confirmarContraseña=(EditText)findViewById(R.id.et_confirmarContraseña);
    }


    public void comprobarDatos(View v){
        if (validarDni()==false || dni.getText().toString().equals("")) {
            Toast.makeText(this, "DNI INCORRECTO", Toast.LENGTH_SHORT).show();
        }else if (validarNombre() == false || nombre.getText().toString().equals("")) {
            Toast.makeText(this, "NOMBRE INCORRECTO", Toast.LENGTH_SHORT).show();
        }else if (validarApellidos() == false || apellidos.getText().toString().equals("")) {
            Toast.makeText(this, "APELLIDOS INCORRECTOS", Toast.LENGTH_SHORT).show();
        }else if (validarCorreo()==false || correo.getText().toString().equals("")){
            Toast.makeText(this, "CORREO INCORRECTO", Toast.LENGTH_SHORT).show();
        }else if (validarTelefono()==false || telefono.getText().toString().equals("")){
            Toast.makeText(this, "TELEFONO INCORRECTO", Toast.LENGTH_SHORT).show();
        }else if (!contraseña.getText().toString().equals(confirmarContraseña.getText().toString()) || contraseña.getText().toString().equals("")){
            Toast.makeText(this, "CONTRASEÑA NO COINCIDE", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Registro realizado correctamente", Toast.LENGTH_SHORT).show();
            String contrasenamd5 = md5(contraseña.getText().toString());
            String SqlQuery = "INSERT INTO usuario (DNI, NOMBRE, APELLIDOS, EMAIL, TELEFONO, PASSWORD, TIPO) VALUES ('" + dni.getText().toString() + "', '" + nombre.getText().toString() + "', '" + apellidos.getText().toString() + "', '" + correo.getText().toString() + "', '" + telefono.getText().toString() + "', '" + contrasenamd5 + "', 0)";
            background bg = new background(this);
            bg.execute(SqlQuery, "insert");
        }
        /*String SqlQuery = "INSERT INTO usuario (DNI, NOMBRE, APELLIDOS, EMAIL, TELEFONO, PASSWORD, TIPO) VALUES ('" + dni.getText().toString() + "', '" + nombre.getText().toString() + "', '" + apellidos.getText().toString() + "', '" + correo.getText().toString() + "', '" + telefono.getText().toString() + "', '" + contraseña.getText().toString() + "', 0)";
        background bg = new background(this);
        bg.execute(SqlQuery, "insert");*/
    }

    public boolean validarTelefono(){

        Pattern pattern = Pattern.compile("^(\\+34|0034|34)?[6789]\\d{8}$");

        String tlf = telefono.getText().toString();

        Matcher mather = pattern.matcher(tlf);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarNombre(){

        Pattern pattern = Pattern.compile("^[A-Za-zá-úÁ-ÚñÑ ]*$");

        String nom = nombre.getText().toString();

        Matcher mather = pattern.matcher(nom);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarApellidos(){

        Pattern pattern = Pattern.compile("^[A-Za-zá-úÁ-ÚñÑ ]*$");

        String ape = apellidos.getText().toString();

        Matcher mather = pattern.matcher(ape);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarCorreo(){

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        String email = correo.getText().toString();

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarDni() {

        String DNI = dni.getText().toString();
        String letraMayuscula = "";

        if(dni.length() != 9 || Character.isLetter(DNI.charAt(8)) == false ) {
            return false;
        }

        letraMayuscula = (DNI.substring(8)).toUpperCase();

        if(soloNumeros() == true && letraDNI().equals(letraMayuscula)) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean soloNumeros() {

        int i, j = 0;
        String numero = "";
        String miDNI = "";
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};
        String DNI = dni.getText().toString();

        for(i = 0; i < dni.length() - 1; i++) {
            numero = DNI.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

    private String letraDNI() {

        String DNI = dni.getText().toString();
        int miDNI = Integer.parseInt(DNI.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
