package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="permiso")
public class Permiso {
    @PrimaryKey
    @AutoIncrement
    private int idpermiso;
    private int idmenu;
    private int idrol;

    public Permiso() {
    }

    public Permiso(int idpermiso, int idmenu, int idrol) {
        this.idpermiso = idpermiso;
        this.idmenu = idmenu;
        this.idrol = idrol;
    }

    public int getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(int idpermiso) {
        this.idpermiso = idpermiso;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }
    
}
