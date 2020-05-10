package com.instructores.servlets;

import com.instructores.conexion.*;
import com.instructores.operaciones.Operaciones;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    
    private ServletContext context;
    
    @Override
    public void init(FilterConfig filterConfig) {        
        this.context = filterConfig.getServletContext();
        this.context.log("LoginFilter initialized");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            Conexion con = new ConexionPool();
            con.conectar();
            Operaciones.abrirConexion(con);
            Operaciones.iniciarTransaccion();
            
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            
            if(req.getSession().getAttribute("gr") == null){
                req.getSession().removeAttribute("accion");
                req.removeAttribute("accion");
                res.sendRedirect("Login");
            }
            
            chain.doFilter(request, response);
            Operaciones.commit();
        }catch(Exception ex){
            try {
                Operaciones.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(LoginFilter.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                Operaciones.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(LoginFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void destroy() {        
    }
}
