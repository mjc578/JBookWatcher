package com.example.owner.jbookwatcher.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFrag extends Fragment {

    public static final String BOOK_DETAIL_KEY = "book";
    ImageView ivBookCover;
    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookPages;
    TextView tvBookPubYear;
    TextView tvAlreadyInList;
    Button bAddToList;

    public BookDetailFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bdfView = inflater.inflate(R.layout.activity_book_detail, container, false);

        ivBookCover = bdfView.findViewById(R.id.iv_book_detail);
        tvBookTitle = bdfView.findViewById(R.id.tv_book_detail_title);
        tvBookAuthor = bdfView.findViewById(R.id.tv_book_detail_author);
        tvBookPages = bdfView.findViewById(R.id.tv_book_detail_pages);
        tvBookPubYear = bdfView.findViewById(R.id.tv_book_detail_publish_year);
        tvAlreadyInList = bdfView.findViewById(R.id.tv_book_detail_already_in_list);
        bAddToList = bdfView.findViewById(R.id.button_book_detail_add);

        Book b = (Book) getArguments().get(BOOK_DETAIL_KEY);
        Glide.with(bdfView).load(b.getLargeCoverUrl()).error(R.drawable.no_cover_avail).into(ivBookCover);
        tvBookAuthor.setText(b.getBookTitle());
        tvBookAuthor.setText(b.getAuthor());

        //TODO:: I would like to replace button with text saying book is in list already if true
        //TODO: here you would check if its in database already and set visibilities appropriately

        return bdfView;
    }

}
