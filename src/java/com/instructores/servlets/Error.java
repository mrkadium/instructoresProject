package com.instructores.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Error extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        String message = (String)request.getAttribute("javax.servlet.error.message");
        String request_uri = (String)request.getAttribute("javax.servlet.error.request_uri");
        String servlet_name = (String)request.getAttribute("javax.servlet.error.servlet_name");
        
        Class exceptionClass = (Class)request.getAttribute("javax.servlet.error.exception_type");
        Exception ex = (Exception)request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("class", exceptionClass);
        request.setAttribute("ex", ex);
//        request.setAttribute("cause",ex.getCause().getMessage());
        request.setAttribute("message", message);
        request.setAttribute("request_uri", request_uri);
        request.setAttribute("servlet_name", servlet_name);
        switch(statusCode){
            case 404:
                request.getRequestDispatcher("error/error404.jsp").forward(request, response);
                break;
            case 500:
                request.getRequestDispatcher("error/error500.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("error/error500.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
