<%@include file="../jsp/_head.jsp"%>
<link rel='stylesheet' href='css/css/styleTable.css'>
<link rel='stylesheet' href='css/css/styleEdicion.css'>
<link rel='stylesheet' href='css/css/styleConfiguracion.css'>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <c:if test="${resultado!=null}">
        <c:if test="${resultado==1}">
            <p class="correcto"><strong>Operación realizada correctamente.</strong></p>
        </c:if>
        <c:if test="${resultado==0}">
            <p class="error"><strong>La operación no se realizó.</strong></p>
        </c:if>
    </c:if>
    <div class="fondo">
        
        <div class="conf">
            <h1>Lista de tests</h1>
            <a href="${pageContext.servletContext.contextPath}/Tests?accion=insertar"><i class="fas fa-plus-circle"></i> Agregar</a>
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