<!DOCTYPE html>
<html lang="Es-es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="icon" href="img/Logo-USO.ico">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
    <link rel="stylesheet" href="css/css/stylePrincipal.css">
    <link rel="stylesheet" href="css/custom_properties.css">
    <link rel="stylesheet" href="css/pag_n_sort.css">
    <script src="js/new.js"></script>
    <style>
        #table01 tbody tr:hover{
            cursor: pointer;
        }
    </style>
    <title>Seleccionar</title>
</head>
<body>
    <div class="tablas">
        <h1>Seleccionar padre</h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>
    <script src="js/pag_n_sort.js"></script>           
    <script>
        //funcion javascript que se ejecuta al hacer click en una fila
        //recibe un elemento de tipo fila como parametro: row
        function _Seleccionar_(row){
            //recupera el idavion de la fila, en la celda 0
            var idpadre = row.cells[0].innerHTML;
            //recupera descripcion del avion de la fila, en la celda 1
            var padre = row.cells[1].innerHTML;
            //asigna a las cajas de texto de la ventana padre los valores
            //obtenidos
            window.opener.setDataPadre(idpadre, padre);
            //cierra la ventana
            window.close();
        }
    </script>
</body>
</html>