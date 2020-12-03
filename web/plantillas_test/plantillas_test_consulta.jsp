<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/custom_properties.css">
<link rel="stylesheet" href="css/pag_n_sort.css">
<%@include file="../jsp/_nav.jsp"%>
<%@include file="../jsp/_toast.jspf"%>
    <div class="tablas">
        <h1>Lista de plantillas de test <a href="${pageContext.servletContext.contextPath}/Plantillas_test?accion=insertar" class="new">+</a></h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>
    <script>setPage('plantillas_test');</script>

<%@include file="../jsp/_footer.jsp"%>