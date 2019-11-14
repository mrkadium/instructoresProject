package com.instructores.viewmodels;

import com.instructores.anotaciones.*;

@Entity(table="menu")
public class ViewModelMenu {
    @PrimaryKey
    @AutoIncrement
    private int idmenu;
    private String menu;
    private String descripcion;
    private String url;
    private String idcss;
    private String img;
    private int idpadre;
    private String padre;

    public ViewModelMenu() {
    }

    public ViewModelMenu(int idmenu, String menu, String descripcion, String url, String idcss, String img, int idpadre, String padre) {
        this.idmenu = idmenu;
        this.menu = menu;
        this.descripcion = descripcion;
        this.url = url;
        this.idcss = idcss;
        this.img = img;
        this.idpadre = idpadre;
        this.padre = padre;
    }
    

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getIdcss() {
        return idcss;
    }

    public void setIdcss(String idcss) {
        this.idcss = idcss;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(int idmenu) {
        this.idmenu = idmenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(int idpadre) {
        this.idpadre = idpadre;
    }
    
}