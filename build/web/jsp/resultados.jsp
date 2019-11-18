<%@include file="../jsp/_head.jsp"%>
<link rel="stylesheet" href="css/css/styleTable.css">
<style>
    .btn:hover{
        cursor: pointer;
    }
</style>
<%@include file="../jsp/_nav.jsp"%>

<main>
    <div class="fondo">
        <div class="header">
            <c:if test="${error != null}">
                <p style="color: red"> ${error}</p>
            </c:if>
            <h1>Lista de grupos</h1>
            <form action="">
                <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
                <select name="" id="category" title="Ciclo">
                    <c:forEach var="c" items="${Ciclos}">
                        <option value="${c}">${c}</option>
                    </c:forEach>
                </select>
            </form>
        </div>    
        <div class="tablas">
            ${tabla}
        </div>        
    </div>
</main>
<script>
    function abrirVentana(URL){
        window.open(URL);
    }
</script>

<%@include file="../jsp/_footer.jsp"%>