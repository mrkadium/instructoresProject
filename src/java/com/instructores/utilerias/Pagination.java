package com.instructores.utilerias;

import javax.servlet.http.HttpServletRequest;

public class Pagination {
    private int total_records;
    private int page_records;
    private int total_pages;
    private int current_page;
    private int current_upper_limit;
    private int current_lower_limit;

    public Pagination() {
    }

    public Pagination(int total_records, int page_records, HttpServletRequest req) {
        this.total_records = total_records;
        this.page_records = page_records;
        this.current_page = req.getParameter("pag_number") != null ? Integer.parseInt(req.getParameter("pag_number")) : 1;        
    }
    
    public int getCurrentPage(){
        return current_page;
    }
    
    public int getTotalPages(){
        if(total_records >= page_records){
            total_pages = (total_records % page_records)  > 0 ? (total_records/page_records) + 1 : total_records/page_records;
        }else{
            total_pages = 1;
        }
        return total_pages;
    }
    
    public int getNextPage(){
        return current_page+1 > total_pages ? current_page : current_page+1;
    }
    
    public int getPrevPage(){
        return current_page-1 == 0 ? current_page : current_page-1;
    }
    
    public int getCurrentUpperLimit(){
        current_upper_limit = total_records >= page_records ? current_page * page_records : total_records;
        current_upper_limit = current_upper_limit > total_records ? total_records : current_upper_limit;
        return current_upper_limit;
    }
    
    public int getCurrentLowerLimit(){
        current_lower_limit = total_records >= page_records ? (current_page * page_records)-(page_records-1) : 1;
        return current_lower_limit;
    }
    
    public int getTotalRecords(){
        return total_records;
    }
    
}
