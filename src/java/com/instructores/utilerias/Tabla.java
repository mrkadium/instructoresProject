package com.instructores.utilerias;

import java.sql.SQLException;

public class Tabla {
    private String estilo;
    private String ancho;
    private int alineacion;
    private String[] cabeceras; 
    private int[] anchocolumnas;
    private String[][] rs;
    
    public static final class ICON {
        public static final String VER_MAS = "<i title='Ver más' class=\"fas fa-folder-open\"></i>";
        public static final String IMPRIMIR = "<i title='Imprimir reporte' class=\"fas fa-print\"></i>";
        public static final String INSERTAR = "<i title='Insertar' class=\"fas fa-plus-circle\"></i>";
        public static final String MODIFICAR = "<i title='Modificar' class=\"fas fa-pencil-alt\"></i>";
        public static final String ELIMINAR = "<i title='Eliminar' class=\"fas fa-trash-alt\"></i>";
    }

    public static final class ALIGN {
        public static final int CENTER = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }
    public static final class STYLE {
        public static final String TABLE01 = "table01";
        public static final String OTRO = "table01";
    }    
    
    private String cabeceraModificable;
    private String cabeceraEliminable;
    private String cabeceraSeleccionable;
    private String cabeceraImprimible;
    
    private boolean modificable;
    private boolean eliminable;
    private boolean seleccionable;
    private String textoEliminable;
    private String iconoEliminable;
    private String textoModificable;
    private String iconoModificable;
    private String textoSeleccionable;
    private String iconoSeleccionable;
    private String paginaModificable;
    private String paginaSeleccionable;
    private String paginaEliminable;    
    private int[] columnasSeleccionables;
    private int numeroColumnas;
    private String pie;
    private boolean filaSeleccionable;
    private String metodoFilaSeleccionable;
    private String pageContext;
    
    private boolean imprimible;
    private String paginaImprimible;
    private String iconoImprimible;
            
    public Tabla(String[][] rs, String ancho, String estilo,int alineacion,String[] cabeceras){
       this.rs = rs;
       this.estilo = estilo; 
       this.ancho = ancho;
       this.alineacion = alineacion;
       this.cabeceras = cabeceras; 
       
       cabeceraSeleccionable = "";
       cabeceraModificable = "";
       cabeceraEliminable = "";
       cabeceraImprimible = "";
       
       anchocolumnas = null;
       modificable = false;
       eliminable = false;
       seleccionable = false;
       textoEliminable = "Eliminar";
       textoModificable = "Modificar";
       textoSeleccionable = "Seleccionar";
       iconoEliminable = "";
       iconoModificable = "";
       iconoSeleccionable = "";   
       paginaEliminable = "";
       paginaModificable = "";
       paginaSeleccionable = "";
       filaSeleccionable=false;
       metodoFilaSeleccionable="=_seleccionar";
       pie="Resultado";
       
       imprimible = false;
       paginaImprimible = "";
       iconoImprimible = "";
    }
    
    public Tabla(String[][] rs, String ancho, String estilo,int alineacion,String[] cabeceras, int[] anchoColumnas){
       this.rs = rs;
       this.estilo = estilo; 
       this.ancho = ancho;
       this.alineacion = alineacion;
       this.cabeceras = cabeceras; 
       this.anchocolumnas = anchoColumnas;
       
       cabeceraSeleccionable = "";
       cabeceraModificable = "";
       cabeceraEliminable = "";
       cabeceraImprimible = "";
       
       modificable = false;
       eliminable = false;
       seleccionable = false;
       textoEliminable = "Eliminar";
       textoModificable = "Modificar";
       textoSeleccionable = "Seleccionar";
       iconoEliminable = "";
       iconoModificable = "";
       iconoSeleccionable = "";   
       paginaEliminable = "";
       paginaModificable = "";
       paginaSeleccionable = "";
       pie="Resultado";
       
       imprimible = false;
       paginaImprimible = "";
       iconoImprimible = "";
    }    

    public String getPageContext() {
        return pageContext;
    }

    public void setPageContext(String pageContext) {
        this.pageContext = pageContext;
    }
    
    private String abrirTabla(){
        String alin = alineacion==1?"margin:auto":alineacion==2?"margin-left:0":"float:right;margin-top:0";
        String cab = "<table id='"+estilo+"'>";
        return cab;
    }
    private String EncbzdTabla(){        
        int i;
        String[] cabec = cabeceras;
        int[] columnas = anchocolumnas;
        String cab = "<thead><tr>";
        for (i=0;i<cabeceras.length;i++){
            if (anchocolumnas==null)
                cab += "<th>"+cabec[i]+"</th>";
            else
                cab += "<th style='width:"+columnas[i]+"%'>"+cabec[i]+"</th>";
        }
        if (isEliminable())
            cab +="<th>";
            cab += getCabeceraEliminable();
            cab +="</th>";
        if (isModificable())
            cab +="<th>";
            cab += getCabeceraModificable();
            cab +="</th>";
        if (isImprimible())
            cab +="<th>";
            cab += getCabeceraImprimible();
            cab +="</th>";
        if (isSeleccionable())
            cab +="<th>";
            cab += getCabeceraSeleccionable();
            cab +="</th>";
        
        cab +="</tr></thead>";
        
        return cab;
    }

