<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
        <link rel="stylesheet" href="css/css/stylePrincipal.css">
        <link rel="stylesheet" href="css/custom_properties.css">
        <link rel="stylesheet" href="css/pag_n_sort.css">
        <style>
            #table01 td{ padding-top: 8px; cursor: pointer}
        </style>
        <title>Tests</title>
    </head>
    <body>
        <div id="contenido" style="padding: 10px">
        <h1>Listado de tests</h1>
        ${tabla}
        <script>
            //funcion javascript que se ejecuta al hacer click en una fila
            //recibe un elemento de tipo fila como parametro: row
            function _Seleccionar_(row){
                //recupera el idavion de la fila, en la celda 0
                var idtest = row.cells[0].innerHTML;
                //recupera descripcion del avion de la fila, en la celda 1
                var ciclo_test = row.cells[1].innerHTML;
                //asigna a las cajas de texto de la ventana padre los valores
                //obtenidos
                window.opener.location.href="${pageContext.servletContext.contextPath}/Tests?accion=literales&idtest="+idtest+"&ciclo_test="+ciclo_test;
                //cierra la ventana
                window.close();
            }
        </script>
        </div>
    </body>
</html>