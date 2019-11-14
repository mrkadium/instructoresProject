package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="rol")
public class Rol {
    @PrimaryKey
    @AutoIncrement
    private int idrol;
    private String rol;

    public Rol() {
    }

    public Rol(int idrol, String rol) {
        this.idrol = idrol;
        this.rol = rol;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
