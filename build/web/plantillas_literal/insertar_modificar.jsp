<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de plantilla de literal</h2>
            <form action="${pageContext.servletContext.contextPath}/Plantillas_literal?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdplantilla_literal">ID Plantilla de literal:</label>
                    <input type="text" id="txtIdplantilla_literal" class="input-corto" name="txtIdplantilla_literal" value="${plantilla_literal.idplantilla_literal}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtIdplantilla_test">Plantilla de test:</label>
                    <input type="text" class="input-corto" required id="txtIdplantilla_test" readonly name="txtIdplantilla_test" value="${plantilla_literal.idplantilla_test}" />
                    <input type="text" class="input-largo" required id="txtCiclo" readonly name="txtCiclo" value="${pt.ciclo}" />
                    <a href="#" onclick="abrirVentan('${pageContext.servletContext.contextPath}/Plantillas_literal?accion=plantillas_test');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="txtLiteral">Literal:</label>
                    <input type="text" id="txtLiteral" class="input-largo" name="txtLiteral" value="${plantilla_literal.literal}" />
                </div>
                <div>
                    <label for="cmbTipo">Tipo:</label>
                    <select name="cmbTipo" id="cmbTipo">
                        <option value="0">-- SELECCIONAR --</option>
                        <c:forEach var="t" items="${Tipos}">
                            <c:if test="${t.idtipo == plantilla_literal.idtipo}">
                                <option value="${t.idtipo}" selected>${t.tipo}</option>
                            </c:if>
                            <c:if test="${t.idtipo != plantilla_literal.idtipo}">
                                <option value="${t.idtipo}">${t.tipo}</option>
                            </c:if>
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
    function abrirVentan(URL){
        window.open(URL,"ventana1","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    const tablas = document.querySelector("div.tablas");
    tablas.style = "display: none;";
    function setDataPTest(idptest, ciclo) {
        document.getElementById("txtIdplantilla_test").value = idptest;
        document.getElementById("txtCiclo").value = ciclo;

        var content = RegExp(idptest, "i");
        var rows = document.querySelectorAll("#table01 tbody tr");

        //MOSTRAR SÓLO LOS REGISTROS QUE CONTENGAN ESE MENÚ
//        for(i=0; i<rowCount; i++){
//            if(rows.eq(i).text().search(content) == "-1"){
//                rows.eq(i).hide();
//            }else{
//                rows.eq(i).show();
//            }
//        }
        for(r of rows){
            r.textContent.search(content) == "-1" ? r.style = "display: none;" : r.style = "";
        }
        tablas.style = "display: none;";
    }
</script>

<%@include file="../jsp/_footer.jsp"%>
