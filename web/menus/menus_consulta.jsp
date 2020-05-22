<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<!--<link rel='stylesheet' href='css/css/styleConfiguracion.css'>-->
<%@include file="../jsp/_nav.jsp"%>

<main>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado==1}">
            <p class="correcto"><strong>Operaci�n realizada correctamente.</strong></p>
        </c:if>
        <c:if test="${resultado==0}">
            <p class="error"><strong>La operaci�n no se realiz�.</strong></p>
        </c:if>
    </c:if>
    <div class="fondo">
        <!--<div class="conf">-->
            <div class="header">
                <h1>Lista de men�s</h1>
                <a class="add_record" href="${pageContext.servletContext.contextPath}/Menus?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            </div>
            <div class="results">
                <form class="results__filter" action="">
                    <input type="text" id="buscar" oninput="hideRows(this, 'Ciclo', false)" autocomplete="off" placeholder="Buscar...">
                    <select name="" id="category" onchange="hideRows(this, 'Rol')" title="Padre"> 
                        <option value="0">-- Filtrar por rol --</option>
                        <option value="null">Sin padre</option>
                        <c:forEach var="m" items="${Menus}">
                            <option value="${m.menu}">${m.menu}</option>
                        </c:forEach>
                    </select>
                    <input type="reset" id="reset"value="Quitar filtro">
                </form>
                <div class="results__total">
                    <p>Resultados</p>
                    <span>${pag.getTotalRecords()}</span>
                </div>
                <div class="results__info">
                    <p>Mostrados</p>
                    <span>${pag.getCurrentLowerLimit()} - ${pag.getCurrentUpperLimit()}</span>
                </div>
                <div class="results__pagination">
                    <p>P�gina</p>
                    <button class="prev" onclick="pageChange('${pageContext.servletContext.contextPath}/Menus?pag_number=${pag.getPrevPage()}')">&lt;</button>
                    <span>${pag.getCurrentPage()}/${pag.getTotalPages()}</span>
                    <button class="next" onclick="pageChange('${pageContext.servletContext.contextPath}/Menus?pag_number=${pag.getNextPage()}')">&gt;</button>
                </div>
            </div>
           <div class="tablas">
                ${tabla}
            </div>
        <!--</div>-->
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>