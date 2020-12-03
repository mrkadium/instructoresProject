<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/custom_properties.css">
<link rel="stylesheet" href="css/pag_n_sort.css">
<%@include file="../jsp/_nav.jsp"%>
<%@include file="../jsp/_toast.jspf"%>
    <div class="tablas">
        <h1>Lista de grupos <a href="${pageContext.servletContext.contextPath}/Grupos?accion=insertar" class="new">+</a></h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <div>
            <select class="filter-field" name="filter-instructor" id="filter-instructor">
                <option value="0">-- Filtrar por Instructor --</option>
                <c:forEach var="c" items="${Instructores}">
                    <option value="${c}">${c}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <select class="filter-field" name="filter-catedratico" id="filter-catedratico">
                <option value="0">-- Filtrar por Catedrático --</option>
                <c:forEach var="c" items="${Catedraticos}">
                    <option value="${c}">${c}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <select class="filter-field" name="filter-ciclo" id="filter-ciclo">
                <option value="0">-- Filtrar por ciclo --</option>
                <c:forEach var="c" items="${Ciclos}">
                    <option value="${c}">${c}</option>
                </c:forEach>
            </select>
        </div>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>
    <script>setPage('grupos');</script>

<%@include file="../jsp/_footer.jsp"%>