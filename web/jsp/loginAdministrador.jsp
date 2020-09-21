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
    <link rel="stylesheet" href="css/custom_properties.css">
    <link rel="stylesheet" href="css/login.css">
    <link rel="icon" href="img/Logo-USO.ico">
    <title>Evaluación de instructores</title>
</head>
<body>
    <div class="login__bg">
        <div class="login__container">
            <div class="login__inner_container">
                <div class="login__showcase">
                    <!-- <img src="../img/bg1.jpg" alt=""> -->
                    <h3>Evaluación de instructores</h3>
                    <p>Universidad de Sonsonate</p>
                    <img src="img/Logo-USO.png" alt="">
                    <!-- <p class="footer">Universidad de Sonsonate 2020</p> -->
                </div>
                <form action="/InstructoresProject/Login?accion=loginAdmin" class="login__form" method="POST">
                    <h1>Inicio de sesión</h1>
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
                    <div>
                        <label for="usuario">Usuario:</label>
                        <input type="text" name="txtUsuario" id="usuario" placeholder="Ejemplo: mario.olivo" autofocus>
                    </div>
                    <div>
                        <label for="clave">Contraseña:</label>
                        <input type="password" name="txtContra" id="clave" placeholder="*********">
                    </div>
                    <input type="submit" value="Ingresar">
                    <p>¿No es administrador?</p>
                    <a href="/InstructoresProject/Login">
                        Ingrese con un código
                    </a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>