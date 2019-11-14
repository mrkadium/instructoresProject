<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<% 
    response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    HttpSession sesion = request.getSession(); 
    if(sesion.getAttribute("gr") == null){
        response.sendRedirect("Login");
    }
    
%>
<!DOCTYPE html>
<html lang="ES-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/Logo-USO.ico">
    <link rel="stylesheet" href="css/css/stylePrincipal.css">
    <link rel="stylesheet" href="css/css/styleTable.css">
    <link rel="stylesheet" href="css/css/styleGrupo.css">
    <link rel="stylesheet" href="css/css/styleTest.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <script src="js/jquery-3.3.1.js"></script>
    <script src="js/main.js"></script>
    <title>Evaluación</title>
</head>
<body>
    <main>
        <div class="fondo">
            <div class="grupo-info">
            <h1>Evaluación de instructor</h1>
                <table>
                    <tr>
                        <th>Materia</th>
                        <th class="icono"><i class="fas fa-book"></i></th>
                        <td>${gr.materia}</td>
                    </tr>
                    <tr>
                        <th>Grupo</th>
                        <th class="icono"><i class="fas fa-users"></i></th>
                        <td>${gr.numero_grupo}</td>
                    </tr>
                    <tr>
                        <th>Instructor</th>
                        <th class="icono"><i class="fas fa-user"></i></th>
                        <td>${gr.instructor}</td>
                    </tr>
                    <tr>
                        <th>Catedrático</th>
                        <th class="icono"><i class="fas fa-user-tie"></i></th>
                        <td>${gr.catedratico}</td>
                    </tr>
                    <tr>
                        <th>Ciclo</th>
                        <th class="icono"><i class="fas fa-calendar"></i></th>
                        <td>${gr.ciclo}</td>
                    </tr>
                </table>
            </div>
            
                <form action="/InstructoresProject/Login?accion=enviarTest" method="post">
                    <c:forEach var="tipo" items="${tipos}"> <!-- una tabla por cada tipo de literal -->
                        <div class="tablas">
                        <table id="table01">
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
                                        <tr class='row'>
                                            <td>${literal.literal}</td>
                                            <c:if test="${tipo.tipo != 'cuantitativa'}">
                                                <c:forEach var="valoracion" items="${valoraciones}">
                                                    <c:if test="${valoracion.idtipo == tipo.idtipo}">
                                                        <td><input type="radio" name="literal${literal.idliteral}" value="${valoracion.idvaloracion}" required></td>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${tipo.tipo == 'cuantitativa'}">
                                                <c:forEach var="valoracion" items="${valoraciones}">
                                                    <c:if test="${valoracion.idtipo == tipo.idtipo}">                                                        
                                                        <td><input style="width:40px" type="text" class="cuan" name="literal${literal.idliteral}" required></td>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </c:forEach>
                    <div class="ob">
                        <label for="observacion">Observación [opcional]:</label>
                        <textarea name="observacion" id="observacion" cols="30" rows="5"></textarea>
                    </div>
                    <input type="submit" value="Enviar">
                </form>                
            
            <a href="/InstructoresProject/Login?accion=logout" class="cancelar">Cancelar</a>
        </div>
    </main>
    
    
<!--    <div class="contenedor2">
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
            
            
            <c:forEach var="tipo" items="${tipos}">  una tabla por cada tipo de literal 
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
                                <tr class='row'>
                                    <td>${literal.literal}</td>
                                    <c:if test="${tipo.tipo != 'cuantitativa'}">
                                        <c:forEach var="valoracion" items="${valoraciones}">
                                            <c:if test="${valoracion.idtipo == tipo.idtipo}">
                                                <td><input type="radio" name="literal${literal.idliteral}" required></td>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${tipo.tipo == 'cuantitativa'}">
                                        <c:forEach var="valoracion" items="${valoraciones}">
                                            <c:if test="${valoracion.idtipo == tipo.idtipo}">
                                                <td><input style="width:40px" type="text" name="literal${literal.idliteral}" required></td>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
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
    </div>-->
   <footer>
        <p>Evaluación de instructores &copy; Universidad de Sonsonate - 2019</p>
        <p>Desarrollador: <span>Mario Adalberto Rivera Olivo</span></p>
    </footer>
            
    <script>
        $(document).ready(function(){
            var campo = $(".cuan");
            campo.keyup(function(){
                var nonum = false, vacio = false;
                if(!jQuery.isNumeric(campo.val())){
                    nonum = true;
                }else{
                    nonum = false;
                }
                if(campo.val() == ""){
                    vacio = true;
                }else{
                    vacio = false;
                }
                if(nonum){
                    campo.val("");
                }else{
                    if(campo.val() < 1 || campo.val() > 10){
                        campo.val("");
                    }
                }
            });
            
            $("form").submit(function(){
                var value = $(".cuan").val();
                $(".cuan").val(value+"@");
            });
        });
    </script>
</body>
</html>