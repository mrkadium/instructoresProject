<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de usuario</h2>
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
                    <input type="text" required class="input-largo" id="txtNombres" name="txtNombres" value="${usuario.nombres}" />
                </div>
                <div>
                    <label for="txtApellidos">Apellidos</label>
                    <input type="text" required class="input-largo" id="txtApellidos" name="txtApellidos" value="${usuario.apellidos}" />
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
            </form>
        </div>
    </div>
</main>
                

<!--
    <h1>Usuarios</h1>
    <br/>
    <form name="form_usuarios" onsubmit="return validar();"
    action="${pageContext.servletContext.contextPath}/Usuarios?accion=insertar_modificar"
    method="POST">
        <table border="0" id="table">
            <thead>
                <tr>
                <th colspan="">Complete la información<br><br></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td>ID Usuario</td>
                <td><input type="text" name="txtIdusuario" value="${usuario.idusuario}" readonly="readonly" /></td>
                </tr>
                <tr>
                <td>Usuario</td>
                <td><input type="text" name="txtUsuario" id="txtPais" value="${usuario.usuario}" /></td>
                </tr>
                <tr>
                <td>Nombres</td>
                <td><input type="text" name="txtNombres" id="txtPais" value="${usuario.nombres}" /></td>
                </tr>
                <tr>
                <td>Apellidos</td>
                <td><input type="text" name="txtApellidos" id="txtPais" value="${usuario.apellidos}" /></td>
                </tr>
                <tr>
                <td>Clave</td>
                <td><input type="text" name="txtClave" id="txtPais" value="${usuario.clave}" /></td>
                </tr>
                <tr>
                <td>Rol</td>
                <td>
                    <select id="cmbRoles">
                        <c:forEach var="r" items="${Roles}">
                            <option value="${r.idrol}">${r.rol}</option>
                        </c:forEach>
                    </select>
                </td>
                </tr>
            </tbody>
        </table>
        <br/>
        <div class="buttons">
            <ul>
                <li><input type="submit" value="Guardar" name="guardar"/></li>
                <li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li>
            </ul>   
        </div>
    </form>-->

<%@include file="../jsp/_footer.jsp"%>
