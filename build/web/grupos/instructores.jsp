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
    <script src="js/new.js"></script>
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
                <h1>Selección de instructor</h1>
                <form action="">
                    <!-- <label for="buscar">Buscar:</label> -->
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
            var idinstructor = row.cells[0].innerHTML;
            var instructor = row.cells[2].innerHTML;
            window.opener.setDataInstructor(idinstructor, instructor);
            window.close();
        }
    </script>
</body>
</html>