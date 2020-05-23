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
    <!--<link rel="stylesheet" href="css/css/styleGrupo.css">-->
    <!--<link rel="stylesheet" href="css/css/styleTest.css">-->
    <link rel="stylesheet" href="css/css/new_stylesTest.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <link rel="stylesheet" href="css/css/modal.css">
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
                    <input type="submit" value="Enviar" onclick="confirmSend(event)">
                </form>                          
            <div class="modal hide__modal">
                <div class="modal__content">
                    <p class="modal__msg"></p>
                    <button class="modal__btn"></button>
                </div>
            </div>
            <a onclick="leave('/InstructoresProject/Login?accion=logout')" class="cancelar">Cancelar</a>
        </div>
    </main>
    
   <footer>
        <p>Evaluación de instructores &copy; Universidad de Sonsonate - 2019</p>
        <p>Desarrollador: <span>Mario Adalberto Rivera Olivo</span></p>
    </footer>
            
    <script src="js/modal.js"></script>    
    <script>            
        // FUNCIÓN PARA DETECTAR SI ES UN MÓVIL
        detectmob();
        function detectmob() { 
            if( navigator.userAgent.match(/Android/i)
                || navigator.userAgent.match(/webOS/i)
                || navigator.userAgent.match(/iPhone/i)
                || navigator.userAgent.match(/iPad/i)
                || navigator.userAgent.match(/iPod/i)
                || navigator.userAgent.match(/BlackBerry/i)
                || navigator.userAgent.match(/Windows Phone/i)
            ){
                // showMensaje();
                showMessage('Para una mejor experiencia, ponga su dispositivo de manera horizontal','Entendido');
            }
        }
        function leave(dir){
            if(confirm('¿Realmente desea salir?')){
                window.location = dir;
            }
        }
        function confirmSend(e){
            if(!confirm('¿Enviar evaluación?')){
                e.preventDefault();
            }
        }
    </script>
</body>
</html>