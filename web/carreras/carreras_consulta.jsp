<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado==1}">
            <p class="correcto"><strong>Operación realizada correctamente <i class="far fa-smile"></i></strong></p>
        </c:if>
        <c:if test="${resultado==0}">
            <p class="error"><strong>La operación no se realizó <i class="far fa-frown"></i></strong></p>
        </c:if>
        <c:if test="${resultado==2}">
            <p class="error"><strong>La operación no se realizó <i class="far fa-frown"></i></strong></p>
        </c:if>
    </c:if>
    <div class="fondo">
        <div class="conf">
            <h1>Lista de carreras</h1>
            <a href="${pageContext.servletContext.contextPath}/Carreras?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            <div class="header">
                <form action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                    <select name="" id="category" title="Facultad"> 
                        <option value="0">-- Filtrar por facultad --</option>
                        <c:forEach var="f" items="${Facultades}">
                            <option value="${f.facultad}">${f.facultad}</option>
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