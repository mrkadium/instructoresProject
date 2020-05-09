<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado==1}">
            <p class="correcto"><strong>Operación realizada correctamente.</strong></p>
        </c:if>
        <c:if test="${resultado==0}">
            <p class="error"><strong>La operación no se realizó.</strong></p>
        </c:if>
    </c:if>
    <div class="fondo">
        <!--<div class="conf">-->
            <div class="header">
                <h1>Lista de valoraciones</h1>
                <a class="add_record" href="${pageContext.servletContext.contextPath}/Valoraciones?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            </div>
            <div class="results">
                <form class="results__filter" action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                </form>
                <div class="results__info">
                    <p>Resultados</p>
                    <span>${pag.getCurrentLowerLimit()} - ${pag.getCurrentUpperLimit()}</span>
                </div>
                <div class="results__pagination">
                    <p>Página</p>
                    <button class="prev" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Valoraciones?pag_number=${pag.getPrevPage()}')">&lt;</button>
                    <span>${pag.getCurrentPage()}/${pag.getTotalPages()}</span>
                    <button class="next" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Valoraciones?pag_number=${pag.getNextPage()}')">&gt;</button>
                </div>
            </div>
           <div class="tablas">
                ${tabla}
            </div>
        <!--</div>-->
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>