    private String creaTabla() throws SQLException{                
        String[][] rst = rs;        
        numeroColumnas = rst.length;  
        String Tabla = "";
        int k=0;
        Tabla += "<tbody>";      
        boolean sw = false;        
        try{
        while (k<rst[0].length){             
            sw=!sw;
            String color_columna;
            if (sw)
                color_columna="row-a";
            else
                color_columna="row-b";
            
            Tabla +="<tr class='"+color_columna+"' align=center";
                    if (filaSeleccionable)
                        Tabla += " onclick='"+metodoFilaSeleccionable+"(this)'>";
                    else
                        Tabla += ">";   
            for (int i=0;i<numeroColumnas;i++){                 
                 if (getColumnasSeleccionables()==null)
                    Tabla +="<td>"+ rst[i][k]+"</td>";
                 else{
                     boolean found=false;
                     for (int j=0;j<getColumnasSeleccionables().length;j++){
                         if (getColumnasSeleccionables()[j]==i){
                             found = true;
                             break;
                         }
                     }
                    if (found){
                        String enlaceSeleccionable;
                        enlaceSeleccionable = rst[i][k];
//                        Tabla +="<td><a onclick=\"return confirm('¿Está seguro?')\" href='"+getPaginaSeleccionable()+(getPaginaSeleccionable().contains("?") ? "&" : "?")  +"id="+rst[0][k]+"'>"
                        Tabla +="<td><a href='"+getPaginaSeleccionable()+(getPaginaSeleccionable().contains("?") ? "&" : "?")  +"id="+rst[0][k]+"'>"
                        +enlaceSeleccionable+"</a></td>";
                        }
                    else
                        Tabla +="<td>"+ rst[i][k]+"</td>";
                 }
            }
           
           if (isModificable()){
                String enlaceModificable;
                if (getIconoModificable().equals(""))
                    enlaceModificable = getTextoModificable();
                else
                    enlaceModificable = getIconoModificable();
                
                Tabla +="<td><a class='btn' href='"+getPaginaModificable()  +"&id="+rst[0][k]+"'>"
                        +enlaceModificable+"</a></td>"; 
           }
           if (isImprimible()){
                String enlaceImprimible;
                if (getIconoImprimible().equals(""))
                    enlaceImprimible = getTextoEliminable();
                else
                    enlaceImprimible = getIconoImprimible();               
                Tabla +="<td>"
//                        + "<a class='btn' href='"+getPaginaImprimible() + (getPaginaImprimible().contains("?") ? "&" : "?") + "id="+rst[0][k]+"'>"
                        + "<a class='btn' onclick=\"abrirVentana('"+getPaginaImprimible() + (getPaginaImprimible().contains("?") ? "&" : "?") + "id="+rst[0][k]+"');\">"
                        +enlaceImprimible+"</a></td>";
           }
           if (isEliminable()){
                String enlaceEliminable;
                if (getIconoEliminable().equals(""))
                    enlaceEliminable = getTextoEliminable();
                else
                    enlaceEliminable = getIconoEliminable();               
                Tabla +="<td>"
                        + "<a class='btn' onclick=\"return confirm('¿Está seguro? Esta acción no se puede deshacer.')\" href='"+getPaginaEliminable() + (getPaginaEliminable().contains("?") ? "&" : "?") + "id="+rst[0][k]+"'>"
                        +enlaceEliminable+"</a></td>";
           }
           if (isSeleccionable()){
                String enlaceSeleccionable;
                if (getIconoSeleccionable().equals(""))
                    enlaceSeleccionable = getTextoSeleccionable();
                else
                    enlaceSeleccionable = getIconoSeleccionable();               
                Tabla +="<td>"
                        + "<a class='btn' onclick=\"abrirVentana('"+getPaginaSeleccionable()+ (getPaginaSeleccionable().contains("?") ? "&" : "?") + "id="+rst[0][k]+"');\">"
                        +enlaceSeleccionable+"</a></td>";  
           }
            
           Tabla +="</tr>";  
           k++;
        }
        Tabla += "</tbody>"; 
        }catch(Exception e){
            Tabla = e.getMessage();
        }
        return Tabla;
    }   
    
//    private String pieTabla(){
//        int ncol=0;
//        if (isEliminable())
//            ncol++;
//        if (isModificable())
//            ncol++;  
//        if (isSeleccionable())
//            ncol++;
//        String cab = "<tfoot><tr><td align=center colspan='"+numeroColumnas+ncol+"'>"+pie+"</td></tr></tfoot>";
//        return cab;
//    }

