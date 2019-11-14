<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h1>Perfil</h1>
            <form name="form_usuarios" action="${pageContext.servletContext.contextPath}/Principal?accion=actualizarUsuario">
                <div>
                    <label for="txtIdusuario">ID Usuario:</label>
                    <input type="text" name="txtIdusuario" id="txtIdusuario" value="${usuario.idusuario}" readonly>
                </div>
                <div>
                    <label for="txtNombres">Nombres:</label>
                    <input type="text" name="txtNombres" id="txtNombres" value="${usuario.nombres}" readonly>
                </div>
                <div>
                    <label for="txtApellidos">Apellidos:</label>
                    <input type="text" name="txtApellidos" id="txtApellidos" value="${usuario.apellidos}" readonly>
                </div>
                <div>
                    <label for="txtUsuario"><span>*</span>Usuario:</label>
                    <input type="text" name="txtUsuario" id="txtUsuario" value="${usuario.usuario}" autocomplete="off">
                </div>
                <div>
                    <label for="txtClave"><span>*</span>Clave:</label>
                    <input type="text" name="txtClave" id="txtClave" autocomplete="off">
                </div>
                <div class="submit">
                    <input type="submit" value="Guardar" name="" id="">
                </div>
                <p><span>*</span> Campos modificables</p>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>