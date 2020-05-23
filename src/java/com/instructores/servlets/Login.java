package com.instructores.servlets;

import com.instructores.viewmodels.*;
import com.instructores.conexion.*;
import com.instructores.entidad.*;
import com.instructores.operaciones.*;
import com.instructores.utilerias.Hash;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("gr");
        sesion.removeAttribute("listados");
        sesion.invalidate();
//        response.sendRedirect("Login");
        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
    }
    
    private void enviarTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        HttpSession sesion = request.getSession();
        PrintWriter io = response.getWriter();
        
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            //CONSULTAR LOS TIPOS QUE TIENEN guarda_valor = true
            
            
            
            String sql = "SELECT a.idvaloracion FROM valoracion a, tipo b WHERE a.idtipo = b.idtipo AND b.tipo = 'cuantitativa'";
            String tipos[][] = Operaciones.consultar(sql, new ArrayList());
            int idValoracionDeTipoCuantitativo = Integer.parseInt(tipos[0][0]);
            
//            sql = "SELECT idtest FROM test WHERE estado = 'ACTIVO';";
//            tipos = Operaciones.consultar(sql, null);
            
            ViewModelGrupo g = (ViewModelGrupo)sesion.getAttribute("gr");
            io.println(g.getMateria());

//            Creando la nueva evaluación
            Evaluacion e = new Evaluacion();
            e.setFecha_realizacion(new Date());
            e.setIdgrupo(g.getIdgrupo());
            if(!request.getParameter("observacion").equals("")){
                e.setObservacion(request.getParameter("observacion"));
            }else{
                e.setObservacion("");
            }
