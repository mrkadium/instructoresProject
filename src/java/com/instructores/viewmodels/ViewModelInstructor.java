package com.instructores.viewmodels;

public class ViewModelInstructor {
    private int idinstructor;
    private String instructor;
    private String usuario;
    private int cantidad;

    public ViewModelInstructor() {
    }

    public ViewModelInstructor(int idinstructor, String instructor, String usuario, int cantidad) {
        this.idinstructor = idinstructor;
        this.instructor = instructor;
        this.usuario = usuario;
        this.cantidad = cantidad;
    }

    public int getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(int idinstructor) {
        this.idinstructor = idinstructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
