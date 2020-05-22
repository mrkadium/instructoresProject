<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de tipo</h2>
            <form action="${pageContext.servletContext.contextPath}/Tipos?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdtipo">ID Tipo</label>
                    <input type="text" id="txtIdtipo" class="input-corto" name="txtIdtipo" value="${tipo.idtipo}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtTipo">Tipo</label>
                    <input type="text" required id="txtTipo" name="txtTipo" value="${tipo.tipo}" />
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Tipos')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
