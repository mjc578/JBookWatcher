package com.example.owner.jbookwatcher;

public class Book {

    private String bookTitle;
    private String author = null;
    private int pageNum = 0;
    private String startDate = null;
    private String endDate = null;

    public Book(String bookTitle){
        this.bookTitle = bookTitle;
    }

    public String getBookTitle(){
        return bookTitle;
    }

    public void setBookTitle(String bookTitle){
        this.bookTitle = bookTitle;
    }

    public int getPageNum(){
        return pageNum;
    }

    public void setPageNum(int pageNum){
        this.pageNum = pageNum;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getStartDate(){
        return startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }
}
