package com.instructores.servlets;

import com.instructores.conexion.Conexion;
import com.instructores.conexion.ConexionPool;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@WebServlet(name = "ReportesTest", urlPatterns = {"/ReportesTest"})
public class ReportesTest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JRException {
        Conexion cn = new ConexionPool();
        cn.conectar();
        Connection conexion = cn.getConexion();

        ServletOutputStream servletOutputStream = response.getOutputStream();
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        JasperDesign jasperDesign;
        
        //PARÁMETROS
        Map parameters = new HashMap();
        
        //SE CARGA Y SE COMPILA EL DISEÑO
        jasperDesign = JRXmlLoader.load(request.getServletContext().getRealPath("/")+"reportes/notasInstructor.jrxml");
        jasperReport = JasperCompileManager.compileReport(jasperDesign);
        
        //SE LLENA EL REPORTE CON LOS DATOS
        parameters.put("idinstructor", "8");
        parameters.put("instructor", "Mario Adalberto Rivera Olivo");
        parameters.put("cantidad", "3");
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conexion);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\Soldier4\\Downloads");
        
        servletOutputStream.flush();
        servletOutputStream.close();
        
        cn.desconectar();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(ReportesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (JRException ex) {
            Logger.getLogger(ReportesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
