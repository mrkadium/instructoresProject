<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% 
    HttpSession sesion = request.getSession(); 
    if(sesion.getAttribute("gr") == null){
        response.sendRedirect("Login");
    }
    
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
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/styleEvaluacion.css">
    <link rel="stylesheet" href="css/styleAdmin.css">
    <title>Evaluación</title>
</head>
<body>
    <div class="contenedor2">
        <form action="/InstructoresProject/Login?accion=enviarTest" method="post">
            <div class="cabecera">
                <div>
                    <p><strong>Instructor: </strong>${gr.instructor}</p>
                    <p><strong>Instructoría: </strong>${gr.materia}</p>
                    <p><strong>Catedrático: </strong>${gr.catedratico}</p>
                    <p><strong>Grupo: </strong>${gr.numero_grupo}</p>  
                    <p><strong>Ciclo: </strong>${gr.ciclo}</p>                       
                </div>
                <div>
                    <img src="img/logo.png">
                </div>
            </div>
            <br>
            
            
            <c:forEach var="tipo" items="${tipos}"> <!-- una tabla por cada tipo de literal -->
                <table>
                    <thead>
                        <tr>
                            <th>Literal</th>
                            <c:forEach var="valoracion" items="${valoraciones}">
                                <c:if test="${valoracion.idtipo == tipo.idtipo}">
                                    <th>${valoracion.valoracion}</th>
                                </c:if>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="literal" items="${literales}">
                            <c:if test="${literal.idtipo == tipo.idtipo}">
                                <tr>
                                    <td>${literal.literal}</td>
                                    <c:forEach var="valoracion" items="${valoraciones}">
                                        <c:if test="${valoracion.idtipo == tipo.idtipo}">
                                            <td><input type="radio" name="literal${literal.idliteral}" required></td>
                                        </c:if>
                                    </c:forEach>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
            </c:forEach>
            
            
            <br>
            <input type="submit" value="Enviar">
        </form>
    <a href="/InstructoresProject/Login?accion=logout">Cancelar</a>
    </div>
    <%@include file="_footer.jsp" %>