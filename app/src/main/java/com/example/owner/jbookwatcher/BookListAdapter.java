package com.example.owner.jbookwatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListAdapter extends ArrayAdapter<Book> {

    private ArrayList<Book> bookList;

    public BookListAdapter(Context context, ArrayList<Book> bookList){
        super(context, 0, bookList);
        this.bookList = bookList;
    }

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
        pageNumTV.setText(currBook.getPageNum() + "");


        return listItemView;
    }
}
