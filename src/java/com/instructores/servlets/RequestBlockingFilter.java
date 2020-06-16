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
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        int idrol = 0;
        try{
            //Se intenta obtener el id de usuario
            idrol = (int)req.getSession().getAttribute("Usuario"); 
        }catch(Exception e){
            idrol = 0;
            req.setAttribute("error", 5);
            //Si no hay id, se redirecciona al login
            req.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(req, res); 
        }

        //Obtine la lista de los id's de administradores        
        String[][] rsAdmins = (String[][])req.getSession().getAttribute("admins");
        boolean encontrado = false;
        if(rsAdmins != null && idrol != 0){ //Si la lista no está vacía y hay un id
            for(int i=0; i<rsAdmins[0].length; i++){
                if(Integer.parseInt(rsAdmins[0][i]) == (idrol)){ //Verifica si el de la lista es igual
                    encontrado = true; //Si lo es
                    break;
                }
            }
        }else //Si la lista está vacía o no hay id, redirecciona al login
            req.getRequestDispatcher("jsp/loginAdministrador.jsp").forward(req, res);

        
        if(!encontrado && rsAdmins != null) //Si no se encontró en la lista, pero hay datos
            //Significa que no rol administrador, y regresa a la principal
            req.getRequestDispatcher("Principal").forward(req, res);             
        else //Si se encontró, es administrador
            chain.doFilter(request, response); //Deja seguir

    }

    public void destroy() {
        //we can close resources here
    }

}