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
            <%/*<c:if test="${error != null}">

            </c:if>*/%>
            <img src="img/Logo-USO.png" alt="">
            <h1>Evaluación de instructores |<span><i class="fas fa-user"></i>Estudiante</span></h1>
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
            <form action="/InstructoresProject/LoginEst" method="POST">
                <div>
                    <input type="text" name="txtClave" placeholder="Clave de ingreso" autofocus>
                    <input type="submit" value="Ingresar">
                </div>
            </form>
            <a href="/InstructoresProject/Login?accion=admin"><i class="fas fa-tools"></i>Ingresar como administrador</a>
        </div>
        </div>
    </div>
</body>
</html>