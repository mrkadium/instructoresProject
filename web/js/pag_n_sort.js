//ELEMENTS
let page_name = '';
let table = document.querySelector('#table01');
let table_rows = Array.from(table.querySelectorAll('tbody tr:not(.hide)'));
const tableHeadRow = table.querySelector('thead tr');
const filters = document.querySelectorAll('.filter-field'); //all the filter fields (selects)
const searchInput = document.querySelector('#search'); //search field
const resetFilters = document.querySelector('#reset'); //reset filters

let page = 1; //global taking the current page number
const rows_to_show_element = document.querySelector('#rows-to-show');
let rows_to_show = rows_to_show_element.value; //const taking number of rows to show
let pages_numbers_container = document.querySelector(".pages__numbers"); //where the page numbers are
const tbody = table.tBodies[0]; //tbody of the table
const total_row_count = tbody.querySelectorAll("tr").length; //row count
let pages = Math.ceil(total_row_count / rows_to_show); //page count according to rows

let current_page = document.querySelector(".current-page"); //text showing current page
let total_pages = document.querySelector(".total-pages"); //text showing current page
const page_count = document.querySelector(".page_count"); //text showing total pages
const row_range = document.querySelector(".row_range"); //text showing range of rows showing
let rows_bottom_limit = 0;
let rows_top_limit = 0;
let total_rows = table.querySelectorAll('tbody tr:not(.hide)').length;
let rows_bottom_limit_element = document.querySelector('.rows-bottom-limit');
let rows_top_limit_element = document.querySelector('.rows-top-limit');
const total_rows_element = document.querySelector(".total-rows"); //text showing total rows

//CALLS

//PAGINATION FUNCTIONS
function paginate(){
    table_rows = Array.from(table.querySelectorAll('tbody tr:not(.hide)')); //all the rows not hidden by filter
    rows_top_limit = rows_to_show * page > table_rows.length ? table_rows.length : rows_to_show * page;
    rows_bottom_limit = (rows_to_show * (page - 1)) + 1;
    
    table_rows.forEach(row => {
        row.classList.add('pagination-hide');
    });
    
    for(let i=rows_bottom_limit; i<=rows_top_limit; i++){
        table_rows[i-1].classList.remove('pagination-hide');
    }

    updateNumbers();
}
function gotoPage(page_number){
    page = page_number; //set the page to the parameter
    paginate(); //paginates with the page selected
}
function firstPage(){
    page = 1;
    paginate();
}
function prevPage(){
    //if it's the first page, don't sub, else sub
    page = page-1 < 1 ? 1 : page-1;
    paginate();
}
function nextPage(){
    //if it's in the last page, don't sum, else sum
    page = page+1 > pages ? pages : page+1;
    paginate();
}
function lastPage(){
    page = pages;
    paginate();
}
function updateNumbers(){
    //top panel
    current_page.textContent = page;
    total_pages.textContent = pages;

    //bottom panel
    rows_bottom_limit_element.textContent = rows_bottom_limit;
    rows_top_limit_element.textContent = rows_top_limit;
    total_rows_element.textContent = total_rows;

    updatePageLinks();
}
function updatePageLinks(){
    let links = '';
    for(i=0; i<pages; i++){
        links += `<a onclick="gotoPage(${i+1})" href="javascript:void(0)" class="${i+1 == page ? "current" : ""}">${i+1}</a>`;
    }
    pages_numbers_container.innerHTML = links;
}


//FILTER FUNCTIONS
function removeSpecialCharacters(text) { //Takes the special characters of the text away
    return text.normalize('NFD').replace(/([aeio])\u0301|(u)[\u0301\u0308]/gi, "$1$2").normalize();
}
function filter(text = null){
    updateFilters();

    //map the values from the filters and verify if it's not 0
    let values = Array.from(filters)
        .map(filter => removeSpecialCharacters(filter.value.toLowerCase()))
        .filter(filterValue => filterValue !== '0');

    //filter the values adding the search bar value and verify if it's not empty
    if(text != null) values.push(removeSpecialCharacters(text.toLowerCase()));
    values = values.filter(filterValue => filterValue !== '');
    
    table_rows = Array.from(table.querySelectorAll('tbody tr')); //all the rows
    table_rows.forEach(row => { //for each row
        let row_text = removeSpecialCharacters(row.textContent.toLowerCase()); //text of the row without special characters and lower-cased
        
        let valid = true; //initialize the valid
        values.forEach(textToFilter => {
            valid = valid && (row_text.search(textToFilter) == -1 ? false : true); //compare with prev          
        });

        if(!valid){ //if it's true, the row contains all the filter values
            row.classList.add('hide'); //hide that row
        }else{ 
            row.classList.remove('hide'); //remove the class, in case the row already had it
            row.classList.remove('pagination-hide'); //also, if the pagination is hiding the row, show it
        }
    });

    reset();
    paginate(); //paginate at the end to show all the rows containing the text searched
}
function reset(){
    page = 1; //reset to page 1
    pages = Math.ceil(table.querySelectorAll('tbody tr:not(.hide)').length / rows_to_show);
    total_rows = table.querySelectorAll('tbody tr:not(.hide)').length;
}
function showRows(){
    table_rows = Array.from(table.querySelectorAll('tbody tr'));
    table_rows.forEach(row => {
        row.classList.remove('hide');
        row.classList.remove('pagination-hide');
    });

    reset();
    paginate();
}
function cleanFilters(){
    searchInput.value = '';
    filters.forEach(f => {
        f.value = 0;
    })
}

//LOCAL STORAGE
function setFilters(){
    let filterArray = localStorage.getItem('filterArray');
    if(filterArray != null){
        filterArray = JSON.parse(filterArray);
        let pageFilter = filterArray.filters.filter(f => f.page === page_name);
        pageFilter[0].values.forEach(f => {
            document.getElementById(f.id).value = f.value;
        })
    }
}
function setPage(page){
    page_name = page;
    setFilters();
    updateFilters();
    paginate();
    filter();
}
function updateFilters(){
    //create the new filter object
    let filterObject = {page: page_name};
    filterObject['values'] = Array.from(filters).map(filter => {
        let id = filter.id;
        let value = filter.value;
        let obj = {'id': id, 'value': value};
        return obj;
    });
    
    //get items from localStorage
    let filterArray = localStorage.getItem('filterArray');

    if(filterArray == null){ //if there is no filter yet 
        filterArray = {}; //create the global obj
        filterArray['filters'] = []; //create the array in the global obj
        filterArray.filters.push(filterObject); //add the filter obj to the global obj
    }else{
        filterArray = JSON.parse(filterArray); //parse the global obj
        let filters = filterArray.filters.filter(f => f.page !== page_name); //get all the filters but the one I'm updating
        filters.push(filterObject); //add the filter obj to the gotten filters
        filterArray['filters'] = filters; //replace the filters in the global obj for the new ones
    }

    localStorage.setItem('filterArray', JSON.stringify(filterArray));
}

//EVENTS
rows_to_show_element.addEventListener('change', function(){
    rows_to_show = this.value;
    reset();
    paginate();
});
searchInput.addEventListener('input', function(){
    filter(this.value);
});
filters.forEach(f => {
    f.addEventListener('change', function(){
        filter();
    });
});
resetFilters.addEventListener('click', (e) => {
    showRows();
    cleanFilters();
    updateFilters();
});