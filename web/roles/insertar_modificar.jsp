<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserci�n/Edici�n de rol</h2>
            <form action="${pageContext.servletContext.contextPath}/Roles?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdrol">ID Rol</label>
                    <input type="text" id="txtIdrol" class="input-corto" name="txtIdrol" value="${rol.idrol}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtRol">Rol:</label>
                    <input type="text" required id="txtRol" name="txtRol" value="${rol.rol}" />
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Roles')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
