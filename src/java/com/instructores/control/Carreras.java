package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Pagination;
import com.instructores.utilerias.Tabla;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Carrearas", urlPatterns = {"/Carreras"})
public class Carreras extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion==null) {
            if(request.getSession().getAttribute("resultado")!=null) {
                request.setAttribute("resultado", request.getSession().getAttribute("resultado"));
                request.getSession().removeAttribute("resultado");
            }
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "";
                if(request.getParameter("txtBusqueda")!=null) {
                sql =   "select \n" +
                        "   c.idcarrera, c.carrera,\n" +
                        "   (select f.facultad from facultad f where f.idfacultad = c.idfacultad) as facultad, c.codigo\n" +
                        "from carrera c where c.carrera like ?";
                } else {
                sql =   "select \n" +
                        "   c.idcarrera, c.carrera,\n" +
                        "   (select f.facultad from facultad f where f.idfacultad = c.idfacultad) as facultad, c.codigo\n" +
                        "from carrera c";
                }
                
                String[][] carreras = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    carreras = Operaciones.consultar(sql, params);
                } else {
                    carreras = Operaciones.consultar(sql, null);
                }
                
                
                
                //Pagination p = new Pagination(carreras[0].length, 10, request);
                //request.setAttribute("pag", p);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Carrera",
                "Nombre Carrera",
                "Facultad",
                "Código"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(carreras, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                //tab.setLimiteInferior(p.getCurrentLowerLimit());
                //tab.setLimiteSuperior(p.getCurrentUpperLimit());
                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Carreras?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Carreras?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Carreras?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado carreras");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                
                String sqlfacultad = "select * from facultad";
                List<Facultad> listaFacultades = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlfacultad, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Facultad f = new Facultad(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], Integer.parseInt(rs[3][i]));
                    listaFacultades.add(f);
                }
                request.setAttribute("Facultades", listaFacultades); 
                
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("carreras/carreras_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlfacultad = "select * from facultad";
                List<Facultad> listaFacultades = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlfacultad, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Facultad p = new Facultad(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], Integer.parseInt(rs[3][i]));
                    listaFacultades.add(p);
                }
                request.setAttribute("Facultades", listaFacultades);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("carreras/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlfacultad = "select * from facultad";
                List<Facultad> listaFacultades = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlfacultad, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Facultad p = new Facultad(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], Integer.parseInt(rs[3][i]));
                    listaFacultades.add(p);
                }
                request.setAttribute("Facultades", listaFacultades);
                
                Carrera c = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Carrera());
                request.setAttribute("carrera", c);                
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("carreras/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Carrera c = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Carrera());
                
                if(c.getIdcarrera()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Carreras");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idCarrera = request.getParameter("txtIdcarrera");
                String carrera = request.getParameter("txtCarrera");
                String idFacultad = request.getParameter("cmbFacultad");
                String codigo = request.getParameter("txtCodigo");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    Carrera c = new Carrera();
                    c.setCodigo(codigo);
                    c.setCarrera(carrera);
                    c.setIdfacultad(Integer.parseInt(idFacultad));
                    if(idCarrera != null && !idCarrera.equals("")){
                        c.setIdcarrera(Integer.parseInt(idCarrera));
                        c = Operaciones.actualizar(c.getIdcarrera(), c);
                    }else
                        c = Operaciones.insertar(c);
                    
                    if(c.getIdcarrera() != 0)
                        request.getSession().setAttribute("resultado", 1);
                    else
                        request.getSession().setAttribute("resultado", 0);
                    
                    Operaciones.commit();
                } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Carreras.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Carreras");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}