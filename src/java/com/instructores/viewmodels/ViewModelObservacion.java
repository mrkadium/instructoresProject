/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.instructores.viewmodels;

/**
 *
 * @author Soldier4
 */
public class ViewModelObservacion {
    private String idevaluacion;
    private String observacion;
    private String fecha_realizacion;

    public ViewModelObservacion() {
    }

    public ViewModelObservacion(String idevaluacion, String observacion, String fecha_realizacion) {
        this.idevaluacion = idevaluacion;
        this.observacion = observacion;
        this.fecha_realizacion = fecha_realizacion;
    }

    public String getIdevaluacion() {
        return idevaluacion;
    }

    public void setIdevaluacion(String idevaluacion) {
        this.idevaluacion = idevaluacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha_realizacion() {
        return fecha_realizacion;
    }

    public void setFecha_realizacion(String fecha_realizacion) {
        this.fecha_realizacion = fecha_realizacion;
    }
    
    
}
