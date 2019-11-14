package com.instructores.entidad;
import com.instructores.anotaciones.*;

@Entity(table="carrera")
public class Carrera {
    @PrimaryKey
    @AutoIncrement
    private int idcarrera;
    private String carrera;
    private int idfacultad;
    private String codigo;

    public Carrera() {
    }

    public Carrera(int idcarrera, String carrera, int idfacultad, String codigo) {
        this.idcarrera = idcarrera;
        this.carrera = carrera;
        this.idfacultad = idfacultad;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(int idcarrera) {
        this.idcarrera = idcarrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getIdfacultad() {
        return idfacultad;
    }

    public void setIdfacultad(int idfacultad) {
        this.idfacultad = idfacultad;
    }
    
}
