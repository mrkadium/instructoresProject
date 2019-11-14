<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Insercion/Edición de plantilla de test</h2>
            <form action="${pageContext.servletContext.contextPath}/Plantillas_test?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdplantilla_test">ID Plantilla:</label>
                    <input type="text" id="txtIdplantilla_test" class="input-corto" name="txtIdplantilla_test" value="${plantilla_test.idplantilla_test}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtCiclo">Ciclo:</label>
                    <input type="text" required id="txtCiclo" name="txtCiclo" value="${plantilla_test.ciclo}" />
                </div>                
                <div class="ob">
                    <label for="observacion">Observación [opcional]:</label>
                    <textarea name="observacion" id="observacion" cols="30" rows="5"></textarea>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
