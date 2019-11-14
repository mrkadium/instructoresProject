package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "Plantillas_literal", urlPatterns = {"/Plantillas_literal"})
public class Plantillas_literal extends HttpServlet {
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
                
                //SE PREPARA PARA OBTENER LOS ELEMENTOS RELACIONADOS AL ELEMENTO SELECCIONADO
                String[][] pliterales = null;
                String sql =   
                        "select\n" +
                        "   idplantilla_literal, literal,\n" +
                        "    (select tipo from tipo where idtipo = pl.idtipo) as tipo,\n" +
                        "    idplantilla_test\n" +
                        "from plantilla_literal pl";
                
                if(request.getParameter("idplantilla_test")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add(request.getParameter("idplantilla_test"));
                    
                    //SE VUELVEN A MANDAR, PARA QUE APAREZCAN COMO EL ELEMENTO SELECCIONADO
                    request.setAttribute("idplantilla_test", request.getParameter("idplantilla_test"));
                    request.setAttribute("ciclo", request.getParameter("ciclo"));
                    
                    //SE LE AÑADE EL ID DEL ELEMENTO SELECCIONADO
                    sql += " where pl.idplantilla_test = ?";
                    
                }
                //SE HACE LA CONSULTA
                pliterales = Operaciones.consultar(sql, null);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Plantilla-literal",
                "Literal",
                "Tipo",
                "ID Plantilla-test"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(pliterales, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Plantillas_literal?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Plantillas_literal?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Plantillas_literal?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado plantillas-literal");
                //imprime la tabla en pantalla
                
                // --------------------------------------------------CONSULTAR TIPOS
                String sqltipo = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sqltipo, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo t = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(t);
                }
                request.setAttribute("Tipos", listaTipos);
                // ------------------------------------------------------------------
                
                String tabla01 = "No hay datos";
                if(pliterales != null)
                    tabla01 = tab.getTabla();
                
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("plantillas_literal/plantillas_literal_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqltipo = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sqltipo, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo t = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(t);
                }
                request.setAttribute("Tipos", listaTipos);
                
                request.getRequestDispatcher("plantillas_literal/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception ex){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("modificar")){
            try{
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                int id = Integer.parseInt(request.getParameter("id"));
                Plantilla_literal pl = Operaciones.get(id, new Plantilla_literal());
                Plantilla_test pt = Operaciones.get(pl.getIdplantilla_test(), new Plantilla_test());
                
                request.setAttribute("plantilla_literal", pl);
                request.setAttribute("pt", pt);
                
                String sqltipo = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sqltipo, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo t = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(t);
                }
                request.setAttribute("Tipos", listaTipos);
                
                request.getRequestDispatcher("plantillas_literal/insertar_modificar.jsp").forward(request, response);
                
                Operaciones.commit();
            }catch(Exception ex){
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }finally{
                try {
                    Operaciones.cerrarConexion();
                    response.sendRedirect(request.getContextPath()+"/Plantillas_literal");
                } catch (SQLException ex) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } 
        else if(accion.equals("plantillas_test")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "select * from plantilla_test";
                String[][] materias = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Plantilla-test",
                    "Ciclo"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(materias, //array que contiene los datos
                "100%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                //icono para modificar y eliminar
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                //columnas seleccionables
//                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado plantilla-test");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (materias!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("plantillas_literal/plantillas_test.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Plantilla_literal pl = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Plantilla_literal());
                
                
                if(pl.getIdplantilla_literal()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                    response.sendRedirect(request.getContextPath()+"/Plantillas_literal");
                } catch (SQLException ex) {
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Plantillas_literal");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                String idPlantilla_literal = request.getParameter("txtIdplantilla_literal");
                String literal = request.getParameter("txtLiteral");
                String idTipo = request.getParameter("cmbTipo");
                String idPlantilla_test = request.getParameter("txtIdplantilla_test");
                
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    Plantilla_literal pt = new Plantilla_literal();
                    pt.setLiteral(literal);
                    pt.setIdtipo(Integer.parseInt(idTipo));
                    pt.setIdplantilla_test(Integer.parseInt(idPlantilla_test));
                    if(idPlantilla_literal!=null && !idPlantilla_literal.equals("")) {
                        pt.setIdplantilla_literal(Integer.parseInt(idPlantilla_literal));
                        pt = Operaciones.actualizar(pt.getIdplantilla_literal(), pt);
                    } else {
                        pt = Operaciones.insertar(pt);
                    }
                    if(pt.getIdplantilla_literal()!=0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    
                    Operaciones.commit();
                } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    request.getSession().setAttribute("resultado", 2);
                    Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex1);
                }
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                        response.sendRedirect(request.getContextPath()+"/Plantillas_literal");
                    } catch (SQLException ex) {
                        Logger.getLogger(Plantillas_literal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }
}