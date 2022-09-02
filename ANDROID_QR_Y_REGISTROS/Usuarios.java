package com.lics.proyectou2;

public class Usuarios {

    private String apellidoM;
    private String apellidoP;
    private String nombre;
    private String nc;
    private String estadoActual;

    public Usuarios(){}

    public Usuarios(String apellidoM, String apellidoP, String nombre, String nc, String estadoActual) {
        this.apellidoM = apellidoM;
        this.apellidoP = apellidoP;
        this.nombre = nombre;
        this.nc = nc;
        this.estadoActual = estadoActual;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }
}
