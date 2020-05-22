<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de carrera</h2>
            <form action="${pageContext.servletContext.contextPath}/Carreras?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdcarrera">ID Carrera</label>
                    <input type="text" class="input-corto" id="txtIdcarrera" name="txtIdcarrera" value="${carrera.idcarrera}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtCarrera">Nombre carrera:</label>
                    <input type="text" class="" required id="txtCarrera" name="txtCarrera" value="${carrera.carrera}" />
                </div>
                <div>
                    <label for="txtCodigo">Código:</label>
                    <input type="text" class="" required id="txtCodigo" name="txtCodigo" value="${carrera.codigo}" />
                </div>
                <div>
                    <label for="cmbFacultad">Facultad:</label>
                    <select name="cmbFacultad" id="cmbFacultad">
                        <option value="0">-- SELECCIONAR --</option>
                        <c:forEach var="f" items="${Facultades}">
                            <c:if test="${carrera.idfacultad != f.idfacultad}">
                                <option value="${f.idfacultad}">${f.facultad}</option>
                            </c:if>
                            <c:if test="${carrera.idfacultad == f.idfacultad}">
                                <option value="${f.idfacultad}" selected>${f.facultad}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Carreras')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>


<%@include file="../jsp/_footer.jsp"%>
