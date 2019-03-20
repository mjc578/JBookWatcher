package com.example.owner.jbookwatcher;

public class Book {

    private String bookTitle;
    private int listIndicator;
    private String author = null;
    private int pageNum = -1;
    private String startDate = null;
    private String endDate = null;

    //basic constructor
    public Book(String bookTitle, int tableIndicator){
        this.bookTitle = bookTitle;
    }

    //full constructor
    public Book(String bookTitle, String author, int pageNum, String startDate, String endDate, int listIndicator){
        this.bookTitle = bookTitle;
        this.author = author;
        this.pageNum = pageNum;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getListIndicator(){
        return listIndicator;
    }

    public void setListIndicator(int listIndicator){
        this.listIndicator = listIndicator;
    }
}
