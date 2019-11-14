<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
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
        <div class="conf">
            <h1>Lista de men�s</h1>
            <a href="${pageContext.servletContext.contextPath}/Menus?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            <div class="header">
                <form action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    <select name="" id="category" title="Padre"> 
                        <option value="0">-- Filtrar por rol --</option>
                        <option value="null">Sin padre</option>
                        <c:forEach var="m" items="${Menus}">
                            <option value="${m.menu}">${m.menu}</option>
                        </c:forEach>
                    </select>
                    <input type="reset" id="reset"value="Quitar filtro">
                </form>
            </div>
           <div class="tablas">
                ${tabla}
            </div>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>