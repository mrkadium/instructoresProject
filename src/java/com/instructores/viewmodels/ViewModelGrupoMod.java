package com.instructores.viewmodels;

public class ViewModelGrupoMod {
    public int idgrupo;
    public int idmateria;
    public String materia;
    public int idinstructor;
    public String instructor;
    public int idcatedratico;
    public String catedratico;
    public String numero_grupo;
    public String clave;
    public String ciclo;
    private Integer idtest;
    private String estado;

    public ViewModelGrupoMod() {
    }

    public ViewModelGrupoMod(int idgrupo, int idmateria, String materia, int idinstructor, String instructor, int idcatedratico, String catedratico, String numero_grupo, String clave, String ciclo, Integer idtest, String estado) {
        this.idgrupo = idgrupo;
        this.idmateria = idmateria;
        this.materia = materia;
        this.idinstructor = idinstructor;
        this.instructor = instructor;
        this.idcatedratico = idcatedratico;
        this.catedratico = catedratico;
        this.numero_grupo = numero_grupo;
        this.clave = clave;
        this.ciclo = ciclo;
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(int idinstructor) {
        this.idinstructor = idinstructor;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getIdcatedratico() {
        return idcatedratico;
    }

    public void setIdcatedratico(int idcatedratico) {
        this.idcatedratico = idcatedratico;
    }

    public String getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(String catedratico) {
        this.catedratico = catedratico;
    }

    public String getNumero_grupo() {
        return numero_grupo;
    }

    public void setNumero_grupo(String numero_grupo) {
        this.numero_grupo = numero_grupo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

}