package com.instructores.servlets;

import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import com.instructores.control.Grupos;
import com.instructores.entidad.*;
import com.instructores.operaciones.Operaciones;
import com.instructores.utilerias.Hash;
import com.instructores.utilerias.Pagination;
import com.instructores.utilerias.Tabla;
import com.instructores.viewmodels.ViewModelGrupo;
import com.instructores.viewmodels.ViewModelInstructor;
import com.instructores.viewmodels.ViewModelObservacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Principal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String accion = request.getParameter("accion");
        accion = getServletConfig().getInitParameter("accion") != null ? getServletConfig().getInitParameter("accion") : request.getParameter("accion");
        
        HttpSession s = request.getSession();
        if(accion == null){
            if(s == null || s.getAttribute("Usuario") == null){                
                request.setAttribute("error", 5);
                request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
            }else{
                List<Menu> per = (List<Menu>)s.getAttribute("Permisos");
                String op = request.getParameter("op");
                if(op != null){
                    List<Menu> PermisosAsignados = new ArrayList();
                    for(int i=0; i<per.size(); i++){
                        if(per.get(i).getIdpadre() == Integer.parseInt(op)){
                            PermisosAsignados.add(per.get(i));
                        }
                    }
                    request.setAttribute("PermisosAsignados", PermisosAsignados);
                }
                request.getRequestDispatcher("jsp/Principal.jsp").forward(request, response);
            }
        }else{
            if(s == null || s.getAttribute("Usuario") == null){                
                request.setAttribute("error", 5);
                request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
            }else{
                switch(accion){
                    case "resultados":
                        if(request.getSession().getAttribute("error") != null){
                            request.setAttribute("error",request.getSession().getAttribute("error"));
                            request.getSession().removeAttribute("error");
                        }
                        resultados(request, response);
                        break;
                    case "grupo":
                        getGrupo(request,response);
                        break;
                    case "instructores":
                        instructores(request,response);
                        break;
                    case "instructor":
                        getInstructor(request,response);
                        break;
                    case "configuraciones":
                        validarAdmin(request, response);
                        configuraciones(request,response);
                        break;
                    case "perfil":
                        perfil(request, response);
                        break;
                    case "actualizarUsuario":
                        actualizarUsuario(request, response);
                        break;
                    case "logout":
                        logout(request, response);
                        break;
                }
            }
        }
    }
    
    private void validarAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        PrintWriter out = response.getWriter();
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            int idrol = (int)request.getSession().getAttribute("Rol");
            String sql = "SELECT idusuario FROM usuario WHERE idrol IN (SELECT idrol FROM rol WHERE rol = 'admin')";
            out.println(sql);
            String[][] rsAdmins = Operaciones.consultar(sql, new ArrayList());
            boolean encontrado = false;
            if(rsAdmins != null){
                for(int i=0; i<rsAdmins[0].length; i++){
                    if(Integer.parseInt(rsAdmins[0][i]) == idrol){
                        out.println(rsAdmins[0][i] + " - " + idrol);
                        encontrado = true;
                    }
                }
            }
            if(!encontrado){
                request.getRequestDispatcher("jsp/Principal.jsp").forward(request, response);                
            }
            
            Operaciones.commit();
        }catch(Exception ex){
            out.println("ERROR: "+ex.getMessage());
            Operaciones.rollback();
        }finally{
            Operaciones.cerrarConexion();
        }
    }
    
    private void perfil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();

            Usuario u = (Usuario)request.getSession().getAttribute("u");
            request.setAttribute("usuario", u);

            request.getRequestDispatcher("jsp/perfil.jsp").forward(request, response);
                
            Operaciones.commit();
        } catch(Exception ex) {
            Operaciones.rollback();
        } finally {
            Operaciones.cerrarConexion();
        }
    }
    
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            String usuario = request.getParameter("txtUsuario");
            String clave = request.getParameter("txtClave") != null ? request.getParameter("txtClave") : "";
            boolean updated = false;

            Usuario u = (Usuario)request.getSession().getAttribute("u");
            
            if(!u.getUsuario().equals(usuario)) {u.setUsuario(usuario); updated = true;}
            if(!clave.isEmpty()) {u.setClave(Hash.generarHash(clave, Hash.SHA256)); updated = true;}
            u = Operaciones.actualizar(u.getIdusuario(), u);
            if(updated && u.getIdusuario() != 0) request.setAttribute("error", 4);
            else request.setAttribute("error", 3);
            request.getSession().invalidate();
            
            Operaciones.commit();
        } catch(Exception ex) {
            Operaciones.rollback();
            request.setAttribute("error", 3);
        } finally {
            Operaciones.cerrarConexion();
            request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);   
        } 
    }
    
    private void instructores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        PrintWriter io = response.getWriter();
        try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = 
                    "SELECT\n" +
                    "	a.idinstructor,\n" +
                    "    CONCAT(b.nombres, ' ', b.apellidos) as instructor,\n" +
                    "    b.usuario,\n" +
                    "    COUNT(*) AS cant_labos,\n" +
                    "    (SELECT COUNT(*) FROM evaluacion WHERE idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = a.idinstructor)) AS cant_ev\n" +
                    "FROM grupo a, usuario b\n" +
                    "WHERE\n" +
                    "	a.idinstructor = b.idusuario\n" +
                    "GROUP BY a.idinstructor\n" +
                    ";";
                String[][] rs = Operaciones.consultar(sql, null);
                String cabeceras[] = {
                    "ID",
                    "Instructor",
                    "Usuario",
                    "Labs. impartidos",
                    "Ev. recibidas"};
                
                Tabla tab = new Tabla(rs, "80%", Tabla.STYLE.TABLE01, Tabla.ALIGN.CENTER, cabeceras);
                tab.setModificable(true);
                tab.setImprimible(true);
                tab.setPageContext(request.getContextPath());
                tab.setPaginaModificable("/Principal?accion=instructor");
                tab.setIconoModificable(Tabla.ICON.VER_MAS);
                tab.setPaginaImprimible("/Reportes");
                tab.setIconoImprimible(Tabla.ICON.IMPRIMIR);
                
                String tabla = (rs != null) ? tab.getTabla() : "No hay datos";
                
                request.setAttribute("tabla", tabla);
                request.getRequestDispatcher("jsp/instructores.jsp").forward(request, response);
                
                Operaciones.commit();
        } catch(Exception ex) {
            Operaciones.rollback();
        } finally {
            Operaciones.cerrarConexion();
        }
    }
    
    private void getInstructor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        PrintWriter io = response.getWriter();
        try {
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
                
            String sql = 
                "SELECT\n" +
                "	a.idinstructor,\n" +
                "    CONCAT(b.nombres, ' ', b.apellidos) as instructor,\n" +
                "    b.usuario,\n" +
                "    COUNT(*) AS cant_labos,\n" +
                "    (SELECT COUNT(*) FROM evaluacion WHERE idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = a.idinstructor)) AS cant_ev\n" +
                "FROM grupo a, usuario b\n" +
                "WHERE\n" +
                "	a.idinstructor = b.idusuario\n" +
                "       AND a.idinstructor = ?\n" +
                "GROUP BY a.idinstructor\n" +
                ";";            
            List<Object> param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rs = Operaciones.consultar(sql, param);
            ViewModelInstructor ins = new ViewModelInstructor(Integer.parseInt(rs[0][0]),rs[1][0],rs[2][0],Integer.parseInt(rs[3][0]));
            request.setAttribute("ins", ins);
            
//            String sqlcount = "select count(*) from evaluacion where idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = ?)";
//            String cant[][] = Operaciones.consultar(sqlcount, param);
            request.setAttribute("cantidad", rs[4][0]);
            
            String sqlObs = 
                "SELECT \n" +
                "	idevaluacion, observacion, fecha_realizacion \n" +
                "FROM evaluacion \n" +
                "WHERE \n" +
                "	idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = ?)\n" +
                "    AND length(observacion) > 0\n" +
                "ORDER BY idevaluacion DESC LIMIT 3;";
            param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rsObs = Operaciones.consultar(sqlObs, param);
            if(rsObs != null){
                List<ViewModelObservacion> getObs = new ArrayList();
                for(int i=0; i<rsObs[0].length; i++){
                    Evaluacion e = new Evaluacion();
                    ViewModelObservacion ob = new ViewModelObservacion(rsObs[0][i], rsObs[1][i], rsObs[2][i]);
                    e.setIdevaluacion(Integer.parseInt(rsObs[0][i]));
                    e.setObservacion(rsObs[1][i]);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");                    
                    e.setFecha_realizacion((df.parse(rsObs[2][i])));
                    getObs.add(ob);
                }
                request.setAttribute("observaciones", getObs);
            }
            
            List<Object> params = new ArrayList();
            String sqlliterales = 
                "SELECT\n" +
                "    c.literal,\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Excelente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Excelente',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Muy bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Muy bueno',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Bueno',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Decente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Decente',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Necesita mejorar') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Necesita mejorar',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Si') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Si',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'No') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'No',\n" +
                "    IF(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) IS NULL, '-' ,FORMAT(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)),0)) AS 'Calificación'\n" +
                "FROM literal_evaluacion a, evaluacion b, literal c\n" +
                "WHERE \n" +
                "	a.idevaluacion = b.idevaluacion \n" +
                "    AND a.idliteral = c.idliteral \n" +
                "    AND b.idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = ?)\n" +
                "GROUP BY a.idliteral\n" +
                ";";
            String promedios = 
                "SELECT\n" +
                "	c.literal,\n" +
                "	(SELECT valoracion FROM valoracion x WHERE x.idvaloracion = FORMAT(AVG(a.idvaloracion),0)) AS 'Promedio valoración',\n" +
                "    IF(AVG(a.calificacion) = 0.00, '-', FORMAT(AVG(a.calificacion),0)) as 'Promedio calificación'\n" +
                "FROM literal_evaluacion a, evaluacion b, literal c\n" +
                "WHERE \n" +
                "	a.idevaluacion = b.idevaluacion \n" +
                "    AND a.idliteral = c.idliteral \n" +
                "    AND b.idgrupo IN (SELECT g.idgrupo FROM grupo g WHERE g.idinstructor = ?)\n" +
                "GROUP BY a.idliteral\n" +
                ";";
            params.add(request.getParameter("id"));
            
            String[][] grupos = Operaciones.consultar(sqlliterales, params);
            String[][] proms = Operaciones.consultar(promedios, params);

            String[] cabeceras = new String[]{
                "Literal",
                "Excelente",
                "Muy bueno",
                "Bueno",
                "Regular",
                "Necesita mejorar",
                "Si",
                "No",
                "Calificación"};
            
            String[] cabecerasproms = new String[]{
                "Literal",
                "Promedio valoración",
                "Promedio calificación"};
            
            Tabla p = new Tabla(proms,"80%",Tabla.STYLE.TABLE01,Tabla.ALIGN.LEFT,cabecerasproms);
            p.setPageContext(request.getContextPath());
            String tablita = (proms != null) ? tablita = p.getTabla() : "No hay datos";            
            request.setAttribute("tablita", tablita);
                        
            Tabla tab = new Tabla(grupos,"80%",Tabla.STYLE.TABLE01,Tabla.ALIGN.LEFT,cabeceras); 
            tab.setPageContext(request.getContextPath());
            String tabla01 = (grupos != null) ? tabla01 = tab.getTabla() : "No hay datos";
            request.setAttribute("tabla", tabla01);
                
            request.getRequestDispatcher("jsp/instructor.jsp").forward(request, response);

            Operaciones.commit();
        } catch(Exception ex) {
            Operaciones.rollback();
        } finally {
            Operaciones.cerrarConexion();
        }
    }
    
    private void resultados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        PrintWriter io = response.getWriter();
        try {
                Conexion conn = new ConexionPool();
                conn.conectar();
                Operaciones.abrirConexion(conn);
                Operaciones.iniciarTransaccion();
                
                String sql = "";                
                sql =   "SELECT\n" +
                        "	a.idgrupo, b.materia, e.alias as facultad,\n" +
                        "    CONCAT(c.nombres,' ',c.apellidos) AS instructor,\n" +
                        "    CONCAT(d.nombres,' ',d.apellidos) AS catedratico,\n" +
                        "    a.numero_grupo, a.clave, a.ciclo,\n" +
                        "    IF(a.idtest IS NULL,'-',a.idtest) as idtest, a.estado,\n" +
                        "   (SELECT COUNT(*) FROM evaluacion x WHERE x.idgrupo = a.idgrupo) AS cant_ev -- NUEVO\n" +
                        "FROM \n" +
                        "	grupo a, materia b, usuario c, usuario d, facultad e, carrera f\n" +
                        "WHERE\n" +
                        "	a.idmateria = b.idmateria\n" +
                        "    AND a.idinstructor = c.idusuario\n" +
                        "    AND a.idcatedratico = d.idusuario\n" +
                        "    AND e.idfacultad = f.idfacultad\n" +
                        "    AND f.idcarrera = b.idcarrera\n";
                
                List<Object> params = new ArrayList<>();
                
                switch(request.getSession().getAttribute("Rolname").toString()){
                    case "decano":
                        sql += "AND a.idmateria IN (\n" +
                        "		SELECT x.idmateria FROM materia x WHERE x.idcarrera IN (\n" +
                        "			SELECT y.idcarrera FROM carrera y WHERE y.idfacultad = (\n" +
                        "				SELECT z.idfacultad FROM facultad z WHERE z.iddecano = ?\n" +
                        "               )\n" +
                        "           )\n" +
                        "	)\n" +
                        "	OR idinstructor = ?";
                        params.add(request.getSession().getAttribute("Idusuario"));
                        params.add(request.getSession().getAttribute("Idusuario"));
                        break;
                    case "catedratico":
                        sql += "AND (a.idcatedratico = ? OR a.idinstructor = ?)";
                        params.add(request.getSession().getAttribute("Idusuario"));
                        params.add(request.getSession().getAttribute("Idusuario"));
                        break;
                    case "instructor":
                        sql += "AND a.idinstructor = ?";
                        params.add(request.getSession().getAttribute("Idusuario"));
                        break;
                    default:
                        sql = sql;
                        break;
                }
                
                String[][] grupos = Operaciones.consultar(sql, params);
                
                Pagination p = new Pagination(grupos[0].length, 10, request);
                request.setAttribute("pag", p);
                
                List<String> instructores = new ArrayList();
                for(int i=0; i<grupos[3].length; i++){
                    if(!instructores.contains(grupos[3][i]))
                        instructores.add(grupos[3][i]);
                }
                request.setAttribute("Instructores", instructores);
                
                String[] cabeceras = new String[]{
                "ID",
                "Materia",
                "Fac.",
                "Instructor",
                "Catedrático",
                "# Grupo",
                "Clave",
                "Ciclo",
                "Test",
                "Estado",
                "# Ev."
                };
                
                Tabla tab = new Tabla(grupos,"80%",Tabla.STYLE.TABLE01,Tabla.ALIGN.LEFT,cabeceras);
                //tab.setLimiteInferior(p.getCurrentLowerLimit());
                //tab.setLimiteSuperior(p.getCurrentUpperLimit());                
                tab.setCopiable(true);
                tab.setDir_Port(request.getServerName()+":"+request.getServerPort());
                tab.setPaginaCopiable("/Login?accion=loginEst&txtClave=");
                tab.setColumnaCopiable(6);
                tab.setModificable(true);
                tab.setPaginaModificable("/Principal?accion=grupo");
                tab.setIconoModificable(Tabla.ICON.VER_MAS);
                //tab.setCabeceraModificable("Ver más");
                String rol = request.getSession().getAttribute("nombre_rol").toString();
                if(rol.equals("admin") || rol.equals("decano")){
                    tab.setImprimible(true);
                    tab.setPaginaImprimible("/Reportes?accion=detalle");
                    tab.setIconoImprimible(Tabla.ICON.IMPRIMIR);
                    //tab.setCabeceraImprimible("Reporte");
                }
//                tab.setSeleccionable(true);
//                tab.setPaginaSeleccionable("/Reportes?accion=detalle");
//                tab.setIconoSeleccionable(Tabla.ICON.IMPRIMIR);
//                tab.setCabeceraSeleccionable("Detalles");
                tab.setPageContext(request.getContextPath());
                String tabla01 = "No hay datos";
                if(grupos != null)
                        tabla01 = tab.getTabla();
                
                sql = "SELECT DISTINCT ciclo FROM grupo ORDER BY idgrupo DESC;";
                List<String> ciclos = new ArrayList();
                String[][] rs = Operaciones.consultar(sql, null);
                for(int i=0; i<rs[0].length; i++){
                    ciclos.add(rs[0][i]);
                }
                request.setAttribute("Ciclos", ciclos);
                
                List<String> catedraticos = new ArrayList();
                sql = "SELECT\n" +
                    "	DISTINCT CONCAT(b.nombres,' ',b.apellidos) AS usuario \n" +
                    "FROM grupo a, usuario b\n" +
                    "WHERE a.idcatedratico = b.idusuario;";
                rs = Operaciones.consultar(sql, null);
                for(int i=0; i<rs[0].length; i++){
                    catedraticos.add(rs[0][i]);
                }
                request.setAttribute("Catedraticos", catedraticos);
                
                request.setAttribute("tabla", tabla01);
                request.getRequestDispatcher("jsp/resultados.jsp").forward(request, response);
                
                Operaciones.commit();
            } catch(Exception ex) {
                Operaciones.rollback();
            } finally {
                Operaciones.cerrarConexion();
            }
    }
    
    private void getGrupo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();

            
            String sql = "select\n" +
                        "	idgrupo,\n" +
                        "    (select concat(i.nombres, ' ', i.apellidos) from usuario i where i.idusuario = g.idinstructor) as instructor,\n" +
                        "    (select m.materia from materia m where m.idmateria = g.idmateria) as materia,\n" +
                        "    (select concat(c.nombres, ' ', c.apellidos) from usuario c where c.idusuario = g.idcatedratico) as catedratico,\n" +
                        "    ciclo,\n" +
                        "    numero_grupo,\n" +
                        "    clave, idtest, estado\n" +
                        "from grupo g where g.idgrupo = ?";
            List<Object> param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rs = Operaciones.consultar(sql, param);
            ViewModelGrupo gr = new ViewModelGrupo(Integer.parseInt(rs[0][0]),rs[1][0],rs[2][0],rs[3][0],rs[4][0],rs[5][0],rs[6][0],rs[8][0]);
            if(rs[7][0] != null)
                gr.setIdtest(Integer.parseInt(rs[7][0]));
            
            String sqlcount = "select count(*) from evaluacion where idgrupo = ?";
            String cant[][] = Operaciones.consultar(sqlcount, param);
            request.setAttribute("cantidad", cant[0][0]);
            
            request.setAttribute("gr", gr);
            
            
            
            String sqlObs = 
                "SELECT \n" +
                "	idevaluacion, observacion, fecha_realizacion \n" +
                "FROM evaluacion \n" +
                "WHERE \n" +
                "	idgrupo = ?\n" +
                "    AND length(observacion) > 0\n" +
                "ORDER BY idevaluacion DESC;";
            param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rsObs = Operaciones.consultar(sqlObs, param);
            if(rsObs != null){
                List<ViewModelObservacion> getObs = new ArrayList();
                for(int i=0; i<rsObs[0].length; i++){
                    Evaluacion e = new Evaluacion();
                    ViewModelObservacion ob = new ViewModelObservacion(rsObs[0][i], rsObs[1][i], rsObs[2][i]);
                    e.setIdevaluacion(Integer.parseInt(rsObs[0][i]));
                    e.setObservacion(rsObs[1][i]);
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");                    
                    e.setFecha_realizacion((df.parse(rsObs[2][i])));
                    getObs.add(ob);
                }
                request.setAttribute("observaciones", getObs);
            }
            
            
            
            List<Object> params = new ArrayList();
            String sqlliterales = 
                "SELECT\n" +
                "    c.literal,\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Excelente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Excelente',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Muy bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Muy bueno',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Bueno') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Bueno',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Decente') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Decente',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Necesita mejorar') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Necesita mejorar',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Si') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'Si',\n" +
                "    COUNT((SELECT x.idvaloracion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'No') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) AS 'No',\n" +
                "    IF(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)) IS NULL, '-' ,FORMAT(AVG((SELECT x.calificacion FROM literal_evaluacion x WHERE x.idvaloracion = (SELECT y.idvaloracion FROM valoracion y WHERE valoracion = 'Calificación') AND x.idliteral = a.idliteral AND x.idevaluacion = b.idevaluacion)),0)) AS 'Calificación'\n" +
                "FROM literal_evaluacion a, evaluacion b, literal c\n" +
                "WHERE a.idevaluacion = b.idevaluacion AND a.idliteral = c.idliteral AND b.idgrupo = ?\n" +
                "GROUP BY a.idliteral\n"+ 
                "ORDER BY a.idvaloracion" +
                ";";
            String promedios = 
                "SELECT\n" +
                "	c.literal,\n" +
                "	(SELECT valoracion FROM valoracion x WHERE x.idvaloracion = FORMAT(AVG(a.idvaloracion),0)) AS 'Promedio valoración',\n" +
                "    IF(AVG(a.calificacion) = 0.00, '-', FORMAT(AVG(a.calificacion),0)) as 'Promedio calificación'\n" +
                "FROM literal_evaluacion a, evaluacion b, literal c\n" +
                "WHERE a.idevaluacion = b.idevaluacion AND a.idliteral = c.idliteral AND b.idgrupo = ?\n" +
                "GROUP BY a.idliteral\n" +
                "ORDER BY a.idvaloracion" +
                ";";
            params.add(request.getParameter("id"));
            
            String[][] grupos = Operaciones.consultar(sqlliterales, params);
            String[][] proms = Operaciones.consultar(promedios, params);

            //declaracion de cabeceras a usar en la tabla HTML
            String[] cabeceras = new String[]{
                "Literal",
                "Excelente",
                "Muy bueno",
                "Bueno",
                "Regular",
                "Necesita mejorar",
                "Si",
                "No",
                "Calificación"
            };
            
            String[] cabecerasproms = new String[]{
                "Literal",
                "Promedio valoración",
                "Promedio calificación"
            };
            
            Tabla p = new Tabla(proms,"80%",Tabla.STYLE.TABLE01,Tabla.ALIGN.LEFT,cabecerasproms);
            p.setPageContext(request.getContextPath());
            String tablita = "No hay datos";
            if(proms != null)
                    tablita = p.getTabla();

            request.setAttribute("tablita", tablita);
            
            //variable de tipo Tabla para generar la Tabla HTML
            Tabla tab = new Tabla(grupos, //array que contiene los datos
            "80%", //ancho de la tabla px | %
            Tabla.STYLE.TABLE01, //estilo de la tabla
            Tabla.ALIGN.LEFT, // alineacion de la tabla
            cabeceras); 
            tab.setPageContext(request.getContextPath());
            String tabla01 = "No hay datos";
            if(grupos != null)
                    tabla01 = tab.getTabla();

            request.setAttribute("tabla", tabla01);
            
            
            
            
            Operaciones.commit();
        }catch(Exception ex) {
            Operaciones.rollback();
        } finally {
            Operaciones.cerrarConexion();
        }
        request.getRequestDispatcher("jsp/grupo.jsp").forward(request, response);
    }
    
    private void configuraciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception{
        //Mostrar tabla con las entidades
        PrintWriter io = response.getWriter();
        
//        List<Menu> per = (List<Menu>)request.getSession().getAttribute("Permisos");
//        String op = "4";
//        List<Menu> PermisosAsignados = per.stream().filter(field -> field.getIdpadre() == Integer.parseInt(op)).collect(Collectors.toList());
//        request.setAttribute("PermisosAsignados", PermisosAsignados);
        
        
        request.getRequestDispatcher("jsp/configuraciones.jsp").forward(request, response);
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.getSession().invalidate();
        request.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
