package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="materia")
public class Materia {
    @PrimaryKey
    @AutoIncrement
    private int idmateria;
    private String materia;
    private String alias;
    private int idcarrera;

    public Materia() {
    }

    public Materia(int idmateria, String materia, String alias, int idcarrera) {
        this.idmateria = idmateria;
        this.materia = materia;
        this.alias = alias;
        this.idcarrera = idcarrera;
    }

    public int getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(int idmateria) {
        this.idmateria = idmateria;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(int idcarrera) {
        this.idcarrera = idcarrera;
    }
    
    
}
