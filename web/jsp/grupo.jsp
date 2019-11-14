<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@include file="_head.jsp" %>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleGrupo.css">

<style>
    <c:forEach var="obs" items="${observaciones}">
        #ob${obs.idevaluacion}::after{
            content: '${obs.fecha_realizacion}';
            position: absolute;
            font-size: 10px;
            bottom: 5px;
            right: 20px;
        }
    </c:forEach>
</style>

<%@include file="_nav.jsp" %>

<main>
    <div class="fondo">
        <div class="grupo-info">
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
            <p>Evaluaciones hechas a este grupo: <span>${cantidad}</span></p>
            <div class="prints">
                <a class="print" href="${pageContext.servletContext.contextPath}/Reportes?accion=detalle&id=${gr.idgrupo}"><i class="fas fa-print"></i> Generar reporte [detallado]</a>
                <a class="print" href="${pageContext.servletContext.contextPath}/Reportes?id=${gr.idgrupo}"><i class="fas fa-print"></i> Generar reporte [promedios]</a>
            </div>
        </div>
        <div class="mostrar">
            <label for="show">Mostrar detalles</label>
            <input type="checkbox" name="" id="show">
        </div>
        <div class="tablas t hide">
            ${tabla}
        </div>
        <div class="tablas tablita">
            ${tablita}
        </div>
        <div class="observaciones">
            <p>Observaciones:</p>
            <p class="cant_observaciones"><i class="fas fa-plus"></i> Mostrar todas</p>
            <c:forEach var="ob" items="${observaciones}" varStatus="i">
                <div class="textarea${i.index > 2 ? ' hide' : ''}" id="ob${ob.idevaluacion}"><p>${ob.observacion}</p></div>
            </c:forEach>
                
        </div>
    </div>
</main>
<script>
//    $(".t").hide();
//
//    $("#show").change(function(){
//        if($("#show").prop("checked")){
//            $(".tablita").hide();
//            $(".t").show();
//        }else{
//            $(".tablita").show();
//            $(".t").hide();
//        }
//    });
</script>
        
    <%@include file="_footer.jsp" %>