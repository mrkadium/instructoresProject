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
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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


//            String idinstructor = request.getParameter("id");
//            String instructor = "", cantidad = "";
//            List<Object> params = new ArrayList();
//            params.add(idinstructor);
//            String sql = 
//                "SELECT\n" +
//                "	CONCAT(c.nombres,' ',c.apellidos) AS instructor,\n" +
//                "    COUNT(*) AS cant\n" +
//                "FROM evaluacion a, grupo b, usuario c\n" +
//                "WHERE\n" +
//                "	a.idgrupo = b.idgrupo\n" +
//                "    AND b.idinstructor = c.idusuario\n" +
//                "    AND c.idusuario = ?\n" +
//                "GROUP BY b.idinstructor\n" +
//                ";";        
//            String[][] rs = Operaciones.consultar(sql, params);    
//            instructor = rs[0][0];
//            cantidad = rs[1][0];

            String sql = "select\n" +
                        "	idgrupo,\n" +
                        "    (select concat(i.nombres, ' ', i.apellidos) from usuario i where i.idusuario = g.idinstructor) as instructor,\n" +
                        "    (select m.materia from materia m where m.idmateria = g.idmateria) as materia,\n" +
                        "    (select concat(c.nombres, ' ', c.apellidos) from usuario c where c.idusuario = g.idcatedratico) as catedratico,\n" +
                        "    ciclo,\n" +
                        "    numero_grupo,\n" +
                        "    clave,\n" +
                        "    idtest\n" +
                        "from grupo g where g.idgrupo = ?";
            List<Object> param = new ArrayList();
            param.add(request.getParameter("id"));
            String[][] rs = null;
            rs = Operaciones.consultar(sql, param);
            if(rs != null && rs[7][0] != null){
                ViewModelGrupo gr = new ViewModelGrupo(Integer.parseInt(rs[0][0]),rs[1][0],rs[2][0],rs[3][0],rs[4][0],rs[5][0],rs[6][0],Integer.parseInt(rs[7][0]),rs[8][0]);


                ServletContext context = request.getServletContext();
                File reportFile = new File(context.getRealPath("/")+"reportes/notasInstructor.jasper"); //obtenemos el archivo .jasper
                Map parameters = new HashMap(); //sirve como lista para los parámetros, método put(nombre_param, param) para agregar parámetros

                String reportName = "";
                JasperReport jasperReport;
                JasperDesign jasperDesign = null;

                if(accion.equals("")){
                    jasperDesign = JRXmlLoader.load(request.getServletContext().getRealPath("/")+"reportes/notasPromedio.jrxml");
                    reportName += "PROM_"+gr.getClave();
                }else if(accion.equals("detalle")){
                    jasperDesign = JRXmlLoader.load(request.getServletContext().getRealPath("/")+"reportes/notasDetallenuevo.jrxml");
                    reportName += "DET_"+gr.getClave();
                }
                
                    
                
                
                Date fecha = new Date();
                DateFormat f = new SimpleDateFormat("ddMMMMyy");
                reportName += ".pdf";

                jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                if(jasperReport.get != 0){
                    jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);
                    parameters.put("idgrupo", Integer.parseInt(request.getParameter("id")));
                    parameters.put("materia", gr.getMateria());
                    parameters.put("numerogrupo", gr.getNumero_grupo());
                    parameters.put("instructor", gr.getInstructor());
                    parameters.put("catedratico", gr.getCatedratico());
                    parameters.put("ciclo", gr.getCiclo());
                    byte[] bytes = null;
                    try {
                        bytes = JasperRunManager.runReportToPdf(jasperReport,parameters,conexion);
                    } catch (JRException ex) {
                        Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename="+reportName);
                    response.setContentLength(bytes.length);
                    //para que el pdf se pueda ver en microsoft explorer: response.setHeader("Cache-Control", "cache");
                    //para que aparezca el diálogo abrir/guardar: response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");

                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                    }
//                }else{
//                    response.getWriter().println("<h1 style='font-family: Helvetica, Arial; color: darkgreen;'>No hay datos<h1>");
//                }
            }else{
                response.sendRedirect(request.getContextPath()+"/Principal?accion=resultados");
            }
            Operaciones.commit();
        }catch(Exception e){
            Operaciones.rollback();
        }finally{
            Operaciones.cerrarConexion();
        }
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