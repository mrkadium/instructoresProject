package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="usuario")
public class Usuario {
    @PrimaryKey
    @AutoIncrement
    private int idusuario;
    private String usuario;
    private String nombres;
    private String apellidos;
    private String clave;
    private int idrol;

    public Usuario() {
    }

    public Usuario(int idusuario, String usuario, String nombres, String apellidos, String clave, int idrol) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.clave = clave;
        this.idrol = idrol;
        this.usuario = usuario;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
