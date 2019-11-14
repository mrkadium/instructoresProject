<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% 
    response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="ES-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="css/login.css">
    <link rel="icon" href="img/Logo-USO.ico">
    <title>Evaluación de instructores</title>
</head>
<body>
    <div class="bg">
        <div class="tenue">
        <div class="contenedor">
            <h2>Universidad de Sonsonate</h2>
            <img src="img/Logo-USO.png" alt="">
            <h1>Evaluación de instructores |<span><i class="fas fa-tools"></i>Administrador</span></h1>
            <c:if test="${error != null}">
                <c:if test="${error == 2}">
                    <p style="color: red">Usuario y/o contraseña inválidos</p>
                </c:if>
                <c:if test="${error == 3}">
                    <p style="color: red">No se pudo actualizar la información</p>
                </c:if>
                <c:if test="${error == 4}">
                    <p style="color: green">Se actualizó su información, ya puede usar sus nuevos datos</p>
                </c:if>
                <c:if test="${error == 5}">
                    <p style="color: red">Debe iniciar sesión para acceder al sitio</p>
                </c:if>
            </c:if>
            <form action="/InstructoresProject/Login?accion=loginAdmin" method="POST">
                <div>
                    <input type="text" id="txtUsuario" name="txtUsuario" placeholder="Nombre de usuario" autofocus >
                    <input type="password" id="txtContra" name="txtContra" placeholder="Contraseña" autocomplete="off">
                    <input type="submit" value="Ingresar">
                </div>
            </form>
            <a href="/InstructoresProject/Login"><i class="fas fa-user"></i>Ingresar como estudiante</a>
        </div>
        </div>
    </div>
</body>
</html>