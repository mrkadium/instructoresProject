<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/new_stylesEdicion.css'>
<!--<link rel='stylesheet' href='css/css/styleConfiguracion.css'>-->
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">        
        
        <div class="formulario">
            <h2>Inserción/Edición de test</h2>
            <form action="${pageContext.servletContext.contextPath}/Tests" method="POST">
                <div>
                    <label for="txtIdtest">ID Test:</label>
                    <input type="text" id="txtIdtest" name="txtIdtest" value="${test.idtest}" readonly />
                </div>
                    <c:if test="${test.idtest == null}">
                <div>
                    <label for="txtIdplantilla_test">ID Plantilla:</label>
                    <input type="text" id="txtIdplantilla_test" name="txtIdplantilla_test" value="${idplantilla_test}" readonly="readonly" />
                    <a href="javascript:void(0)" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Tests?accion=plantillas_test');" class="puntos"><i class="fas fa-search"></i></a>   
                </div>
                    </c:if>
                <div>
                    <label for="txtCiclo">Ciclo:</label>
                    <input type="text" required id="txtCiclo" name="txtCiclo" value="${test.ciclo}" />
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Tests')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
                <br>
        <div class="conf">
            <h1>${op}</h1>
            <c:if test="${observacion != null}">
                <p>Observación de la plantilla: <i>${observacion}</i></p>
            </c:if>
            <div class="header">
                <form action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                </form>
            </div>
           <div class="tablas">
                ${tabla}
            </div> 
        </div>
                
    </div>
</main>

    <script>
        function abrirVentana (URL){
            //funcion javascript para abrir un subventana para realizar
            //busquedas, se le pasa la pagina a mostrar como parametro
            window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
        }
        
        function setDataPlantillaTest(idplantilla_test, ciclo) {
            document.getElementById("txtIdplantilla_test").value = idplantilla_test;
            document.getElementById("txtCiclo").value = ciclo;
        }
    </script>
<%@include file="../jsp/_footer.jsp"%>