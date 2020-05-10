<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleEdicion.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado==1}">
            <p class="correcto"><strong>Operaci�n realizada correctamente.</strong></p>
        </c:if>
        <c:if test="${resultado==0}">
            <p class="error"><strong>La operaci�n no se realiz�.</strong></p>
        </c:if>
    </c:if>
    <div class="fondo">
        
        <!--<div class="conf">-->
            <div class="header">
                <h1>Lista de tests</h1>
                <a class="add_record" href="${pageContext.servletContext.contextPath}/Tests?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
            </div>
            <div class="results">
                <form class="results__filter" action="">
                    <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                </form>
                <div class="results__total">
                    <p>Resultados</p>
                    <span>${pag.getTotalRecords()}</span>
                </div>
                <div class="results__info">
                    <p>Mostrados</p>
                    <span>${pag.getCurrentLowerLimit()} - ${pag.getCurrentUpperLimit()}</span>
                </div>
                <div class="results__pagination">
                    <p>P�gina</p>
                    <button class="prev" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Tests?pag_number=${pag.getPrevPage()}')">&lt;</button>
                    <span>${pag.getCurrentPage()}/${pag.getTotalPages()}</span>
                    <button class="next" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Tests?pag_number=${pag.getNextPage()}')">&gt;</button>
                </div>
            </div>
           <div class="tablas">
                ${tabla}
            </div> 
        <!--</div>-->
            
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