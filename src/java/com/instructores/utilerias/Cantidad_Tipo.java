package com.instructores.utilerias;

public class Cantidad_Tipo {
    private int idtipo;
    private String tipo;
    private int cantidad;

    public Cantidad_Tipo() {
        this.cantidad = 0;
    }

    public Cantidad_Tipo(int idTipo, String tipo, int cantidad) {
        this.idtipo = idTipo;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public int getIdTipo() {
        return idtipo;
    }

    public void setIdTipo(int idTipo) {
        this.idtipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public void aumentarCantidad(){
        this.cantidad++;
    }
}
