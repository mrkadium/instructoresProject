<%--<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../jsp/_header.jsp"%>

    <h1>Valoraciones</h1>
    <br/>
    <form name="form_valoraciones" onsubmit="return validar();"
    action="${pageContext.servletContext.contextPath}/Valoraciones?accion=insertar_modificar"
    method="POST">
        <table border="0" id="table">
            <thead>
                <tr>
                <th colspan="">Complete la información<br><br></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td>ID Valoración</td>
                <td><input type="text" name="txtIdvaloracion" value="${valoracion.idvaloracion}" readonly="readonly" /></td>
                </tr>
                <tr>
                <td>Valoración</td>
                <td><input type="text" name="txtValoracion" id="txtPais" value="${valoracion.valoracion}" /></td>
                </tr>
                <tr>
                <td>Ponderación</td>
                <td><input type="text" name="txtPonderacion" id="txtPais" value="${valoracion.ponderacion}" /></td>
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
    </form>

<%@include file="../jsp/_footer.jsp"%>--%>

<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de valoración</h2>
            <form action="${pageContext.servletContext.contextPath}/Valoraciones?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdvaloracion">ID Valoración</label>
                    <input type="text" id="txtIdvaloracion" class="input-corto" name="txtIdvaloracion" value="${valoracion.idvaloracion}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtValoracion">Valoración:</label>
                    <input type="text" required id="txtValoracion" name="txtValoracion" value="${valoracion.valoracion}" />
                </div>
                <div>
                    <label for="cmbTipo">Tipo:</label>
                    <select name="cmbTipo" id="cmbTipo">
                        <c:forEach var="t" items="${Tipos}">
                            <option value="${t.idtipo}">${t.tipo}</option>
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

<%@include file="../jsp/_footer.jsp"%>
