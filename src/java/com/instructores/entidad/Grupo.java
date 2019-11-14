package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="grupo")
public class Grupo {
    @PrimaryKey
    @AutoIncrement
    private int idgrupo;
    private int idmateria;
    private int idinstructor;
    private int idcatedratico;
    private String numero_grupo;
    private String ciclo;
    private String clave;
    private Integer idtest;
    private String estado;
    
    public Grupo() {
    }

    public Grupo(int idgrupo, int idmateria, int idinstructor, int idcatedratico, String numero_grupo, String ciclo, String clave, Integer idtest, String estado) {
        this.idgrupo = idgrupo;
        this.idmateria = idmateria;
        this.idinstructor = idinstructor;
        this.idcatedratico = idcatedratico;
        this.numero_grupo = numero_grupo;
        this.ciclo = ciclo;
        this.clave = clave;
        this.idtest = idtest;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdtest() {
        return idtest;
    }

    public void setIdtest(Integer idtest) {
        this.idtest = idtest;
    }

    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }

    public int getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(int idmateria) {
        this.idmateria = idmateria;
    }

    public int getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(int idinstructor) {
        this.idinstructor = idinstructor;
    }

    public int getIdcatedratico() {
        return idcatedratico;
    }

    public void setIdcatedratico(int idcatedratico) {
        this.idcatedratico = idcatedratico;
    }

    public String getNumero_grupo() {
        return numero_grupo;
    }

    public void setNumero_grupo(String numero_grupo) {
        this.numero_grupo = numero_grupo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
}
