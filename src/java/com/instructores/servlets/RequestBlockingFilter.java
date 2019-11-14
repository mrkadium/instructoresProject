package com.instructores.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.instructores.conexion.*;
import com.instructores.operaciones.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBlockingFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("RequestBlockingFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try{
            Conexion conn = new ConexionPool();
            conn.conectar();
            Operaciones.abrirConexion(conn);
            Operaciones.iniciarTransaccion();
            
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            
            int idrol = 0;
            try{
                idrol = (int)req.getSession().getAttribute("Usuario");
            }catch(Exception e){
                req.setAttribute("error", 5);
                req.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(req, res); 
            }
            String sql = "select idusuario from usuario where idrol in (select idrol from rol where rol = 'admin')";
            out.println(sql);
            String[][] rsAdmins = Operaciones.consultar(sql, new ArrayList());
            boolean encontrado = false;
            if(rsAdmins != null){
                for(int i=0; i<rsAdmins[0].length; i++){
                    if(Integer.parseInt(rsAdmins[0][i]) == (idrol)){
                        encontrado = true;
                    }
                }
            }else{
                req.getRequestDispatcher("jsp/index.jsp").forward(req, res); 
            }
            if(!encontrado && (rsAdmins != null)){
                req.getRequestDispatcher("Principal").forward(req, res);                
            }
            
            chain.doFilter(request, response);
            
            Operaciones.commit();
        }catch(Exception ex){
            out.println("ERROR: "+ex.getMessage());
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(RequestBlockingFilter.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(RequestBlockingFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void destroy() {
        //we can close resources here
    }

}