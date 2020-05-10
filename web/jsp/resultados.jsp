<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<style>
    .btn:hover{
        cursor: pointer;
    }
</style>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="header">
            <c:if test="${error != null}">
                <p style="color: red"> ${error}</p>
            </c:if>
            <h1>Lista de grupos</h1>
        </div>
        <div class="results">
            <form class="results__filter" action="">
                <input type="text" id="buscar" oninput="hideRows(this, 'Ciclo', false)" autocomplete="off" placeholder="Buscar...">
                <select name="" id="category" onchange="hideRows(this, 'Ciclo')" title="Ciclo">
                    <option value="0">-- Filtrar por ciclo --</option>
                    <c:forEach var="c" items="${Ciclos}">
                        <option value="${c}">${c}</option>
                    </c:forEach>
                </select>
                <select name="" id="category" onchange="hideRows(this, 'Instructor')" title="Instructor">
                    <option value="0">-- Filtrar por instructor --</option>
                    <c:forEach var="i" items="${Instructores}">
                        <option value="${i}">${i}</option>
                    </c:forEach>
                </select>
                <input onclick="clear()" type="reset" id="reset"value="Quitar filtro">
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
                <p>Página</p>
                <button class="prev" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Principal?accion=resultados&pag_number=${pag.getPrevPage()}')">&lt;</button>
                <span>${pag.getCurrentPage()}/${pag.getTotalPages()}</span>
                <button class="next" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Principal?accion=resultados&pag_number=${pag.getNextPage()}')">&gt;</button>
            </div>
        </div>
        <div class="tablas">
            ${tabla}
        </div>        
    </div>
</main>
<script src="js/table_search.js"></script>
<script>
    function abrirVentana(URL){
        window.open(URL);
    }
</script>

<%@include file="../jsp/_footer.jsp"%>