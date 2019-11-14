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
    .print:hover{
        cursor: pointer;
    }
</style>

<%@include file="_nav.jsp" %>

<main>
    <div class="fondo">
        <div class="grupo-info">
            <table>
                <tr>
                    <th>ID</th>
                    <th class="icono"><i class="fas fa-book"></i></th>
                    <td>${ins.idinstructor}</td>
                </tr>
                <tr>
                    <th>Instructor</th>
                    <th class="icono"><i class="fas fa-users"></i></th>
                    <td>${ins.instructor}</td>
                </tr>
                <tr>
                    <th>Usuario</th>
                    <th class="icono"><i class="fas fa-user"></i></th>
                    <td>${ins.usuario}</td>
                </tr>
                <tr>
                    <th>Cantidad de laboratorios</th>
                    <th class="icono"><i class="fas fa-user"></i></th>
                    <td>${ins.cantidad}</td>
                </tr>
            </table>
            <p>Evaluaciones hechas a este instructor: <span>${cantidad}</span></p>
            <a class="print" onclick='abrirVentana("${pageContext.servletContext.contextPath}/Reportes?id=${ins.idinstructor}");'><i class="fas fa-print"></i> Generar reporte</a>
        </div>
        <div class="mostrar">
            <label for="show">Mostrar detalles</label>
            <input type="checkbox" name="" id="show">
        </div>
        <div class="tablas t">
            ${tabla}
        </div>
        <div class="tablas tablita">
            ${tablita}
        </div>
        <div class="observaciones">
            <p>Últimas observaciones:</p>
            <c:forEach var="ob" items="${observaciones}">
                <div class="textarea" id="ob${ob.idevaluacion}"><p>${ob.observacion}</p></div>
            </c:forEach>
                
        </div>
    </div>
</main>
<script>
    $(".t").hide();

    $("#show").change(function(){
        if($("#show").prop("checked")){
            $(".tablita").hide();
            $(".t").show();
        }else{
            $(".tablita").show();
            $(".t").hide();
        }
    });
    function abrirVentana(URL){
        window.open(URL);
    }
</script>
        
    <%@include file="_footer.jsp" %>