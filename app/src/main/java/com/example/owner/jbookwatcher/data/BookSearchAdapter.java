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
    private static class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
    }

    public BookSearchAdapter(Context context, ArrayList<Book> aBooks) {
        super(context, 0, aBooks);
    }

    // Translates a particular `Book` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_list_entry, parent, false);
            viewHolder.ivCover = convertView.findViewById(R.id.entryBookCover);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor = convertView.findViewById(R.id.tvAuthor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.tvTitle.setText(book.getBookTitle());
        viewHolder.tvAuthor.setText(book.getAuthor());
        Glide.with(convertView.getContext()).load(book.getCoverUrl()).error(R.drawable.no_cover_avail).into(viewHolder.ivCover);
        // Return the completed view to render on screen
        return convertView;
    }
}
