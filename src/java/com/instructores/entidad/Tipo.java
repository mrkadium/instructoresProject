package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="tipo")
public class Tipo {
    @PrimaryKey
    @AutoIncrement
    private int idtipo;
    private String tipo;
    private String tipo_input;

    public Tipo() {
    }

    public Tipo(int idtipo, String tipo, String tipo_input) {
        this.idtipo = idtipo;
        this.tipo = tipo;
        this.tipo_input = tipo_input;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_input() {
        return tipo_input;
    }

    public void setTipo_input(String tipo_input) {
        this.tipo_input = tipo_input;
    }
    
}
