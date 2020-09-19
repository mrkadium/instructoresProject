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
        <h1>Seleccionar materia</h1>
        <%@include file="../jsp/_table_top_panel.jspf"%>
        <%@include file="../jsp/_table_bottom_panel.jspf"%> 
    </div>        
    <script src="js/pag_n_sort.js"></script>  
    <script>
        function _Seleccionar_(row){
            var idmateria = row.cells[0].innerHTML;
            var materia = row.cells[1].innerHTML;
            window.opener.setDataMateria(idmateria, materia);
            window.close();
        }
    </script>
</body>
</html>