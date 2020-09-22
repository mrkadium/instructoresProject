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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Evaluación de instructores</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
    <link rel="stylesheet" href="css/css/stylePrincipal.css">
    <link rel="stylesheet" href="css/test.css">
    <link rel="stylesheet" href="css/custom_properties.css">
    <link rel="stylesheet" href="css/pag_n_sort.css">
    <link rel="stylesheet" href="css/css/modal.css">
</head>
<body>
    <h1>Evaluación de instructores</h1>
    <main>        
        <header>
            <div class="test-info">
                <div class="info-item info-item--main">
                    <i class="fas fa-user"></i>
                    <h3>Instructor</h3>
                    <p>${gr.instructor}</p>
                </div>
                <div class="info-item">
                    <i class="fas fa-user-tie"></i>
                    <h3>Catedrático</h3>
                    <p>${gr.catedratico}</p>
                </div>
                <div class="info-item">
                    <i class="fas fa-book"></i>
                    <h3>Materia</h3>
                    <p>${gr.materia}</p>
                </div>
                <div class="info-item">
                    <i class="fas fa-users"></i>
                    <h3>Grupo</h3>
                    <p>${gr.numero_grupo}</p>
                </div>
                <div class="info-item">
                    <i class="fas fa-calendar"></i>
                    <h3>Ciclo</h3>
                    <p>${gr.ciclo}</p>
                </div>
            </div>
        </header>
                <form action="/InstructoresProject/Login?accion=enviarTest" method="POST">
            <div class="tablas">
            <c:forEach var="tipo" items="${CantidadesTipo}"> <!-- una tabla por cada tipo de literal -->
            <c:if test="${tipo.cantidad != 0}">
                <div class="tabla">
                    <table id="table01">
                        <thead>
                            <tr>
                                <th>Literal</th>
                                <c:forEach var="valoracion" items="${valoraciones}">
                                    <c:if test="${valoracion.idtipo == tipo.getIdTipo()}">
                                        <th>${valoracion.valoracion}</th>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </thead>                        
                        <tbody>
                            <c:forEach var="literal" items="${literales}">
                                <c:if test="${literal.idtipo == tipo.getIdTipo()}">
                                    <tr class='row'>
                                        <td>${literal.literal}</td>
                                        <c:if test="${tipo.tipo != 'cuantitativa'}">
                                            <c:forEach var="valoracion" items="${valoraciones}">
                                                <c:if test="${valoracion.idtipo == tipo.getIdTipo()}">
                                                    <td><input type="radio" name="literal${literal.idliteral}" value="${valoracion.idvaloracion}" required></td>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${tipo.tipo == 'cuantitativa'}">
                                            <c:forEach var="valoracion" items="${valoraciones}">
                                                <c:if test="${valoracion.idtipo == tipo.getIdTipo()}">                                                        
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
            </c:if>
            </c:forEach>
                <div class="form-bottom">
                    <div class="form-item">
                        <label for="observacion">Observación</label>
                        <textarea class="observacion" name="observacion" id="" cols="30" rows="4"></textarea>
                    </div>
                    <div class="form-buttons">
                        <a class="cancelar"  onclick="leave('/InstructoresProject/Login?accion=logout')">Cancelar</a>
                        <input class="enviar"  onclick="confirmSend(event)" type="submit" value="Enviar evaluación">
                    </div>
                </div>
            </div>
        </form>                          
        <div class="modal hide__modal">
            <div class="modal__content">
                <p class="modal__msg"></p>
                <button class="modal__btn"></button>
            </div>
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