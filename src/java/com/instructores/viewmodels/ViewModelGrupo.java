package com.instructores.viewmodels;

public class ViewModelGrupo {
    private int idgrupo;
    private String instructor;
    private String materia;
    private String catedratico;
    private String ciclo;
    private String numero_grupo;
    private String clave;
    private Integer idtest;
    private String estado;

    public ViewModelGrupo() {
    }

    public ViewModelGrupo(int idgrupo, String instructor, String materia, String catedratico, String ciclo, String numero_grupo, String clave, Integer idtest, String estado) {
        this.idgrupo = idgrupo;
        this.instructor = instructor;
        this.materia = materia;
        this.catedratico = catedratico;
        this.ciclo = ciclo;
        this.numero_grupo = numero_grupo;
        this.clave = clave;
        this.idtest = idtest;
        this.estado = estado;
    }

    public ViewModelGrupo(int idgrupo, String instructor, String materia, String catedratico, String ciclo, String numero_grupo, String clave, String estado) {
        this.idgrupo = idgrupo;
        this.instructor = instructor;
        this.materia = materia;
        this.catedratico = catedratico;
        this.ciclo = ciclo;
        this.numero_grupo = numero_grupo;
        this.clave = clave;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
        
    public int getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(String catedratico) {
        this.catedratico = catedratico;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
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

    public Integer getIdtest() {
        return idtest;
    }

    public void setIdtest(Integer idtest) {
        this.idtest = idtest;
    }
    
}
