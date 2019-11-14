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
            <h1>Lista de instructores</h1>
            <form action="">
                <input type="text" id="buscar" autocomplete="off" placeholder="Buscar...">
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