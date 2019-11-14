package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="plantilla_literal")
public class Plantilla_literal {
    @PrimaryKey
    @AutoIncrement
    private int idplantilla_literal;
    private String literal;
    private int idtipo;
    private int idplantilla_test;

    public Plantilla_literal() {
    }

    public Plantilla_literal(int idplantilla_literal, String literal, int idtipo, int idplantilla_test) {
        this.idplantilla_literal = idplantilla_literal;
        this.literal = literal;
        this.idtipo = idtipo;
        this.idplantilla_test = idplantilla_test;
    }

    public int getIdplantilla_literal() {
        return idplantilla_literal;
    }

    public void setIdplantilla_literal(int idplantilla_literal) {
        this.idplantilla_literal = idplantilla_literal;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    public int getIdplantilla_test() {
        return idplantilla_test;
    }

    public void setIdplantilla_test(int idplantilla_test) {
        this.idplantilla_test = idplantilla_test;
    }
    
    
}
