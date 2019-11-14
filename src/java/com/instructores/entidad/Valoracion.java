package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="valoracion")
public class Valoracion {
    @PrimaryKey
    @AutoIncrement
    private int idvaloracion;
    private String valoracion;
    private int idtipo;

    public Valoracion() {
    }

    public Valoracion(int idvaloracion, String valoracion, int idtipo) {
        this.idvaloracion = idvaloracion;
        this.valoracion = valoracion;
        this.idtipo = idtipo;
    }

    public int getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(int idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }
    
}
