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
    <script src="js/js.js"></script>
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
                <h1>Selecci�n de men�</h1>
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
        //funcion javascript que se ejecuta al hacer click en una fila
        //recibe un elemento de tipo fila como parametro: row
        function _Seleccionar_(row){
            //recupera el idavion de la fila, en la celda 0
            var idmenu = row.cells[0].innerHTML;
            //recupera descripcion del avion de la fila, en la celda 1
            var menu = row.cells[1].innerHTML;
            //asigna a las cajas de texto de la ventana padre los valores
            //obtenidos
            window.opener.setDataMenu(idmenu, menu);
            //cierra la ventana
            window.close();
        }
//        $("#buscar").on('input', function(e){
//
//            //ordena el contenido de #buscar sin case-sensitiveness
//            var content = RegExp($("#buscar").val(), "i");
//
//            var rows = $('#table01 tbody tr');
//            var rowCount = ${rowCount};
//
//            for(i=0; i<rowCount; i++){
//                if(rows.eq(i).text().search(content) == "-1"){
//                    rows.eq(i).hide();
//                }else{
//                    rows.eq(i).show();
//                }
//            }
//        });
//        $("#buscar").parent().submit(function(e){
//            e.preventDefault();
//        });
    </script>
</body>
</html>
