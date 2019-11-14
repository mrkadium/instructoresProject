package com.instructores.entidad;

import com.instructores.anotaciones.*;
import java.util.Date;

@Entity(table="evaluacion")
public class Evaluacion {
    @PrimaryKey
    @AutoIncrement
    private int idevaluacion;
    private int idgrupo;
    private Date fecha_realizacion;
    private String observacion;
//    private int idtest;

    public Evaluacion() {
    }

    public Evaluacion(int idevaluacion, int idgrupo, Date fecha_realizacion, String observacion) {
        this.idevaluacion = idevaluacion;
        this.idgrupo = idgrupo;
        this.fecha_realizacion = fecha_realizacion;
        this.observacion = observacion;
    }

    public int getIdevaluacion() {
        return idevaluacion;
    }

    public void setIdevaluacion(int idevaluacion) {
        this.idevaluacion = idevaluacion;
    }

    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }

    public Date getFecha_realizacion() {
        return fecha_realizacion;
    }

    public void setFecha_realizacion(Date fecha_realizacion) {
        this.fecha_realizacion = fecha_realizacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}