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
        <link rel="stylesheet" href="css/custom_properties.css">
        <link rel="stylesheet" href="css/toast.css">
        <link rel="stylesheet" href="css/css/stylePrincipal.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="icon" href="img/Logo-USO.ico">
        <link href="../css/datetimepicker.css" rel="stylesheet" type="text/css"/>
        <script src="js/new.js"></script>
        <title>Administrador</title>