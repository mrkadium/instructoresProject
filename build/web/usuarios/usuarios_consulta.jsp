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
        <div class="conf">
            <h1>Lista de usuarios</h1>
            <a href="${pageContext.servletContext.contextPath}/Usuarios?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            <div class="header">
                <form action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    <select name="" id="category" title="Rol"> 
                        <option value="0">-- Filtrar por rol --</option>
                        <c:forEach var="r" items="${Roles}">
                            <option value="${r.rol}">${r.rol}</option>
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