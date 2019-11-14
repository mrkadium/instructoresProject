package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="literal_evaluacion")
public class Literal_evaluacion {
    @PrimaryKey
    @AutoIncrement
    private int id;
    private int idevaluacion;
    private int idliteral;
    private int idvaloracion;
    private int calificacion;

    public Literal_evaluacion() {
    }

    public Literal_evaluacion(int id, int idevaluacion, int idliteral, int idvaloracion) {
        this.id = id;
        this.idevaluacion = idevaluacion;
        this.idliteral = idliteral;
        this.idvaloracion = idvaloracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdevaluacion() {
        return idevaluacion;
    }

    public void setIdevaluacion(int idevaluacion) {
        this.idevaluacion = idevaluacion;
    }

    public int getIdliteral() {
        return idliteral;
    }

    public void setIdliteral(int idliteral) {
        this.idliteral = idliteral;
    }

    public int getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(int idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
    
    
}
