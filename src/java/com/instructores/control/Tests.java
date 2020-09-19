package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Pagination;
import com.instructores.utilerias.Tabla;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
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

@WebServlet(name = "Tests", urlPatterns = {"/Tests"})
public class Tests extends HttpServlet {
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
                
                String sql =    "select\n" +
                                "   idliteral, literal,\n" +
                                "   (select tipo from tipo where idtipo = l.idtipo) as tipo\n" +
                                "from literal l";
                String[][] test=null;
                if (request.getParameter("idtest")!=null){
                    List<Object> param = new ArrayList();
                    param.add(request.getParameter("idtest"));
                    request.setAttribute("idtest", request.getParameter("idtest"));
                    request.setAttribute("ciclo", request.getParameter("ciclo"));
                    sql+=" where l.idtest = ?";
                    test= Operaciones.consultar(sql, param);
                }
                
                sql = "select * from test";
                test= Operaciones.consultar(sql, null);
                
                
                
//                Pagination p = new Pagination(test[0].length, 10, request);
//                request.setAttribute("pag", p);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Test",
                "Ciclo",
                "Estado"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(test, //array que contiene los datos
                "100%", //ancho de la tabla px | %
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
                tab.setPaginaEliminable("/Tests?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Tests?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Tests?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado plantillas-test");
                //imprime la tabla en pantalla
                String tabla01 = "No hay datos";
                if(test != null)
                    tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("tests/tests_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if (accion.equals("insertar")){
            request.setAttribute("op", "Literales de la plantilla de test");
            request.getRequestDispatcher("tests/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("plantillas_test")) {
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
                    "Ciclo",
                    "Observación"
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
                request.getRequestDispatcher("tests/plantillas_test.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if(accion.equals("tests")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "select * from test";
                String[][] materias = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Test",
                    "Ciclo",
                    "Estado"
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
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
//                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado test");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (materias!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("tests/tests.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("plantillas_literal")){
        try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql =   "select\n" +
                        "   idplantilla_literal, literal,\n" +
                        "    (select tipo from tipo where idtipo = pl.idtipo) as tipo,\n" +
                        "    idplantilla_test\n" +
                        "from plantilla_literal pl";
                
                String[][] pliterales = null;
                if(request.getParameter("idplantilla_test")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add(request.getParameter("idplantilla_test"));
                    request.setAttribute("idplantilla_test", request.getParameter("idplantilla_test"));
                    request.setAttribute("ciclo", request.getParameter("ciclo"));
                    request.setAttribute("observacion", request.getParameter("observacion"));
                    sql+=" where pl.idplantilla_test = ?";
                    pliterales = Operaciones.consultar(sql, params);
                }
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
//                tab.setEliminable(true);
                //boton actualizar
//                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Plantillas_literal?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Plantillas_literal?accion=modificar");
                //pagina encargada de seleccion para operaciones
//                tab.setPaginaSeleccionable("/Plantillas_literal?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
//                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado plantillas-literal");
                //imprime la tabla en pantalla
                
                String sqltipo = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sqltipo, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo t = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(t);
                }
                request.setAttribute("Tipos", listaTipos); 
                
                String tabla01 = "No hay datos";
                if(pliterales != null)
                    tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("tests/insertar_modificar.jsp").forward(request, response);
                
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
        }
        else if(accion.equals("literales")){
        try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql =   "select\n" +
                        "   idliteral, literal,\n" +
                        "    (select tipo from tipo where idtipo = l.idtipo) as tipo,\n" +
                        "    idtest\n" +
                        "from literal l";
                
                String[][] pliterales = null;
                if(request.getParameter("idtest")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add(request.getParameter("idtest"));
                    request.setAttribute("idtest", request.getParameter("idtest"));
                    request.setAttribute("ciclo_test", request.getParameter("ciclo_test"));
                    sql+=" where l.idtest = ?";
                    pliterales = Operaciones.consultar(sql, params);
                }
                String[] cabeceras = new String[]{
                "ID Literal",
                "Literal",
                "Tipo",
                "ID Test"
                };
                Tabla tab = new Tabla(pliterales, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                tab.setEliminable(true);
                tab.setModificable(true);
                tab.setPageContext(request.getContextPath());
                tab.setPaginaEliminable("/Plantillas_literal?accion=eliminar");
                tab.setPaginaModificable("/Plantillas_literal?accion=modificar");
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                tab.setPie("Resultado literal");
                
                String sqltipo = "select * from tipo";
                List<Tipo> listaTipos = new ArrayList();
                String[][] rs = Operaciones.consultar(sqltipo, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Tipo t = new Tipo(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i]);
                    listaTipos.add(t);
                }
                request.setAttribute("Tipos", listaTipos); 
                
                String tabla01 = "No hay datos";
                if(pliterales != null)
                    tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("tests/tests_consulta.jsp").forward(request, response);
                
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
        }else if(accion.equals("insertar")) {
            request.getRequestDispatcher("tests/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                request.setAttribute("op", "Literales de test");
                
                Test t = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Test());
                request.setAttribute("test", t);
                
                String sql =   
                    "SELECT\n" +
                    "	l.idliteral, l.literal, t.tipo, l.idtest\n" +
                    "FROM literal l, tipo t\n" +
                    "WHERE \n" +
                    "	l.idtipo = t.idtipo\n" +
                    "	AND idtest = ?;";
                
                String[][] pliterales = null;
//                if(reqCuest.getParameter("idtest")!=null) {
                List<Object> params = new ArrayList<>();
                params.add(t.getIdtest());
                request.setAttribute("idtest", request.getParameter("idtest"));
                request.setAttribute("ciclo_test", request.getParameter("ciclo_test"));
//                    sql+=" where l.idtest = ?";
                pliterales = Operaciones.consultar(sql, params);
//                }
                String[] cabeceras = new String[]{
                "ID Literal",
                "Literal",
                "Tipo",
                "ID Test"
                };
                Tabla tab = new Tabla(pliterales, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                tab.setEliminable(true);
                tab.setModificable(true);
                tab.setPageContext(request.getContextPath());
                tab.setPaginaEliminable("/Literales?accion=eliminar");
                tab.setPaginaModificable("/Literales?accion=modificar");
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                tab.setPie("Resultado literal");
                String tabla01 = "No hay datos";
                if(pliterales != null)
                    tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("tests/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Test t = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Test());
                
                
                if(t.getIdtest()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Tests");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String idtest = request.getParameter("txtIdtest");
        String idplantilla_test = request.getParameter("txtIdplantilla_test");
        String ciclo_actual = request.getParameter("txtCiclo");
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            boolean insert = false;
            Test t = new Test();
            t.setCiclo(ciclo_actual);
            t.setEstado("ACTIVO");
            if(idtest != null && !idtest.equals("")){
                t.setIdtest(Integer.parseInt(idtest));
                t = Operaciones.actualizar(t.getIdtest(), t);
            }else{
                t = Operaciones.insertar(t);
                insert = true;
            }
            if(t.getIdtest() != 0 && insert){
                String query = "insert into literal(literal, idtipo, idtest) select literal, idtipo, ? from plantilla_literal where idplantilla_test = ?";
                PreparedStatement st = conn.getConexion().prepareStatement(query);
                st.setInt(1, t.getIdtest());
                st.setInt(2, Integer.parseInt(idplantilla_test));

                if(st.executeUpdate() != 0){                    
                    query = "UPDATE test SET estado = 'INACTIVO' WHERE idtest <> ?";
                    PreparedStatement st2 = conn.getConexion().prepareStatement(query);
                    st2.setInt(1, t.getIdtest());
                    st2.executeUpdate();
                    request.getSession().setAttribute("resultado", 1);
                }else
                    request.getSession().setAttribute("resultado", 0);
            }
            
            Operaciones.commit();
        } catch(Exception ex) {
        try {
            Operaciones.rollback();
            request.getSession().setAttribute("resultado", 2);
        } catch (SQLException ex1) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex1);
        }
        } finally {
            try {
                Operaciones.cerrarConexion();
                response.sendRedirect(request.getContextPath()+"/Tests");
            } catch (SQLException ex) {
                Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                
    }
}