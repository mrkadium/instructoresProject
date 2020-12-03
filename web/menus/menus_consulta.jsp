<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/custom_properties.css">
<link rel="stylesheet" href="css/pag_n_sort.css">
<%@include file="../jsp/_nav.jsp"%>
<%@include file="../jsp/_toast.jspf"%>
    <div class="tablas">
        <h1>Grupos de menús <a href="${pageContext.servletContext.contextPath}/Menus?accion=insertar" class="new">+</a></h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <div>
            <select class="filter-field" name="filter-rol" id="filter-rol">
                <option value="0">-- Filtrar por padre --</option>
                <option value="null">Sin padre</option>
                <c:forEach var="m" items="${Menus}">
                    <option value="${m.menu}">${m.menu}</option>
                </c:forEach>
            </select>
        </div>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>
    <script>setPage('menus');</script>

<%@include file="../jsp/_footer.jsp"%>