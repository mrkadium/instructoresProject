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

@WebServlet(name = "Materias", urlPatterns = {"/Materias"})
public class Materias extends HttpServlet {
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
                sql =   "select\n" +
                        "	m.idmateria, m.materia, m.alias\n" +
                        "	(select c.carrera from carrera c where c.idcarrera = m.idcarrera) as carrera\n" +
                        "from materia m where m.materia like ?";
                } else {
                sql =   "select\n" +
                        "	m.idmateria, m.materia, m.alias,\n" +
                        "	(select c.carrera from carrera c where c.idcarrera = m.idcarrera) as carrera\n" +
                        "from materia m;";
                }
                
                String[][] materias = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    materias = Operaciones.consultar(sql, params);
                } else {
                    materias = Operaciones.consultar(sql, null);
                }
                
                
                Pagination p = new Pagination(materias[0].length, 10, request);
                request.setAttribute("pag", p);
                
                
                String sqlcarrera = "select * from carrera";
                List<Carrera> listaCarreras = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlcarrera, new ArrayList());
                
                for(int i=0; i<rs[0].length; i++){
                    Carrera c = new Carrera(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i]), rs[3][i]);
                    listaCarreras.add(c);
                }
                request.setAttribute("Carreras", listaCarreras);
                
                String[] cabeceras = new String[]{
                "ID Materia",
                "Nombre Materia",
                "Alias",
                "Carrera"
                };                
                Tabla tab = new Tabla(materias,"80%",Tabla.STYLE.TABLE01,Tabla.ALIGN.LEFT,cabeceras);
                tab.setLimiteInferior(p.getCurrentLowerLimit());
                tab.setLimiteSuperior(p.getCurrentUpperLimit());
                tab.setEliminable(true);
                tab.setModificable(true);
                tab.setPageContext(request.getContextPath());
                tab.setPaginaEliminable("/Materias?accion=eliminar");
                tab.setPaginaModificable("/Materias?accion=modificar");
                tab.setPaginaSeleccionable("/Materias?accion=modificar");
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                tab.setColumnasSeleccionables(new int[]{1});
                tab.setPie("Resultado materias");                
                String tabla01 = tab.getTabla();
                
                
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("materias/materias_consulta.jsp").forward(request, response);
                
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
                
                String sqlcarrera = "select * from carrera";
                List<Carrera> listaCarreras = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlcarrera, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Carrera c = new Carrera(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i]), rs[3][i]);
                    listaCarreras.add(c);
                }
                request.setAttribute("Carreras", listaCarreras);                
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("materias/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlcarrera = "select * from carrera";
                List<Carrera> listaCarreras = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlcarrera, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Carrera c = new Carrera(Integer.parseInt(rs[0][i]), rs[1][i], Integer.parseInt(rs[2][i]), rs[3][i]);
                    listaCarreras.add(c);
                }
                request.setAttribute("Carreras", listaCarreras);
                
                Materia m = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Materia());
                request.setAttribute("materia", m);
                
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("materias/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Materia m = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Materia());
                
                if(m.getIdmateria()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
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
                String idMateria = request.getParameter("txtIdmateria");
                String materia = request.getParameter("txtMateria");
                String alias = request.getParameter("txtAlias");
                String idCarrera = request.getParameter("cmbCarreras");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idMateria!=null && !idMateria.equals("")) {
                        Materia m = new Materia(Integer.parseInt(idMateria), materia, alias, Integer.parseInt(idCarrera));
                        m = Operaciones.actualizar(m.getIdmateria(), m);
                        if(m.getIdcarrera()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Materia m = new Materia();
                        m.setMateria(materia);
                        m.setAlias(alias);
                        m.setIdcarrera(Integer.parseInt(idCarrera));
                        
                        m = Operaciones.insertar(m);
                        if(m.getIdmateria()!=0) {
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
                    Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Materias.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Materias");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}