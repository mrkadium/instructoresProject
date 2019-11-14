<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
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
                    <input type="text" class="input-largo" required id="txtCarrera" name="txtCarrera" value="${carrera.carrera}" />
                </div>
                <div>
                    <label for="txtCodigo">Código:</label>
                    <input type="text" class="input-largo" required id="txtCodigo" name="txtCodigo" value="${carrera.codigo}" />
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
            </form>
        </div>
    </div>
</main>




<!--    <h1>Carreras</h1>
    <br/>
    <form name="form_facultades" onsubmit="return validar();"
    action="${pageContext.servletContext.contextPath}/Facultades?accion=insertar_modificar"
    method="POST">
        <table border="0" id="table">
            <thead>
                <tr>
                <th colspan="">Complete la información<br><br></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td>ID Carrrera</td>
                <td><input type="text" name="txtIdcarrera" value="${carrera.idcarrera}" readonly="readonly" /></td>
                </tr>
                <tr>
                <td>Nombre Carrera</td>
                <td><input type="text" name="txtCarrera" id="txtPais" value="${carrera.carrera}"  required/></td>
                </tr>
                <tr>
                <td>Facultad</td>
                <td>
                    <select id="cmbFacultad" required>
                        <c:forEach var="f" items="${Facultades}">
                            <option value="${f.idfacultad}">${f.facultad}</option>
                        </c:forEach>
                    </select>
                </td>
                </tr>
            </tbody>
        </table>
        <br/>
        <div class="buttons">
            <ul>
                <li><input type="submit" value="Guardar" name="guardar"/></li>
                <li><a href="#" onclick="javascript: return window.history.back()">Regresar</a></li>
            </ul>   
        </div>
    </form>-->

<%@include file="../jsp/_footer.jsp"%>
