window.onload = function(){

    // BURGER MENU
    const mob = document.querySelector(".mob");
    const fabars = document.querySelector(".burger .fa-bars");
    const fatimes = document.querySelector(".burger .fa-times");

    mob.style = "display: none;";

    if(mob && fabars && fatimes){
        fabars.addEventListener('click', function(){
            fabars.style = "display: none;";
            fatimes.style = "display: block;";
            mob.style = "";
        });
    }

    if(mob && fabars && fatimes){
        fatimes.addEventListener('click', function(){
            fatimes.style = "display: none;";
            fabars.style = "display: block;";
            mob.style = "display: none;";
        });
    }



    
    // PRINCIPAL
//    const article = document.querySelector("article");
//    
//    if(article){
//        article.onclick = function(){
//            location.href = "tablas.html";
//        }
//    }




    // MODIFICABLES
    const configs = document.querySelector('.configuraciones');
    const h1Configs = document.querySelectorAll('.configuraciones h1');
    const h1IConfigs = document.querySelectorAll('.configuraciones h1 i');
    const ulConfigs = document.querySelectorAll('.configuraciones h1+ul');
    const arrowUp = 'fa-caret-up';
    const arrowDown = 'fa-caret-down';

    if(configs){
        for(i=0; i < h1Configs.length; i++){
            let ul = ulConfigs[i];
            let h1 = h1Configs[i];
            let h1I = h1IConfigs[i];
            h1.addEventListener('click', function(){
                ul.classList.toggle('hide');
                if(!ul.classList.contains('hide')){
                    h1I.classList.remove(arrowDown);
                    h1I.classList.add(arrowUp);
                }else{
                    h1I.classList.remove(arrowUp);
                    h1I.classList.add(arrowDown);
                }
            });
        }
    }
    
    
    
    
    //OCULTAR TABLA SECUNDARIA (GRUPO)
    const show = document.querySelector("#show");
    const tabla = document.querySelector(".t");
    const tablita = document.querySelector(".tablita");
    
    if(show){
        show.addEventListener('change', function(){
            tabla.classList.toggle("hide");
            tablita.classList.toggle("hide");
        });
    }




    // BUSCAR
    const buscar = document.querySelector('#buscar');
    const category = document.querySelector('#category');
    const reset = document.querySelector('#reset');
    const tableHeadRow = document.querySelector('#table01 thead tr');

    function filter(value){
        let texto = value;
        texto = texto.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
        let contenido = RegExp(texto, 'i');
        let filas = document.querySelectorAll("#table01 tbody tr");

        for(i = 0; i < filas.length; i++){
            let f = filas[i];
            let filtro = f.textContent.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
            filtro.search(contenido) == '-1' ? f.style = 'display: none;' : f.style = '';
        }
    }
    function filterSelect(value, index){
        let texto = value;
        texto = texto.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
        let contenido = RegExp(texto, 'i');
        let filas = document.querySelectorAll("#table01 tbody tr");

        for(i = 0; i < filas.length; i++){
            let f = filas[i].cells[index];
            let filtro = f.textContent.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
            filtro.search(contenido) == '-1' ? filas[i].style = 'display: none;' : filas[i].style = '';
        }
    }

    if(buscar){
        buscar.addEventListener('input', function(){
            filter(buscar.value);
        });

        buscar.parentElement.addEventListener('submit', function(e){
            e.preventDefault();
        });
    }
    if(category){
        let index = -1;
        for(i = 0; i < tableHeadRow.cells.length; i++){ //identifica columna del select
            let aux = tableHeadRow.cells[i];
            if(aux.textContent === category.getAttribute('title')){
                if(index == -1){
                    index = i;
                }
            }
        }
        if(index != -1 && category.value != 0){
            filterSelect(category.value, index);
        }else{
            filter('');
        }
        category.addEventListener('change', function(){
            if(index != -1 && category.value != 0){
                filterSelect(category.value, index);
            }else{
                filter('');
            }
        });
    }
    if(reset){
        reset.parentElement.addEventListener('reset', function(){
            filter('');
        });
    }

    // OBSERVACIONES
    const observaciones = document.querySelectorAll('.observaciones .textarea');
    const cant_observaciones = document.querySelector('.cant_observaciones');
    const icon = document.querySelector('.cant_observaciones .fas');
    let toggle = 2;

    if(cant_observaciones){
        cant_observaciones.addEventListener('click', function(){
            if(toggle%2 == 0){
                icon.classList.remove('fa-plus');
                icon.classList.add('fa-minus');
                cant_observaciones.innerText = ' Mostrar menos';
                cant_observaciones.prepend(icon);
            }else{            
                icon.classList.remove('fa-minus');
                icon.classList.add('fa-plus');
                cant_observaciones.textContent = ' Mostrar todas';
                cant_observaciones.prepend(icon);
            }
            toggle++;
            if(cant_observaciones){
                for(let i=3; i<observaciones.length; i++){
                    observaciones[i].classList.toggle('hide');
                }
            }
        });
    }
}