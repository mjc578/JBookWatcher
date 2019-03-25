package com.example.owner.jbookwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK_DETAIL_KEY = "book";
    ImageView ivBookCover;
    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookPages;
    TextView tvBookPubYear;
    TextView tvAlreadyInList;
    Button bAddToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivBookCover = findViewById(R.id.iv_book_detail);
        tvBookTitle = findViewById(R.id.tv_book_detail_title);
        tvBookAuthor = findViewById(R.id.tv_book_detail_author);
        tvBookPages = findViewById(R.id.tv_book_detail_pages);
        tvBookPubYear = findViewById(R.id.tv_book_detail_publish_year);
        tvAlreadyInList = findViewById(R.id.tv_book_detail_already_in_list);
        bAddToList = findViewById(R.id.button_book_detail_add);

        Book b = (Book) getIntent().getSerializableExtra(BOOK_DETAIL_KEY);

        //set the activity layout features
        Glide.with(this).load(b.getLargeCoverUrl()).error(R.drawable.no_cover_avail).into(ivBookCover);
        tvBookTitle.setText(b.getBookTitle());
        tvBookAuthor.setText(b.getAuthor());
    }
}
