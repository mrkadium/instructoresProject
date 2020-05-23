<!--</div>-->
                    
    <a class="regresar" href="#" onclick="javascript: return window.history.back()"><i class="fas fa-arrow-left"></i> Regresar</a>
    <footer>
        <p>Evaluación de instructores &copy; Universidad de Sonsonate - 2019</p>
        <p>Desarrollador: <span>Mario Adalberto Rivera Olivo</span></p>
        <p id="flaticon">Icons made by <a href="https://www.flaticon.com/authors/pixelmeetup" title="Pixelmeetup">Pixelmeetup</a> from <a href="https://www.flaticon.com/" 			    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></p>
    </footer>
    <script src="js/gen_pwd.js"></script>
    <script src="js/table_search.js"></script>
    <script src="js/copyToClipboard.js"></script>
    <script>
        function abrirVentana(URL){
            window.open(URL,"_blank","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
        }
        function pageChange(URL){
            window.open(URL,"_self","width=700,height=400,scrollbars=YES,statusbar=YES,top=150,left=300");
        }
        function leave(dir){
            if(confirm('¿Realmente desea salir?')){
                window.location = dir;
            }
        }
    </script>
    </body>
</html>
