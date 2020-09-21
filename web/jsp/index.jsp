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
                    <h3>Universidad de Sonsonate</h3>
                    <p>Evaluación de instructores</p>
                    <img src="img/Logo-USO.png" alt="">
                </div>
                <form action="/InstructoresProject/LoginEst" class="login__form" method="POST">
                    <h1>Inicio de sesión</h1>
                    <c:if test="${error != null}">
                        <c:if test="${error == 0}">
                            <p style="color:red">Error de servidor</p>
                        </c:if>
                        <c:if test="${error == 1}">
                            <p style="color:red">El código ingresado no es válido</p>
                        </c:if>
                        <c:if test="${error == 2}">
                            <p style="color:red">No hay evaluaciones disponibles para este grupo</p>
                        </c:if>
                        <c:if test="${error == 3}">
                            <p style="color:green">Evaluación realizada con éxito</p>
                        </c:if>
                        <c:if test="${error == 4}">
                            <p style="color:red">Hubo un error al realizar la evaluación</p>
                        </c:if>
                        <c:if test="${error == 5}">
                            <p style="color:red">Las evaluaciones para este grupo están deshabilitadas</p>
                        </c:if>
                        <c:if test="${error == 6}">
                            <p style="color:red">Debe ingresar un código para realizar una evaluación</p>
                        </c:if>
                        <c:if test="${error == 7}">
                            <p style="color:red">Las evaluaciones para este grupo ya han sido cerradas</p>
                        </c:if>
                    </c:if>
                    <div>
                        <label for="codigo">Código:</label>
                        <input type="text" name="codigo" id="codigo" placeholder="Ejemplo: xYlHmNoRqW" autofocus>
                    </div>
                    <input type="submit" value="Ingresar">
                    <p>¿Es administrador?</p>
                    <a href="/InstructoresProject/Login?accion=admin">
                        Inicie sesión aquí
                    </a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>