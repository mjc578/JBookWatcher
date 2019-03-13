package com.example.owner.jbookwatcher;

public class Book {

    private String bookTitle;
    private String author;
    private int pageNum;
    private String startDate;
    private String endDate;

    public Book(String bookTitle){
        this.bookTitle = bookTitle;
    }

    public String getBookTitle(){
        return bookTitle;
    }

    
}
