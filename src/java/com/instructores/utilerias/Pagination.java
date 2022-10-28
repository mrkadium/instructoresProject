package com.instructores.utilerias;

import javax.servlet.http.HttpServletRequest;

public class Pagination {
    private int total_records;
    private int records_per_page;
    private int total_pages;
    private int current_page;
    private int current_upper_limit;
    private int current_lower_limit;
    private String context;

    public Pagination() {
    }

    public Pagination(int total_records, int page_records, HttpServletRequest req) {
        this.total_records = total_records;
        this.records_per_page = page_records;
        this.current_page = req.getParameter("pag_number") != null ? Integer.parseInt(req.getParameter("pag_number")) : 1;        
    }
    
    public Pagination(int total_records, int records_per_page, int current_page, String context){
        this.total_records = total_records;
        this.records_per_page = records_per_page;
        this.current_page = current_page;
        this.context = context;
    }
    
    public int getCurrentPage(){
        return current_page;
    }
    
    public int getFirstPage(){
        return 1;
    }
    
    public int getLastPage(){
        return getTotalPages();
    }
    
    public int getTotalPages(){
        if(total_records >= records_per_page){
            total_pages = (total_records % records_per_page)  > 0 ? (total_records/records_per_page) + 1 : total_records/records_per_page;
        }else{
            total_pages = 1;
        }
        return total_pages;
    }
    
    public int getNextPage(){
        return current_page+1 > total_pages ? current_page : current_page+1;
    }
    
    public int getPrevPage(){
        return current_page-1 <= 0 ? current_page : current_page-1;
    }
    
    public int getCurrentUpperLimit(){
        current_upper_limit = total_records >= records_per_page ? current_page * records_per_page : total_records;
        current_upper_limit = current_upper_limit > total_records ? total_records : current_upper_limit;
        return current_upper_limit;
    }
    
    public int getCurrentLowerLimit(){
        current_lower_limit = total_records >= records_per_page ? (current_page * records_per_page)-(records_per_page-1) : 1;
        return current_lower_limit;
    }
    
    public int getTotalRecords(){
        return total_records;
    }
    
    public String getPageButtons(){
        int pageButtons = 0;
        int lowerLimit = 0;
        int upperLimit = 0;
        
        if(this.current_page - 2 <= 0){
            lowerLimit = 1;
            upperLimit = lowerLimit + 4 > getTotalPages() ? getTotalPages() : lowerLimit + 4;
        }else if(this.current_page + 2 > getTotalPages()){
            upperLimit = getTotalPages();
            lowerLimit = upperLimit - 4 < 1 ? 1 : upperLimit - 4;
        }else{
            lowerLimit = this.current_page - 2;
            upperLimit = this.current_page + 2;
        }
        
        String html = "";
        for(int i=lowerLimit; i<=upperLimit; i++){
            if(i == this.current_page)
                html += "<a href='javascript:void(0);' class='active' onclick='gotoPage(event, "+i+")'>"+i+"</a>";
            else
                html += "<a href='javascript:void(0);' onclick='gotoPage(event, "+i+")'>"+i+"</a>";
        }
        
        return html;
    }
    
    public String paginationElements(){
        String html = "<div class='pagination-container'>";        
        html += "<p>Mostrando "+getCurrentLowerLimit()+" - "+getCurrentUpperLimit()+" de "+getTotalRecords()+"</p>";        
        html += "<div class='pagination'>";        
            html += "<div class='pagination-buttons'>";
                html += "<a href='javascript:void(0);' onclick='gotoPage(event, "+getFirstPage()+")'>Primera</a>\n" +
"                        <a href='javascript:void(0);' onclick='gotoPage(event, "+getPrevPage()+")' class=\"pagination-btn\"><i class=\"icon icon-arrow-left\"></i></a>";
            html += "</div>";
            html += "<div class='pagination-numbers'>";
                html += getPageButtons();
            html += "</div>";
            html += "<div class='pagination-buttons'>";
                html += "<a href='javascript:void(0);' onclick='gotoPage(event, "+getNextPage()+")' class=\"pagination-btn\"><i class=\"icon icon-arrow-right\"></i></a>\n"
                        + "<a href='javascript:void(0);' onclick='gotoPage(event, "+getLastPage()+")'>Última</a>";
            html += "</div>";
        html += "</div>";
        html += "<div class=\"pagination-options\">\n"
                    + "<div>" +
    "                    <label for=\"gotoPage\">Ir a página: </label>\n" +
    "                    <input id='inputPageNumber' type=\"number\" min=\"1\" max='"+getLastPage()+"'>\n" +
    "                    <button class='btn' onclick='selectPage();'>Ir</button>\n"
                    + "</div>"
                    + "<div>" +
    "                    <label for=\"shownRecords\">Registros mostrados: </label>\n" +
    "                    <select name='shownRecords'>\n" +
    "                        <option value='12' "+(records_per_page == 12 ? "selected" : "")+">12</option>\n" +
    "                        <option value='24' "+(records_per_page == 24 ? "selected" : "")+">24</option>\n" +
    "                        <option value='50' "+(records_per_page == 50 ? "selected" : "")+">50</option>\n" +
    "                        <option value='100' "+(records_per_page == 100 ? "selected" : "")+">100</option>\n" +
    "                    </select>\n"
                    + "</div>" +
"                </div>";
        html += "</div>";
        
        return html;
    }
    
}

