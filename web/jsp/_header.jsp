<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% HttpSession sesion = request.getSession(); 
    if(sesion.getAttribute("Usuario") == null || request.getSession() == null || sesion.getAttribute("MenuPrincipal") == null){
        response.sendRedirect("Login?accion=admin");
    }
    response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="ES-es">
    <head id='head'>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="css/principal.css" type="text/css">
        <link rel="stylesheet" href="css/table.css" type="text/css">
        <link rel="icon" href="img/logo.ico">
        <link href="../css/datetimepicker.css" rel="stylesheet" type="text/css"/>
        <title>Administrador</title>
    </head>
    <body>
        <nav>
            <a href="${pageContext.servletContext.contextPath}/Principal" id="logo"><img src="img/logo.png" alt=""></a>
            <c:forEach var="menu" items="${MenuPrincipal}">
                <a href="${pageContext.servletContext.contextPath}${menu.url}">${menu.menu}</a>
            </c:forEach>
            <div id="usuario">
                <img src="img/user.png" alt="">
                <p>${Nombre}</p>
                <a href="/InstructoresProject/Principal?accion=logout"> [Cerrar sesión]</a>
            </div>
        </nav>
                <div id="cont" class="contenedor column">