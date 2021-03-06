package com.example.owner.jbookwatcher;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    private String bookTitle;
    private String olid;
    private String author;
    private String startDate;
    private String endDate;
    private int listIndicator;
    private int pageCount;

    public Book(){}

    public Book(String olid, String bookTitle, String author, String startDate, String endDate, int listIndicator, int pageCount){
        this.olid = olid;
        this.bookTitle = bookTitle;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
        this.listIndicator = listIndicator;
        this.pageCount = pageCount;
    }

    public String getBookTitle(){
        return bookTitle;
    }

    public String getOLID(){
        return olid;
    }

    public String getAuthor(){
        return author;
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

    public int getPageNum(){
        return pageCount;
    }

    public void setPageNum(int PageNum){
        this.pageCount = PageNum;
    }

    // Get medium sized book cover from covers API
    public String getCoverUrl() {
        return "https://covers.openlibrary.org/b/olid/" + olid + "-S.jpg?default=false";
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "https://covers.openlibrary.org/b/olid/" + olid + "-L.jpg?default=false";
    }

    // Returns a Book given the expected JSON
    private static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            // Deserialize json into object fields
            // get isbn (edition key for safety net)
            if (jsonObject.has("cover_edition_key"))  {
                book.olid = jsonObject.getString("cover_edition_key");
            } else if(jsonObject.has("edition_key")) {
                final JSONArray ids = jsonObject.getJSONArray("edition_key");
                book.olid = ids.getString(0);
            }
            book.bookTitle = jsonObject.has("title_suggest") ? jsonObject.getString("title_suggest") : "";
            book.author = getAuthor(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }

    // Return comma separated author list when there is more than one author
    private static String getAuthor(final JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray("author_name");
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }

    // Decodes array of book json results into business model objects
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
