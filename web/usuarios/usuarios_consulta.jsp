<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/custom_properties.css">
<link rel="stylesheet" href="css/pag_n_sort.css">
<%@include file="../jsp/_nav.jsp"%>
<%@include file="../jsp/_toast.jspf"%>
    <div class="tablas">
        <h1>Lista de usuarios</h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <div>
            <select class="filter-field" name="filter-instructor" id="filter-instructor">
                <option value="0">-- Filtrar por rol --</option>
                <c:forEach var="r" items="${Roles}">
                    <option value="${r.rol}">${r.rol}</option>
                </c:forEach>
            </select>
        </div>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>

<%@include file="../jsp/_footer.jsp"%>