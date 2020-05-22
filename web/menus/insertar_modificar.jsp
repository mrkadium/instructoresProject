<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de menú</h2>
            <form action="${pageContext.servletContext.contextPath}/Menus?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdmenu">ID Menú</label>
                    <input type="text" id="txtIdmenu" class="input-corto" name="txtIdmenu" value="${menu.idmenu}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtMenu">Nombre menú:</label>
                    <input type="text" required id="txtMenu" name="txtMenu" value="${menu.menu}" />
                </div>
                <div>
                    <label for="txtDescripcion">Descripción:</label>
                    <input type="text" class="" required id="txtDescripcion" name="txtDescripcion" value="${menu.descripcion}" />
                </div>
                <div>
                    <label for="txtUrl">URL:</label>
                    <input type="text" class="" required id="txtUrl" name="txtUrl" value="${menu.url}" />
                </div>
                <div>
                    <label for="txtIdcss">ID CSS:</label>
                    <input type="text" required id="txtIdcss" name="txtIdcss" value="${menu.idcss}" />
                </div>
                <div>
                    <label for="txtImg">Imagen:</label>
                    <input type="text" required id="txtImg" name="txtImg" value="${menu.img}" />
                </div>
                <div>
                    <label for="txtIdpadre">Padre:</label>
                    <input type="text" class="input-corto" required id="txtIdpadre" name="txtIdpadre" value="${menu.idpadre}" />
                    <input type="text" class="input-largo" required id="txtPadre" name="txtPadre" value="${menu.padre}" />
                    <a href="javascript:void(0)" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Menus?accion=padre');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Menus')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>
<script>
    function abrirVentana(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataPadre(idpadre, padre) {
    document.getElementById("txtIdpadre").value = idpadre;
    document.getElementById("txtPadre").value = padre;
    }
</script>

<%@include file="../jsp/_footer.jsp"%>
