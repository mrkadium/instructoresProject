package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Pagination;
import com.instructores.utilerias.Tabla;
import com.instructores.utilerias.Tabla.ICON;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

@WebServlet(name = "Valoraciones", urlPatterns = {"/Valoraciones"})
public class Valoraciones extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException {
        String accion = request.getParameter("accion");
        PrintWriter io = response.getWriter();
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
                sql =   "select * from valoracion where valoracion like ?";
                } else {
                sql =   
                    "SELECT \n" +
                    "	a.idvaloracion, a.valoracion, b.tipo, IFNULL(a.puntaje, '-') AS puntaje\n" +
                    "FROM valoracion a\n" +
                    "INNER JOIN tipo b ON a.idtipo = b.idtipo;";
                }
                String[][] valoraciones = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    valoraciones = Operaciones.consultar(sql, params);
                } else {
                    valoraciones = Operaciones.consultar(sql, null);
                }
                
                
//                Pagination p = new Pagination(valoraciones[0].length, 10, request);
//                request.setAttribute("pag", p);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Valoración",
                "Valoración",
                "Tipo",
                "Puntaje"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(valoraciones, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
//                tab.setLimiteInferior(p.getCurrentLowerLimit());
//                tab.setLimiteSuperior(p.getCurrentUpperLimit());
                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Valoraciones?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Valoraciones?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Valoraciones?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(ICON.MODIFICAR);
                tab.setIconoEliminable(ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado valoraciones");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("valoraciones/valoraciones_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sql, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo p = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(p);
                }
                request.setAttribute("Tipos", listaTipos);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("valoraciones/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Valoracion c = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Valoracion());
                request.setAttribute("valoracion", c);       
                
                String sql = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sql, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo p = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(p);
                }
                request.setAttribute("Tipos", listaTipos);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("valoraciones/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Valoracion v = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Valoracion());
                
                if(v.getIdvaloracion()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Valoraciones");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idvaloracion = request.getParameter("txtIdvaloracion");
                String valoracion = request.getParameter("txtValoracion");
                String idtipo = request.getParameter("cmbTipo");
                String puntaje = request.getParameter("txtPunjate");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idvaloracion!=null && !idvaloracion.equals("")) {
                        Valoracion v = new Valoracion(Integer.parseInt(idvaloracion), valoracion, Integer.parseInt(idtipo), new BigDecimal(puntaje));
                        v = Operaciones.actualizar(v.getIdvaloracion(), v);
                        if(v.getIdvaloracion()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Valoracion v = new Valoracion();
                        v.setValoracion(valoracion);
                        v.setIdtipo(Integer.parseInt(idtipo));
                        if(v.getIdvaloracion()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    
                    Operaciones.commit();
                } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Valoraciones.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Valoraciones");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}