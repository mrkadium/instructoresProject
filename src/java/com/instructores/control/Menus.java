package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Tabla;
import com.instructores.viewmodels.ViewModelMenu;
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

@WebServlet(name = "Menus", urlPatterns = {"/Menus"})
public class Menus extends HttpServlet {
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
                sql =   "select \n" +
                        "	m.idmenu, m.menu,\n" +
                        "	(select menu from menu where idmenu = m.idpadre) as padre\n" +
                        "from menu m where menu like ?";
                } else {
                sql =   
                    "SELECT \n" +
                    "	a.idmenu, a.menu, a.descripcion, a.url, a.idcss, a.img,\n" +
                    "    IF(b.idmenu IS NULL, 0, b.idmenu) as idpadre, \n" +
                    "    b.menu as padre\n" +
                    "FROM menu a\n" +
                    "LEFT OUTER JOIN menu b\n" +
                    "ON a.idpadre = b.idmenu\n" +
                    ";";
                }
                
                String[][] menus = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    menus = Operaciones.consultar(sql, params);
                } else {
                    menus = Operaciones.consultar(sql, null);
                }
                
                
                String sqlmenu = "select * from menu where idmenu in (select distinct idpadre from menu)";
                List<Menu> listaMenus = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlmenu, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Menu m = new Menu(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], rs[3][i], rs[4][i], rs[5][i], (rs[6][i] == null ? 0 : Integer.parseInt(rs[6][i])));
                    listaMenus.add(m);
                }
                request.setAttribute("Menus", listaMenus); 
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Menú",
                "Menú",
                "Descripción",
                "URL",
                "ID CSS",
                "Imagen",
                "ID Padre",
                "Padre"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(menus, //array que contiene los datos
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
                tab.setPaginaEliminable("/Menus?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Menus?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Menus?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado menus");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("menus/menus_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlmenu = "select * from menu";
                List<Menu> listaMenus = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlmenu, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Menu m = new Menu(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], rs[3][i], rs[4][i], rs[5][i], Integer.parseInt(rs[6][i]));
                    listaMenus.add(m);
                }
                request.setAttribute("Menus", listaMenus);                
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("menus/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                
                String sql = 
                    "SELECT \n" +
                    "	a.idmenu, a.menu, a.descripcion, a.url, a.idcss, a.img,\n" +
                    "    IF(b.idmenu IS NULL, 0, b.idmenu) as idpadre, \n" +
                    "    b.menu as padre\n" +
                    "FROM menu a\n" +
                    "LEFT OUTER JOIN menu b\n" +
                    "ON a.idpadre = b.idmenu\n" +
                    "WHERE a.idmenu = ?\n" +
                    ";";
                List<Object> params = new ArrayList<>();
                params.add(request.getParameter("id"));
                String[][] rs = Operaciones.consultar(sql, params);
                ViewModelMenu m = new ViewModelMenu(
                    Integer.parseInt(rs[0][0]),
                    rs[1][0],
                    rs[2][0],
                    rs[3][0],
                    rs[4][0],
                    rs[5][0],
                    Integer.parseInt(rs[6][0]),
                    rs[7][0]
                );
                
                
                
                
//                String sqlmenu = "select * from menu";                
//                List<Menu> listaMenus = new ArrayList();
//                String[][] rs = Operaciones.consultar(sqlmenu, new ArrayList());
//                Menu m = new Menu();
//                for(int i=0; i<rs[0].length; i++){
//                    int f1 = Integer.parseInt(rs[0][i]);
//                    String f2 = rs[1][i];
//                    String f3 = rs[2][i];
//                    String f4 = rs[3][i];
//                    String f5 = rs[4][i];
//                    String f6 = rs[5][i];
//                    //VERIFICA QUE EL CAMPO NO SEA NULO
//                    int f7;
//                    if(rs[6][i]==null || rs[6][i].equals("null")){
//                        f7 = 0;
//                    }else{
//                        f7 = Integer.parseInt(rs[6][i]);
//                    }
//                    if(f1 == Integer.parseInt(request.getParameter("id"))){
//                        m.setIdmenu(f1);
//                        m.setMenu(f2);
//                        m.setDescripcion(f3);
//                        m.setUrl(f4);
//                        m.setIdcss(f5);
//                        m.setImg(f6);
//                        m.setIdpadre(f7);
//                    }                    
//                    Menu p = new Menu(f1, f2, f3, f4, f5, f6, f7);
//                    listaMenus.add(p);
//                }
//                request.setAttribute("Menus", listaMenus);
                
                request.setAttribute("menu", m);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    io.println("3"+ex.getMessage());
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    io.println("3"+ex.getMessage());
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("menus/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Menu m = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Menu());
                if(m.getIdmenu()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Menus");
        } else if(accion.equals("padre")){
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
                request.getRequestDispatcher("menus/padres.jsp").forward(request,response);
                
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
                String idMenu = request.getParameter("txtIdmenu");
                String menu = request.getParameter("txtMenu");
                String descripcion = request.getParameter("txtDescripcion");
                String url = request.getParameter("txtUrl");
                String idcss = request.getParameter("txtIdcss");
                String img = request.getParameter("txtImg");
                String idPadre = request.getParameter("txtIdpadre");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idMenu!=null && !idMenu.equals("")) {
                        Menu m = new Menu(Integer.parseInt(idMenu),menu,descripcion,url,idcss,img,Integer.parseInt(idPadre));                        
                        m = Operaciones.actualizar(Integer.parseInt(idMenu), m);
                        if(m.getIdmenu()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Menu m = new Menu();
                        m.setMenu(menu);
                        m.setUrl(url);
                        m.setDescripcion(descripcion);
                        m.setIdcss(idcss);
                        m.setImg(img);
                        m.setIdpadre(Integer.parseInt(idPadre));                        
                        m = Operaciones.insertar(m);
                        if(m.getIdmenu()!=0) {
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
                    Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Menus.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Menus");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}