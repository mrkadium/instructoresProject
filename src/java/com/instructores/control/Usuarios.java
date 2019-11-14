package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Hash;
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

@WebServlet(name = "Usuarios", urlPatterns = {"/Usuarios"})
public class Usuarios extends HttpServlet {
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
                        "    u.idusuario, u.usuario,\n" +
                        "    concat(u.nombres, ' ', u.apellidos) as nombre,\n" +
                        "    (select rol from rol where idrol = u.idrol) as rol\n" +
                        "from usuario u where nombres like ? or apellidos like ?";
                } else {
                sql =   
                    "SELECT\n" +
                    "	a.idusuario,\n" +
                    "    a.usuario,\n" +
                    "    CONCAT(a.nombres, ' ', a.apellidos) as nombre,\n" +
                    "    a.idrol,\n" +
                    "    b.rol\n" +
                    "FROM usuario a, rol b\n" +
                    "WHERE\n" +
                    "	a.idrol = b.idrol\n" +
                    ";";
                }
                
                String sqlrol = "select * from rol";
                List<Rol> listaRoles = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Rol r = new Rol(Integer.parseInt(rs[0][i]), rs[1][i]);
                    listaRoles.add(r);
                }
                request.setAttribute("Roles", listaRoles);
                
                
                String[][] usuarios = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    usuarios = Operaciones.consultar(sql, params);
                } else {
                    usuarios = Operaciones.consultar(sql, null);
                }
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Usuario",
                "Usuario",
                "Nombre",
                "ID Rol",
                "Rol"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(usuarios, //array que contiene los datos
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
                tab.setPaginaEliminable("/Usuarios?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Usuarios?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Usuarios?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado usuarios");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("usuarios/usuarios_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        } else if(accion.equals("insertar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlrol = "select * from rol";
                List<Rol> listaRoles = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Rol r = new Rol(Integer.parseInt(rs[0][i]), rs[1][i]);
                    listaRoles.add(r);
                }
                request.setAttribute("Roles", listaRoles);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("usuarios/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("modificar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sqlrol = "select * from rol";
                List<Rol> listaRoles = new ArrayList();
                String[][] rs = Operaciones.consultar(sqlrol, new ArrayList());
                for(int i=0; i<rs[0].length; i++){
                    Rol r = new Rol(Integer.parseInt(rs[0][i]), rs[1][i]);
                    listaRoles.add(r);
                }
                request.setAttribute("Roles", listaRoles);
                
                
                
                List<Usuario> user = new ArrayList();
                List<Object> param = new ArrayList();
                param.add(Integer.parseInt(request.getParameter("id")));
                String[][] rsuser = Operaciones.consultar("select * from usuario where idusuario = ?", param);
                for(int i=0; i<rsuser[0].length; i++){
                    Usuario u = new Usuario(Integer.parseInt(rsuser[0][i]), rsuser[1][i], rsuser[2][i], rsuser[3][i], rsuser[4][i], Integer.parseInt(rsuser[5][i]));
                    user.add(u);
                }
                
                request.setAttribute("usuario", user.get(0));
                
                request.getSession().setAttribute("contra", user.get(0).getClave());
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.getRequestDispatcher("usuarios/insertar_modificar.jsp").forward(request, response);
        } else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Usuario u = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Usuario());
                
                if(u.getIdusuario()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Usuarios");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String accion = request.getParameter("accion");
        switch(accion) {
            case "insertar_modificar": {
                String idUsuario = request.getParameter("txtIdusuario");
                String usuario = request.getParameter("txtUsuario");
                String nombres = request.getParameter("txtNombres");
                String apellidos = request.getParameter("txtApellidos");
                String clave = request.getParameter("txtClave");
                String idRol = request.getParameter("cmbRoles");              
                
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    if(idUsuario!=null && !idUsuario.equals("")) {
                        Usuario u = new Usuario();
                        String contraAnterior = request.getSession().getAttribute("contra").toString();
                        
                        //si la clave es igual a la de antes, no se pone el Hash
                        if(clave.equals(contraAnterior)){
                            u = new Usuario(Integer.parseInt(idUsuario), usuario, nombres, apellidos, clave, Integer.parseInt(idRol));
                        }else{
                            u = new Usuario(Integer.parseInt(idUsuario), usuario, nombres, apellidos, Hash.generarHash(clave, Hash.SHA256), Integer.parseInt(idRol));
                        }
                        
                        u = Operaciones.actualizar(u.getIdusuario(), u);
                        
                        if(u.getIdusuario()!=0) {
                            request.getSession().setAttribute("resultado", 1);
                        } else {
                            request.getSession().setAttribute("resultado", 0);
                        }
                    } else {
                        Usuario u = new Usuario();
                        u.setUsuario(usuario);
                        u.setNombres(nombres);
                        u.setApellidos(apellidos);
                        u.setClave(Hash.generarHash(clave, Hash.SHA256));
                        u.setIdrol(Integer.parseInt(idRol));
                        
                        u = Operaciones.insertar(u);
                        if(u.getIdusuario()!=0) {
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
                    Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Usuarios");
                break;
            }
            case "eliminar": {
            break;
            }
        }
    }
}