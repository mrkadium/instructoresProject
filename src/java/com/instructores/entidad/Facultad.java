package com.instructores.entidad;
import com.instructores.anotaciones.*;

@Entity(table="facultad")
public class Facultad {
    @PrimaryKey
    @AutoIncrement
    private int idfacultad;
    private String facultad;
    private String alias;
    private int iddecano;

    public Facultad() {
    }

    public Facultad(int idfacultad, String facultad, String alias, int iddecano) {
        this.idfacultad = idfacultad;
        this.facultad = facultad;
        this.alias = alias;
        this.iddecano = iddecano;
    }
    

    public int getIdfacultad() {
        return idfacultad;
    }

    public void setIdfacultad(int idfacultad) {
        this.idfacultad = idfacultad;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public int getIddecano() {
        return iddecano;
    }

    public void setIddecano(int iddecano) {
        this.iddecano = iddecano;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
