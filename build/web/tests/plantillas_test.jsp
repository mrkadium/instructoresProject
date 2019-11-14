<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="Es-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/Logo-USO.ico">
    <link rel="stylesheet" href="css/css/stylePrincipal.css">
    <link rel="stylesheet" href="css/css/styleTable.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
    <script src="js/jquery-3.3.1.js"></script>
    <script src="js/main.js"></script>
    <style>
        #table01 tbody tr:hover{
            cursor: pointer;
        }
    </style>
    <title>Seleccionar</title>
</head>
<body>        
    <main>
        <div class="fondo">
            <div class="header">
                <h1>Selecci�n de materia</h1>
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
        function _Seleccionar_(row){
            var idplantilla_test = row.cells[0].innerHTML;
            var ciclo = row.cells[1].innerHTML;
            var observacion = row.cells[2].innerHTML;
            window.opener.location.href="${pageContext.servletContext.contextPath}/Tests?accion=plantillas_literal&idplantilla_test="+idplantilla_test+"&ciclo="+ciclo+"&observacion="+observacion;
            window.close();
        }
    </script>
</body>
</html>