    private String cerrarTabla(){
        String cab = "</table>";
        return cab;
    }
    
    public String getTabla() throws SQLException{
        return abrirTabla()+EncbzdTabla()+creaTabla()+cerrarTabla();
    };

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    public boolean isEliminable() {
        return eliminable;
    }

    public void setEliminable(boolean eliminable) {
        this.eliminable = eliminable;
    }

    public boolean isSeleccionable() {
        return seleccionable;
    }

    public void setSeleccionable(boolean seleccionable) {
        this.seleccionable = seleccionable;
    }

    public String getTextoEliminable() {
        return textoEliminable;
    }

    public void setTextoEliminable(String textoEliminable) {
        this.textoEliminable = textoEliminable;
    }

    public String getIconoEliminable() {
        return iconoEliminable;
    }

    public void setIconoEliminable(String iconoEliminable) {
        this.iconoEliminable = iconoEliminable;
    }

    public String getTextoModificable() {
        return textoModificable;
    }

    public void setTextoModificable(String textoModificable) {
        this.textoModificable = textoModificable;
    }

    public String getIconoModificable() {
        return iconoModificable;
    }

    public void setIconoModificable(String iconoModificable) {
        this.iconoModificable = iconoModificable;
    }

    public String getTextoSeleccionable() {
        return textoSeleccionable;
    }

    public void setTextoSeleccionable(String textoSeleccionable) {
        this.textoSeleccionable = textoSeleccionable;
    }

    public String getIconoSeleccionable() {
        return iconoSeleccionable;
    }

    public void setIconoSeleccionable(String iconoSeleccionable) {
        this.iconoSeleccionable = iconoSeleccionable;
    }

     public String getPaginaModificable() {
        return this.pageContext+paginaModificable;
    }

    public void setPaginaModificable(String paginaModificable) {
        this.paginaModificable = paginaModificable;
    }

    public String getPaginaSeleccionable() {
        return this.pageContext+paginaSeleccionable;
    }

    public void setPaginaSeleccionable(String paginaSeleccionable) {
        this.paginaSeleccionable = paginaSeleccionable;
    }

    public String getPaginaEliminable() {
        return this.pageContext+paginaEliminable;
    }

    public void setPaginaEliminable(String paginaEliminable) {
        this.paginaEliminable = paginaEliminable;
    }

    public int[] getColumnasSeleccionables() {
        return columnasSeleccionables;
    }

    public void setColumnasSeleccionables(int[] columnasSeleccionables) {
        this.columnasSeleccionables = columnasSeleccionables;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }
    public boolean isFilaSeleccionable() {
        return filaSeleccionable;
    }

    public void setFilaSeleccionable(boolean filaSeleccionable) {
        this.filaSeleccionable = filaSeleccionable;
    }

    public String getMetodoFilaSeleccionable() {
        return metodoFilaSeleccionable;
    }

    public void setMetodoFilaSeleccionable(String metodoFilaSeleccionable) {
        this.metodoFilaSeleccionable = metodoFilaSeleccionable;
    }    
    
    

    public boolean isImprimible() {
        return imprimible;
    }

    public void setImprimible(boolean imprimible) {
        this.imprimible = imprimible;
    }

    public String getPaginaImprimible() {
        return this.pageContext + paginaImprimible;
    }

    public void setPaginaImprimible(String paginaImprimible) {
        this.paginaImprimible = paginaImprimible;
    }

    public String getIconoImprimible() {
        return iconoImprimible;
    }

    public void setIconoImprimible(String iconoImprimible) {
        this.iconoImprimible = iconoImprimible;
    }

    public String getCabeceraModificable() {
        return cabeceraModificable;
    }

    public void setCabeceraModificable(String cabeceraModificable) {
        this.cabeceraModificable = cabeceraModificable;
    }

    public String getCabeceraEliminable() {
        return cabeceraEliminable;
    }

    public void setCabeceraEliminable(String cabeceraEliminable) {
        this.cabeceraEliminable = cabeceraEliminable;
    }

    public String getCabeceraSeleccionable() {
        return cabeceraSeleccionable;
    }

    public void setCabeceraSeleccionable(String cabeceraSeleccionable) {
        this.cabeceraSeleccionable = cabeceraSeleccionable;
    }

    public String getCabeceraImprimible() {
        return cabeceraImprimible;
    }

    public void setCabeceraImprimible(String cabeceraImprimible) {
        this.cabeceraImprimible = cabeceraImprimible;
    }
    
}
