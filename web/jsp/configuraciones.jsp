<%@include file="_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
<%@include file="_nav.jsp" %>

<main>
    <div class="fondo">
        <div class="configuraciones">
            <h1>Modificables <i class="fas fa-caret-down"></i></h1>
            <ul class="hide">
                <li><a href="${pageContext.servletContext.contextPath}/Carreras">Carreras</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Facultades">Facultades</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Grupos">Grupos</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Materias">Materias</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Usuarios">Usuarios</a></li>
            </ul>
        </div>
        <div class="configuraciones">
            <h1>Plantillas <i class="fas fa-caret-down"></i></h1>
            <ul class="hide">
                <li><a href="${pageContext.servletContext.contextPath}/Plantillas_test">Plantillas de test</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Plantillas_literal">Plantillas de literal</a></li>
            </ul>
            <h1>Gestionar tests <i class="fas fa-caret-down"></i></h1>
            <ul class="hide">
                <li><a href="${pageContext.servletContext.contextPath}/Tests">Tests</a></li>
            </ul>
        </div>
        <div class="configuraciones">
            <h1>Avanzadas <i class="fas fa-caret-down"></i></h1>
            <ul class="hide">
                <li><a href="${pageContext.servletContext.contextPath}/Menus">Menús</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Permisos">Permisos</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Roles">Roles</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Tipos">Tipos</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/Valoraciones">Valoraciones</a></li>
            </ul>
        </div>
    </div>
</main>

<%@include file="_footer.jsp"%>