package com.instructores.servlets;

import com.instructores.conexion.*;
import com.instructores.operaciones.Operaciones;
import com.instructores.viewmodels.ViewModelGrupo;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "Reportes", urlPatterns = {"/Reportes"})
public class Reportes extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, Exception {
        try{
            Conexion cn = new ConexionPool();
            cn.conectar();
            Connection conexion=cn.getConexion();

            Operaciones.abrirConexion(cn);
            Operaciones.iniciarTransaccion();

            String accion = request.getParameter("accion") == null ? "" : request.getParameter("accion");

            String sql = "select\n" +
                        "	idgrupo,\n" +
                        "    (select concat(i.nombres, ' ', i.apellidos) from usuario i where i.idusuario = g.idinstructor) as instructor,\n" +
                        "    (select m.materia from materia m where m.idmateria = g.idmateria) as materia,\n" +
                        "    (select concat(c.nombres, ' ', c.apellidos) from usuario c where c.idusuario = g.idcatedratico) as catedratico,\n" +
                        "    ciclo,\n" +
                        "    numero_grupo,\n" +
                        "    clave,\n" +
                        "    idtest, estado\n" +
                        "from grupo g where g.idgrupo = ?";
            List<Object> param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rs = null;
            rs = Operaciones.consultar(sql, param);
            
            if(rs != null && rs[7][0] != null){
                ViewModelGrupo gr = new ViewModelGrupo(Integer.parseInt(rs[0][0]),rs[1][0],rs[2][0],rs[3][0],rs[4][0],rs[5][0],rs[6][0],Integer.parseInt(rs[7][0]),rs[8][0]);

                //Verifica si existen los tipos en la evalación
                sql = "SELECT \n" +
                    "	a.tipo, (COUNT(*) > 0) AS hasValue\n" +
                    "FROM tipo a\n" +
                    "LEFT JOIN valoracion b ON a.idtipo = b.idtipo\n" +
                    "INNER JOIN literal_evaluacion c ON c.idvaloracion = b.idvaloracion\n" +
                    "INNER JOIN evaluacion d ON c.idevaluacion = d.idevaluacion\n" +
                    "INNER JOIN grupo e ON d.idgrupo = e.idgrupo\n" +
                    "WHERE e.idgrupo = ?\n" +
                    "GROUP BY a.idtipo\n" +
                    ";";
                rs = Operaciones.consultar(sql, param);
                boolean haveCualitativas = verificarTipo(rs, "cualitativa");
                boolean haveCuantitativas = verificarTipo(rs, "cuantitativa");
                boolean haveSino = verificarTipo(rs, "si-no");
                
                //Verifica si existen observaciones
                sql = "SELECT COUNT(*) > 0 AS hasEvaluacion FROM evaluacion WHERE idgrupo = ?;";
                rs = Operaciones.consultar(sql, param);
                boolean haveObservaciones = rs[0][0].equals("1");
                
                ServletContext context = request.getServletContext();
                File reportFile = null;
                String reportName = "";
                
                if(accion.equals("")){
                    reportName += "PROM_"+gr.getClave();
                    reportFile = new File(context.getRealPath("/")+"reportes/notasPromedio.jasper"); //obtenemos el archivo .jasper
                }else{
                    reportName += gr.getClave();
                    String path = context.getRealPath("/")+"reportes/notas/notas_detalle.jasper";
                    reportFile = new File(path); //obtenemos el archivo .jasper
                }
                Map parameters = new HashMap(); //sirve como lista para los parámetros, método put(nombre_param, param) para agregar parámetros

                
                
                
//                JasperReport jasperReport;
//                JasperDesign jasperDesign = null;
//
//                if(accion.equals("")){
//                    jasperDesign = JRXmlLoader.load(request.getServletContext().getRealPath("/")+"reportes/notasPromedio.jrxml");
//                    reportName += "PROM_"+gr.getClave();
//                }else if(accion.equals("detalle")){
//                    jasperDesign = JRXmlLoader.load(request.getServletContext().getRealPath("/")+"reportes/notasDetallenuevo.jrxml");
//                    reportName += "DET_"+gr.getClave();
//                }
                
                    
                
                
                Date fecha = new Date();
                DateFormat f = new SimpleDateFormat("ddMMMMyy");
                reportName += ".pdf";
//
//                jasperReport = JasperCompileManager.compileReport(jasperDesign);
////                if(jasperReport.get != 0){
//                jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);
                parameters.put("idgrupo", Integer.parseInt(request.getParameter("id")));
                parameters.put("haveObservaciones", haveObservaciones);
                parameters.put("haveSino", haveSino);
                parameters.put("haveCuantitativas", haveCuantitativas);
                parameters.put("haveCualitativas", haveCualitativas);
                parameters.put("materia", gr.getMateria());
                parameters.put("grupo", gr.getNumero_grupo());
                parameters.put("instructor", gr.getInstructor());
                parameters.put("catedratico", gr.getCatedratico());
                parameters.put("ciclo", gr.getCiclo());
                byte[] bytes = null;
                try {
//                    bytes = JasperRunManager.runReportToPdf(jasperReport,parameters,conexion);
                    bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),parameters,conexion);
                } catch (JRException ex) {
                    request.getSession().setAttribute("error","No se puede compilar");
                    response.sendRedirect(request.getContextPath()+"/Principal?accion=resultados");
                    Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename="+reportName);
                if(bytes != null){
                    response.setContentLength(bytes.length);
                    //para que el pdf se pueda ver en microsoft explorer: response.setHeader("Cache-Control", "cache");
                    //para que aparezca el diálogo abrir/guardar: response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");

                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                    }
                }else{
                    request.getSession().setAttribute("error","bytes = null");
                    response.sendRedirect(request.getContextPath()+"/Principal?accion=resultados");
                }
            }else{
                request.getSession().setAttribute("error","No hay test asignado o no hay evaluaciones");
                response.sendRedirect(request.getContextPath()+"/Principal?accion=resultados");
            }
            Operaciones.commit();
        }catch(Exception e){
            request.getSession().setAttribute("error","Error: " + e.getMessage() + e.toString() + e.getCause().toString());
            Operaciones.rollback();
            response.sendRedirect(request.getContextPath()+"/Principal?accion=resultados");
        }finally{
            Operaciones.cerrarConexion();
        }
    }
    
    public boolean verificarTipo (String[][] rs, String tipo){
        boolean found = false;
        
        for(String[] col: rs){
            for(String row: col){
                if(row.equals(tipo)){
                    found = true;
                    break;
                }
            }
            if(found) break;
        }
        
        return found;
    }           
            
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}