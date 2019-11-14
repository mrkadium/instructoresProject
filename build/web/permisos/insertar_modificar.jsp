<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de permiso</h2>
            <form action="${pageContext.servletContext.contextPath}/Permisos?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdpermiso">ID Permiso:</label>
                    <input type="text" id="txtIdpermiso" class="input-corto" name="txtIdmenu" value="${menu.idmenu}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtIdmenu">Menú:</label>
                    <input type="text" class="input-corto" required id="txtIdmenu" readonly name="txtIdmenu" value="${menu.idpadre}" />
                    <input type="text" class="input-largo" required id="txtMenu" readonly name="txtMenu" value="${menu.padre}" />
                    <a onclick="abrirVentana('${pageContext.servletContext.contextPath}/Permisos?accion=menus');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="cmbRol">Rol:</label>
                    <select name="cmbRol" id="cmbRol">
                        <option value="0">-- SELECCIONAR --</option>
                        <c:forEach var="r" items="${Roles}">
                            <option value="${r.idrol}">${r.rol}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
            </form>
        </div>
        <br><br>
        <div class="tablas">
             ${tabla}
         </div> 
    </div>
</main>
<script>
    function setDataMenu(idmenu, menu) {
        document.getElementById("txtIdmenu").value = idmenu;
        document.getElementById("txtMenu").value = menu;

        var content = RegExp(menu, "i");
        var rows = $('#table01 tbody tr');
        var rowCount = ${rowCount};

        //MOSTRAR SÓLO LOS REGISTROS QUE CONTENGAN ESE MENÚ
        for(i=0; i<rowCount; i++){
            if(rows.eq(i).text().search(content) == "-1"){
                rows.eq(i).hide();
            }else{
                rows.eq(i).show();
            }
        }
        $("div.tablas").show();
    }
</script>

<%@include file="../jsp/_footer.jsp"%>
