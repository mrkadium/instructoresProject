package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="pregunta_abierta")
public class Pregunta_abierta {
    @PrimaryKey
    @AutoIncrement
    private int idpregunta_abierta;
    private String pregunta_abierta;
    private int idevaluacion;

    public Pregunta_abierta() {
    }

    public Pregunta_abierta(int idpregunta_abierta, String pregunta_abierta, int idevaluacion) {
        this.idpregunta_abierta = idpregunta_abierta;
        this.pregunta_abierta = pregunta_abierta;
        this.idevaluacion = idevaluacion;
    }

    public int getIdpregunta_abierta() {
        return idpregunta_abierta;
    }

    public void setIdpregunta_abierta(int idpregunta_abierta) {
        this.idpregunta_abierta = idpregunta_abierta;
    }

    public String getPregunta_abierta() {
        return pregunta_abierta;
    }

    public void setPregunta_abierta(String pregunta_abierta) {
        this.pregunta_abierta = pregunta_abierta;
    }

    public int getIdevaluacion() {
        return idevaluacion;
    }

    public void setIdevaluacion(int idevaluacion) {
        this.idevaluacion = idevaluacion;
    }
    
}
