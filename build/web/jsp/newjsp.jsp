<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:if test="${error != null}">
            <p>Clave inválida</p>
        </c:if>
        <c:if test="${error == null}">
            <p>Materia ${Materia}</p>
            <p>Instructor ${Instructor}</p>
            <p>Catedrático ${Catedratico}</p>
            <p>Número de grupo ${Numero_grupo}</p>
            <p>Ciclo ${Ciclo}</p>
            <a href="/InstructoresProject/Login?accion=logout">Salir</a>
        </c:if>
            <h1>${accion}</h1> 
    </body>
</html>
