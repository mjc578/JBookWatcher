package com.example.owner.jbookwatcher.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.R;

import java.util.ArrayList;

public class BookSearchAdapter extends ArrayAdapter<Book> {

    public BookSearchAdapter(Context context, ArrayList<Book> aBooks) {
        super(context, 0, aBooks);
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.book_list_entry, parent, false);
        }
        final Book book = getItem(position);
        // Populate the data into the template view using the data object
        TextView tvTitle = convertView.findViewById(R.id.search_tv_title);
        TextView tvAuthor = convertView.findViewById(R.id.search_tv_author);
        ImageView ivCover = convertView.findViewById(R.id.search_book_cover);
        tvTitle.setText(book.getBookTitle());
        tvAuthor.setText(book.getAuthor());
        Glide.with(convertView.getContext()).load(book.getCoverUrl()).error(R.drawable.no_cover_avail).into(ivCover);
        // Return the completed view to render on screen
        return convertView;
    }
}
