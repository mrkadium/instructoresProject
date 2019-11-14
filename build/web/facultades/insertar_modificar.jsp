<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de facultad</h2>
            <form action="${pageContext.servletContext.contextPath}/Facultades?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdfacultad">ID Facultad:</label>
                    <input type="text" class="input-corto" id="txtIdfacultad" name="txtIdfacultad" value="${facultad.idfacultad}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtFacultad">Nombre facultad:</label>
                    <input type="text" required class="input-largo" id="txtFacultad" name="txtFacultad" value="${facultad.facultad}" />
                </div>
                <div>
                    <label for="txtAlias">Alias:</label>
                    <input type="text" required id="txtAlias" name="txtAlias" value="${facultad.alias}" />
                </div>
                <div>
                    <label for="cmbDecano">Decano</label>
                    <select name="cmbDecano" id="cmbDecano">
                        <option value="0">-- SELECCIONAR --</option>
                        <c:forEach var="usuario" items="${Usuarios}">
                            <option value="${usuario.idusuario}" ${facultad.iddecano == usuario.idusuario ? 'selected' : ''}>${usuario.nombres} ${usuario.apellidos}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
