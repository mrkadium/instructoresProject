<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
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
                    <label for="txtClave"><span>*</span>Clave:</label>
                    <input type="text" name="txtClave" id="txtClave" autocomplete="off" disabled><br>
                    <input type="checkbox" name="" class="side_checkbox" id="change_pwd">
                    <label class="side_checkbox__label" for="change_pwd">Modificar</label>
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
                <script>
    const change_pwd__textbox = change_pwd.previousElementSibling.previousElementSibling;
    change_pwd.addEventListener('change',function(){
        change_pwd.checked ? 
        change_pwd__textbox.removeAttribute("disabled") : 
        change_pwd__textbox.setAttribute("disabled", true);
        change_pwd__textbox.focus();
    });
</script>
         
<%@include file="../jsp/_footer.jsp"%>
