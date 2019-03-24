package com.example.owner.jbookwatcher.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.owner.jbookwatcher.Book;

import java.util.ArrayList;

public class BookListAdapter extends ArrayAdapter<Book> {
    private ArrayList<Book> bookList;

    public BookListAdapter(Context context, ArrayList<Book> bookList){

        super(context, 0, bookList);
        this.bookList = bookList;
    }
/*
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_entry, parent, false);
        }
        Book currBook = getItem(position);

        TextView bookNameTV = listItemView.findViewById(R.id.collapsed_book_name);
        bookNameTV.setText(currBook.getBookTitle());

        TextView pageNumTV = listItemView.findViewById(R.id.collapsed_page_num);
        if(currBook.getPageNum() != -1) {
            pageNumTV.setText(currBook.getPageNum() + " Pages");
        }

        TextView authorTV = listItemView.findViewById(R.id.collapsed_book_author);
        if(currBook.getAuthor() != null){
            authorTV.setText(currBook.getAuthor());
        }

        TextView startDateTV = listItemView.findViewById(R.id.collapsed_book_start_date);
        if(currBook.getStartDate() != null){
            String[] sDate = currBook.getStartDate().split("/");
            startDateTV.setText(sDate[0] + "/" + sDate[1] + "/" + sDate[2].substring(2));
        }

        TextView endDateTV = listItemView.findViewById(R.id.collapsed_book_end_date);
        if(currBook.getEndDate() != null){
            String[] fDate = currBook.getEndDate().split("/");
            endDateTV.setText(fDate[0] + "/" + fDate[1] + "/" + fDate[2].substring(2));
        }

        return listItemView;
    }

    //method for retrieving the saved project list when resuming/creating activity
    public ArrayList<Book> getBookList(){
        return bookList;
    }*/
}
