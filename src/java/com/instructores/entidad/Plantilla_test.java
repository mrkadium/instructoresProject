package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="plantilla_test")
public class Plantilla_test {
    @PrimaryKey
    @AutoIncrement
    private int idplantilla_test;
    private String ciclo;
    private String observacion;

    public Plantilla_test() {
    }

    public Plantilla_test(int idplantilla_test, String ciclo) {
        this.idplantilla_test = idplantilla_test;
        this.ciclo = ciclo;
    }

    public Plantilla_test(int idplantilla_test, String ciclo, String observacion) {
        this.idplantilla_test = idplantilla_test;
        this.ciclo = ciclo;
        this.observacion = observacion;
    }

    public int getIdplantilla_test() {
        return idplantilla_test;
    }

    public void setIdplantilla_test(int idplantilla_test) {
        this.idplantilla_test = idplantilla_test;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