//            e.setIdtest(Integer.parseInt(tipos[0][0]));
            e = Operaciones.insertar(e);
            
            List<Literal_evaluacion> le = new ArrayList();
            List<Literal> getLiterales = (List<Literal>)sesion.getAttribute("literales");
            int cont = 1;
            for(Literal l: getLiterales){
                String literal = "literal"+(l.getIdliteral()); //concatena para obtener name definido en jsp
                int valoracion = 0;
                int calificacion = 0;
                if(l.getIdtipo() == 2){ //IDTIPO = 2 ES EL DE LAS CUANTITATIVAS
                    valoracion = idValoracionDeTipoCuantitativo;
                    calificacion = Integer.parseInt(request.getParameter(literal));
                }else
                    valoracion = Integer.parseInt(request.getParameter(literal));
                
                
//                String car = Character.toString(request.getParameter(literal).charAt(request.getParameter(literal).length() -1));
//                if(car.equals("@")){
//                    if(!request.getParameter(literal).substring(0,request.getParameter(literal).length()-1).equals("")){
//                        calificacion = Integer.parseInt(request.getParameter(literal).substring(0,request.getParameter(literal).length()-1));
//                    }                    
//                    valoracion = idValoracionDeTipoCuantitativo;
//                }else{
//                    valoracion = Integer.parseInt(request.getParameter(literal));
//                }
                
                
                Literal_evaluacion literalEvaluacion = new Literal_evaluacion();
                literalEvaluacion.setIdevaluacion(e.getIdevaluacion());
                literalEvaluacion.setIdliteral(l.getIdliteral());
                literalEvaluacion.setIdvaloracion(valoracion);
                literalEvaluacion.setCalificacion(calificacion);
                literalEvaluacion = Operaciones.insertar(literalEvaluacion);
            }
            request.setAttribute("error", 3);

            sesion.removeAttribute("gr");
            sesion.removeAttribute("literales");
            sesion.invalidate();
            
            Operaciones.commit();
        }catch(Exception ex) {
            io.println(ex.getMessage());
            request.setAttribute("error", 4);
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
    }
    
    private void iniciarSesionEst(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{
        request.getSession().invalidate();
        String clave = request.getParameter("txtClave") != null ? request.getParameter("txtClave") : "";
        boolean errorFound = false;
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            if(con.getConexion() == null){
                request.setAttribute("error", 0);
                errorFound = true;
            }else{
                //OBTENEMOS LOS DATOS DE LA TABLA 'GRUPO' Y LOS ALMACENAMOS EN EL VIEWMODEL
                HttpSession sesion = request.getSession();
                String sql = 
                    "SELECT\n" +
                    "	a.idgrupo, b.materia,\n" +
                    "    CONCAT(c.nombres, ' ', c.apellidos) AS instructor,\n" +
                    "    CONCAT(d.nombres, ' ', d.apellidos) AS catedratico,\n" +
                    "    a.numero_grupo, a.ciclo, a.clave, a.idtest, a.estado\n" +
                    "FROM grupo a, materia b, usuario c, usuario d\n" +
                    "WHERE\n" +
                    "	a.idmateria = b.idmateria\n" +
                    "    AND a.idinstructor = c.idusuario\n" +
                    "    AND a.idcatedratico = d.idusuario\n" +
                    "   AND a.clave = ? \n" +
                    ";";
                List<Object> param = new ArrayList();
                param.add(clave);
                String[][] rs = Operaciones.consultar(sql, param);
                ViewModelGrupo gr = new ViewModelGrupo();
                if(rs != null){
                    gr.setIdgrupo(Integer.parseInt(rs[0][0]));
                    gr.setMateria(rs[1][0]);
                    gr.setInstructor(rs[2][0]);
                    gr.setCatedratico(rs[3][0]);
                    gr.setNumero_grupo(rs[4][0]);
                    gr.setCiclo(rs[5][0]);
                    gr.setClave(rs[6][0]);
                    gr.setIdtest(rs[7][0] != null ? Integer.parseInt(rs[7][0]) : 0);
                    gr.setEstado(rs[8][0]);
                }
                if(rs != null && gr.getEstado().equals("Habilitado") && gr.getIdtest() != 0){
                    sesion.setAttribute("gr", gr);
                    
                    String sqlTipos = "select * from tipo";
                    String[][] rsTipos = Operaciones.consultar(sqlTipos, new ArrayList());
                    if(rsTipos != null){
                        List<Tipo> getTipos = new ArrayList();
                        for(int i=0; i<rsTipos[0].length; i++){
                            Tipo t = new Tipo(Integer.parseInt(rsTipos[0][i]), rsTipos[1][i], rsTipos[2][i]);
                            getTipos.add(t);
                        }
                        sesion.setAttribute("tipos", getTipos);
                    }
                    
                    String sqlValoraciones = "select * from valoracion";
                    String[][] rsValoraciones = Operaciones.consultar(sqlValoraciones, new ArrayList());
                    if(rsValoraciones != null){
                        List<Valoracion> getValoraciones = new ArrayList();
                        for(int i=0; i<rsValoraciones[0].length; i++){
                            Valoracion v = new Valoracion(Integer.parseInt(rsValoraciones[0][i]), rsValoraciones[1][i], Integer.parseInt(rsValoraciones[2][i]));
                            getValoraciones.add(v);
                        }
                        sesion.setAttribute("valoraciones", getValoraciones);
                    }         
                
                    String sqlLiterales = "select * from literal where idtest = ?";
                    List<Object> params = new ArrayList();
                    params.add(gr.getIdtest());
                    String[][] rsLiterales = Operaciones.consultar(sqlLiterales, params);
                    if(rsLiterales != null){
                        List<Literal> getLiterales = new ArrayList();
                        for(int i=0; i<rsLiterales[0].length; i++){
                            Literal lit = new Literal(Integer.parseInt(rsLiterales[0][i]), rsLiterales[1][i],Integer.parseInt(rsLiterales[2][i]),Integer.parseInt(rsLiterales[3][i]));
                            getLiterales.add(lit);
                        }
                        sesion.setAttribute("literales", getLiterales);
                        response.sendRedirect("Test");                   
                    }else{
                        request.setAttribute("error", 2);
                        Operaciones.rollback();
                        errorFound = true;
                    }
                }else if(rs == null) {
                    request.setAttribute("error", 1); 
                    errorFound = true;
                }else{
                    if(gr.getIdtest() == 0) {request.setAttribute("error", 2); errorFound = true;}
                    if(!gr.getEstado().equals("Habilitado")) {
                        if(gr.getEstado().equals("Inhabilitado")) request.setAttribute("error", 5); 
                        if(gr.getEstado().equals("Finalizado")) request.setAttribute("error", 7); 
                        errorFound = true;
                    }
                }
            }
            
            Operaciones.commit();
        }catch(Exception e){
            Operaciones.rollback();
            errorFound = true;
            request.setAttribute("error", 0);
        }finally{
            if(errorFound) request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
            Operaciones.cerrarConexion();
        }
    }
    
    private void iniciarSesionAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException{
        String usuario = request.getParameter("txtUsuario");
        String clave = request.getParameter("txtContra");
        PrintWriter io = response.getWriter();
        if(usuario == null){usuario = "";}
        if(clave == null){clave = "";}
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            if(con.getConexion() == null){
                request.setAttribute("error", 1);
                request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
            }else{
                HttpSession sesion = request.getSession();
                Usuario u = new Usuario();
                String sql = 
                    "SELECT\n" +
                    "	a.*, b.rol\n" +
                    "FROM usuario a, rol b\n" +
                    "WHERE \n" +
                    "	a.idrol = b.idrol\n" +
                    "    AND a.usuario = ?\n" +
                    ";";
                List<Object> param = new ArrayList();
                param.add(usuario);
                String[][] rs = Operaciones.consultar(sql, param);
                if(rs == null){                    
                    request.setAttribute("error", 2);
                    Operaciones.rollback();
                    request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
                }
                u = new Usuario(Integer.parseInt(rs[0][0]), rs[1][0], rs[2][0], rs[3][0], rs[4][0], Integer.parseInt(rs[5][0]));
                if(u.getClave().equals(Hash.generarHash(clave, Hash.SHA256))){
                    sesion.setAttribute("Idusuario", u.getIdusuario());
                    sesion.setAttribute("Usuario", u.getIdusuario());
                    sesion.setAttribute("Nombre", u.getNombres() + " " + u.getApellidos());
                    sesion.setAttribute("Rol", u.getIdrol());
                    sesion.setAttribute("user", u.getUsuario());
                    sesion.setAttribute("u", u);
                    sesion.setAttribute("Rolname", rs[6][0]);
                    
                    
                    String rol = "select rol from rol where idrol = ?";
                    List<Object> params = new ArrayList();
                    params.add(u.getIdrol());
                    String[][] rsRol = Operaciones.consultar(rol, params);
                    sesion.setAttribute("nombre_rol", rsRol[0][0]);
                    
                    
                    List<Menu> permisos = getPermisos(u.getIdrol());
                    List<Menu> MenuPrincipal = new ArrayList();
                    for(int i=0; i<permisos.size(); i++){
                        if(permisos.get(i).getIdpadre() == 0){
                            MenuPrincipal.add(permisos.get(i));
                        }
                    }

                    sesion.setAttribute("MenuPrincipal", MenuPrincipal);
                    sesion.setAttribute("Permisos", permisos);
//                    for(Menu m: MenuPrincipal){
//                        io.println(m.getMenu() + m.getIdcss());
//                    }
//                    io.println(u.getIdusuario()+u.getUsuario()+u.getNombres()+u.getIdrol()+permisos.size());
                    response.sendRedirect("Principal");  
                }else{
                    request.setAttribute("error", 2);
                    Operaciones.rollback();
                    request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
                }
            }
            
            Operaciones.commit();
        }catch(Exception e){
            Operaciones.rollback();
            io.print(e);
        }finally{
            Operaciones.cerrarConexion();
        }
    }
    
    private List<Menu> getPermisos(Integer idrol) throws SQLException{
        List<Menu> permisos = new ArrayList();
        try{
            String sql = "select * from menu where idmenu in (select idmenu from permiso where idrol = ?)";
            List<Object> param = new ArrayList();
            param.add(idrol);
            String[][] rs = Operaciones.consultar(sql, param);
            for(int i=0; i<rs[0].length; i++){
                Menu m = new Menu(Integer.parseInt(rs[0][i]), rs[1][i], rs[2][i], rs[3][i], rs[4][i], rs[5][i], Integer.parseInt(rs[6][i]==null?"0":rs[6][i]));
                permisos.add(m);
            }
        }catch(Exception e){
            Operaciones.rollback();
        }
        return permisos;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter io = response.getWriter();
            io.println(request.getParameter("accion"));
            String accion = getServletConfig().getInitParameter("accion") == null ? request.getParameter("accion") : getServletConfig().getInitParameter("accion");
                       
//            response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma", "no-cache");
//            response.setDateHeader("Expires", 0);
            
            
            if(accion == null){
                request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
            }else{
                switch(accion){
                    case "admin":
                        request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
                    break;
                    case "loginAdmin":
                        iniciarSesionAdmin(request, response);
                    break;
                    case "loginEst":
                        iniciarSesionEst(request, response);
                    break;
                    case "logout":
                        logout(request, response);
                    break;
                    case "enviarTest":
                        enviarTest(request, response);
                    break;
                    case "test":
                        request.getRequestDispatcher("jsp/test.jsp").forward(request, response);
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter io = response.getWriter();
            
//            response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
//            response.setHeader("Pragma", "no-cache");
//            response.setDateHeader("Expires", 0);
            
            String accion = getServletConfig().getInitParameter("accion") == null ? request.getParameter("accion") : getServletConfig().getInitParameter("accion");
        try {
            if(accion == null){
                request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
            }else{
                switch(accion){
                    case "admin":
                        request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
                    break;
                    case "loginAdmin":
                        iniciarSesionAdmin(request, response);
                    break;
                    case "loginEst":
                        iniciarSesionEst(request, response);
                    break;
                    case "logout":
                        logout(request, response);
                    break;
                    case "enviarTest":
//                        request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
                        enviarTest(request, response);
                    break;
                }
            }
        } catch (SQLException ex) {
            io.println(ex.getMessage());
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
