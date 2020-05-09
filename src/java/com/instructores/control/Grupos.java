package com.instructores.control;
import com.instructores.entidad.*;
import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Pagination;
import com.instructores.utilerias.Tabla;
import com.instructores.viewmodels.ViewModelGrupoMod;
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

@WebServlet(name = "Grupos", urlPatterns = {"/Grupos"})
public class Grupos extends HttpServlet {
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
                        "   idgrupo,\n" +
                        "    (select materia from materia where idmateria = g.idmateria) as materia,\n" +
                        "    (select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idinstructor) as instructor,\n" +
                        "    (select concat(nombres, ' ', apellidos) from usuario where idusuario = g.idcatedratico) as catedratico,\n" +
                        "    numero_grupo, clave, ciclo\n" +
                        "from grupo g where idgrupo like ?";
                } else {
                sql =   "SELECT\n" +
                        "	a.idgrupo, b.materia,\n" +
                        "    CONCAT(c.nombres,' ',c.apellidos) AS instructor,\n" +
                        "    CONCAT(d.nombres,' ',d.apellidos) AS catedratico,\n" +
                        "    a.numero_grupo, a.clave, a.ciclo,\n" +
                        "    IF(a.idtest IS NULL,'-',a.idtest) as idtest, a.estado\n" +
                        "FROM \n" +
                        "	grupo a, materia b, usuario c, usuario d\n" +
                        "WHERE\n" +
                        "	a.idmateria = b.idmateria\n" +
                        "    AND a.idinstructor = c.idusuario\n" +
                        "    AND a.idcatedratico = d.idusuario\n" +
                        ";";
                }
                String[][] grupos = null;
                if(request.getParameter("txtBusqueda")!=null) {
                    List<Object> params = new ArrayList<>();
                    params.add("%"+request.getParameter("txtBusqueda").toString()+"%");
                    grupos = Operaciones.consultar(sql, params);
                } else {
                    grupos = Operaciones.consultar(sql, null);
                }
                
                
                
                Pagination p = new Pagination(grupos[0].length, 10, request);
                request.setAttribute("pag", p);
                
                
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                "ID Grupo",
                "Materia",
                "Instructor",
                "Catedrático",
                "Número de grupo",
                "Clave",
                "Ciclo",
                "ID Test",
                "Estado"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(grupos, //array que contiene los datos
                "80%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                tab.setLimiteInferior(p.getCurrentLowerLimit());
                tab.setLimiteSuperior(p.getCurrentUpperLimit());
                //boton eliminar
                tab.setEliminable(true);
                //boton actualizar
                tab.setModificable(true);
                //url del proyecto
                tab.setPageContext(request.getContextPath());
                //pagina encargada de eliminar
                tab.setPaginaEliminable("/Grupos?accion=eliminar");
                //pagina encargada de actualizacion
                tab.setPaginaModificable("/Grupos?accion=modificar");
                //pagina encargada de seleccion para operaciones
                tab.setPaginaSeleccionable("/Grupos?accion=modificar");
                //icono para modificar y eliminar
                tab.setIconoModificable(Tabla.ICON.MODIFICAR);
                tab.setIconoEliminable(Tabla.ICON.ELIMINAR);
                //columnas seleccionables
                tab.setColumnasSeleccionables(new int[]{1});
                //pie de tabla
                tab.setPie("Resultado grupos");
                //imprime la tabla en pantalla
                String tabla01 = tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("valor", request.getParameter("txtBusqueda"));
                request.getRequestDispatcher("grupos/grupos_consulta.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    io.println(ex.getMessage());
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
            //request.getRequestDispatcher("paises/paises_consulta.jsp").forward(request, response);
        }else if(accion.equals("insertar")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                request.getRequestDispatcher("grupos/insertar_modificar.jsp").forward(request, response);
                
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
        }else if(accion.equals("modificar")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                int id = Integer.parseInt(request.getParameter("id"));
                Grupo g = Operaciones.get(id, new Grupo());
                Materia m = Operaciones.get(g.getIdmateria(), new Materia());
                Usuario c = Operaciones.get(g.getIdcatedratico(), new Usuario());
                Usuario i = Operaciones.get(g.getIdinstructor(), new Usuario());
                Test t = Operaciones.get(g.getIdtest(), new Test());
                
                request.setAttribute("grupo", g);
                request.setAttribute("m", m);
                request.setAttribute("c", c);
                request.setAttribute("i", i);
                request.setAttribute("t", t);
                request.getRequestDispatcher("grupos/insertar_modificar.jsp").forward(request, response);
                
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
        } else if(accion.equals("materias")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = 
                    "SELECT\n" +
                    "	a.idmateria, a.materia, a.alias, b.carrera\n" +
                    "FROM materia a, carrera b\n" +
                    "WHERE\n" +
                    "	a.idcarrera = b.idcarrera\n" +
                    ";";
                
                String[][] rs = Operaciones.consultar(sql, new ArrayList());
                
                String[] cabeceras = new String[]{
                    "ID Materia",
                    "Materia",
                    "Alias",
                    "Carrera"
                };
                
                Tabla tab = new Tabla(rs,
                "80%",
                Tabla.STYLE.TABLE01,
                Tabla.ALIGN.LEFT,
                cabeceras);
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                tab.setPie("Resultado grupos");
                
                String tabla01 = tab.getTabla();
                
                request.setAttribute("tabla", tabla01);
                
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
                    request.getRequestDispatcher("grupos/materias.jsp").forward(request, response);
                } catch (SQLException ex) {
                    Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(accion.equals("instructores")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = 
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
                String[][] instructores = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID Usuario",
                    "Usuario",
                    "Nombre",
                    "ID Rol",
                    "Rol"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(instructores, //array que contiene los datos
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
                tab.setPie("Resultado instructores");
                String tabla01="No hay datos";
                if (instructores!=null)
                    tabla01= tab.getTabla();
                
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("grupos/instructores.jsp").forward(request, response);
                
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
        } else if(accion.equals("catedraticos")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = 
                    "SELECT\n" +
                    "	a.idusuario,\n" +
                    "    a.usuario,\n" +
                    "    CONCAT(a.nombres, ' ', a.apellidos) as nombre,\n" +
                    "    a.idrol,\n" +
                    "    b.rol\n" +
                    "FROM usuario a, rol b\n" +
                    "WHERE\n" +
                    "	a.idrol = b.idrol\n" +
                    "	AND b.rol NOT IN ('instructor')\n" +
                    ";";
                String[][] catedraticos = Operaciones.consultar(sql, null);
                String[] cabeceras = new String[]{
                    "ID Usuario",
                    "Usuario",
                    "Nombre",
                    "ID Rol",
                    "Rol"
                };
                Tabla tab = new Tabla(catedraticos, //array que contiene los datos
                "100%", //ancho de la tabla px | %
                Tabla.STYLE.TABLE01, //estilo de la tabla
                Tabla.ALIGN.LEFT, // alineacion de la tabla
                cabeceras); //array con las cabeceras de la tabla
                tab.setPageContext(request.getContextPath());
                tab.setFilaSeleccionable(true);
                tab.setMetodoFilaSeleccionable("_Seleccionar_");
                tab.setIconoModificable("/iconos/edit.png");
                tab.setIconoEliminable("/iconos/delete.png");
                tab.setPie("Resultado catedraticos");
                String tabla01="No hay datos";
                if (catedraticos!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("grupos/catedraticos.jsp").forward(request, response);
                
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
        }else if(accion.equals("tests")){
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                String sql = 
                    "SELECT idtest, ciclo FROM test;";
                String[][] catedraticos = Operaciones.consultar(sql, null);
                //declaracion de cabeceras a usar en la tabla HTML
                String[] cabeceras = new String[]{
                    "ID",
                    "Ciclo"
                };
                //variable de tipo Tabla para generar la Tabla HTML
                Tabla tab = new Tabla(catedraticos, //array que contiene los datos
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
                tab.setPie("Resultado catedraticos");
                //imprime la tabla en pantalla
                String tabla01="No hay datos";
                if (catedraticos!=null)
                    tabla01= tab.getTabla();
                request.setAttribute("tabla", tabla01);
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("grupos/plantillas_test.jsp").forward(request, response);
                
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
        else if(accion.equals("eliminar")) {
            try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                Grupo g = Operaciones.eliminar(Integer.parseInt(request.getParameter("id")), new Grupo());
                
                if(g.getIdgrupo()!=0) {
                    request.getSession().setAttribute("resultado", 1);
                } else {
                    request.getSession().setAttribute("resultado", 0);
                }
                
                Operaciones.commit();
            } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 0);
            } finally {
                try {
                    Operaciones.cerrarConexion();
                } catch (SQLException ex) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            response.sendRedirect(request.getContextPath()+"/Grupos");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                String idGrupo = request.getParameter("txtIdgrupo");
                String idMateria = request.getParameter("txtIdmateria");
                String idInstructor = request.getParameter("txtIdinstructor");
                String idCatedratico = request.getParameter("txtIdcatedratico");
                String numeroGrupo = request.getParameter("txtNumeroGrupo");
                String ciclo = request.getParameter("txtCiclo");
                String clave = request.getParameter("txtClave");
                String idtest = request.getParameter("txtIdtest");
                String estado = request.getParameter("cmbEstado");
                try {
                    Conexion conn = new ConexionPool();
                    conn.conectar();
                    Operaciones.abrirConexion(conn);
                    Operaciones.iniciarTransaccion();
                    
                    Grupo g = new Grupo();
                    g.setIdmateria(Integer.parseInt(idMateria));
                    g.setIdinstructor(Integer.parseInt(idInstructor));
                    g.setIdcatedratico(Integer.parseInt(idCatedratico));
                    g.setNumero_grupo(numeroGrupo);
                    g.setCiclo(ciclo);
                    g.setClave(clave);
                    g.setEstado(estado);
                    if(idtest != null && !idtest.equals(""))
                        g.setIdtest(Integer.parseInt(idtest));
                    
                    if(idGrupo!=null && !idGrupo.equals("")) {
                        g.setIdgrupo(Integer.parseInt(idGrupo));
                        g = Operaciones.actualizar(g.getIdgrupo(), g);
                    } else {
                        g = Operaciones.insertar(g);
                    }                        
                    if(g.getIdgrupo()!=0) {
                        request.getSession().setAttribute("resultado", 1);
                    } else {
                        request.getSession().setAttribute("resultado", 0);
                    }
                    
                    Operaciones.commit();
                } catch(Exception ex) {
                try {
                    Operaciones.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex1);
                }
                request.getSession().setAttribute("resultado", 2);
                ex.printStackTrace();
                } finally {
                    try {
                        Operaciones.cerrarConexion();
                    } catch (SQLException ex) {
                        Logger.getLogger(Roles.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect(request.getContextPath()+"/Grupos");
//                break;
//            }
//            case "eliminar": {
//            break;
//            }
//        }
    }
}