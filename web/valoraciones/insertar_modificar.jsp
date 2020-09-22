<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de valoración</h2>
            <form action="${pageContext.servletContext.contextPath}/Valoraciones?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdvaloracion">ID Valoración</label>
                    <input type="text" id="txtIdvaloracion" class="input-corto" name="txtIdvaloracion" value="${valoracion.idvaloracion}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtValoracion">Valoración:</label>
                    <input type="text" required id="txtValoracion" name="txtValoracion" value="${valoracion.valoracion}" />
                </div>
                <div>
                    <label for="cmbTipo">Tipo:</label>
                    <select name="cmbTipo" id="cmbTipo">
                        <c:forEach var="t" items="${Tipos}">
                            <option value="${t.idtipo}" ${valoracion.idtipo == t.idtipo ? 'selected' : ''}>${t.tipo}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label for="txtPuntaje">Puntaje</label>
                    <input type="text" required id="txtPuntaje" name="txtPuntaje" value="${valoracion.puntaje}" />
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Valoraciones')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
