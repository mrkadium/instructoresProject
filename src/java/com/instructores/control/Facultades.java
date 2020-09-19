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

@WebServlet(name = "Facultades", urlPatterns = {"/Facultades"})
public class Facultades extends HttpServlet {
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
                sql = "select\n" +
                        "	idfacultad, facultad, alias,\n" +
                        "    (select concat(nombres, ' ', apellidos) from usuario where idusuario = iddecano) as decano\n" +
                        "from facultad where facultad like ?";
                } else {
                sql = "select\n" +
                    "	idfacultad, facultad, alias,\n" +
                    "    (select concat(nombres, ' ', apellidos) from usuario where idusuario = iddecano) as decano\n" +
                    "from facultad";
                }
                
                String[][] facultades = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    facultades = Operaciones.consultar(sql, params);
                } else {
                    facultades = Operaciones.consultar(sql, null);
                }
                
                
                
//                Pagination p = new Pagination(facultades[0].length, 10, request);
//                request.setAttribute("pag", p);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Facultad",
                "Nombre Facultad",
                "Alias",
                "Decano"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(facultades, //array que contiene los datos
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
                tab.setPaginaEliminable("/Facultades?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Facultades?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Facultades?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado facultades");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("facultades/facultades_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "select * from usuario where "
                        + "idrol = (select idrol from rol where rol = 'decano') "
                        + "AND idusuario NOT IN (SELECT iddecano FROM facultad);";
                String[][] rs = Operaciones.consultar(sql, null);
                List<Usuario> listaUsuarios = new ArrayList();
                for(int i=0; i<rs[0].length; i++){
                    Usuario u = new Usuario(Integer.parseInt(rs[0][i]),rs[1][i],rs[2][i],rs[3][i],rs[4][i],Integer.parseInt(rs[5][i]));
                    listaUsuarios.add(u);
                }
                request.setAttribute("Usuarios", listaUsuarios);         
                
                Operaciones.commit();
            }catch(Exception e){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("facultades/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Facultad f = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Facultad());
                request.setAttribute("facultad", f);
                
                String sql = "select * from usuario where idrol = (select idrol from rol where rol = 'decano')";
                String[][] rs = Operaciones.consultar(sql, null);
                List<Usuario> listaUsuarios = new ArrayList();
                for(int i=0; i<rs[0].length; i++){
                    Usuario u = new Usuario(Integer.parseInt(rs[0][i]),rs[1][i],rs[2][i],rs[3][i],rs[4][i],Integer.parseInt(rs[5][i]));
                    listaUsuarios.add(u);
                }
                request.setAttribute("Usuarios", listaUsuarios);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("facultades/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Facultad f = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Facultad());
                if(f.getIdfacultad()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Facultades");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idFacultad = request.getParameter("txtIdfacultad");
                String facultad = request.getParameter("txtFacultad");
                String alias = request.getParameter("txtAlias");
                String iddecano = request.getParameter("cmbDecano");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idFacultad!=null && !idFacultad.equals("")) {
                        Facultad f = new Facultad(Integer.parseInt(idFacultad), facultad, alias, Integer.parseInt(iddecano));
                        f = Operaciones.actualizar(f.getIdfacultad(), f);
                        if(f.getIdfacultad()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Facultad f = new Facultad();
                        f.setFacultad(facultad);
                        f.setAlias(alias);
                        f.setIddecano(Integer.parseInt(iddecano));
                        
                        f = Operaciones.insertar(f);
                        if(f.getIdfacultad()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    }
                    
                    Operaciones.commit();
                } catch(Exception ex) {
                try {
                    request.getSession().setAttribute("resultado", 0);
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex1);
                }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Facultades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Facultades");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}