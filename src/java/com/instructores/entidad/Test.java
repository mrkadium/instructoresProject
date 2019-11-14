package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="test")
public class Test {
    @PrimaryKey
    @AutoIncrement
    private int idtest;
    private String ciclo;
    private String estado;

    public Test() {
    }

    public Test(int idtest, String ciclo) {
        this.idtest = idtest;
        this.ciclo = ciclo;
    }

    public Test(int idtest, String ciclo, String estado) {
        this.idtest = idtest;
        this.ciclo = ciclo;
        this.estado = estado;
    }

    public int getIdtest() {
        return idtest;
    }

    public void setIdtest(int idtest) {
        this.idtest = idtest;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
