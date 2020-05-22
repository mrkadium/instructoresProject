<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserci�n/Edici�n de usuario</h2>
            <form action="${pageContext.servletContext.contextPath}/Usuarios?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdusuario">ID Usuario:</label>
                    <input type="text" class="input-corto" id="txtIdusuario" name="txtIdusuario" value="${usuario.idusuario}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtUsuario">Usuario:</label>
                    <input type="text" required id="txtUsuario" name="txtUsuario" value="${usuario.usuario}" />
                </div>
                <div>
                    <label for="txtClave">Clave:</label>
                    <input type="text" required id="txtClave" name="txtClave" value="${usuario.clave}" />
                </div>
                <div>
                    <label for="txtNombres">Nombres:</label>
                    <input type="text" required class="" id="txtNombres" name="txtNombres" value="${usuario.nombres}" />
                </div>
                <div>
                    <label for="txtApellidos">Apellidos</label>
                    <input type="text" required class="" id="txtApellidos" name="txtApellidos" value="${usuario.apellidos}" />
                </div>
                <div>
                    <label for="cmbRoles">Rol:</label>
                    <select name="cmbRoles" id="cmbRol">
                        <option value="0">-- SELECCIONAR --</option>
                        <c:forEach var="r" items="${Roles}">
                            <c:if test="${usuario.idrol != r.idrol}">
                                <option value="${r.idrol}">${r.rol}</option>
                            </c:if>
                            <c:if test="${usuario.idrol == r.idrol}">
                                <option value="${r.idrol}" selected>${r.rol}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Usuarios')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
         
<%@include file="../jsp/_footer.jsp"%>
