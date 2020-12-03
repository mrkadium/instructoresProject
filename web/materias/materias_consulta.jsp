<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/custom_properties.css">
<link rel="stylesheet" href="css/pag_n_sort.css">
<%@include file="../jsp/_nav.jsp"%>
<%@include file="../jsp/_toast.jspf"%>
    <div class="tablas">
        <h1>Lista de materias <a href="${pageContext.servletContext.contextPath}/Materias?accion=insertar" class="new">+</a></h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <div>
            <select class="filter-field" name="filter-carrera" id="filter-carrera">
                <option value="0">-- Filtrar por carrera --</option>
                <c:forEach var="c" items="${Carreras}">
                    <option value="${c.carrera}">${c.carrera}</option>
                </c:forEach>
            </select>
        </div>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>
    <script>setPage('resultados');</script>

<%@include file="../jsp/_footer.jsp"%>