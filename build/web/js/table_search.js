// DOM ELEMENTS
const tableHeadRow = document.querySelector('#table01 thead tr');
const tableRows = document.querySelectorAll('#table01 tbody tr');
const resetFilters = document.querySelector('#reset');


// FUNCTIONS
function filter(text){ //Takes the special characters away
    return text.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi,"$1$2").normalize();
}

function findCol(heading){ //Returns the index of the column searched
    for(i = 0; i < tableHeadRow.cells.length; i++){
        if(tableHeadRow.cells[i].textContent == heading){
            return i;
        }       
    }
}

function filterHiddenRows(){ //Returs an array with the rows that don't have the class .hide
    let list = [];
    for(i = 0; i < tableRows.length; i++){
        let current_row = tableRows[i];
        if(!current_row.classList.contains('hide'))
            list.push(current_row);
    }
    return list;
}

function hideRows(element, name, isCol = true){ //Hides every row on wich the cell's text doesn't match the searched text
    let value = element.value;
     
    value = filter(value);

    let content = RegExp(value, 'i');

    let column = findCol(name);

    let rows = isCol ? filterHiddenRows() : tableRows;

    for(i = 0; i < rows.length; i++){
        let current = isCol ? rows[i].cells[column] : rows[i]; /* Selects the current row cell or row (if is the search field) */
        
        let filteredText = filter(current.textContent); /* filters the cell text */

        /* Adds class .hide to every row on wich the selected cell contains the text */
        filteredText.search(content) == '-1' ? 
            rows[i].classList.add('hide') : rows[i].classList.remove('hide');
    }   
}

function showRows(){ //Shows every row
    for(i = 0; i < tableRows.length; i++){
        let current_row = tableRows[i];
        current_row.classList.remove('hide');
    }
}

resetFilters.addEventListener('click', (e) => {
    showRows();
});