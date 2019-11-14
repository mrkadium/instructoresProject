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

@WebServlet(name = "Permisos", urlPatterns = {"/Permisos"})
public class Permisos extends HttpServlet {
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
                sql =   "select\n" +
                        "	p.idpermiso,\n" +
                        "    (select menu from menu where idmenu = p.idmenu) as menu,\n" +
                        "    (select rol from rol where idrol = p.idrol) as rol\n" +
                        "from permiso p where idpermiso like ?";
                } else {
                sql =   
                    "SELECT\n" +
                    "	a.idpermiso,\n" +
                    "    b.menu,\n" +
                    "    c.rol\n" +
                    "FROM \n" +
                    "	permiso a, \n" +
                    "	menu b, \n" +
                    "    rol c\n" +
                    "WHERE \n" +
                    "	a.idmenu = b.idmenu\n" +
                    "    AND a.idrol = c.idrol\n" +
                    ";";
                }
                
                //----------CANTIDAD DE FILAS PARA LA BÚSQUEDA EN LA TABLA
                
                String rCount = "select count(*) from permiso";
                String[][] rowCount = Operaciones.consultar(rCount, null);
                request.setAttribute("rowCount", rowCount[0][0]);
                
                //--------------------------------------------------------
                
                String[][] permisos = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    permisos = Operaciones.consultar(sql, params);
                } else {
                    permisos = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Permiso",
                "Menú",
                "Rol"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(permisos, //array que contiene los datos
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
                tab.setPaginaEliminable("/Permisos?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Permisos?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Permisos?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado permisos");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("permisos/permisos_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    io.println(ex.getMessage());
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();            
                
                String sql = "SELECT\n" +
                            "	a.idpermiso,\n" +
                            "    b.menu,\n" +
                            "    c.rol\n" +
                            "FROM \n" +
                            "	permiso a, \n" +
                            "	menu b, \n" +
                            "    rol c\n" +
                            "WHERE \n" +
                            "	a.idmenu = b.idmenu\n" +
                            "    AND a.idrol = c.idrol\n" +
                            "--    AND a.idmenu = 1\n" +
                            ";";
                
                String[][] materias = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Permiso",
                "Menú",
                "Rol",
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
                tab.setPie("Resultado Materias");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (materias!=null)
                    tabla01= tab.getTabla();              
                request.setAttribute("tabla", tabla01);
                
                
                
                String sqlmenu = "select * from menu";
                List<Menu> listaMenus = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlmenu, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    int f1 = Integer.parseInt(rs[0][i]);
                    String f2 = rs[1][i];
                    String f3 = rs[2][i];
                    String f4 = rs[3][i];
                    String f5 = rs[4][i];
                    String f6 = rs[5][i];
                    int f7;
                    if(rs[6][i]==null || rs[6][i].equals("null")){
                        f7 = 0;
                    }else{
                        f7 = Integer.parseInt(rs[6][i]);
                    }                    
                    Menu p = new Menu(f1, f2, f3, f4, f5, f6, f7);
                    listaMenus.add(p);
                }
                request.setAttribute("Menus", listaMenus);
                
                String sqlrol = "select * from rol";
                List<Rol> listaRoles = new ArrayList();
                String[][] rs2 = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rs2[0].length; i++){
                    Rol r = new Rol(Integer.parseInt(rs2[0][i]), rs2[1][i]);
                    listaRoles.add(r);
                }
                request.setAttribute("Roles", listaRoles);              
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("permisos/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlmenu = "select * from menu";
                List<Menu> listaMenus = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlmenu, new ArrayList()); 
                Menu m = new Menu();
                for(int i=0; i<rs[0].length; i++){
                    int f1 = Integer.parseInt(rs[0][i]);
                    String f2 = rs[1][i];
                    String f3 = rs[2][i];
                    String f4 = rs[3][i];
                    String f5 = rs[4][i];
                    String f6 = rs[5][i];
                    int f7;
                    if(rs[6][i]==null || rs[6][i].equals("null")){
                        f7 = 0;
                    }else{
                        f7 = Integer.parseInt(rs[6][i]);
                    }
                    if(f1 == Integer.parseInt(request.getParameter("id"))){
                        m.setIdmenu(f1);
                        m.setMenu(f2);
                        m.setDescripcion(f3);
                        m.setUrl(f4);
                        m.setIdcss(f5);
                        m.setImg(f6);
                        m.setIdpadre(f7);
                    }                    
                    Menu p = new Menu(f1, f2, f3, f4, f5, f6, f7);
                    listaMenus.add(p);
                }
                request.setAttribute("Menus", listaMenus);
                
                String sqlrol = "select * from rol";
                List<Rol> listaRoles = new ArrayList();
                String[][] rs2 = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rs2[0].length; i++){
                    Rol r = new Rol(Integer.parseInt(rs2[0][i]), rs2[1][i]);
                    listaRoles.add(r);
                }
                request.setAttribute("Roles", listaRoles);  
                
                Permiso p = Operaciones.get(Integer.parseInt(request.getParameter("id")), new Permiso());
                request.setAttribute("permiso", p);
                
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("permisos/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Permiso c = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Permiso());
                if(c.getIdpermiso()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Permisos");
        }  else if(accion.equals("menus")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = "SELECT \n" +
                            "	a.idmenu, a.menu, a.descripcion, a.url, a.idcss, a.img, b.menu\n" +
                            "FROM menu a\n" +
                            "LEFT OUTER JOIN menu b\n" +
                            "ON a.idpadre = b.idmenu\n" +
                            ";";
                
                String[][] materias = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Menú",
                "Menú",
                "Descripción",
                "URL",
                "ID CSS",
                "Imagen",
                "Padre"
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
                tab.setPie("Resultado Materias");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (materias!=null)
                    tabla01= tab.getTabla();
                
                
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("permisos/menus.jsp").forward(request,response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }    
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idPermiso = request.getParameter("txtIdcarrera");
                String idMenu = request.getParameter("txtIdmenu");
                String idRol = request.getParameter("cmbRol");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idPermiso!=null && !idPermiso.equals("")) {
                        Permiso p = new Permiso(Integer.parseInt(idPermiso),Integer.parseInt(idMenu),Integer.parseInt(idRol));
                        p = Operaciones.actualizar(p, new Permiso());
                        if(p.getIdpermiso()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Permiso p = new Permiso();
                        p.setIdmenu(Integer.parseInt(idMenu));
                        p.setIdrol(Integer.parseInt(idRol));
                        if(p.getIdpermiso()!=0) {
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
                    Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Permisos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Permisos");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}