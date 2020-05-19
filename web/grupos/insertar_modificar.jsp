<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<link rel="stylesheet" href="css/css/styleEdicion.css">
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="formulario">
            <h2>Inserción/Edición de grupo</h2>
            <form action="${pageContext.servletContext.contextPath}/Grupos?accion=insertar_modificar" method="POST">
                <div>
                    <label for="txtIdgrupo">ID Grupo:</label>
                    <input type="text" class="input-corto" id="txtIdgrupo" name="txtIdgrupo" value="${grupo.idgrupo}" readonly="readonly" />
                </div>
                <div>
                    <label for="txtIdmateria">Materia:</label>
                    <input type="text" class="input-corto" required id="txtIdmateria" readonly name="txtIdmateria" value="${grupo.idmateria}" />
                    <input type="text" class="input-largo" required id="txtMateria" readonly name="txtMateria" value="${m.materia}" />
                    <a href="#" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Grupos?accion=materias');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="txtIdcatedratico">Catedrático:</label>
                    <input type="text" class="input-corto" required id="txtIdcatedratico" readonly name="txtIdcatedratico" value="${grupo.idcatedratico}" />
                    <input type="text" class="input-largo" required id="txtCatedratico" readonly name="txtCatedratico" value="${c.nombres} ${c.apellidos}" />
                    <a href="#" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Grupos?accion=catedraticos');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="txtIdinstructor">Instructor:</label>
                    <input type="text" class="input-corto" required id="txtIdinstructor" readonly name="txtIdinstructor" value="${grupo.idinstructor}" />
                    <input type="text" class="input-largo" required id="txtInstructor" readonly name="txtInstructor" value="${i.nombres} ${i.apellidos}" />
                    <a href="#" onclick="abrirVentana('${pageContext.servletContext.contextPath}/Grupos?accion=instructores');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="txtNumeroGrupo">No. Grupo:</label>
                    <input type="text" id="txtNumeroGrupo" name="txtNumeroGrupo" value="${grupo.numero_grupo}"/>
                </div>
                <div>
                    <label for="txtClave">Clave:</label>
                    <input type="text" class="gen_pwd_out" required id="txtClave" name="txtClave" value="${grupo.clave}" />
                    <button id="gen_pwd">Generar contraseña</button>
                </div>
                <div>
                    <label for="txtCiclo">Ciclo:</label>
                    <input type="text" id="txtCiclo" name="txtCiclo" value="${grupo.ciclo}"/>
                </div>
                <div>
                    <label for="txtIdtest">Test</label>
                    <input type="text" class="input-corto" required id="txtIdtest" readonly name="txtIdtest" value="${grupo.idtest}" />
                    <input type="text" class="input-largo" required id="txtCiclo_test" readonly name="txtCiclo_test" value="${t.ciclo}" />
                    <a href="#" onclick="openWindow('${pageContext.servletContext.contextPath}/Grupos?accion=tests');" class="puntos"><i class="fas fa-search"></i></a>                    
                </div>
                <div>
                    <label for="cmbEstado">Estado</label>
                    <select name="cmbEstado" id="cmbEstado">
                        <option value="Habilitado" ${grupo.estado == 'Habilitado' ? 'selected' : ''}>Habilitado</option>
                        <option value="Inhabilitado"${grupo.estado == 'Inhabilitado' ? 'selected' : ''}>Inhabilitado</option>
                    </select>
                </div>
                <div class="submit">
                    <input type="submit" value="Enviar" name="" id="">
                </div>
            </form>
        </div>
    </div>
</main>
<script> 
    function openWindow(URL){
        window.open(URL,"_blank","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
    }
    function setDataMateria(idmateria, materia) {
        document.getElementById("txtIdmateria").value = idmateria;
        document.getElementById("txtMateria").value = materia;
    }
    function setDataCatedratico(idcatedratico, catedratico) {
        document.getElementById("txtIdcatedratico").value = idcatedratico;
        document.getElementById("txtCatedratico").value = catedratico;
    }
    function setDataInstructor(idinstructor, instructor) {
        document.getElementById("txtIdinstructor").value = idinstructor;
        document.getElementById("txtInstructor").value = instructor;
    }
    function setDataTest(idtest, ciclo) {
        document.getElementById("txtIdtest").value = idtest;
        document.getElementById("txtCiclo_test").value = ciclo;
    }
</script>

<%@include file="../jsp/_footer.jsp"%>