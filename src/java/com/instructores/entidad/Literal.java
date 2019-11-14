package com.instructores.entidad;

import com.instructores.anotaciones.*;

@Entity(table="literal")
public class Literal {
    @PrimaryKey
    @AutoIncrement
    private int idliteral;
    private String literal;
    private int idtipo;
    private int idtest;

    public Literal() {
    }

    public Literal(int idliteral, String literal, int idtipo, int idtest) {
        this.idliteral = idliteral;
        this.literal = literal;
        this.idtipo = idtipo;
        this.idtest = idtest;
    }

    public int getIdliteral() {
        return idliteral;
    }

    public void setIdliteral(int idliteral) {
        this.idliteral = idliteral;
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

    public int getIdtest() {
        return idtest;
    }

    public void setIdtest(int idtest) {
        this.idtest = idtest;
    }
    
    
}
