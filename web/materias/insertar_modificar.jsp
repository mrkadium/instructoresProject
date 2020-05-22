<%@include file="../jsp/_head.jsp"%>
<!--<link rel="stylesheet" href="css/css/styleTable.css">-->
<link rel="stylesheet" href="css/css/new_stylesEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de materia</h2>
            <form action="${pageContext.servletContext.contextPath}/Materias?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdmateria">ID Materia</label>
                    <input type="text" id="txtIdmateria" class="input-corto" name="txtIdmateria" value="${materia.idmateria}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtMateria">Nombre materia:</label>
                    <input type="text" class="" required id="txtMateria" name="txtMateria" value="${materia.materia}" />
                </div>
                <div>
                    <label for="txtAlias">Alias:</label>
                    <input type="text" required id="txtAlias" name="txtAlias" value="${materia.alias}" />
                </div>
                <div>
                    <label for="cmbCarreras">Carrera:</label>
                    <select name="cmbCarreras" id="cmbCarreras">
                        <c:forEach var="c" items="${Carreras}">
                            <option value="${c.idcarrera}" ${materia.idcarrera == c.idcarrera ? 'selected' : ''}>${c.carrera}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
                <div>
                    <a href="javascript:void(0)" onclick="leave('${pageContext.servletContext.contextPath}/Materias')" class="cancel">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@include file="../jsp/_footer.jsp"%>
